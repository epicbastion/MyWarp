package me.taylorkelly.mywarp.dataconnections;

import java.util.Map;

import com.google.common.util.concurrent.ListenableFuture;

import me.taylorkelly.mywarp.data.Warp;

/**
 * This interface defines all usable methods for data-connections used by
 * MyWarp. Implementations are expected to be threadsafe!
 */
public interface DataConnection {

    /**
     * Closes all pending database connections
     */
    public void close();

    /**
     * Loads all warps from the database and returns them as map. Each warp is
     * stored under their name.
     * 
     * @return a map with all warps
     */
    public ListenableFuture<Map<String, Warp>> getMap();

    /**
     * Adds the warp to the database
     * 
     * @param warp
     *            the warp
     */
    public void addWarp(Warp warp);

    /**
     * Deletes the warp from the database
     * 
     * @param warp
     *            the warp
     */
    public void deleteWarp(Warp warp);

    /**
     * Publicizes or privatizes a warp in the database.
     * 
     * @param warp
     *            the warp
     */
    public void publicizeWarp(Warp warp);

    /**
     * Updates a warp's creator in the database
     * 
     * @param warp
     *            the warp
     */
    public void updateCreator(Warp warp);

    /**
     * Updates the location of the given warp in the database
     * 
     * @param warp
     *            the warp
     */
    public void updateLocation(Warp warp);

    /**
     * Updates the permissions (list of invited players) of the given warp in
     * the database
     * 
     * @param warp
     *            the warp
     */
    public void updatePermissions(Warp warp);

    /**
     * Updates the group-permissions (list of invited groups) of the given warp
     * in the database
     * 
     * @param warp
     *            the warp
     */
    public void updateGroupPermissions(Warp warp);

    /**
     * Updates the visits of the given warp in the database
     * 
     * @param warp
     *            the warp
     */
    public void updateVisits(Warp warp);

    /**
     * Updates the welcome message of the given warp in the database
     * 
     * @param warp
     *            the warp
     */
    public void updateWelcomeMessage(Warp warp);
}
