package me.taylorkelly.mywarp.commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import me.taylorkelly.mywarp.MyWarp;
import me.taylorkelly.mywarp.data.Warp;
import me.taylorkelly.mywarp.dataconnections.DataConnection;
import me.taylorkelly.mywarp.dataconnections.MySQLConnection;
import me.taylorkelly.mywarp.dataconnections.SQLiteConnection;
import me.taylorkelly.mywarp.economy.Fee;
import me.taylorkelly.mywarp.utils.CommandUtils;
import me.taylorkelly.mywarp.utils.commands.Command;
import me.taylorkelly.mywarp.utils.commands.CommandContext;
import me.taylorkelly.mywarp.utils.commands.CommandException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * This class contains all commands that cover admin tasks. They should be
 * included in the <code>mywarp.admin.*</code> permission container.
 */
public class AdminCommands {

    @Command(aliases = { "import" }, usage = "<sqlite/mysql>", desc = "commands.import.description", min = 1, max = 1, flags = "f", permissions = { "mywarp.admin.import" })
    public void importWarps(final CommandContext args, final CommandSender sender) throws CommandException {
        
        final Holder<DataConnection> connectionHolder = new Holder<DataConnection>();

        // create the data connection
        ListenableFuture<DataConnection> connectionFuture;
        if (args.getString(0).equalsIgnoreCase("mysql")) {
            connectionFuture = MySQLConnection.establish(false, true);
        } else if (args.getString(0).equalsIgnoreCase("sqlite")) {
            connectionFuture = SQLiteConnection.establish(false, true);
        } else {
            throw new CommandException(MyWarp.inst().getLocalizationManager()
                    .getString("commands.import.invalid-option", sender, args.getString(0)));
        }

        // chain the warp-loading - executed async!
        ListenableFuture<Map<String, Warp>> warpsFuture = Futures.chain(connectionFuture,
                new Function<DataConnection, ListenableFuture<Map<String, Warp>>>() {

                    @Override
                    public ListenableFuture<Map<String, Warp>> apply(DataConnection connection) {
                        connectionHolder.setValue(connection);
                        return connection.getMap();
                    }

                });

        // import the warps when they are ready - executed async!
        Futures.addCallback(warpsFuture, new FutureCallback<Map<String, Warp>>() {

            @Override
            public void onFailure(final Throwable t) {
                if (connectionHolder.getValue() != null) {
                    connectionHolder.getValue().close();
                    connectionHolder.setValue(null);
                }
                // back to the main thread...
                MyWarp.server().getScheduler().runTask(MyWarp.inst(), new Runnable() {

                    @Override
                    public void run() {
                        sender.sendMessage(MyWarp.inst().getLocalizationManager()
                                .getString("commands.import.no-connection", sender)
                                + " " + t.getMessage());
                    }

                });
            }

            @Override
            public void onSuccess(final Map<String, Warp> warps) {
                if (connectionHolder.getValue() != null) {
                    connectionHolder.getValue().close();
                    connectionHolder.setValue(null);
                }
                // back to the main thread...
                MyWarp.server().getScheduler().runTask(MyWarp.inst(), new Runnable() {

                    @Override
                    public void run() {
                        int counter = 0;
                        Iterator<Entry<String, Warp>> it = warps.entrySet().iterator();

                        while (it.hasNext()) {
                            Entry<String, Warp> entry = it.next();
                            String name = entry.getKey();
                            Warp importedWarp = entry.getValue();

                            if (MyWarp.inst().getWarpManager().warpExists(name)) {
                                if (!args.hasFlag('f')) {
                                    continue;
                                }
                                // remove the old warp before adding the
                                // new one
                                MyWarp.inst().getWarpManager()
                                        .deleteWarp(MyWarp.inst().getWarpManager().getWarp(name));
                            }
                            MyWarp.inst().getWarpManager().addWarp(name, importedWarp);
                            counter++;

                            it.remove();
                        }

                        if (warps.isEmpty()) {
                            sender.sendMessage(MyWarp.inst().getLocalizationManager()
                                    .getString("commands.import.import-successful", sender, counter));
                        } else {
                            sender.sendMessage(MyWarp
                                    .inst()
                                    .getLocalizationManager()
                                    .getString("commands.import.import-with-skips", sender, counter,
                                            warps.size())
                                    + " " + CommandUtils.joinWarps(warps.values()));
                        }
                    }

                });
            }

        });
    }

    @Command(aliases = { "reload" }, usage = "", desc = "commands.reload.description", max = 0, permissions = { "mywarp.admin.reload" })
    public void reload(CommandContext args, CommandSender sender) throws CommandException {
        MyWarp.inst().reload();
        sender.sendMessage(MyWarp.inst().getLocalizationManager()
                .getString("commands.reload.reload-message", sender));
    }

    @Command(aliases = { "player" }, usage = "<player> <name>", desc = "commands.warp-player.description", fee = Fee.WARP_PLAYER, min = 2, permissions = { "mywarp.admin.warpto" })
    public void warpPlayer(CommandContext args, CommandSender sender) throws CommandException {
        Player invitee = CommandUtils.matchPlayer(sender, args.getString(0));
        Warp warp = CommandUtils.getWarpForUsage(sender, args.getJoinedStrings(1));

        warp.warp(invitee, false);
        sender.sendMessage(MyWarp
                .inst()
                .getLocalizationManager()
                .getString("commands.warp-player.teleport-successful", sender, invitee.getName(),
                        warp.getName()));
    }

    /**
     * This class can hold objects that need to passed to inner classes and, by
     * doing so, provide mutable access to the object.
     * 
     * @param <T>
     *            the type to hold
     */
    private final class Holder<T> {
        private T value;

        /**
         * Gets the stored value. May return null.
         * 
         * @return the stored value
         */
        public T getValue() {
            return value;
        }

        /**
         * Sets the value to store
         * 
         * @param value
         *            the value
         */
        public void setValue(T value) {
            this.value = value;
        }
    }
}
