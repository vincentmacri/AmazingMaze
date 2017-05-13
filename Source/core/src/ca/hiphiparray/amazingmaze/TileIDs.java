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

/**
 * Static class to keep track of the IDs of different tiles and tile properties.
 * Tile's IDs are kept track of by adding up the IDs of their properties.
 * Very basic tiles, such as the background have only one property, which is their own ID.
 *
 * @author Vincent Macri
 */
public final class TileIDs {

	/** The placeholder tile ID. */
	public static final int PLACEHOLDER = 0;
	/** The background tile ID. */
	public static final int BACKGROUND = 1;

	/** The first digit in the ID of power-up tiles. */
	public static final int POWERUP_RANGE = 10;
	/** The first digit in the ID of wire tiles. */
	public static final int WIRE_RANGE = 100;
	/** The first digit in the ID of logic gate tiles. */
	public static final int GATE_RANGE = 1000;

	/** The value of the vertical wire property. */
	public static final int VERTICAL = 10;
	/** The value of the horizontal wire property. */
	public static final int HORIZONTAL = 20;

	/** The value of the property for on electrical components. */
	public static final int ON = 1;
	/** The value of the property for off electrical components. */
	public static final int OFF = 2;
	/** The value of the property for unknown electrical components. */
	public static final int UNKNOWN = 3;

	/** Compute and return the ID of the tile with the given property. */
	public static int computeID(int... ids) {
		int sum = 0;
		for (int i : ids) {
			sum += i;
		}
		return sum;
	}

	/** Prevent the {@link TileIDs} class from being instantiated. */
	private TileIDs() {
	}

}
