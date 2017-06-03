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

import java.util.Arrays;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;

/**
 * This class saves the game state.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 * @since 0.2
 * <br>
 * Time (Chloe): 2 hours
 * <br>
 * Time (Vincent): 3 hours
 * @since 0.2
 */
public class Save {

	/** The high scores preferences. */
	private Preferences gameScores;
	/** The settings preferences. */
	private Preferences gameSettings;

	/** The name of the settings file. */
	private static final String SETTINGS_FILE = "ca.hiphiparray.amazingmaze.settings";
	/** The name of the high scores file. */
	private static final String SCORES_FILE = "ca.hiphiparray.amazingmaze.highscores";

	/** The number of high score entries. */
	private static final int MAX_HIGH_SCORES = 10;

	/** The offset of high scores from names in the save file. */
	private static final int HIGH_SCORES_OFFSET = 100;

	/** For when the user moves up. */
	private int upButton;
	/** For when the user moves right. */
	private int rightButton;
	/** For when the user moves left. */
	private int leftButton;
	/** For when the user moves down. */
	private int downButton;
	/** The button for when the player pauses the game. */
	private int pauseButton;
	/** The volume of the game music, in the range [0, 1]. */
	private float musicLevel;

	/** The name of the up button setting. */
	private static final String UP_SETTING = "upButton";
	/** The name of the down button setting. */
	private static final String DOWN_SETTING = "downButton";
	/** The name of the left button setting. */
	private static final String LEFT_SETTING = "leftButton";
	/** The name of the right button setting. */
	private static final String RIGHT_SETTING = "rightButton";
	/** The name of the pause button setting. */
	private static final String PAUSE_SETTING = "pauseButton";
	/** The name of the music level setting. */
	private static final String MUSIC_SETTING = "musicLevel";

	/** The level the player is currently on. */
	private int level;
	/** The score the player currently has. */
	private int score;
	/** The score the player had at the start of this set of levels. */
	private int startScore;
	/** How many lives the player currently has left. */
	private int lives;

	/** The array of high scores. */
	private HighScore[] highScores;

	/** Create the Save instance. */
	public Save() {
		gameSettings = Gdx.app.getPreferences(SETTINGS_FILE);
		gameScores = Gdx.app.getPreferences(SCORES_FILE);

		resetSave();
		loadSettings();
		loadScores();
	}

	/** Load settings from file. */
	public void loadSettings() {
		upButton = gameSettings.getInteger(UP_SETTING, Keys.UP);
		downButton = gameSettings.getInteger(DOWN_SETTING, Keys.DOWN);
		leftButton = gameSettings.getInteger(LEFT_SETTING, Keys.LEFT);
		rightButton = gameSettings.getInteger(RIGHT_SETTING, Keys.RIGHT);
		pauseButton = gameSettings.getInteger(PAUSE_SETTING, Keys.ESCAPE);
		musicLevel = gameSettings.getFloat(MUSIC_SETTING, 1f);
		writeSettings();
	}

	/** Load scores from file. */
	public void loadScores() {
		resetScores();
		Map<String, ?> scores = gameScores.get();

		for (int i = 0; i < MAX_HIGH_SCORES; i++) {
			Object nameObj = scores.get(Integer.toString(i));
			Object scoreObj = scores.get(Integer.toString(i + HIGH_SCORES_OFFSET));
			if (nameObj == null || scoreObj == null) {
				System.out.println("Nonexistant or corrupt high scores file.");
				resetScores();
				break;
			} else {
				try {
					String name = (String) nameObj;
					int score = Integer.parseInt((String) scoreObj);
					addHighScore(new HighScore(name, score));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Nonexistant or corrupt high scores file.");
					resetScores();
					break;
				}
			}
		}
		writeScores();
	}

	/** Write the high scores file. */
	public void writeScores() {
		gameScores.clear();
		for (int i = 0; i < MAX_HIGH_SCORES; i++) {
			gameScores.putString(Integer.toString(i), highScores[i].getName());
			gameScores.putInteger(Integer.toString(i + HIGH_SCORES_OFFSET), highScores[i].getScore());
		}
		gameScores.flush();
	}

	/** Write the settings file. */
	public void writeSettings() {
		gameSettings.clear();
		gameSettings.putInteger(UP_SETTING, getUpButton());
		gameSettings.putInteger(DOWN_SETTING, getDownButton());
		gameSettings.putInteger(LEFT_SETTING, getLeftButton());
		gameSettings.putInteger(RIGHT_SETTING, getRightButton());
		gameSettings.putInteger(PAUSE_SETTING, getPauseButton());
		gameSettings.putFloat(MUSIC_SETTING, getMusicLevel());
		gameSettings.flush();
	}

	/** Reset the save state. */
	public void resetSave() {
		this.level = 1;
		this.score = 0;
		this.lives = 3;
		this.startScore = 0;
	}

