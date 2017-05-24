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

package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

/**
 * Manages the game's music.
 *
 * @author Vincent Macri
 */
public class MusicManager {

	/** The songs in the game. */
	public enum Song {
		/** The menu song. */
		MENU,
		/** The maze song. */
		MAZE,
		/** The credits song. */
		CREDITS
	}

	/** The music that plays on the menu. */
	private Music menuMusic;
	/** The music that plays in the maze. */
	private Music mazeMusic;
	/** The music that plays during the credits. */
	private Music creditsMusic;

	/**
	 * Create a new {@link MusicManager}.
	 *
	 * @param assets the {@link AssetManager} to load music from.
	 */
	public MusicManager(Assets assets) {
		menuMusic = assets.manager.get(Assets.MENU_SONG, Music.class);
		mazeMusic = assets.manager.get(Assets.MAZE_SONG, Music.class);
		creditsMusic = assets.manager.get(Assets.CREDITS_SONG, Music.class);

		menuMusic.setLooping(true);
		mazeMusic.setLooping(true);
		creditsMusic.setLooping(true);
	}

	/**
	 * Change the playing music.
	 *
	 * @param song the song to change to.
	 */
	public void setSong(Song song) {
		switch (song) {
			case MENU:
				mazeMusic.stop();
				creditsMusic.stop();
				menuMusic.play();
				break;
			case MAZE:
				menuMusic.stop();
				creditsMusic.stop();
				mazeMusic.play();
				break;
			case CREDITS:
				menuMusic.stop();
				mazeMusic.stop();
				creditsMusic.play();
				break;
		}
	}

	/**
	 * Set the music volume.
	 * The volume level is clamped to be in the range [0, 1].
	 *
	 * @param level the new volume level.
	 */
	public void setVolume(float value) {
		value = MathUtils.clamp(value, 0, 1);
		menuMusic.setVolume(value);
		mazeMusic.setVolume(value);
		creditsMusic.setVolume(value);
	}

}
