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
 * <br>
 * Time (Vincent): 1 hour
 */
public final class TileIDs {

	/** The placeholder tile ID. */
	public static final int PLACEHOLDER = 0;
	/** The background tile ID. */
	public static final int BACKGROUND = 1;
	/** The barrier tile ID. */
	public static final int BARRIER = 2;

	/** The first digit in the ID of wire tiles. */
	public static final int WIRE_RANGE = 1000;
	/** The first digit in the ID of logic gate tiles. */
	public static final int GATE_RANGE = 2000;
	/** The first digit in the ID of power-up tiles. */
	public static final int POWERUP_RANGE = 3000;

	/** The ID modifier of the fish. */
	public static final int FISH = 100;
	/** The ID modifier of the cheese. */
	public static final int CHEESE = 200;

	/** The value of the vertical wire property. */
	public static final int VERTICAL = 1;
	/** The value of the horizontal wire property. */
	public static final int HORIZONTAL = 2;
	/** The value of the up left wire property. */
	public static final int UP_LEFT = 3;
	/** The value of the up right wire property. */
	public static final int UP_RIGHT = 4;
	/** The value of the down left wire property. */
	public static final int DOWN_LEFT = 5;
	/** The value of the down right wire property. */
	public static final int DOWN_RIGHT = 6;

	/** The value of the turn wire property. */
	public static final int TURN = 100;

	/** The value of the property for on electrical components. */
	public static final int ON = 10;
	/** The value of the property for off electrical components. */
	public static final int OFF = 20;
	/** The value of the property for unknown electrical components. */
	public static final int UNKNOWN = 30;

	/** The value of the property for an AND gate. */
	public static final int AND_GATE = 100;
	/** The value of the property for an NAND gate. */
	public static final int NAND_GATE = 200;
	/** The value of the property for an OR gate. */
	public static final int OR_GATE = 300;
	/** The value of the property for a NOR gate. */
	public static final int NOR_GATE = 400;
	/** The value of the property for an XOR gate. */
	public static final int XOR_GATE = 500;

	/** The value of the property for a gate facing up. */
	public static final int UP_GATE = 1;
	/** The value of the property for a gate facing down. */
	public static final int DOWN_GATE = 2;

	/** The value of the property for the blue fish. */
	public static final int BLUE = 10;
	/** The value of the property for the purple fish. */
	public static final int PURPLE = 20;
	/** The value of the property for the green fish. */
	public static final int GREEN = 30;
	/** The value of the property for the red fish. */
	public static final int RED = 40;
	/** The value of the property for the orange fish. */
	public static final int ORANGE = 50;

	/** Compute and return the ID of the tile with the given property. */
	public static int computeID(int... ids) {
		int sum = 0;
		for (int i : ids) {
			sum += i;
		}
		return sum;
	}

	/**
	 * Remove the electric status property from the given ID.
	 *
	 * @param currentID the ID of the electrical component.
	 * @return the given ID, without the electric state.
	 */
	public static int stripElectricState(int currentID) {
		int ones = currentID % 10;
		currentID /= 100;
		currentID *= 100;
		currentID += ones;
		return currentID;
	}

	/** Prevent the {@link TileIDs} class from being instantiated. */
	private TileIDs() {
	}

}
