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

package me.taylorkelly.mywarp.timer;

/**
 * An action that is executed after a timer finishes.
 *
 * @param <T> the type of the subject the timer runs on
 */
public abstract class TimerAction<T> implements Runnable {

  private final T timedSuject;

  /**
   * Creates an instance.
   *
   * @param timedSubject the instance the timer runs on
   */
  public TimerAction(T timedSubject) {
    this.timedSuject = timedSubject;
  }

  /**
   * Gets the instance the timer runs on.
   *
   * @return the instance
   */
  public T getTimedSuject() {
    return timedSuject;
  }

}
