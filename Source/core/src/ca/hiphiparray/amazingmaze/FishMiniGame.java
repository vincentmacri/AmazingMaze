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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.hiphiparray.amazingmaze.MusicManager.Song;

/**
 * The fish minigame.
 *
 * @author Susie Son
 * @author Vincent Macri
 */
public class FishMiniGame implements Screen, InputProcessor {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** Manages mouse input. */
	private InputMultiplexer input;

	/** Stage that contains all of the screen components. */
	private Stage stage;
	/** Table for the top menu buttons. */
	private Table menuTable;
	/** Table for the fish counting. */
	private Table fishTable;

	/** Pencil button. */
	private Button pencilButton;
	/** Eraser button. */
	private Button eraserButton;
	/** Help button. */
	private Button helpButton;
	/** Answer field. */
	private TextField answerField;
	/** Check answer button. */
	private Button checkButton;
	/** Clear canvas button. */
	private Button clearButton;

	/** Player's answer. */
	private int message;
	/** Answer value. */
	private int answer;
	/** If user's answers is within this range, they get 70% of the fish. */
	private final int range70 = 5;
	/** If user's answers is within this range, they get 50% of the fish. */
	private final int range50 = 15;

	/** Number of fish for each type. */
	private int[] fishNumber;
	/** Values of each type of fish. */
	private final int[] fishValue = {5, 10, 20, 50, 100};
	/** Fish images. */
	private Image[] fishImage;

	/** Custom class for Pixmap manipulation. */
	private Canvas canvas;
	/** Number of pixels the pixmap drawing should be shifted. */
	private final static int shift = -80;
	/** Saves last drawing point. */
	private Vector2 previous;
	/** Whether the mouse has been pressed and left down. */
	private boolean leftDown;

	/** The pencil colour. */
	private final static Color drawColor = Color.LIGHT_GRAY;
	/** The eraser colour. */
	private final static Color clearColor = Color.DARK_GRAY;

	/**
	 * Constructor for FishMiniGame.
	 *
	 * @param game the {@link AmazingMazeGame} instance that is managing this screen.
	 * @param blueCollected the number of blue fish.
	 * @param purpleCollected the number of purple fish.
	 * @param greenCollected the number of green fish.
	 * @param redCollected the number of red fish.
	 * @param orangeCollected the number of orange fish.
	 */
	public FishMiniGame(final AmazingMazeGame game, int blueCollected, int purpleCollected, int greenCollected, int redCollected, int orangeCollected) {
		// System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

		fishNumber = new int[5];

		fishNumber[0] = blueCollected;
		fishNumber[1] = purpleCollected;
		fishNumber[2] = greenCollected;
		fishNumber[3] = redCollected;
		fishNumber[4] = orangeCollected;

		answer = 0;
		for (int i = 0; i < fishNumber.length; i++) {
			answer += fishNumber[i] * fishValue[i];
		}

		this.game = game;

		stage = new Stage(new ScreenViewport(), this.game.batch);

		menuTable = new Table();
		fishTable = new Table();

		canvas = new Canvas(new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + shift, Pixmap.Format.RGB565));

		menuTable.setFillParent(true);
		menuTable.top();
		fishTable.bottom();
		fishTable.setFillParent(true);

		fishImage = new Image[5];

		TextureAtlas atlas = this.game.assets.manager.get(Assets.GAME_ATLAS_LOCATION, TextureAtlas.class);
		fishImage[0] = new Image(atlas.findRegion(Assets.FISH + Assets.BLUE_MODIFIER));
		fishImage[0].setScale(4f);
		fishImage[1] = new Image(atlas.findRegion(Assets.FISH + Assets.PURPLE_MODIFIER));
		fishImage[1].setScale(4f);
		fishImage[2] = new Image(atlas.findRegion(Assets.FISH + Assets.GREEN_MODIFIER));
		fishImage[2].setScale(4f);
		fishImage[3] = new Image(atlas.findRegion(Assets.FISH + Assets.RED_MODIFIER));
		fishImage[3].setScale(4f);
		fishImage[4] = new Image(atlas.findRegion(Assets.FISH + Assets.ORANGE_MODIFIER));
		fishImage[4].setScale(4f);

