package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AmazingMazeGame extends Game {

	/** The SpriteBatch to use for drawing. */
	protected SpriteBatch batch;
	/** The {@link Assets} instance used for loading assets into and from. */
	protected Assets assets;

	/** The main menu screen. */
	protected MainMenuScreen menuScreen;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new Assets();

		menuScreen = new MainMenuScreen(this);
		this.setScreen(menuScreen);
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
