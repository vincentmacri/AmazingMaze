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

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.hiphiparray.amazingmaze.MusicManager.Song;
import ca.hiphiparray.amazingmaze.Player.HorizontalDirection;
import ca.hiphiparray.amazingmaze.Player.VerticalDirection;

/**
 * The maze screen. This is where most of the gameplay takes place.
 *
 * @since 0.1
 * @author Vincent Macri
 * @author Chloe Nguyen
 * @author Susie Son
 * <br>
 * Time (Vincent): 5 hours
 * <br>
 * Time (Chloe): 30 minutes
 * <br>
 * Time (Susie): 5 minutes
 */
public class MazeScreen implements Screen, InputProcessor {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	protected final AmazingMazeGame game;

	/** Handle passing input around to different components. */
	private InputMultiplexer input;

	/** The game HUD. */
	protected Stage hud;

	/** The pause menu. */
	private Stage pauseMenu;

	/** How big the tiles are, in pixels. */
	protected static final int TILE_SIZE = 16;
	/** Constant to use when converting between world and screen units. */
	private static final float MAP_SCALE = 1f / TILE_SIZE;

	/** The number of tiles wide the map is. */
	protected final int mapWidth;
	/** The number of tiles high the map is. */
	protected int mapHeight;

	/** The level's map. */
	protected TiledMap map;

	/** The renderer for the map. */
	private OrthogonalTiledMapRenderer mapRenderer;

	/** The camera. */
	private OrthographicCamera camera;
	/** The viewport. */
	private ExtendViewport viewport;

	/** The player. */
	private Player player;

	/** Array of bounding boxes for collision with objects. */
	protected Array<Rectangle> obstacleBoxes;
	/** Array of bounding boxes for collision with electrified wires. */
	protected Array<Rectangle> wireBoxes;
	/** Array of bounding boxes for collision with fish. */
	protected Array<Rectangle> fishBoxes;
	/** Array of bounding boxes for collision with cheese. */
	protected Array<Rectangle> cheeseBoxes;

	/** Array of locations of the gates. */
	private Array<Point> gateLocations;

	/** ArrayList of gates of wires that are on. */
	protected ArrayList<Circuit> gateOn;

	/** Label to show how many lives the player has left. */
	private Label livesLeft;

	/** If the game is paused. */
	private boolean paused;

	/** If the game is in tutorial mode. */
	protected boolean help;

	/**
	 * Constructor for the maze screen.
	 *
	 * @param game the {@link AmazingMazeGame} instance that is managing this screen.
	 * @param help if this is the tutorial level.
	 */
	public MazeScreen(final AmazingMazeGame game, boolean help) {
		final int mapSize = 2;
		this.game = game;
		this.paused = false;
		this.help = help;

		this.mapWidth = 16 * mapSize + game.save.getLevel() * 5;
		this.mapHeight = 9 * mapSize;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16 * mapSize, this.mapHeight);

		viewport = new ExtendViewport(0, this.mapHeight, this.mapWidth, this.mapHeight, camera);

		MapFactory factory;
		if (!help) {
			factory = new MapFactory(game, game.save.getLevel(), this.mapWidth, this.mapHeight, TILE_SIZE);
		} else {
			this.mapHeight = this.mapHeight * 5 / 8;
			factory = new MapFactory(game, -3, this.mapWidth, this.mapHeight, TILE_SIZE);
		}
		map = factory.generateMap();
		gateLocations = factory.getGateLocations();
		gateOn = factory.getGateOn();
		createBoundingBoxes();

		mapRenderer = new OrthogonalTiledMapRenderer(map, MAP_SCALE, game.batch);
		player = new Player(game.assets.manager.get(Assets.GAME_ATLAS_LOCATION, TextureAtlas.class).findRegion(Assets.PLACEHOLDER), this);
		player.setScale(MAP_SCALE);