		pencilButton = new Button(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.PENCIL_BUTTON, Texture.class))));
		pencilButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (pencilButton.isPressed()) {
					canvas.setColor(drawColor);
				}
			}
		});

		eraserButton = new Button(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.ERASER_BUTTON, Texture.class))));
		eraserButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (eraserButton.isPressed()) {
					canvas.setColor(clearColor);
				}
			}
		});

		helpButton = new Button(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.HELP_BUTTON, Texture.class))));
		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (helpButton.isPressed()) {
					Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.getFont(Assets.MONO_REGULAR, Assets.SMALL_FONT_SIZE), Color.WHITE);
					final Dialog dialog = new Dialog("Help", game.assets.skin);
					final TextButton okButton = new TextButton("OK", game.assets.skin);
					dialog.getButtonTable().bottom();
					Label label = new Label("Find the total value of fish that you retrieved!\n" + "Each colour corresponds to the colour of Canadian money.\n" + "The numbers correspond to each number of fish you got.\n\n" + "In case you forgot: blue is 5, purple is 10, green is 20, red is 50, and orange is 100.", labelStyle);
					label.setScale(.5f);
					label.setWrap(true);
					label.setAlignment(Align.center);
					dialog.add(label).width(500).pad(50);
					dialog.add(okButton).bottom();
					okButton.addListener(new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							if (okButton.isPressed()) {
								dialog.hide();
								canvas.setColor(drawColor);
							}
						}
					});
					dialog.key(Keys.ENTER, true);
					dialog.show(stage);
				}
			}
		});

		checkButton = new Button(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.CHECK_BUTTON, Texture.class))));
		checkButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				message = formatString(answerField.getText());
				Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.getFont(Assets.MONO_REGULAR, Assets.SMALL_FONT_SIZE), Color.WHITE);
				final Dialog dialog = new Dialog("Results", game.assets.skin);
				final TextButton okButton = new TextButton("OK", game.assets.skin);
				dialog.getButtonTable().bottom();
				if (checkAnswer() == -1) {
					Label label = new Label("Invalid answer. Please try again.", labelStyle);
					label.setScale(.5f);
					label.setWrap(true);
					label.setAlignment(Align.center);
					dialog.add(label).width(500).pad(50);
					dialog.add(okButton).bottom();
					okButton.addListener(new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							if (okButton.isPressed()) {
								dialog.hide();
								canvas.setColor(drawColor);
							}
						}
					});
				} else {
					Label label = new Label("Your answer was: " + message + ". " + "The correct answer was: " + answer + ". " + "You get " + checkAnswer() + " back!", labelStyle);
					label.setScale(.5f);
					label.setWrap(true);
					label.setAlignment(Align.center);
					dialog.add(label).width(500).pad(50);
					dialog.add(okButton).bottom();
					okButton.addListener(new ChangeListener() {
						@Override
						public void changed(ChangeEvent event, Actor actor) {
							if (okButton.isPressed()) {
								dialog.cancel();

								if ((game.set.getLevel() - 1) % 5 == 0) {
									game.setScreen(new ContinueScreen(game));
								} else {
									game.setScreen(new MazeScreen(game, false));
								}
							}
						}
					});
				}
				dialog.key(Keys.ENTER, true);
				dialog.show(stage);
			}
		});

		clearButton = new Button(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.CLEAR_BUTTON, Texture.class))));
		clearButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (clearButton.isPressed()) {
					canvas.clear();
					canvas.setColor(drawColor);
				}
			}
		});

		answerField = new TextField("", game.assets.skin);
		stage.addActor(menuTable);
		stage.addActor(canvas);
		stage.addActor(fishTable);

		menuTable.clear();
		fishTable.clear();
		helpButton.right();
		menuTable.background(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.MINI_BACKGROUND, Texture.class))));
		menuTable.add(pencilButton).pad(10).size(64);
		menuTable.add(eraserButton).pad(10).size(64);
		menuTable.add(clearButton).pad(10).size(64);
		menuTable.add(helpButton).pad(10).size(64);

		menuTable.row();

		Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.getFont(Assets.SANS_REGULAR, Assets.REGULAR_FONT_SIZE), Color.WHITE);

		for (int i = 0; i < 5; i++) {
			fishTable.add(fishImage[i]).bottom().left().padLeft(10);
		}
		fishTable.add(answerField).minWidth(150).padLeft(150);
		fishTable.add(checkButton).pad(10).size(64);
		fishTable.row();

		for (int i = 0; i < 5; i++) {
			fishTable.add(new Label(fishNumber[i] + "", labelStyle)).pad(30).center();
		}
		fishTable.row();

		input = new InputMultiplexer(stage, this);
	}

	/**
	 * Formats the string into an integer.
	 *
	 * @param s
	 *            The string being formatted.
	 * @return The formatted string.
	 */
	public int formatString(String s) {
		if (s == null)
			return -1;
		try {
			if (Integer.parseInt(s) < 0)
				return -1;
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Checks the player's answer and gives fish accordingly.
	 *
	 * @return The number of fish given back to the player.
	 */
	public int checkAnswer() {
		if (message == -1) {
			return -1;
		}
		if (message == answer) {
			return answer;
		}
		if (Math.abs(message - answer) < range70) {
			return (int) (answer * 0.70);
		}
		if (Math.abs(message - answer) < range50) {
			return (int) (answer * 0.50);
		}
		return (int) (answer * 0.15);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(input);
		Gdx.input.setCursorCatched(false);
		game.music.setSong(Song.MATH);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		canvas.update();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		canvas.dispose();
		System.out.println("Dispose minigame.");
	}

	/** Custom Canvas class for Pixmap manipulation. */
	private static class Canvas extends Actor implements Disposable {

		/** Radius of the drawing tool. */
		private static final int pencilSize = 1;
		/** Radius of the erasing tool. */
		private static final int eraserSize = 5;

		/** The current brush size. */
		private int brushSize;

		/** Canvas' pixmap. */
		private Pixmap pixmap;

		/** Texture that updates from the pixmap. */
		private Texture texture;

		/** Whether the pixmap has been manipulated. */
		private boolean pixmapChanged;

		/** The Canvas constructor. */
		public Canvas(Pixmap pixmap) {
			this.pixmap = pixmap;
			this.clear();
			pixmap.setColor(drawColor);

			this.texture = new Texture(pixmap);
			this.pixmapChanged = false;
			this.brushSize = pencilSize;
		}

		/**
		 * Sets the color of the drawing tool.
		 *
		 * @param color color selected for drawing.
		 */
		@Override
		public void setColor(Color color) {
			pixmap.setColor(color);
			if (color.equals(drawColor)) {
				brushSize = pencilSize;
			} else {
				brushSize = eraserSize;
			}
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(texture, 0, 0);
		}

		/** Updates the texture. */
		public void update() {
			if (pixmapChanged) {
				texture.draw(pixmap, 0, 0);
				pixmapChanged = false;
			}
		}

		/** Clears the pixmap. */
		@Override
		public void clear() {
			pixmap.setColor(clearColor);
			pixmap.fill();
			pixmapChanged = true;
		}

		/**
		 * Draw a dot on the pixmap.
		 *
		 * @param x The x-coordinate.
		 * @param y The y-coordinate.
		 */
		private void drawDot(Vector2 spot) {
			pixmap.fillCircle((int) spot.x, (int) spot.y + shift, brushSize);
			pixmapChanged = true;
		}

		/**
		 * Draws a line when mouse is dragged.
		 *
		 * @param from
		 *            Where line starts.
		 * @param to
		 *            Where line ends.
		 */
		public void drawLine(Vector2 from, Vector2 to) {
			for (float i = 0; i < 1f; i += brushSize / (100f * to.dst(from))) {
				Vector2 line = from.lerp(to, i);
				drawDot(line);
			}

			drawDot(to);
			pixmapChanged = true;
		}

		@Override
		public void dispose() {
			texture.dispose();
			pixmap.dispose();
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			Vector2 current = new Vector2(screenX, screenY);
			canvas.drawDot(current);
			previous = current;
			leftDown = true;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			canvas.drawDot(new Vector2(screenX, screenY));
			previous = null;
			leftDown = false;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (leftDown) {
			Vector2 current = new Vector2(screenX, screenY);
			canvas.drawLine(previous, current);
			previous = current;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
