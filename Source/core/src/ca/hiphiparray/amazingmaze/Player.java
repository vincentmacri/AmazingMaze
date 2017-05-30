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

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ca.hiphiparray.amazingmaze.FishCell.FishColour;

/**
 * The player class.
 *
 * @author Vincent Macri
 */
public class Player extends Sprite {

	/** The direction the player is moving vertically. */
	public enum VerticalDirection {
		/** If the player is moving up. */
		UP,
		/** If the player is moving down. */
		DOWN,
		/** If the player is not moving vertically. */
		NONE
	}

	/** The direction the player is moving horizontally. */
	public enum HorizontalDirection {
		/** If the player is moving left. */
		LEFT,
		/** If the player is moving right. */
		RIGHT,
		/** If the player is not moving horizontally. */
		NONE
	}

	/** The speed of the player. */
	private float SPEED = 10f;

	/** The current vertical direction of the player. */
	private VerticalDirection verticalDir;
	/** The current horizontal direction of the player. */
	private HorizontalDirection horizontalDir;

	/** The last vertical direction of the player. */
	private VerticalDirection lastVerticalDir;
	/** The last horizontal direction of the player. */
	private HorizontalDirection lastHorizontalDir;

	/** A 2D vector representing the direction and magnitude the player is moving with. */
	private Vector2 direction;

	/** The {@link MazeScreen} managing this player. */
	private final MazeScreen maze;

	/** The side length of the player's bounding box. */
	protected static final int PLAYER_SIZE = 1;

	/** How many blue fish have been collected. */
	protected int blueCollected;
	/** How many purple fish have been collected. */
	protected int purpleCollected;
	/** How many green fish have been collected. */
	protected int greenCollected;
	/** How many red fish have been collected. */
	protected int redCollected;
	/** How many orange fish have been collected. */
	protected int orangeCollected;

	/** How many lives the player has left. */
	private int lives;

	/** How long the player has been in the current animation state. */
	private float stateTime;

	/** If the player is dead. */
	private boolean dead;

	/**
	 * Create the player.
	 *
	 * @param region the player's image.
	 * @param maze the {@link MazeScreen} instance managing this player.
	 */
	public Player(TextureRegion region, final MazeScreen maze) {
		super(region);
		this.maze = maze;
		setOrigin(0, 0);
		setPosition(0, this.maze.mapHeight / 2);
		this.direction = new Vector2(0, 0);
		this.horizontalDir = HorizontalDirection.NONE;
		this.verticalDir = VerticalDirection.NONE;
		this.lastHorizontalDir = HorizontalDirection.NONE;
		this.lastVerticalDir = VerticalDirection.NONE;
		this.lives = 3;
		this.stateTime = 0;
		this.dead = false;
	}

	/**
	 * Update the player's status.
	 *
	 * @param deltaTime how much time has passed since the last frame.
	 */
	protected void update(float deltaTime) {
		Point2D.Float newPos = doObjectCollision(new Vector2(direction).scl(deltaTime));
		setPosition(newPos.x, newPos.y);
		handleDeath();
		collectFish();
		collectCheese();

		updateImage(deltaTime);

		lastHorizontalDir = horizontalDir;
		lastVerticalDir = verticalDir;
	}

	/**
	 * Set the correct image for the player.
	 *
	 * @param deltaTime how much time has passed since the last frame.
	 */
	private void updateImage(float deltaTime) {
		if (!direction.equals(Vector2.Zero)) { // If the mouse if moving, use frame 3.
			stateTime = (Assets.MOUSE_RUN_FRAME - 1) * Assets.MOUSE_FRAME_DURATION;
		} else if (horizontalDir != lastHorizontalDir || verticalDir != lastVerticalDir) {
			stateTime = (Assets.MOUSE_FRAME_COUNT - 1) * Assets.MOUSE_FRAME_DURATION;
		} else {
			stateTime += deltaTime;
		}

		if (verticalDir == VerticalDirection.UP) {
			setRegion(Assets.mouseUp.getKeyFrame(stateTime));
		} else if (verticalDir == VerticalDirection.DOWN) {
			setRegion(Assets.mouseDown.getKeyFrame(stateTime));
		} else if (horizontalDir == HorizontalDirection.LEFT) {
			setRegion(Assets.mouseLeft.getKeyFrame(stateTime));
		} else {
			setRegion(Assets.mouseRight.getKeyFrame(stateTime));
		}
	}

	/** Handle the player collecting fish. */
	private void collectFish() {
		Rectangle thisBox = getBoundingRectangle();
		for (int i = 0; i < maze.fishBoxes.size; i++) {
			if (thisBox.overlaps(maze.fishBoxes.get(i))) {
				TiledMapTileLayer layer = (TiledMapTileLayer) maze.map.getLayers().get(MapFactory.ITEM_LAYER);
				int x = (int) maze.fishBoxes.get(i).x;
				int y = (int) maze.fishBoxes.get(i).y;
				FishColour colour = ((FishCell) layer.getCell(x, y)).getColour();
				layer.setCell(x, y, null);
				maze.fishBoxes.removeIndex(i);

				switch (colour) {
					case BLUE:
						blueCollected++;
						break;
					case PURPLE:
						purpleCollected++;
						break;
					case GREEN:
						greenCollected++;
						break;
					case RED:
						redCollected++;
						break;
					case ORANGE:
						orangeCollected++;
						break;
				}
				break;
			}
		}
	}

