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

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * A simple class for fish cells that allows us to keep track of the fish types.
 *
 * @author Vincent Macri
 */
public class FishCell extends Cell {

	/** The possible colours of the fish. */
	public enum FishColour {
		/** A blue fish. */
		BLUE,
		/** A purple fish. */
		PURPLE,
		/** A green fish. */
		GREEN,
		/** A red fish. */
		RED,
		/** A orange fish. */
		ORANGE
	}

	/** The colour of this fish. */
	private final FishColour colour;

	/**
	 * Constructor for {@link FishCell}.
	 *
	 * @param tile the tile to use for this cell.
	 * @param colour the colour of the new fish.
	 */
	public FishCell(TiledMapTile tile, FishColour colour) {
		setTile(tile);
		this.colour = colour;
	}

	/**
	 * Getter for {@link #colour}.
	 *
	 * @return the colour of the fish.
	 */
	public FishColour getColour() {
		return colour;
	}

}
