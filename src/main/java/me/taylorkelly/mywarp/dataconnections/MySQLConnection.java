package me.taylorkelly.mywarp.dataconnections;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import me.taylorkelly.mywarp.MyWarp;
import me.taylorkelly.mywarp.data.Warp;

/**
 * A {@link DataConnection} that stores warps via an external MySQL-Server
 */
public class MySQLConnection implements DataConnection {

    /**
     * Username.
     */
    private final String user;

    /**
     * Password.
     */
    private final String password;

    /**
     * Table.
     */
    private final String tableName;

    /**
     * DSN.
     */
    private final String dsn;

    /**
     * Database connection.
     */
    private Connection conn;

    /**
     * The executor service that runs all tasks
     */
    private final ListeningExecutorService executor;

    /**
     * Gets an active connection with the underling MySQL database using default
     * values. The returned connection is completely setup, updated and ready to
     * use.
     * 
     * @see #establish(String, int, String, String, String, String, boolean,
     *      boolean) ;
     * 
     * @param createIfNotExist
     *            whether the table should be created, if it does not exist
     * @param updateIfNecessary
     *            whether the database scheme should be updated, if needed
     * @return a {@link CheckedFuture} containing the DataConnection
     */
    public static CheckedFuture<DataConnection, DataConnectionException> establish(
            final boolean createIfNotExist, final boolean updateIfNecessary) {
        return establish(MyWarp.inst().getWarpSettings().mysqlHost,
                MyWarp.inst().getWarpSettings().mysqlPort, MyWarp.inst().getWarpSettings().mysqlDatabase,
                MyWarp.inst().getWarpSettings().mysqlUsername, MyWarp.inst().getWarpSettings().mysqlPassword,
                MyWarp.inst().getWarpSettings().mysqlTable, createIfNotExist, updateIfNecessary);
    }

    /**
     * Gets an active connection with the underling MySQL database. The returned
     * connection is completely setup, updated and ready to use.
     * 
     * @param host
     *            the host of the MySQL-Server
     * @param port
     *            the port the MySQL-Server is listening to
     * @param database
     *            the name of the MySQL database
     * @param user
     *            the user-name
     * @param password
     *            the password of the user
     * @param tableName
     *            the name of the table
     * @param createIfNotExist
     *            whether the table should be created, if it does not exist
     * @param updateIfNecessary
     *            whether the database scheme should be updated, if needed
     * @return a {@link CheckedFuture} containing the DataConnection
     */
    public static CheckedFuture<DataConnection, DataConnectionException> establish(final String host,
            final int port, final String database, final String user, final String password,
            final String tableName, final boolean createIfNotExist, final boolean updateIfNecessary) {
        final ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors
                .newSingleThreadExecutor());