	/** Handle the player collecting cheese. */
	private void collectCheese() {
		Rectangle thisBox = getBoundingRectangle();
		for (int i = 0; i < maze.cheeseBoxes.size; i++) {
			if (thisBox.overlaps(maze.cheeseBoxes.get(i))) {
				TiledMapTileLayer layer = (TiledMapTileLayer) maze.map.getLayers().get(MapFactory.ITEM_LAYER);
				int x = (int) maze.cheeseBoxes.get(i).x;
				int y = (int) maze.cheeseBoxes.get(i).y;
				layer.setCell(x, y, null);
				maze.cheeseBoxes.removeIndex(i);
				lives++;
				maze.updateLivesLabel();

				break;
			}
		}
	}

	/** Handle the player dying. */
	private void handleDeath() {
		Rectangle thisBox = getBoundingRectangle();
		for (Rectangle wire : maze.wireBoxes) {
			if (thisBox.overlaps(wire)) {
				if (lives <= 0) {
					dead = true;
					return;
				}
				if (!maze.help) {
					lives--;
				}
				setPosition(0, maze.mapHeight / 2);
				maze.loseLife((int) ((getX() - MapFactory.START_DISTANCE + 1) / MapFactory.WIRE_DISTANCE));
				maze.updateLivesLabel();
				break;
			}
		}
	}

	/**
	 * Return where the player should be after handling object collision.
	 *
	 * @param deltaPos the change in position since the last position update.
	 * @return the new position, as a {@link Point2D.Float}.
	 */
	private Point2D.Float doObjectCollision(Vector2 deltaPos) {
		float newX = getX() + deltaPos.x;
		float newY = getY() + deltaPos.y;
		Point2D.Float nextTilePos = new Point2D.Float(newX, newY);

		Rectangle nextBoundingBox = new Rectangle(nextTilePos.x, nextTilePos.y, PLAYER_SIZE, PLAYER_SIZE);
		float nextStartX = nextBoundingBox.getX();
		float nextEndX = nextStartX + nextBoundingBox.getWidth();
		float nextStartY = nextBoundingBox.getY();
		float nextEndY = nextStartY + nextBoundingBox.getHeight();
		for (Rectangle obstacle : maze.obstacleBoxes) {
			if (nextBoundingBox.overlaps(obstacle)) {
				float objectStartX = obstacle.getX();
				float objectEndX = objectStartX + obstacle.getWidth();
				float objectStartY = obstacle.getY();
				float objectEndY = objectStartY + obstacle.getHeight();

				if (deltaPos.x != 0) {
					if (nextStartX > objectStartX && nextStartX < objectEndX) { // Collided on right.
						newX = objectEndX;
					} else if (nextEndX > objectStartX && nextEndX < objectEndX) { // Collided on left.
						newX = objectStartX - PLAYER_SIZE;
					}
				} else if (deltaPos.y != 0) {
					if (nextStartY > objectStartY && nextStartY < objectEndY) { // Collided on bottom.
						newY = objectEndY;
					} else if (nextEndY > objectStartY && nextEndY < objectEndY) { // Collided on top.
						newY = objectStartY - PLAYER_SIZE;
					}
				}
			}
		}
		newX = Math.max(newX, 0);
		newX = Math.min(newX, maze.mapWidth - PLAYER_SIZE);
		nextTilePos.setLocation(newX, newY);
		return nextTilePos;
	}

	/**
	 * Setter for {@link #verticalDir}
	 *
	 * @param verticalDir the new vertical direction of the player.
	 */
	public void setVerticalDir(VerticalDirection verticalDir) {
		this.horizontalDir = HorizontalDirection.NONE;
		direction.x = 0;

		switch (verticalDir) {
			case UP:
				direction.y = SPEED;
				this.verticalDir = verticalDir;
				break;
			case DOWN:
				direction.y = -SPEED;
				this.verticalDir = verticalDir;
				break;
			default:
				direction.y = 0;
				break;
		}
	}

	/**
	 * Setter for {@link #horizontalDir}.
	 *
	 * @param horizontalDir the new horizontal direction of the player.
	 */
	public void setHorizontalDir(HorizontalDirection horizontalDir) {
		this.verticalDir = VerticalDirection.NONE;
		direction.y = 0;

		switch (horizontalDir) {
			case LEFT:
				direction.x = -SPEED;
				this.horizontalDir = horizontalDir;
				break;
			case RIGHT:
				direction.x = SPEED;
				this.horizontalDir = horizontalDir;
				break;
			default:
				direction.x = 0;
				break;
		}
	}

	/**
	 * Getter for {@link #dead}.
	 *
	 * @return if the player is dead.
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Getter for {@link #lives}.
	 *
	 * @return how many lives the player has left.
	 */
	public int getLives() {
		return lives;
	}
}
