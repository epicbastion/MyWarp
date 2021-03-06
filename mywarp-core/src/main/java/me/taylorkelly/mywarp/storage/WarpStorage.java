/*
 * Copyright (C) 2011 - 2015, MyWarp team and contributors
 *
 * This file is part of MyWarp.
 *
 * MyWarp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyWarp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyWarp. If not, see <http://www.gnu.org/licenses/>.
 */

package me.taylorkelly.mywarp.storage;

import me.taylorkelly.mywarp.util.profile.Profile;
import me.taylorkelly.mywarp.warp.Warp;

import java.util.List;

/**
 * A connection to a data storage, e.g. a rational database.
 */
public interface WarpStorage extends WarpSource {

  /**
   * Adds the given {@code Warp} to the underlying data storage.
   *
   * @param warp the {@code Warp} to add
   */
  void addWarp(Warp warp);

  /**
   * Removes the given {@code Warp} from the underlying data storage.
   *
   * @param warp the {@code Warp} to remove
   */
  void removeWarp(Warp warp);

  /**
   * Gets a {@code List} with every {@code Warp} stored in the underlying data storage.
   *
   * @return all stored {@code Warp}s
   */
  @Override
  List<Warp> getWarps();

  /**
   * Adds the given {@code groupId} to the list of invited groupIds for the given {@code Warp}.
   *
   * @param warp    the {@code Warp}
   * @param groupId the identifier of the group to add
   */
  void inviteGroup(Warp warp, String groupId);

  /**
   * Adds the given {@code Profile} to the invited profiles for the given {@code Warp}.
   *
   * @param warp          the {@code Warp}
   * @param playerProfile the {@code Profile} to add
   */
  void invitePlayer(Warp warp, Profile playerProfile);

  /**
   * Removes the given {@code groupId} from the list of invited groupIds for the given {@code Warp}.
   *
   * @param warp    the {@code Warp}
   * @param groupId the identifier of the group to remove
   */
  void uninviteGroup(Warp warp, String groupId);

  /**
   * Removes the given {@code Profile} from the invited player-profiles for the given {@code Warp}.
   *
   * @param warp          the {@code Warp}
   * @param playerProfile the {@code Profile} to remove
   */
  void uninvitePlayer(Warp warp, Profile playerProfile);

  /**
   * Updates the creator of the given {@code Warp}.
   *
   * @param warp the {@code Warp} to update
   */
  void updateCreator(Warp warp);

  /**
   * Updates the location of the given {@code Warp}.
   *
   * @param warp the {@code Warp} to update
   */
  void updateLocation(Warp warp);

  /**
   * Updates the type of the given {@code Warp}.
   *
   * @param warp the {@code Warp} to update
   */
  void updateType(Warp warp);

  /**
   * Updates the visits of the given {@code Warp}.
   *
   * @param warp the {@code Warp} to update
   */
  void updateVisits(Warp warp);

  /**
   * Update the welcome-message of the given {@code Warp}.
   *
   * @param warp the {@code Warp} to update
   */
  void updateWelcomeMessage(Warp warp);

}
