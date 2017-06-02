package ca.hiphiparray.amazingmaze;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * This class saves the game state.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 * @since 0.2
 * Time (Chloe): 2 hours
 */
public class Save {

	/** The save preferences. */
	private Preferences gameSave;
	/** The high scores preferences. */
	private Preferences gameScores;
	/** The settings preferences. */
	private Preferences gameSettings;

	/** The name of the settings file. */
	private static final String SETTINGS_FILE = "ca.hiphiparray.amazingmaze.settings";
	/** The name of the save file. */
	private static final String SAVE_FILE = "ca.hiphiparray.amazingmaze.save";
	/** The name of the high scores file. */
	private static final String SCORES_FILE = "ca.hiphiparray.amazingmaze.highscores";

	/** The number of high score entries. */
	private static final int MAX_HIGH_SCORES = 10;

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

	/** The level the player is currently on. */
	private int level;

	/** The array of high scores. */
	private HighScore[] highScores;

	/** Create the Save instance. */
	public Save() {

		gameSettings = Gdx.app.getPreferences(SETTINGS_FILE);
		gameSave = Gdx.app.getPreferences(SAVE_FILE);
		gameScores = Gdx.app.getPreferences(SCORES_FILE);

		readSettings();
		// resetSettings();
		// writeSave();
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

	/** Reset the save state. */
	public void resetSave() {
		this.level = 1;
	}

	/** Reset the settings and the save state. */
	public void resetAll() {
		resetSettings();
		resetSave();
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

	/** Load settings from file. */
	public void readSettings() {
		upButton = gameSettings.getInteger("upButton", Keys.UP);
		downButton = gameSettings.getInteger("downButton", Keys.DOWN);
		leftButton = gameSettings.getInteger("leftButton", Keys.LEFT);
		rightButton = gameSettings.getInteger("rightButton", Keys.RIGHT);
		musicLevel = gameSettings.getFloat("musicLevel", 1f);
	}

	/**
	 * Write all {@link Preferences} instances.
	 *
	 * These are: {@link #gameSave}, {@link #gameScores}, and {@link #gameSettings}.
	 */
	public void writeSave() {
		FileHandle f = Gdx.files.local(SAVE_FILE);

		Json json = new Json();
		json.setUsePrototypes(false);

		f.writeString(json.prettyPrint(json.toJson(this)), false);
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

	/** Reset the high scores to 10 default values. */
	public void resetHighScores() {
		highScores = new HighScore[MAX_HIGH_SCORES];
		for (int i = 0; i < highScores.length; i++) {
			highScores[i] = new HighScore();
		}
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
}
