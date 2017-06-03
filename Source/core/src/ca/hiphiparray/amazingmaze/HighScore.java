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
 * Represents an entry in the high scores.
 *
 * @author Vincent Macri
 * <br>
 * Time (Vincent): 15 minutes
 */
public class HighScore implements Comparable<HighScore> {

	/** Name the name of the player who got this score. */
	private final String name;
	/** Score the score they got. */
	private final int score;

	/**
	 * The default high score constructor, to be used for empty entries.
	 *
	 * Sets the name to an empty string.
	 * Sets the score to -1.
	 */
	public HighScore() {
		this.name = "";
		this.score = -1;
	}

	/**
	 * Create a new high score entry.
	 *
	 * @param name the name of the player who got this score.
	 * @param score the score they got.
	 */
	public HighScore(String name, int score) {
		if (name.length() > 50) {
			name = name.substring(0, 50) + "...";
		}
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(HighScore o) {
		return this.score - o.score;
	}

	/**
	 * Get the name of this high score entry.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the score of this high score entry.
	 *
	 * @return the score.
	 */
	public int getScore() {
		return score;
	}

}