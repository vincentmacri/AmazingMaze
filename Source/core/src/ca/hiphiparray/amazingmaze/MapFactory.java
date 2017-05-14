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

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Class to procedurally generate maps.
 *
 * @author Vincent Macri
 */
public class MapFactory {

	/** The width of the maps generated by this factory. */
	private final int width;
	/** The height of the maps generated by this factory. */
	private final int height;

	/** The random number generator used by this factory. */
	private final Random random;

	/** Reference to an {@link Assets} instance to get images from. Should be {@link AmazingMazeGame#assets}. */
	private final Assets assets;

	/**
	 * Constructor for creation of a map factory.
	 *
	 * @param game The {@link AmazingMazeGame} instance to get resources from.
	 * @param seed The seed to use for generation by this factory.
	 * @param width The width of the maps (in tiles) generated by this factory.
	 * @param height The height of the maps (in tiles) generated by this factory.
	 * @param tileSize The side length (in pixels) of the tiles.
	 */
	public MapFactory(final AmazingMazeGame game, long seed, int width, int height, int tileSize) {
		this.assets = game.assets;
		this.random = new Random(seed);
		this.width = width;
		this.height = height;
	}

	public TiledMap getMap() {
		TiledMap map = new TiledMap();

		TiledMapTileLayer backgroundLayer = new TiledMapTileLayer(width, height, MazeScreen.TILE_SIZE, MazeScreen.TILE_SIZE);
		for (int c = 0; c < backgroundLayer.getWidth(); c++) {
			for (int r = 0; r < backgroundLayer.getHeight(); r++) {
				Cell cell = new Cell();
				cell.setTile(assets.tiles.getTile(TileIDs.computeID(TileIDs.BACKGROUND)));
				backgroundLayer.setCell(c, r, cell);
			}
		}
		map.getLayers().add(backgroundLayer);

		final int gateSpace = 3;
		int[] splits = generateWireLocations();

		TiledMapTileLayer objectLayer = new TiledMapTileLayer(width, height, MazeScreen.TILE_SIZE, MazeScreen.TILE_SIZE);
		for (int split : splits) {
			int barrierLoc = randomInt(gateSpace + 1, height - (gateSpace + 1));
			Cell cell = new Cell();
			cell.setTile(assets.tiles.getTile(TileIDs.computeID(TileIDs.BARRIER)));
			objectLayer.setCell(split, barrierLoc, cell);
			for (int r = barrierLoc - 1; r >= gateSpace; r--) {
				cell = new Cell();
				cell.setTile(assets.tiles.getTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.UNKNOWN)));
				objectLayer.setCell(split, r, cell);
			}
			for (int r = barrierLoc + 1; r < height - gateSpace; r++) {
				cell = new Cell();
				cell.setTile(assets.tiles.getTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.UNKNOWN)));
				objectLayer.setCell(split, r, cell);

			}
		}
		map.getLayers().add(objectLayer);

		return map;
	}

	/**
	 * Generate the placement of wires.
	 *
	 * @return A boolean array of where wires are to be placed.
	 */
	private int[] generateWireLocations() {
		final int wireDistance = 5;
		final int startDistance = 3;
		int[] wires = new int[width / wireDistance];

		for (int i = 0; i < wires.length; i++) {
			wires[i] = startDistance + (i * wireDistance);
		}

		return wires;
	}

	/**
	 * Use {@link #random} to generate a random integer in the given range.
	 *
	 * @param low The lowest number that can be generated (inclusive).
	 * @param high The highest number that can be generated (exclusive).
	 * @return A random number in the given range, or {@code high} if {@code high <= low}. If {@code high <= low} then {@code high} is no longer exclusive.
	 */
	private int randomInt(int low, int high) {
		if (high <= low) {
			return high;
		}
		return low + random.nextInt(high - low);
	}
}
