/********************************************************************************
 * Amazing Maze is an educational game created in Java with the libGDX library. Copyright (C) 2017 Hip Hip Array
 *
 * This file is part of Amazing Maze.
 *
 * Amazing Maze is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Amazing Maze is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class to easily manage assets, furthering the capabilities of an {@link AssetManager};
 *
 * @author Vincent Macri
 * @author Chloe Nguyen
 */
public class Assets implements Disposable {

	/** Efficiently manages game assets. */
	protected AssetManager manager;

	/** The UI skin. */
	protected Skin skin;

	/** The name of the credits header style in the UI skin. */
	protected static final String CREDITS_HEADER_STYLE = "header";
	/** The name of the credits contents style in the UI skin. */
	protected static final String CREDITS_CONTENTS_STYLE = "contents";
	/** The name of the credits small contents style in the UI skin. */
	protected static final String CREDITS_SMALL_CONTENTS_STYLE = "small-contents";
	/** The name of the tutorial label style. */
	protected static final String TUTORIAL_SYTLE = "tutorial";
	/** The name of the story label style. */
	protected static final String STORY_STYLE = "story";

	/** The location of the tile atlas. */
	protected static final String TILE_ATLAS_LOCATION = "tiles/tiles.atlas";

	/** The regular monospace font. */
	protected static final String MONO_REGULAR = "LiberationMono-Regular";
	/** The bold monospace font. */
	protected static final String MONO_BOLD = "LiberationMono-Bold";
	/** The italic monospace font. */
	protected static final String MONO_ITALIC = "LiberationMono-Italic";
	/** The bold italic monospace font. */
	protected static final String MONO_BOLD_ITALIC = "LiberationMono-BoldItalic";

	/** The regular sans font. */
	protected static final String SANS_REGULAR = "LiberationSans-Regular";
	/** The bold sans font. */
	protected static final String SANS_BOLD = "LiberationSans-Bold";
	/** The italic sans font. */
	protected static final String SANS_ITALIC = "LiberationSans-Italic";
	/** The bold italic sans font. */
	protected static final String SANS_BOLD_ITALIC = "LiberationSans-BoldItalic";

	/** The regular serif font. */
	protected static final String SERIF_REGULAR = "LiberationSerif-Regular";
	/** The bold serif font. */
	protected static final String SERIF_BOLD = "LiberationSerif-Bold";
	/** The italic serif font. */
	protected static final String SERIF_ITALIC = "LiberationSerif-Italic";
	/** The bold italic serif font. */
	protected static final String SERIF_BOLD_ITALIC = "LiberationSerif-BoldItalic";

	/** The game logo. */
	protected static final String GAME_LOGO = "logos/game.png";
	/** The company logo. */
	protected static final String COMPANY_LOGO = "logos/company.png";
	/** The life HUD image. */
	protected static final String LIFE_HUD_IMAGE = "hud/life.png";

	/** The font size for small text. */
	protected static final int SMALL_FONT_SIZE = 32;
	/** The font size for regular text. */
	protected static final int REGULAR_FONT_SIZE = 64;
	/** The font size for large text. */
	protected static final int LARGE_FONT_SIZE = 128;

	/** The file name of the song that plays on the menu screen. */
	protected static final String MENU_SONG = "music/SecretsOfTheSchoolyard.mp3";
	/** The file name of the song that plays on the story screen. */
	protected static final String STORY_SONG = "music/Vanes.mp3";
	/** The file name of the song that plays in the maze. */
	protected static final String MAZE_SONG = "music/LightlessDawn.mp3";
	/** The file name of the song that plays on the math screen. */
	protected static final String MATH_SONG = "music/Babylon.mp3";
	/** The file name of the song that plays during the credits. */
	protected static final String CREDITS_SONG = "music/DigitalLemonade.mp3";

	/** The atlas name of the background tile. */
	private static final String BACKGROUND = "background";
	/** The atlas name of the barrier tile. */
	private static final String BARRIER = "blocked";
	/** The atlas name of the placeholder tile. */
	private static final String PLACEHOLDER = "placeholder";
	/** The atlas name of the mouse. */
	protected static final String MOUSE = "mouse";

