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

package me.taylorkelly.mywarp.bukkit.util.parametric.binding;

import com.sk89q.intake.parametric.argument.ArgumentStack;
import com.sk89q.intake.parametric.binding.BindingBehavior;
import com.sk89q.intake.parametric.binding.BindingHelper;
import com.sk89q.intake.parametric.binding.BindingMatch;

import me.taylorkelly.mywarp.Actor;

/**
 * A binding that gets {@link Actor}s from the {@link com.sk89q.intake.context.CommandLocals}s.
 */
public class ActorBindung extends BindingHelper {

  /**
   * Gets an Actor from the CommandLocales.
   *
   * @param context the command's context
   * @return the Actor
   * @throws IllegalArgumentException if CommandLocals does not contain a mapping for the {@link Actor} class
   */
  @BindingMatch(type = Actor.class, behavior = BindingBehavior.PROVIDES)
  public Actor getActor(ArgumentStack context) {
    Actor actor = context.getContext().getLocals().get(Actor.class);

    if (actor == null) {
      throw new IllegalArgumentException(
          "No Actor available. Either this command was not used by one or he is missing from the CommandLocales.");
    }
    return actor;
  }
}
