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
 */
public class HelpScreen extends MazeScreen {

	/** The label for the instructions. */
	private Label instruct;
	/** The label telling the users about the click options. */
	private Label clicks;
	/** The array of labels for different types of gates. */
	private Label[] gates;
	/** The array of images for the different types of gates. */
	private Image[] gatePics;
	/** The table at the top for the instructions. */
	private Table table;
	/** The table at the bottom for information on how to play. */
	private Table tableBottom;
	/** The table for the different types of gates. */
	private Table gatesTable;

	/**
	 * Creates the help screen.
	 *
	 * @param game The instance for the AmazingMazeGame used.
	 */
	public HelpScreen(final AmazingMazeGame game) {
		super(game, true);
		instruct = new Label("Check the logic gates to find which circuits are on and make your\nway through the maze. Be careful and make sure not to run into\nany electrified wires!", game.assets.skin, Assets.TUTORIAL_STYLE);
		clicks = new Label("You can also mark the wires by clicking on the gates.\nLeft click is on. Right click is off. Middle click is unknown.", game.assets.skin, Assets.TUTORIAL_STYLE);
		gatesTable = new Table();
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
				new Label("AND Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label("NAND Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label("OR Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label("NOR Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label("NOT Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label("XOR Gate", game.assets.skin, Assets.TUTORIAL_STYLE),
				new Label(" The Gates: ", game.assets.skin, Assets.TUTORIAL_STYLE)};

		TextureAtlas atlas = game.assets.manager.get(Assets.GAME_ATLAS_LOCATION, TextureAtlas.class); // Reference used for readability.
		gatePics = new Image[] {
				new Image(atlas.findRegion(Assets.AND_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
				new Image(atlas.findRegion(Assets.NAND_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
				new Image(atlas.findRegion(Assets.OR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
				new Image(atlas.findRegion(Assets.NOR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
				new Image(atlas.findRegion(Assets.XOR_GATE + Assets.UNKNOWN_MODIFIER + Assets.UP_MODIFIER)),
		};
		tableBottom.add(gatesTable);
		tableBottom.row();
		tableBottom.add(clicks).pad(45);
		gatesTable.add(gates[gates.length - 1]);
		for (int x = 0; x < gatePics.length; x++) {
			gatesTable.add(gates[x]).pad(8);
			gatesTable.add(gatePics[x]);
		}
	}

	@Override
	/** Tells user what they've done wrong.
	 * @param Gate that the user died at */
	public void loseLife(int i) {
		instruct.setText("That doesn't work.\nThe gate type:" + super.gateOn.get(i).getGate() + "         The inputs:" + super.gateOn.get(i).isInputA() + " and " + super.gateOn.get(i).isInputB() + "\nThis means that the wire is electrified and you would've died.");
	}

}
