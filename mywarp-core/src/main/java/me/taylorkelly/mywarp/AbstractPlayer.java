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

package me.taylorkelly.mywarp;

/**
 * An abstract implementation of {@link LocalPlayer} that provides platform independent functions.
 */
public abstract class AbstractPlayer implements LocalPlayer {

  @Override
  public boolean canAccessWorld(LocalWorld world) {
    if (getWorld().equals(world) && hasPermission("mywarp.warp.world.currentworld")) { // NON-NLS
      return true;
    }
    if (hasPermission("mywarp.warp.world." + world.getName())) { // NON-NLS
      return true;
    }
    return false;
  }

}
