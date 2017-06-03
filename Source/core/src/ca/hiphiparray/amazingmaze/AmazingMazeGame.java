/********************************************************************************
 * Amazing Maze is an educational game created in Java with the libGDX library. Copyright (C) 2017 Hip Hip Array
 *
 * This file is part of Amazing Maze.
 *
 * Amazing Maze is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Amazing Maze is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main game class. Manages program flow.
 *
 * @author Vincent Macri
 * @author Chloe Nguyen
 * @since 0.1
 * <br>
 * Time (Chloe): 15 mins
 */
public class AmazingMazeGame extends Game {

	/** The SpriteBatch to use for drawing. */
	protected SpriteBatch batch;
	/** The {@link Assets} instance used for loading assets into and from. */
	protected Assets assets;
	/** Manages the game's music. */
	protected MusicManager music;

	/** The main menu screen. */
	protected MainMenuScreen menuScreen;
	/** The story screen. */
	protected StoryScreen storyScreen;
	/** The settings screen */
	protected SettingsScreen settingsScreen;
	/** The high scores screen. */
	protected HighScoresScreen highScoresScreen;

	/** The settings for the game. */
	protected Save set; // TODO: Rename to save.

	@Override
	public void create() {
		set = new Save();
		batch = new SpriteBatch();
		assets = new Assets();
		music = new MusicManager(this);

		settingsScreen = new SettingsScreen(this);
		menuScreen = new MainMenuScreen(this);
		storyScreen = new StoryScreen(this);

		settingsScreen.setSourceScreen(menuScreen);

		this.setScreen(menuScreen);
		// setScreen(new HighScoresScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		super.dispose();
	}
}