	/** Reset all of the settings to the default values. */
	public void resetSettings() {
		this.upButton = Keys.UP;
		this.rightButton = Keys.RIGHT;
		this.leftButton = Keys.LEFT;
		this.downButton = Keys.DOWN;
		this.pauseButton = Keys.ESCAPE;

		this.musicLevel = 1f;
	}

	/** Reset the high scores to 10 default values. */
	public void resetScores() {
		highScores = new HighScore[MAX_HIGH_SCORES];
		for (int i = 0; i < highScores.length; i++) {
			highScores[i] = new HighScore();
		}
	}

	/** Reset the settings and the save state. */
	public void resetAll() {
		resetSave();
		resetScores();
		resetSettings();
	}

	/**
	 * Returns upButton.
	 *
	 * @return upButton
	 */
	public int getUpButton() {
		return upButton;
	}

	/**
	 * Sets upButton.
	 *
	 * @param upButton The new key for upButton.
	 */
	public void setUpButton(int upButton) {
		this.upButton = upButton;
	}

	/**
	 * Returns rightButton.
	 *
	 * @return rightButton
	 */
	public int getRightButton() {
		return rightButton;
	}

	/**
	 * Sets key for rightButton.
	 *
	 * @param rightButton The new key for rightButton.
	 */
	public void setRightButton(int rightButton) {
		this.rightButton = rightButton;
	}

	/**
	 * Returns leftButton.
	 *
	 * @return leftButton
	 */
	public int getLeftButton() {
		return leftButton;
	}

	/**
	 * Sets value for leftButton.
	 *
	 * @param leftButton The new key for leftButton.
	 */
	public void setLeftButton(int leftButton) {
		this.leftButton = leftButton;
	}

	/**
	 * Returns downButton.
	 *
	 * @return downButton
	 */
	public int getDownButton() {
		return downButton;
	}

	/**
	 * Sets value for downButton.
	 *
	 * @param downButton The new key for downButton.
	 */
	public void setDownButton(int downButton) {
		this.downButton = downButton;
	}

	/**
	 * Get the keycode for the pause button.
	 *
	 * @return the pause button keycode.
	 */
	public int getPauseButton() {
		return pauseButton;
	}

	/**
	 * Set the keycode for the pause button.
	 *
	 * @param pauseButton the new keycode for the pause button.
	 */
	public void setPauseButton(int pauseButton) {
		this.pauseButton = pauseButton;
	}

	/**
	 * Gets the musicLevel.
	 *
	 * @return musicLevel
	 */
	public float getMusicLevel() {
		return musicLevel;
	}

	/**
	 * Sets the music level.
	 *
	 * @param musicLevel The new value of musicLevel.
	 */
	public void setMusicLevel(float musicLevel) {
		this.musicLevel = musicLevel;
	}

	/**
	 * Get the current level.
	 * Also ensure that the level is >= 1, if not, set it to 1.
	 *
	 * @return the current level.
	 */
	public int getLevel() {
		if (level >= 1) {
			return level;
		}
		setLevel(1);
		return level;
	}

	/**
	 * Set the current level.
	 *
	 * @param level the level to be set to.
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Getter for {@link #highScores}.
	 *
	 * @return the high scores array.
	 */
	public HighScore[] getHighScores() {
		return highScores;
	}

	/**
	 * Add the given high score to its appropriate position in {@link #highScores}.
	 * The high score is not added if it is not high enough to be in the top 10.
	 * As part of processing, this method will sort {@link #highScores} into descending order.
	 *
	 * @param score the high score to add.
	 */
	public void addHighScore(HighScore score) {
		HighScore[] newScores = new HighScore[highScores.length + 1];

		int i;
		for (i = 0; i < highScores.length; i++) {
			newScores[i] = highScores[i];
		}
		newScores[i] = score;

		Arrays.sort(newScores);
		for (i = 0; i < highScores.length; i++) {
			highScores[i] = newScores[newScores.length - 1 - i];
		}
	}

	/**
	 * Getter for {@link #score}.
	 *
	 * @return the player's current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Setter for {@link #score}.
	 *
	 * @param score the new score of the player.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Increase the player's score by the given amount.
	 *
	 * @param deltaScore how much to increase {@link #score} by.
	 */
	public void addScore(int deltaScore) {
		score += deltaScore;
	}

	/**
	 * Getter for {@link #lives}.
	 *
	 * @return how many lives the player has left.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Setter for {@link #lives}.
	 *
	 * @param lives how many lives the player now has.
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Getter for {@link #startScore}.
	 *
	 * @return the score the player had at the start of the set of levels.
	 */
	public int getStartScore() {
		return startScore;
	}

	/**
	 * Setter for {@link #startScore}.
	 *
	 * @param startScore the new value of {@link #startScore}.
	 */
	public void setStartScore(int startScore) {
		this.startScore = startScore;
	}
}
