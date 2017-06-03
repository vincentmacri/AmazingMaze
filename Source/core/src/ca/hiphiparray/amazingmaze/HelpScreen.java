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

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The tutorial screen. This is where users learn how to play the game.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 * <br>
 * Time (Chloe): 11 hours
 * <br>
 * Time (Vincent): 45 minutes
 * @since 0.3
 */
public class HelpScreen extends MazeScreen {

	/** The label for the instructions. */
	private Label instruct;
	/** The label telling the users about the click options. */
	private Label clicks;
	/** The array of labels for different types of gates. */
	private Label[] gates;
	/** The array of labels for the truth table. */
	private Label[][] truth;
	/** The array of images for the different types of gates. */
	private Image[] gatePics;
	/** The table at the top for the instructions. */
	private Table table;
	/** The table for the truth table. */
	private Table truthTable;

	/**
	 * Creates the help screen.
	 *
	 * @param game The instance for the AmazingMazeGame used.
	 */
	public HelpScreen(final AmazingMazeGame game) {
		super(game, true);
		instruct = new Label("Check the logic gates to find the state of each wire and make your way through the maze. Be careful to avoid\nany wires that are on, as that means that they are electrified!", game.assets.skin, Assets.HUD_STYLE);
		instruct.setAlignment(Align.center);
		clicks = new Label("Mark the wires by clicking on the gates. Left click = on. Right click = off. Middle click = unknown.", game.assets.skin, Assets.HUD_STYLE);
		clicks.setAlignment(Align.center);

		super.hud = new Stage(new ScreenViewport(), game.batch);

		table = new Table();
		table.top();
		table.setFillParent(true);
		super.hud.addActor(table);

		table.add(instruct).top().pad(10);
		table.row();
		table.add(clicks).pad(10);
		table.row();
		table.add().expand();
		table.row();

		gates = new Label[] {
			new Label("AND Gate", game.assets.skin, Assets.HUD_STYLE),
			new Label("NAND Gate", game.assets.skin, Assets.HUD_STYLE),
			new Label("OR Gate", game.assets.skin, Assets.HUD_STYLE),
			new Label("NOR Gate", game.assets.skin, Assets.HUD_STYLE),
			new Label("XOR Gate", game.assets.skin, Assets.HUD_STYLE)};

		TextureAtlas atlas = game.assets.manager.get(Assets.GAME_ATLAS_LOCATION, TextureAtlas.class); // Reference used for readability.
		gatePics = new Image[] {
			new Image(atlas.findRegion(Assets.AND_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
			new Image(atlas.findRegion(Assets.NAND_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
			new Image(atlas.findRegion(Assets.OR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
			new Image(atlas.findRegion(Assets.NOR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
			new Image(atlas.findRegion(Assets.XOR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
		};

		truthTable = new Table();
		truthTable.top().center();
		table.add(truthTable).bottom().pad(10);

		truthTable.add();
		for (int x = 0; x < gatePics.length; x++) {
			truthTable.add(gatePics[x]).size(gatePics[x].getWidth() * 2, gatePics[x].getHeight() * 2).pad(5);
		}
		truthTable.row();
		truthTable.add();
		for (int x = 0; x < gates.length; x++) {
			truthTable.add(gates[x]).pad(5);
		}
		truthTable.row();

		truth = new Label[][] {
			{
				new Label("false and false", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE)},
			{
				new Label("true and false", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE)},
			{
				new Label("true and true", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("on", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE),
				new Label("off", game.assets.skin, Assets.HUD_STYLE)}
		};

		for (int x = 0; x < truth.length; x++) {
			for (int y = 0; y < truth[x].length; y++) {
				truthTable.add(truth[x][y]).pad(5);
			}
			truthTable.row();
		}
	}

	@Override
	public void updateLives(int gate) {
		if (gate >= 0) {
			instruct.setText("That doesn't work!\nThe gate type: " + super.gateOn.get(gate).getGate() + "         The inputs: " + super.gateOn.get(gate).isInputA() + " and " + super.gateOn.get(gate).isInputB() + "\nThis means that the wire is electrified and you would've died.");
		}
	}

}
