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

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

/**
 * Manages the game's music.
 *
 * @since 0.2
 * @author Vincent Macri
 * <br>
 * Time (Vincent): 10 minutes
 */
public class MusicManager {

	/** The songs in the game. */
	public enum Song {
		/** The menu song. */
		MENU,
		/** The story song. */
		STORY,
		/** The maze song. */
		MAZE,
		/** The math song. */
		MATH,
		/** The credits song. */
		CREDITS,
	}

	/** The music that plays on the menu. */
	private Music menuMusic;
	/** The music that plays on the story screen. */
	private Music storyMusic;
	/** The music that plays on the math screen. */
	private Music mathMusic;
	/** The music that plays in the maze. */
	private Music mazeMusic;
	/** The music that plays during the credits. */
	private Music creditsMusic;

	/** The current game volume. */
	private float volume;

	/**
	 * Create a new {@link MusicManager}.
	 *
	 * @param game the {@link AmazingMazeGame} managing this object.
	 */
	public MusicManager(AmazingMazeGame game) {
		menuMusic = game.assets.manager.get(Assets.MENU_SONG, Music.class);
		storyMusic = game.assets.manager.get(Assets.STORY_SONG, Music.class);
		mathMusic = game.assets.manager.get(Assets.MATH_SONG, Music.class);
		mazeMusic = game.assets.manager.get(Assets.MAZE_SONG, Music.class);
		creditsMusic = game.assets.manager.get(Assets.CREDITS_SONG, Music.class);

		menuMusic.setLooping(true);
		// Story music intentionally not looping.
		mazeMusic.setLooping(true);
		mathMusic.setLooping(true);
		creditsMusic.setLooping(true);

		setVolume(game.save.getMusicLevel());
	}

	/**
	 * Change the playing music.
	 *
	 * @param song the song to change to.
	 */
	public void setSong(Song song) {
		switch (song) {
			case MENU:
				storyMusic.stop();
				mazeMusic.stop();
				mathMusic.stop();
				creditsMusic.stop();
				menuMusic.play();
				break;
			case STORY:
				menuMusic.stop();
				mazeMusic.stop();
				mathMusic.stop();
				creditsMusic.stop();
				storyMusic.play();
				break;
			case MAZE:
				menuMusic.stop();
				storyMusic.stop();
				mathMusic.stop();
				creditsMusic.stop();
				mazeMusic.play();
				break;
			case MATH:
				menuMusic.stop();
				storyMusic.stop();
				mazeMusic.stop();
				creditsMusic.stop();
				mathMusic.play();
				break;
			case CREDITS:
				menuMusic.stop();
				storyMusic.stop();
				mazeMusic.stop();
				mathMusic.stop();
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
		volume = MathUtils.clamp(value, 0, 1);
		menuMusic.setVolume(volume);
		storyMusic.setVolume(volume);
		mazeMusic.setVolume(volume);
		mathMusic.setVolume(volume);
		creditsMusic.setVolume(volume);
	}

	/**
	 * Get the music volume.
	 *
	 * @return the current value of {@link #volume}.
	 */
	public float getVolume() {
		return volume;
	}

}