	/** The string appended to the end of atlas names for their on variation. */
	private static final String ON_MODIFIER = "-on";
	/** The string appended to the end of atlas names for their off variation. */
	private static final String OFF_MODIFIER = "-off";
	/** The string appended to the end of atlas names for their unknown variation. */
	private static final String UNKNOWN_MODIFIER = "-unknown";

	/** The string appended to the end of atlas names for their up variation. */
	private static final String UP_MODIFIER = "-up";
	/** The string appended to the end of atlas names for their down variation. */
	private static final String DOWN_MODIFIER = "-down";
	/** The string appended to the end of atlas names for their left variation. */
	private static final String LEFT_MODIFIER = "-left";
	/** The string appended to the end of atlas names for their right variation. */
	private static final String RIGHT_MODIFIER = "-right";

	/** The atlas base name of the vertical wire. */
	private static final String VERTICAL = "vertical";
	/** The atlas base name of the turn wire. */
	private static final String TURN = "turn";

	/** The atlas base name of the AND gate. */
	private static final String AND_GATE = "and";
	/** The atlas base name of the NAND gate. */
	private static final String NAND_GATE = "nand";
	/** The atlas base name of the OR gate. */
	private static final String OR_GATE = "or";
	/** The atlas base name of the NOR gate. */
	private static final String NOR_GATE = "nor";
	/** The atlas base name of the XOR gate. */
	private static final String XOR_GATE = "xor";

	/** The atlas base name of the fish. */
	protected static final String FISH = "fish";

	/** The string appended to the end of the fish atlas name for the blue variant. */
	protected static final String BLUE_MODIFIER = "-blue";
	/** The string appended to the end of the fish atlas name for the blue variant. */
	protected static final String PURPLE_MODIFIER = "-purple";
	/** The string appended to the end of the fish atlas name for the blue variant. */
	protected static final String GREEN_MODIFIER = "-green";
	/** The string appended to the end of the fish atlas name for the blue variant. */
	protected static final String RED_MODIFIER = "-red";
	/** The string appended to the end of the fish atlas name for the blue variant. */
	protected static final String ORANGE_MODIFIER = "-orange";

	/** The set of tiles used in the maps. */
	protected TiledMapTileSet tiles;

	/**
	 * {@link Assets} constructor. Calling this constructor loads in all of the game assets. As such, only one {@link Assets} instance should ever be created.
	 */
	public Assets() {
		manager = new AssetManager();

		// Allow loading FreeTypeFonts.
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		loadSkin();
		loadMapResources();
		manager.load(GAME_LOGO, Texture.class);
		manager.load(COMPANY_LOGO, Texture.class);
		manager.load(LIFE_HUD_IMAGE, Texture.class);
		loadMusic();

		manager.finishLoading();
	}

	/** Helper method to load the game's music. */
	private void loadMusic() {
		manager.load(MENU_SONG, Music.class);
		manager.load(STORY_SONG, Music.class);
		manager.load(MAZE_SONG, Music.class);
		manager.load(MATH_SONG, Music.class);
		manager.load(CREDITS_SONG, Music.class);
	}

