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

/**
 * Represents a single boolean circuit.
 * Used for generating the circuits in each map.
 *
 * @author Vincent Macri
 */
public class Circuit {

	/** The types of possible logic gates. */
	public enum LogicGate {
		/** An AND gate. */
		AND,
		/** A NAND gate. */
		NAND,
		/** An OR gate. */
		OR,
		/** A NOR gate. */
		NOR,
		/** An XOR gate. */
		XOR,
	}

	/**
	 * The random number generator to use.
	 * This should be a reference to {@link MapFactory#random} from the {@link MapFactory} that initialized this object.
	 */
	private final Random random;

	/** What we want this circuit to evaluate to. */
	private final boolean target;

	/** The first input. */
	private boolean inputA;
	/** The second input. */
	private boolean inputB;

	/** The logic gate in this circuit. */
	private LogicGate gate;

	/**
	 * Create a new {@link Circuit} instance.
	 *
	 * @param target What we want this circuit to evaluate to.
	 */
	public Circuit(boolean target, Random random) {
		this.target = target;
		this.random = random;
		this.gate = LogicGate.values()[random.nextInt(LogicGate.values().length)];
		solveInputs();
	}

	/**
	 * Evaluate the output of the given logic gate with the given inputs.
	 *
	 * @param gate The gate to evaluate with.
	 * @param inputA The first input.
	 * @param inputB The second input.
	 * @return The result of the logic circuit.
	 *         Returns {@code false} if the given gate has not been fully implemented yet.
	 */
	public static boolean evaluateGate(LogicGate gate, boolean inputA, boolean inputB) {
		switch (gate) {
			case AND:
				return inputA && inputB;
			case NAND:
				return !(inputA && inputB);
			case OR:
				return inputA || inputB;
			case NOR:
				return !(inputA || inputB);
			case XOR:
				return inputA ^ inputB;
			default:
				return false;
		}
	}

	/** Assign random values to {@link #inputA} and {@link #inputB} such that the gate will evaluate to {@link #target}. */
	private void solveInputs() {
		do {
			inputA = random.nextBoolean();
			inputB = random.nextBoolean();
		} while (evaluateGate(gate, inputA, inputB) != target);
	}

	/**
	 * Getter for {@link #inputB}.
	 *
	 * @return the second input to the circuit.
	 */
	public boolean isInputB() {
		return inputB;
	}

	/**
	 * Getter for {@link #inputA}.
	 *
	 * @return the first input to the circuit.
	 */
	public boolean isInputA() {
		return inputA;
	}

	/**
	 * Getter for {@link #gate}
	 *
	 * @return the gate in this circuit.
	 */
	public LogicGate getGate() {
		return gate;
	}

	/**
	 * Return the ID from {@link TileIDs} for the given logic gate.
	 *
	 * @param gate the logic gate to get the ID of.
	 * @return The ID of the given logic gate.
	 *         Returns {@link TileIDs#PLACEHOLDER} if the given gate has not been fully implemented yet.
	 */
	public static int getID(LogicGate gate) {
		switch (gate) {
			case AND:
				return TileIDs.AND_GATE;
			case NAND:
				return TileIDs.NAND_GATE;
			case OR:
				return TileIDs.OR_GATE;
			case NOR:
				return TileIDs.NOR_GATE;
			case XOR:
				return TileIDs.XOR_GATE;
			default:
				return TileIDs.PLACEHOLDER;
		}
	}
}
