package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * This class saves the game state.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 */
public class Save {

	/** The name of the save file. */
	private static final String SAVE_FILE = "save.json";

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

	/** If the game is in fullscreen mode. */
	private boolean fullscreen;

	/** The level the player is currently on. */
	private int level;

	/** The array of high scores. */
	private HighScore[] highScores;

	/** Default constructor to be used by JSON parser. */
	public Save() {
	}

	public Save(boolean readFromFile) {
		if (readFromFile) {
			readSettings();
		} else {
			resetSettings();
			writeSettings();
		}
	}

	/**
	 * Reset all of the settings to the default values.
	 */
	public void resetSettings() {
		this.upButton = Keys.UP;
		this.rightButton = Keys.RIGHT;
		this.leftButton = Keys.LEFT;
		this.downButton = Keys.DOWN;
		this.pauseButton = Keys.ESCAPE;

		this.musicLevel = 1f;
		this.fullscreen = true;

		this.level = 1;
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
	 * If the game's settings are set to fullscreen mode.
	 *
	 * @return if the game should be in fullscreen mode.
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Sets whether the game should be in fullscreen or not.
	 *
	 * @param fullscreen the new value of fullscreen.
	 */
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	/**
	 * Reads from file for the values for the settings. If they're valid, use them, if not, reset the settings.
	 */
	public void readSettings() {
		FileHandle f = Gdx.files.local(SAVE_FILE);
		Json json = new Json();
		Save savedSettings;
		try {
			savedSettings = json.fromJson(Save.class, f);
		} catch (Exception e) {
			System.out.println("Invalid settings.");
			resetSettings();
			writeSettings();
			return;
		}

		upButton = savedSettings.upButton;
		rightButton = savedSettings.rightButton;
		leftButton = savedSettings.leftButton;
		downButton = savedSettings.downButton;
		pauseButton = savedSettings.pauseButton;

		musicLevel = savedSettings.musicLevel;
		fullscreen = savedSettings.fullscreen;

		level = savedSettings.level;
	}

	/**
	 * Write settings to Settings.json.
	 */
	public void writeSettings() {
		FileHandle f = Gdx.files.local(SAVE_FILE);

		Json json = new Json();
		json.setUsePrototypes(false);

		f.writeString(json.prettyPrint(json.toJson(this)), false);
	}

	/**
	 * Get the current level.
	 *
	 * @return the current level.
	 */
	public int getLevel() {
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
}
