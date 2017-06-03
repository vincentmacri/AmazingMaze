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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.hiphiparray.amazingmaze.MusicManager.Song;

/**
 * The menu screen for the game.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 * @since 0.1
 * <br>
 * Time (Chloe): 6 hours
 */
public class MainMenuScreen implements Screen {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** Stage that contains all of the main menu options. */
	private Stage menu;
	/** The Table container for the main menu buttons. */
	private Table table;

	/** Play button. */
	private TextButton playButton;
	/** Help button. */
	private TextButton helpButton;
	/** Settings button. */
	private TextButton settingsButton;
	/** High scores button. */
	private TextButton highScoresButton;
	/** Credits button. */
	private TextButton creditsButton;
	/** License button. */
	private TextButton licenseButton;
	/** Quit button. */
	private TextButton quitButton;

	/** The copyright notice dialog. */
	private Dialog licenseDialog;

	/** Title of menu. */
	private Image menuTitle;

	/**
	 * Creates the main menu.
	 *
	 * @param game The instance for the AmazingMazeGame used.
	 */
	public MainMenuScreen(final AmazingMazeGame game) {
		this.game = game;
		menu = new Stage(new ScreenViewport(), this.game.batch);

		table = new Table();

		table.setFillParent(true);
		table.bottom();
		menu.addActor(table);

		menuTitle = new Image(this.game.assets.manager.get(Assets.GAME_LOGO, Texture.class));

		// Play
		playButton = new TextButton("Play", game.assets.skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (playButton.isPressed()) {
					game.setScreen(game.storyScreen);
				}
			}
		});

		// Help
		helpButton = new TextButton("Help", game.assets.skin);
		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (helpButton.isPressed()) {
					game.setScreen(new HelpScreen(game));
				}
			}
		});

		// Settings
		settingsButton = new TextButton("Settings", game.assets.skin);
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (settingsButton.isPressed()) {
					game.setScreen(game.settingsScreen);
				}
			}
		});

		// Credits
		creditsButton = new TextButton("Credits", game.assets.skin);
		creditsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (creditsButton.isPressed()) {
					game.setScreen(new CreditsScreen(game));
				}
			}
		});

		licenseDialog = new Dialog("License Information", game.assets.skin);
		licenseDialog.button("Okay");

		licenseDialog.text(
			"Amazing Maze is an educational game created in Java with the libGDX library.\n"
				+ "Copyright (C) 2017 Hip Hip Array\n"
				+ "\n"
				+ "Amazing Maze is free software: you can redistribute it and/or modify\n"
				+ "it under the terms of the GNU General Public License as published by\n"
				+ "the Free Software Foundation, either version 3 of the License, or\n"
				+ "(at your option) any later version.\n"
				+ "\n"
				+ "Amazing Maze is distributed in the hope that it will be useful,\n"
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n"
				+ "GNU General Public License for more details.\n"
				+ "\n"
				+ "You should have received a copy of the GNU General Public License\n"
				+ "along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.",
			game.assets.skin.get(Assets.WHITE_SANS_STYLE, LabelStyle.class));

		// License
		licenseButton = new TextButton("License", game.assets.skin);
		licenseButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (licenseButton.isPressed()) {
					licenseDialog.show(menu);
				}
			}
		});

		// High scores
		highScoresButton = new TextButton("High Scores", game.assets.skin);
		highScoresButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (highScoresButton.isPressed()) {
					game.setScreen(game.highScoresScreen);
				}
			}
		});

		// Quit
		quitButton = new TextButton("Quit", game.assets.skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (quitButton.isPressed()) {
					game.save.writeScores();
					game.save.writeSettings();
					Gdx.app.exit();
				}
			}
		});
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(menu);
		Gdx.input.setCursorCatched(false);
		game.music.setSong(Song.MENU);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		menu.act(Gdx.graphics.getDeltaTime());
		menu.draw();
	}

	@Override
	public void resize(int width, int height) {
		menu.getViewport().update(width, height, true);
		layoutMenu(width, height);
	}

	/**
	 * Adds buttons and the title as well as set layout for the menu.
	 *
	 * @param width The width of the screen.
	 * @param height The height of the screen.
	 */
	private void layoutMenu(int width, int height) {
		table.clear();

		table.background(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.MENU_BACKGROUND_IMAGE, Texture.class))));

		// Add title
		table.add(menuTitle).expand();
		table.row();

		// Add buttons.
		table.add(playButton).minSize(width / 4, height / 22).maxSize(width, height / 6).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(helpButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(highScoresButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(settingsButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(creditsButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(licenseButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
		table.row();
		table.add(quitButton).minSize(width / 4, height / 22).maxSize(width, height / 8).prefSize(width / 2.5f, height / 10).padBottom(10);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		menu.dispose();
	}

}
