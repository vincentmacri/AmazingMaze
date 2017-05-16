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
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ca.hiphiparray.amazingmaze.Player.HorizontalDirection;
import ca.hiphiparray.amazingmaze.Player.VerticalDirection;

/**
 * The maze screen. This is where most of the gameplay takes place.
 *
 * @author Vincent Macri
 */
public class MazeScreen implements Screen, InputProcessor {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** How big the tiles are, in pixels. */
	protected static final int TILE_SIZE = 16;
	/** Constant to use when converting between world and screen units. */
	private static final float MAP_SCALE = 1f / TILE_SIZE;

	/** The number of tiles wide the map is. */
	protected final int mapWidth;
	/** The number of tiles high the map is. */
	protected final int mapHeight;

	/** The level's map. */
	private TiledMap map;

	/** The renderer for the map. */
	private OrthogonalTiledMapRenderer mapRenderer;

	/** The camera. */
	private OrthographicCamera camera;
	/** The viewport. */
	private FitViewport viewport;

	/** The player. */
	private Player player;

	/** Array of bounding boxes for collision with objects. */
	private Array<Rectangle> obstacleBoxes;

	/**
	 * Constructor for the maze screen.
	 *
	 * @param game the {@link AmazingMazeGame} instance that is managing this screen.
	 */
	public MazeScreen(final AmazingMazeGame game) {
		final int mapSize = 2;
		this.game = game;
		this.mapWidth = 16 * mapSize;
		this.mapHeight = 9 * mapSize;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, this.mapWidth, this.mapHeight);

		viewport = new FitViewport(this.mapWidth, this.mapHeight, camera);

		MapFactory m = new MapFactory(game, 1, this.mapWidth, this.mapHeight, TILE_SIZE);
		map = m.getMap();
		createBoundingBoxes();

		mapRenderer = new OrthogonalTiledMapRenderer(map, MAP_SCALE, game.batch);
		player = new Player(game.assets.manager.get("tiles/tiles.atlas", TextureAtlas.class).findRegion("placeholder"), this);
		player.setScale(MAP_SCALE);
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
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(false);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		// Update the game state
		update(delta);

		// Do the rendering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		mapRenderer.setView(camera);

		mapRenderer.render();
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
	}

	/**
	 * Update the game state.
	 *
	 * @param delta the time passed since the last frame.
	 */
	private void update(float delta) {
		player.update(delta, obstacleBoxes);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			player.setHorizontalDir(HorizontalDirection.LEFT);
		} else if (keycode == Keys.RIGHT) {
			player.setHorizontalDir(HorizontalDirection.RIGHT);
		} else if (keycode == Keys.UP) {
			player.setVerticalDir(VerticalDirection.UP);
		} else if (keycode == Keys.DOWN) {
			player.setVerticalDir(VerticalDirection.DOWN);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player.setHorizontalDir(HorizontalDirection.RIGHT);
			} else if (Gdx.input.isKeyPressed(Keys.UP)) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else {
				player.setHorizontalDir(HorizontalDirection.NONE);
			}
		} else if (keycode == Keys.RIGHT) {
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(Keys.UP)) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else {
				player.setHorizontalDir(HorizontalDirection.NONE);
			}
		} else if (keycode == Keys.UP) {
			if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				player.setVerticalDir(VerticalDirection.DOWN);
			} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player.setHorizontalDir(HorizontalDirection.RIGHT);
			} else {
				player.setVerticalDir(VerticalDirection.NONE);
			}
		} else if (keycode == Keys.DOWN) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				player.setVerticalDir(VerticalDirection.UP);
			} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.setHorizontalDir(HorizontalDirection.LEFT);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
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
		if (button == Buttons.LEFT) {
			return true;
		} else if (button == Buttons.RIGHT) {
			return true;
		} else if (button == Buttons.MIDDLE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
