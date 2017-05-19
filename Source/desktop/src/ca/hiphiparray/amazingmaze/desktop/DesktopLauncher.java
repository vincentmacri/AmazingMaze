/********************************************************************************
 * Amazing Maze is an educational game created in Java with the libGDX library.
 * Copyright (C) 2017 Hip Hip Array
 *
 * This file is part of Amazing Maze.
 *
 * Amazing Maze is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazing Maze is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.hiphiparray.amazingmaze.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ca.hiphiparray.amazingmaze.AmazingMazeGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.setFromDisplayMode(displayMode);
		config.title = "Amazing Maze";
		config.vSyncEnabled = true;
		config.foregroundFPS = 0;

		new LwjglApplication(new AmazingMazeGame(), config);
	}
}
