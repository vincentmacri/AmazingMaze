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

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * A simple class that adds an on/off property to {@link Cell} for use in wire cells.
 *
 * @author Vincent Macri
 */
public class WireCell extends Cell {

	/** If the cell is on. */
	private final boolean on;

	/**
	 * Create a new wire cell.
	 *
	 * @param onindex
	 */
	public WireCell(boolean on) {
		this.on = on;
	}

	/**
	 * @return if the wire is electrified or not.
	 */
	public boolean isOn() {
		return on;
	}
}