        ListenableFuture<DataConnection> futureConnection = executor.submit(new Callable<DataConnection>() {

            @Override
            public DataConnection call() throws Exception {
                return new MySQLConnection(host, port, database, user, password, tableName, createIfNotExist,
                        updateIfNecessary, executor);
            }

        });
        return Futures.makeChecked(futureConnection, new Function<Exception, DataConnectionException>() {

            @Override
            public DataConnectionException apply(Exception ex) {
                if (ex instanceof DataConnectionException) {
                    return (DataConnectionException) ex;
                }
                if (ex instanceof ExecutionException && ex.getCause() instanceof DataConnectionException) {
                    return (DataConnectionException) ex.getCause();
                }
                return new DataConnectionException(ex);
            }

        });
    }

    /**
     * Initializes this MySQLConnection, using the given arguments to setup and
     * verify the connection.
     * {@link #establish(String, int, String, String, String, String, boolean, boolean)}
     * must be used to create a MySQLConnection from outside.
     * 
     * @param host
     *            the host of the MySQL-Server
     * @param port
     *            the port the MySQL-Server is listening to
     * @param database
     *            the name of the MySQL database
     * @param user
     *            the user-name
     * @param password
     *            the password of the user
     * @param tableName
     *            the name of the table
     * @param createIfNotExist
     *            whether the table should be created, if it does not exist
     * @param updateIfNecessary
     *            whether the database scheme should be updated, if needed
     * @param executor
     *            the listening executor service that executes all tasks in this
     *            connection
     * @throws DataConnectionException
     *             if (1) the SQlite driver is not present in the classpath, (2)
     *             {@link #checkDB(File, boolean)} fails and (3)
     *             {@link #updateDB(boolean)} fails.
     */
    private MySQLConnection(String host, int port, String database, String user, String password,
            String tableName, boolean createIfNotExist, boolean updateIfNecessary,
            ListeningExecutorService executor) throws DataConnectionException {
        dsn = "jdbc:mysql://" + host + ":" + port + "/" + database;

        this.user = user;
        this.password = password;
        this.tableName = tableName;
        this.executor = executor;

        checkDB(createIfNotExist);
        updateDB(updateIfNecessary);
    }

    /**
     * Establishes the first connection with the database, thus creating all
     * needed tables if they do not exist.
     * 
     * @param createIfNotExist
     *            whether tables should be created if they do not exist
     * @throws DataConnectionException
     *             if <code>createIfNotExist</code> is set to false and
     *             database-file or table do not exist or if the connection
     *             false
     */
    private void checkDB(boolean createIfNotExist) throws DataConnectionException {
        Statement stmnt = null;

        try {
            conn = getConnection();
            DatabaseMetaData dbm = conn.getMetaData();
            stmnt = conn.createStatement();

            if (!JDBCUtil.tableExists(dbm, tableName)) {
                if (createIfNotExist) {
                    stmnt.execute("CREATE TABLE `" + tableName + "` ("
                            + "`id` INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                            + "`name` varchar(32) NOT NULL DEFAULT 'warp',"
                            + "`creator` varchar(32) NOT NULL DEFAULT 'Player',"
                            + "`world` varchar(32) NOT NULL DEFAULT '0',"
                            + "`x` DOUBLE NOT NULL DEFAULT '0'," + "`y` smallint NOT NULL DEFAULT '0',"
                            + "`z` DOUBLE NOT NULL DEFAULT '0'," + "`yaw` smallint NOT NULL DEFAULT '0',"
                            + "`pitch` smallint NOT NULL DEFAULT '0',"
                            + "`publicAll` boolean NOT NULL DEFAULT '1'," + "`permissions` text NOT NULL,"
                            + "`groupPermissions` text NOT NULL,"
                            + "`welcomeMessage` varchar(100) NOT NULL DEFAULT '',"
                            + "`visits` int DEFAULT '0'" + ");");
                } else {
                    executor.shutdown();
                    throw new DataConnectionException("Table '" + tableName + "' does not exist.");
                }
            }

        } catch (SQLException ex) {
            executor.shutdown();
            throw new DataConnectionException("Failed to check/create '" + tableName + "': " + ex, ex);
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
                }
            } catch (SQLException ex) {
                MyWarp.logger().log(Level.SEVERE, "Table Check Exception (on close): " + ex);
            }
        }
    }

    /**
     * Checks if the database schema needs any updates and executes them.
     * 
     * @param updateIfNecessary
     *            whether any needed updates should be executed
     * @throws DataConnectionException
     *             if <code>updateIfNecessary</code> is set to false and the
     *             table would need updates or if the connection false
     */
    private void updateDB(boolean updateIfNecessary) throws DataConnectionException {
        Statement stmnt = null;

        try {
            conn = getConnection();
            DatabaseMetaData dbm = conn.getMetaData();
            stmnt = conn.createStatement();

            // changes 'y' to smallint, changed with 2.4
            if (!JDBCUtil.columnIsDataType(dbm, tableName, "y", "smallint")) {
                if (updateIfNecessary) {
                    stmnt.execute("ALTER TABLE " + tableName + " MODIFY `y` smallint");
                } else {
                    executor.shutdown();
                    throw new DataConnectionException("Column 'y' has the wrong data type.");
                }
            }
            // groupPermissions, added with 2.4
            if (!JDBCUtil.columnExistsCaseSensitive(dbm, tableName, "groupPermissions")) {
                if (updateIfNecessary) {
                    stmnt.execute("ALTER TABLE " + tableName
                            + " ADD COLUMN `groupPermissions` text NOT NULL AFTER `permissions`");
                } else {
                    executor.shutdown();
                    throw new DataConnectionException("Column 'groupPermissions' does not exist.");
                }
            }
            // visits, added with 2.4
            if (!JDBCUtil.columnExistsCaseSensitive(dbm, tableName, "visits")) {
                if (updateIfNecessary) {
                    stmnt.execute("ALTER TABLE " + tableName + " ADD COLUMN `visits` int DEFAULT '0'");
                } else {
                    executor.shutdown();
                    throw new DataConnectionException("Column 'visits' does not exist.");
                }
            }

        } catch (SQLException ex) {
            executor.shutdown();
            throw new DataConnectionException("Failed to update the table scheme to the newest version: "+ ex, ex);
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
                }
            } catch (SQLException ex) {
                MyWarp.logger().log(Level.SEVERE, "Table Update Exception (on close): " + ex);
            }
        }
    }

    /**
     * Establishes a connection with the database
     * 
     * @return a valid connection to the database
     * @throws SQLException
     *             if a database access error occurs
     */
    private Connection getConnection() throws SQLException {
        if (conn != null && !conn.isValid(5)) {
            conn.close();
        }
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(dsn, user, password);
        }
        return conn;
    }

    @Override
    public void close() {
        executor.shutdown();
        // all tasks are completed, so the connection can be closed
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            MyWarp.logger().log(Level.SEVERE, "Unable to close SQL connection: " + ex);
        }
    }

    @Override
    public ListenableFuture<Map<String, Warp>> getMap() {
        return executor.submit(new Callable<Map<String, Warp>>() {

            @Override
            public Map<String, Warp> call() throws Exception {
                HashMap<String, Warp> ret = new HashMap<String, Warp>();
                Statement stmnt = null;
                ResultSet rsWarps = null;

                try {
                    conn = getConnection();
                    stmnt = conn.createStatement();

                    rsWarps = stmnt.executeQuery("SELECT * FROM " + tableName);
                    while (rsWarps.next()) {
                        int index = rsWarps.getInt("id");
                        String name = rsWarps.getString("name");
                        String creator = rsWarps.getString("creator");
                        String world = rsWarps.getString("world");
                        double x = rsWarps.getDouble("x");
                        int y = rsWarps.getInt("y");
                        double z = rsWarps.getDouble("z");
                        int yaw = rsWarps.getInt("yaw");
                        int pitch = rsWarps.getInt("pitch");
                        boolean publicAll = rsWarps.getBoolean("publicAll");
                        String permissions = rsWarps.getString("permissions");
                        String groupPermissions = rsWarps.getString("groupPermissions");
                        String welcomeMessage = rsWarps.getString("welcomeMessage");
                        int visits = rsWarps.getInt("visits");
                        Warp warp = new Warp(index, name, creator, world, x, y, z, yaw, pitch, publicAll,
                                permissions, groupPermissions, welcomeMessage, visits);
                        ret.put(name, warp);
                    }
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Load Exception: " + ex);
                } finally {
                    try {
                        if (rsWarps != null) {
                            rsWarps.close();
                        }
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Load Exception (on close): " + ex);
                    }
                }
                return ret;
            }
        });

    }

    @Override
    public void addWarp(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn
                            .prepareStatement("INSERT INTO "
                                    + tableName
                                    + " (id, name, creator, world, x, y, z, yaw, pitch, publicAll, permissions, groupPermissions, welcomeMessage, visits) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    stmnt.setInt(1, warp.getIndex());
                    stmnt.setString(2, warp.getName());
                    stmnt.setString(3, warp.getCreator());
                    stmnt.setString(4, warp.getWorld());
                    stmnt.setDouble(5, warp.getX());
                    stmnt.setInt(6, warp.getY());
                    stmnt.setDouble(7, warp.getZ());
                    stmnt.setInt(8, warp.getYaw());
                    stmnt.setInt(9, warp.getPitch());
                    stmnt.setBoolean(10, warp.isPublicAll());
                    stmnt.setString(11, warp.permissionsString());
                    stmnt.setString(12, warp.groupPermissionsString());
                    stmnt.setString(13, warp.getRawWelcomeMessage());
                    stmnt.setInt(14, warp.getVisits());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Insert Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Insert Exception (on close): ", ex);
                    }
                }
            }
        });

    }

    @Override
    public void deleteWarp(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
                    stmnt.setInt(1, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Delete Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Delete Exception (on close): ", ex);
                    }
                }
            }
        });
    }

    @Override
    public void publicizeWarp(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName + " SET publicAll = ? WHERE id = ?");
                    stmnt.setBoolean(1, warp.isPublicAll()); // true -> public,
                                                             // false -> private
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Publicize Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Publicize Exception (on close): ", ex);
                    }
                }
            }
        });

    }

    @Override
    public void updateCreator(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName + " SET creator = ? WHERE id = ?");
                    stmnt.setString(1, warp.getCreator());
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Creator Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Creator Exception (on close): ", ex);
                    }
                }
            }
        });

    }

    @Override
    public void updateLocation(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName
                            + " SET world = ?, x = ?, y = ?, Z = ?, yaw = ?, pitch = ? WHERE id = ?");
                    stmnt.setString(1, warp.getWorld());
                    stmnt.setDouble(2, warp.getX());
                    stmnt.setInt(3, warp.getY());
                    stmnt.setDouble(4, warp.getZ());
                    stmnt.setInt(5, warp.getYaw());
                    stmnt.setInt(6, warp.getPitch());
                    stmnt.setInt(7, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Location Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Location Exception (on close): ", ex);
                    }
                }
            }
        });
    }

    @Override
    public void updatePermissions(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn
                            .prepareStatement("UPDATE " + tableName + " SET permissions = ? WHERE id = ?");
                    stmnt.setString(1, warp.permissionsString());
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Permissions Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Permissions Exception (on close): ", ex);
                    }
                }
            }
        });

    }

    @Override
    public void updateGroupPermissions(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName
                            + " SET groupPermissions = ? WHERE id = ?");
                    stmnt.setString(1, warp.groupPermissionsString());
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp GroupPermissions Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp GroupPermissions Exception (on close): ", ex);
                    }
                }
            }
        });

    }

    @Override
    public void updateVisits(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName + " SET visits = ? WHERE id = ?");
                    stmnt.setInt(1, warp.getVisits());
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Visits Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Visits Exception (on close): ", ex);
                    }
                }
            }
        });
    }

    @Override
    public void updateWelcomeMessage(final Warp warp) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                PreparedStatement stmnt = null;

                try {
                    conn = getConnection();

                    stmnt = conn.prepareStatement("UPDATE " + tableName
                            + " SET welcomeMessage = ? WHERE id = ?");
                    stmnt.setString(1, warp.getRawWelcomeMessage());
                    stmnt.setInt(2, warp.getIndex());
                    stmnt.executeUpdate();

                } catch (SQLException ex) {
                    MyWarp.logger().log(Level.SEVERE, "Warp Welcome Exception: ", ex);
                } finally {
                    try {
                        if (stmnt != null) {
                            stmnt.close();
                        }
                    } catch (SQLException ex) {
                        MyWarp.logger().log(Level.SEVERE, "Warp Welcome Exception (on close): ", ex);
                    }
                }
            }
        });
    }

}