	/** Helper method for loading the map resources. */
	private void loadMapResources() {
		manager.load(TILE_ATLAS_LOCATION, TextureAtlas.class);
		manager.finishLoadingAsset(TILE_ATLAS_LOCATION);
		TextureAtlas atlas = manager.get(Assets.TILE_ATLAS_LOCATION, TextureAtlas.class); // Reference used for readability.

		tiles = new TiledMapTileSet();

		StaticTiledMapTile background = new StaticTiledMapTile(atlas.findRegion(BACKGROUND));
		StaticTiledMapTile barrier = new StaticTiledMapTile(atlas.findRegion(BARRIER));
		StaticTiledMapTile placeholder = new StaticTiledMapTile(atlas.findRegion(PLACEHOLDER));

		background.setId(TileIDs.computeID(TileIDs.BACKGROUND));
		barrier.setId(TileIDs.computeID(TileIDs.BARRIER));
		placeholder.setId(TileIDs.computeID(TileIDs.PLACEHOLDER));

		tiles.putTile(background.getId(), background);
		tiles.putTile(barrier.getId(), barrier);
		tiles.putTile(placeholder.getId(), placeholder);

		StaticTiledMapTile verticalOn = new StaticTiledMapTile(atlas.findRegion(VERTICAL + ON_MODIFIER));
		StaticTiledMapTile verticalOff = new StaticTiledMapTile(atlas.findRegion(VERTICAL + OFF_MODIFIER));
		StaticTiledMapTile verticalUnknown = new StaticTiledMapTile(atlas.findRegion(VERTICAL + UNKNOWN_MODIFIER));

		verticalOn.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.ON));
		verticalOff.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.OFF));
		verticalUnknown.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.UNKNOWN));

		tiles.putTile(verticalOn.getId(), verticalOn);
		tiles.putTile(verticalOff.getId(), verticalOff);
		tiles.putTile(verticalUnknown.getId(), verticalUnknown);

		loadGates(atlas, ON_MODIFIER, UP_MODIFIER, TileIDs.ON, TileIDs.UP_GATE);
		loadGates(atlas, ON_MODIFIER, DOWN_MODIFIER, TileIDs.ON, TileIDs.DOWN_GATE);
		loadGates(atlas, OFF_MODIFIER, UP_MODIFIER, TileIDs.OFF, TileIDs.UP_GATE);
		loadGates(atlas, OFF_MODIFIER, DOWN_MODIFIER, TileIDs.OFF, TileIDs.DOWN_GATE);
		loadGates(atlas, UNKNOWN_MODIFIER, UP_MODIFIER, TileIDs.UNKNOWN, TileIDs.UP_GATE);
		loadGates(atlas, UNKNOWN_MODIFIER, DOWN_MODIFIER, TileIDs.UNKNOWN, TileIDs.DOWN_GATE);

		StaticTiledMapTile turnOnUpLeft = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + UP_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOffUpLeft = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + UP_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOnUpRight = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + UP_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOffUpRight = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + UP_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOnDownLeft = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + DOWN_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOffDownLeft = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + DOWN_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOnDownRight = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + DOWN_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOffDownRight = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + DOWN_MODIFIER + RIGHT_MODIFIER));

		turnOnUpLeft.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.UP_LEFT));
		turnOffUpLeft.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.UP_LEFT));
		turnOnUpRight.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.UP_RIGHT));
		turnOffUpRight.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.UP_RIGHT));
		turnOnDownLeft.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.DOWN_LEFT));
		turnOffDownLeft.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.DOWN_LEFT));
		turnOnDownRight.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.DOWN_RIGHT));
		turnOffDownRight.setId(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.DOWN_RIGHT));

		tiles.putTile(turnOnUpLeft.getId(), turnOnUpLeft);
		tiles.putTile(turnOffUpLeft.getId(), turnOffUpLeft);
		tiles.putTile(turnOnUpRight.getId(), turnOnUpRight);
		tiles.putTile(turnOffUpRight.getId(), turnOffUpRight);
		tiles.putTile(turnOnDownLeft.getId(), turnOnDownLeft);
		tiles.putTile(turnOffDownLeft.getId(), turnOffDownLeft);
		tiles.putTile(turnOnDownRight.getId(), turnOnDownRight);
		tiles.putTile(turnOffDownRight.getId(), turnOffDownRight);

		loadFish(atlas, BLUE_MODIFIER, TileIDs.BLUE);
		loadFish(atlas, PURPLE_MODIFIER, TileIDs.PURPLE);
		loadFish(atlas, GREEN_MODIFIER, TileIDs.GREEN);
		loadFish(atlas, RED_MODIFIER, TileIDs.RED);
		loadFish(atlas, ORANGE_MODIFIER, TileIDs.ORANGE);
	}

	/**
	 * Load the fish with the given parameters.
	 *
	 * @param atlas the {@link TextureAtlas} to load from.
	 * @param colourName the name of the colour in the atlas.
	 * @param colourID the ID of the colour in {@link TileIDs}.
	 */
	private void loadFish(TextureAtlas atlas, String colourName, int colourID) {
		StaticTiledMapTile fish = new StaticTiledMapTile(atlas.findRegion(FISH + colourName));
		fish.setId(TileIDs.computeID(TileIDs.POWERUP_RANGE, TileIDs.FISH, colourID));
		tiles.putTile(fish.getId(), fish);
	}

	/**
	 * Load all gates for the given parameters.
	 *
	 * @param atlas the {@link TextureAtlas} to load from.
	 * @param electricStateName the name of the electrical state of the gates to load.
	 * @param directionName the name of the direction of the gates to load.
	 * @param electricID the ID of the electrical state of the gates to load.
	 * @param directionID the directional ID of the gates to load.
	 */
	private void loadGates(TextureAtlas atlas, String electricStateName, String directionName, int electricID, int directionID) {
		StaticTiledMapTile and = new StaticTiledMapTile(atlas.findRegion(AND_GATE + electricStateName + directionName));
		StaticTiledMapTile nand = new StaticTiledMapTile(atlas.findRegion(NAND_GATE + electricStateName + directionName));
		StaticTiledMapTile or = new StaticTiledMapTile(atlas.findRegion(OR_GATE + electricStateName + directionName));
		StaticTiledMapTile nor = new StaticTiledMapTile(atlas.findRegion(NOR_GATE + electricStateName + directionName));
		StaticTiledMapTile xor = new StaticTiledMapTile(atlas.findRegion(XOR_GATE + electricStateName + directionName));

		and.setId(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.AND_GATE, directionID, electricID));
		nand.setId(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NAND_GATE, directionID, electricID));
		or.setId(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.OR_GATE, directionID, electricID));
		nor.setId(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NOR_GATE, directionID, electricID));
		xor.setId(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.XOR_GATE, directionID, electricID));

		tiles.putTile(and.getId(), and);
		tiles.putTile(nand.getId(), nand);
		tiles.putTile(or.getId(), or);
		tiles.putTile(nor.getId(), nor);
		tiles.putTile(xor.getId(), xor);
	}

	/** Load the UI skin. */
	private void loadSkin() {
		manager.load("ui/uiskin.json", Skin.class);
		manager.finishLoadingAsset("ui/uiskin.json"); // Make sure the skin is loaded before continuing.
		skin = manager.get("ui/uiskin.json", Skin.class); // Retrieve the skin.

		// Set the fonts. This is not done in the .json in order to allow easier adjusting of font size
		skin.get(LabelStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TextButtonStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);

		skin.get(CREDITS_HEADER_STYLE, LabelStyle.class).font = getFont(SERIF_BOLD, LARGE_FONT_SIZE);
		skin.get(CREDITS_CONTENTS_STYLE, LabelStyle.class).font = getFont(SERIF_REGULAR, LARGE_FONT_SIZE);
		skin.get(CREDITS_SMALL_CONTENTS_STYLE, LabelStyle.class).font = getFont(SERIF_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TUTORIAL_SYTLE, LabelStyle.class).font = getFont(SANS_REGULAR, SMALL_FONT_SIZE);
		skin.get(STORY_STYLE, LabelStyle.class).font = getFont(SERIF_REGULAR, REGULAR_FONT_SIZE);
		skin.get(CheckBoxStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
	}

	/**
	 * Return the given font, if it is loaded. The font will be lazily loaded if it is not loaded already.
	 *
	 * @param fontName the name of the font.
	 * @param fontSize the size of the font.
	 * @return the desired {@link BitmapFont}.
	 */
	protected BitmapFont getFont(String fontName, int fontSize) {
		if (manager.containsAsset(fontName + fontSize + ".ttf")) {
			return manager.get(fontName + fontSize + ".ttf");
		}
		FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();
		fontParams.fontFileName = "fonts/" + fontName + ".ttf";
		float screenSize = Math.min(Gdx.graphics.getWidth() / 1920f, Gdx.graphics.getHeight() / 1080f);
		fontParams.fontParameters.size = (int) (fontSize * Gdx.graphics.getDensity() * screenSize);
		manager.load(fontName + fontSize + ".ttf", BitmapFont.class, fontParams);
		manager.finishLoadingAsset(fontName + fontSize + ".ttf");
		return manager.get(fontName + fontSize + ".ttf");
	}

	@Override
	public void dispose() {
		manager.dispose();
	}
}
