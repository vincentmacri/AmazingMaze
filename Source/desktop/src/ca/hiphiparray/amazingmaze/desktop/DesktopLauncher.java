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

		new LwjglApplication(new AmazingMazeGame(), config);
	}
}
