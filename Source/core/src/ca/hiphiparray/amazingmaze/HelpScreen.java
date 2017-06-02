package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The tutorial screen. This is where users learn how to play the game.
 *
 * @author Chloe Nguyen
 * @since 0.3
 * Time (Chloe): 11 hours
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
	/** The table at the bottom for information on how to play. */
	private Table tableBottom;
	/** The table for the different types of gates. */
	private Table gatesTable;
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
		clicks = new Label("Mark the wires by clicking on the gates. Left click = on. Right click = off. Middle click = unknown.", game.assets.skin, Assets.HUD_STYLE);

		gatesTable = new Table();
		truthTable = new Table();
		table = new Table();
		tableBottom = new Table();
		table.top().left();
		tableBottom.bottom().left();
		table.setFillParent(true);
		tableBottom.setFillParent(true);
		super.hud = new Stage(new ScreenViewport(), game.batch);
		super.hud.addActor(table);
		super.hud.addActor(tableBottom);
		table.add(instruct).pad(45);
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
		tableBottom.add(gatesTable).center().right().padRight(205);
		tableBottom.row();
		truthTable.top().left();
		tableBottom.add(truthTable).left().padLeft(195);

		for (int x = 0; x < gatePics.length; x++) {
			gatePics[x].setScale(2);
			gatesTable.add(gatePics[x]).left().padLeft(50);
		}
		gatesTable.row();
		for (int x = 0; x < gatePics.length; x++) {
			gatesTable.add(gates[x]).left().padLeft(46);
		}

		truth = new Label[][] {{
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
						new Label("off", game.assets.skin, Assets.HUD_STYLE)}};

		for (int x = 0; x < truth.length; x++) {
			// gatesTable.left();
			for (int y = 0; y < truth[0].length; y++) {

				truthTable.add(truth[x][y]).left().padLeft(129);

			}
			truthTable.row();
		}
		tableBottom.row();
		tableBottom.add(clicks).padLeft(45).padTop(10);
	}

	@Override
	/** Tells user what they've done wrong.
	 * @param Gate that the user died at */
	public void loseLife(int i) {
		instruct.setText("That doesn't work!\nThe gate type: " + super.gateOn.get(i).getGate() + "         The inputs: " + super.gateOn.get(i).isInputA() + " and " + super.gateOn.get(i).isInputB() + "\nThis means that the wire is electrified and you would've died.");
	}

}