		if (!help) {
			setupHUD();
		}
		setupPauseMenu();
		input = new InputMultiplexer(pauseMenu, this);
	}

	/** Create the pause menu. */
	private void setupPauseMenu() {
		pauseMenu = new Stage(new ScreenViewport(), game.batch);

		Table table = new Table();
		table.setFillParent(true);
		table.center();
		pauseMenu.addActor(table);

		TextButton resumeButton = new TextButton("Resume", game.assets.skin);
		resumeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				paused = false;
			}
		});
		table.add(resumeButton).pad(10).width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 8);
		table.row();

		TextButton settingsButton = new TextButton("Settings", game.assets.skin);
		final Screen sourceScreen = this;
		settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.settingsScreen.setSourceScreen(sourceScreen);
				game.setScreen(game.settingsScreen);
			}
		});
		table.add(settingsButton).pad(10).width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 8);
		table.row();

		TextButton quitButton = new TextButton("Main Menu", game.assets.skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.menuScreen);
			}
		});
		table.add(quitButton).pad(10).width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 8);
	}

	/** Create the game HUD. */
	private void setupHUD() {
		hud = new Stage(new ScreenViewport(), game.batch);

		Table table = new Table();
		table.setFillParent(true);
		table.top().left();
		hud.addActor(table);

		Label level = new Label("Level " + game.save.getLevel(), game.assets.skin, Assets.HUD_STYLE);
		table.add(level).colspan(2);
		table.row();

		Image lifeIcon = new Image(game.assets.manager.get(Assets.LIFE_HUD_IMAGE, Texture.class));
		table.add(lifeIcon).pad(Gdx.graphics.getWidth() / 128).left();

		livesLeft = new Label("", game.assets.skin, Assets.HUD_STYLE);
		table.add(livesLeft);

		updateLives(-2);
	}

	/** Create the bounding boxes for collision detection. */
	private void createBoundingBoxes() {
		obstacleBoxes = new Array<Rectangle>(false, 16);
		TiledMapTileLayer objects = (TiledMapTileLayer) map.getLayers().get(MapFactory.OBJECT_LAYER);
		for (int r = 0; r < mapHeight; r++) {
			for (int c = 0; c < mapWidth; c++) {
				Cell cell = objects.getCell(c, r);
				if (cell != null) {
					obstacleBoxes.add(new Rectangle(c, r, 1, 1));
				}
			}
		}
		wireBoxes = new Array<Rectangle>(false, 16);
		TiledMapTileLayer wires = (TiledMapTileLayer) map.getLayers().get(MapFactory.WIRE_LAYER);
		for (int r = 0; r < mapHeight; r++) {
			for (int c = 0; c < mapWidth; c++) {
				WireCell wire = (WireCell) wires.getCell(c, r);
				if (wire != null && wire.isOn()) {
					wireBoxes.add(new Rectangle(c + 5f / 16f, r, 6f / 16f, 1));
				}
			}
		}
		fishBoxes = new Array<Rectangle>(false, 16);
		cheeseBoxes = new Array<Rectangle>(false, 16);
		TiledMapTileLayer items = (TiledMapTileLayer) map.getLayers().get(MapFactory.ITEM_LAYER);
		for (int r = 0; r < mapHeight; r++) {
			for (int c = 0; c < mapWidth; c++) {
				Cell item = items.getCell(c, r);
				if (item != null) {
					if (item.getClass() == FishCell.class) {
						fishBoxes.add(new Rectangle(c, r, 1, 1));
					} else {
						cheeseBoxes.add(new Rectangle(c, r, 1, 1));
					}
				}
			}
		}
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(false);
		Gdx.input.setInputProcessor(input);
		game.music.setSong(Song.MAZE);
	}

	@Override
	public void render(float delta) {
		// Update the game state
		if (!paused) {
			update(delta);
			hud.act();
		} else {
			pauseMenu.act();
		}

		// Do the rendering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Vector3 target = new Vector3(player.getX(), mapHeight / 2, 0);
		target.x = Math.min(player.getX(), mapWidth - viewport.getWorldWidth() / 2);
		target.x = Math.max(viewport.getWorldWidth() / 2, target.x);

		camera.position.lerp(target, 0.25f);
		camera.update();
		mapRenderer.setView(camera);

		mapRenderer.render();
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		hud.draw();

		if (paused) {
			pauseMenu.draw();
		}
	}

	/**
	 * Update the game state.
	 *
	 * @param delta the time passed since the last frame.
	 */
	private void update(float delta) {
		player.update(delta);
		if (player.getX() + 1 * Player.PLAYER_SIZE >= mapWidth) {
			nextScreen();
			dispose();
		} else if (player.isDead()) {
			game.setScreen(new ContinueScreen(game, false));
			dispose();
		}
	}

	/** Advance the game to the next screen. */
	public void nextScreen() {
		game.save.setLevel(game.save.getLevel() + 1);
		game.save.setLives(player.getLives());
		game.setScreen(new FishMiniGame(game, player));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		hud.getViewport().update(width, height);
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
		mapRenderer.dispose();
		map.dispose();
		hud.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == game.save.getLeftButton()) {
			player.setHorizontalDir(HorizontalDirection.LEFT);
		} else if (keycode == game.save.getRightButton()) {
			player.setHorizontalDir(HorizontalDirection.RIGHT);
		} else if (keycode == game.save.getUpButton()) {
			player.setVerticalDir(VerticalDirection.UP);
		} else if (keycode == game.save.getDownButton()) {
			player.setVerticalDir(VerticalDirection.DOWN);
		} else if (keycode == game.save.getPauseButton()) {
			paused = !paused;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == game.save.getLeftButton()) {
			if (Gdx.input.isKeyPressed(game.save.getRightButton())) {
				player.setHorizontalDir(HorizontalDirection.RIGHT);
			} else if (Gdx.input.isKeyPressed(game.save.getUpButton())) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(game.save.getDownButton())) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else {
				player.setHorizontalDir(HorizontalDirection.NONE);
			}
		} else if (keycode == game.save.getRightButton()) {
			if (Gdx.input.isKeyPressed(game.save.getLeftButton())) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(game.save.getUpButton())) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(game.save.getDownButton())) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else {
				player.setHorizontalDir(HorizontalDirection.NONE);
			}
		} else if (keycode == game.save.getUpButton()) {
			if (Gdx.input.isKeyPressed(game.save.getDownButton())) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else if (Gdx.input.isKeyPressed(game.save.getLeftButton())) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(game.save.getRightButton())) {
				player.setHorizontalDir(HorizontalDirection.RIGHT);
			} else {
				player.setVerticalDir(VerticalDirection.NONE);
			}
		} else if (keycode == game.save.getDownButton()) {
			if (Gdx.input.isKeyPressed(game.save.getUpButton())) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(game.save.getLeftButton())) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(game.save.getRightButton())) {
				player.setHorizontalDir(HorizontalDirection.RIGHT);
			} else {
				player.setVerticalDir(VerticalDirection.NONE);
			}
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 worldClickPos = viewport.getCamera().unproject(new Vector3(screenX, screenY, 0));
		int x = (int) worldClickPos.x;
		int y = (int) worldClickPos.y;

		TiledMapTileLayer objects = (TiledMapTileLayer) map.getLayers().get(MapFactory.OBJECT_LAYER);
		Cell gate;
		int newID;
		for (Point point : gateLocations) {
			if (point.x == x && point.y == y) {
				gate = objects.getCell(x, y);
				newID = TileIDs.computeID(TileIDs.stripElectricState(gate.getTile().getId()));
				if (button == Buttons.LEFT) {
					newID = TileIDs.computeID(newID, TileIDs.ON);
					updateWires(x, y, TileIDs.ON);
				} else if (button == Buttons.RIGHT) {
					newID = TileIDs.computeID(newID, TileIDs.OFF);
					updateWires(x, y, TileIDs.OFF);
				} else if (button == Buttons.MIDDLE) {
					newID = TileIDs.computeID(newID, TileIDs.UNKNOWN);
					updateWires(x, y, TileIDs.UNKNOWN);
				} else {
					return true;
				}
				gate.setTile(game.assets.tiles.getTile(newID));
				break;
			}
		}
		return true;
	}

	/**
	 * Update the wires connected to the gate at (x, y).
	 *
	 * @param x the x position of the gate being updated.
	 * @param y the y position of the gate being updated.
	 * @param state the new state of the wires.
	 */
	private void updateWires(int x, int y, int state) {
		TiledMapTileLayer wires = (TiledMapTileLayer) map.getLayers().get(MapFactory.WIRE_LAYER);
		for (int r = y + 1; r < mapHeight; r++) {
			if (wires.getCell(x, r) != null) {
				Cell cell = wires.getCell(x, r);
				int newID = TileIDs.stripElectricState(cell.getTile().getId());
				newID = TileIDs.computeID(newID, state);
				cell.setTile(game.assets.tiles.getTile(newID));
			} else {
				break;
			}
		}
		for (int r = y - 1; r >= 0; r--) {
			if (wires.getCell(x, r) != null) {
				Cell cell = wires.getCell(x, r);
				int newID = TileIDs.stripElectricState(cell.getTile().getId());
				newID = TileIDs.computeID(newID, state);
				cell.setTile(game.assets.tiles.getTile(newID));
			} else {
				break;
			}
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	/**
	 * Called on item collision events to update UI.
	 *
	 * @param gate the gate that the user collided at.
	 * Will be -1 if collided with cheese, and some other negative value for any other non-gate calls.
	 */
	public void updateLives(int gate) {
		livesLeft.setText("x " + Integer.toString(player.getLives()));
	}

}
