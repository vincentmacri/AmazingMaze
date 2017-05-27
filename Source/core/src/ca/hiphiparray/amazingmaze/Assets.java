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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class to easily manage assets, furthering the capabilities of an {@link AssetManager};
 *
 * @author Vincent Macri
 */
public class Assets implements Disposable {

	/** Efficiently manages game assets. */
	protected AssetManager manager;

	/** The UI skin. */
	protected Skin skin;
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

	/** The menu image. */
	protected static final String MENU_IMAGE = "menu/title.png";
	/** The menu background image. */
	protected static final String MENU_BACKGROUND_IMAGE = "menu/main.png";
	
	/** The fish mini game background image. */
	protected static final String MINI_BACKGROUND = "mini/background.png";
	/** The red fish image. */
	protected static final String FISH_RED = "items/fish-red.png";
	/** The orange fish image. */
	protected static final String FISH_ORANGE = "items/fish-orange.png";
	/** The green fish image. */
	protected static final String FISH_GREEN = "items/fish-green.png";
	/** The blue fish image. */
	protected static final String FISH_BLUE = "items/fish-blue.png";
	/** The purple fish image. */
	protected static final String FISH_PURPLE = "items/fish-purple.png";
	
	/** The pencil button image. */
	protected static final String PENCIL_BUTTON = "mini/pencil.png";
	/** The eraser button image. */
	protected static final String ERASER_BUTTON = "mini/eraser.png";
	/** The help button image. */
	protected static final String HELP_BUTTON = "mini/help.png";
	/** The check answer button image. */
	protected static final String CHECK_BUTTON = "mini/check.png";
	/** The clear button image. */
	protected static final String CLEAR_BUTTON = "mini/clear.png";

	/** The font size for small text. */
	protected static final int SMALL_FONT_SIZE = 32;
	/** The font size for regular text. */
	protected static final int REGULAR_FONT_SIZE = 64;
	/** The font size for large text. */
	protected static final int LARGE_FONT_SIZE = 128;
	
	/** The atlas name of the background tile. */
	private final String BACKGROUND = "background";
	/** The atlas name of the barrier tile. */
	private final String BARRIER = "blocked";
	/** The atlas name of the placeholder tile. */
	private final String PLACEHOLDER = "placeholder";

	/** The string appended to the end of atlas names for their on variation. */
	private final String ON_MODIFIER = "-on";
	/** The string appended to the end of atlas names for their off variation. */
	private final String OFF_MODIFIER = "-off";
	/** The string appended to the end of atlas names for their unknown variation. */
	private final String UNKNOWN_MODIFIER = "-unknown";

	/** The string appended to the end of atlas names for their up variation. */
	private final String UP_MODIFIER = "-up";
	/** The string appended to the end of atlas names for their down variation. */
	private final String DOWN_MODIFIER = "-down";
	/** The string appended to the end of atlas names for their left variation. */
	private final String LEFT_MODIFIER = "-left";
	/** The string appended to the end of atlas names for their right variation. */
	private final String RIGHT_MODIFIER = "-right";

	/** The atlas base name of the vertical wire. */
	private final String VERTICAL = "vertical";
	/** The atlas base name of the turn wire. */
	private final String TURN = "turn";

	/** The atlas base name of the AND gate. */
	private final String AND_GATE = "and";
	/** The atlas base name of the NAND gate. */
	private final String NAND_GATE = "nand";
	/** The atlas base name of the OR gate. */
	private final String OR_GATE = "or";
	/** The atlas base name of the NOR gate. */
	private final String NOR_GATE = "nor";
	/** The atlas base name of the XOR gate. */
	private final String XOR_GATE = "xor";

	/** The set of tiles used in the maps. */
	protected TiledMapTileSet tiles;

	/**
	 * {@link Assets} constructor.
	 * Calling this constructor loads in all of the game assets.
	 * As such, only one {@link Assets} instance should ever be created.
	 */
	public Assets() {
		manager = new AssetManager();

		// Allow loading FreeTypeFonts.
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		loadSkin();
		loadMapResources();
		manager.load(MENU_IMAGE, Texture.class);
		manager.load(MENU_BACKGROUND_IMAGE, Texture.class);
		manager.load(MINI_BACKGROUND, Texture.class);
		manager.load(FISH_RED, Texture.class);
		manager.load(FISH_ORANGE, Texture.class);
		manager.load(FISH_GREEN, Texture.class);
		manager.load(FISH_BLUE, Texture.class);
		manager.load(FISH_PURPLE, Texture.class);
		manager.load(PENCIL_BUTTON, Texture.class);
		manager.load(ERASER_BUTTON, Texture.class);
		manager.load(HELP_BUTTON, Texture.class);
		manager.load(CHECK_BUTTON, Texture.class);
		manager.load(CLEAR_BUTTON, Texture.class);

		manager.finishLoading();
	}

	/** Helper method for loading the map resources. */
	private void loadMapResources() {
		manager.load(TILE_ATLAS_LOCATION, TextureAtlas.class);
		manager.finishLoadingAsset(TILE_ATLAS_LOCATION);
		TextureAtlas atlas = manager.get(Assets.TILE_ATLAS_LOCATION, TextureAtlas.class); // Reference used for readability.

		tiles = new TiledMapTileSet();

		StaticTiledMapTile background = new StaticTiledMapTile(atlas.findRegion(BACKGROUND));
		StaticTiledMapTile barrier = new StaticTiledMapTile(atlas.findRegion(BARRIER));
		StaticTiledMapTile placeHolder = new StaticTiledMapTile(atlas.findRegion(PLACEHOLDER));

		tiles.putTile(TileIDs.computeID(TileIDs.BACKGROUND), background);
		tiles.putTile(TileIDs.computeID(TileIDs.BARRIER), barrier);
		tiles.putTile(TileIDs.computeID(TileIDs.PLACEHOLDER), placeHolder);

		StaticTiledMapTile verticalOn = new StaticTiledMapTile(atlas.findRegion(VERTICAL + ON_MODIFIER));
		StaticTiledMapTile verticalOff = new StaticTiledMapTile(atlas.findRegion(VERTICAL + OFF_MODIFIER));
		StaticTiledMapTile verticalUnknown = new StaticTiledMapTile(atlas.findRegion(VERTICAL + UNKNOWN_MODIFIER));
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.ON), verticalOn);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.OFF), verticalOff);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.VERTICAL, TileIDs.UNKNOWN), verticalUnknown);

		StaticTiledMapTile andUnknownUp = new StaticTiledMapTile(atlas.findRegion(AND_GATE + UNKNOWN_MODIFIER + UP_MODIFIER));
		StaticTiledMapTile nandUnknownUp = new StaticTiledMapTile(atlas.findRegion(NAND_GATE + UNKNOWN_MODIFIER + UP_MODIFIER));
		StaticTiledMapTile orUnknownUp = new StaticTiledMapTile(atlas.findRegion(OR_GATE + UNKNOWN_MODIFIER + UP_MODIFIER));
		StaticTiledMapTile norUnknownUp = new StaticTiledMapTile(atlas.findRegion(NOR_GATE + UNKNOWN_MODIFIER + UP_MODIFIER));
		StaticTiledMapTile xorUnknownUp = new StaticTiledMapTile(atlas.findRegion(XOR_GATE + UNKNOWN_MODIFIER + UP_MODIFIER));
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.AND_GATE, TileIDs.UP_GATE, TileIDs.UNKNOWN), andUnknownUp);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NAND_GATE, TileIDs.UP_GATE, TileIDs.UNKNOWN), nandUnknownUp);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.OR_GATE, TileIDs.UP_GATE, TileIDs.UNKNOWN), orUnknownUp);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NOR_GATE, TileIDs.UP_GATE, TileIDs.UNKNOWN), norUnknownUp);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.XOR_GATE, TileIDs.UP_GATE, TileIDs.UNKNOWN), xorUnknownUp);

		StaticTiledMapTile andUnknownDown = new StaticTiledMapTile(atlas.findRegion(AND_GATE + UNKNOWN_MODIFIER + DOWN_MODIFIER));
		StaticTiledMapTile nandUnknownDown = new StaticTiledMapTile(atlas.findRegion(NAND_GATE + UNKNOWN_MODIFIER + DOWN_MODIFIER));
		StaticTiledMapTile orUnknownDown = new StaticTiledMapTile(atlas.findRegion(OR_GATE + UNKNOWN_MODIFIER + DOWN_MODIFIER));
		StaticTiledMapTile norUnknownDown = new StaticTiledMapTile(atlas.findRegion(NOR_GATE + UNKNOWN_MODIFIER + DOWN_MODIFIER));
		StaticTiledMapTile xorUnknownDown = new StaticTiledMapTile(atlas.findRegion(XOR_GATE + UNKNOWN_MODIFIER + DOWN_MODIFIER));
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.AND_GATE, TileIDs.DOWN_GATE, TileIDs.UNKNOWN), andUnknownDown);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NAND_GATE, TileIDs.DOWN_GATE, TileIDs.UNKNOWN), nandUnknownDown);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.OR_GATE, TileIDs.DOWN_GATE, TileIDs.UNKNOWN), orUnknownDown);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.NOR_GATE, TileIDs.DOWN_GATE, TileIDs.UNKNOWN), norUnknownDown);
		tiles.putTile(TileIDs.computeID(TileIDs.GATE_RANGE, TileIDs.XOR_GATE, TileIDs.DOWN_GATE, TileIDs.UNKNOWN), xorUnknownDown);

		StaticTiledMapTile turnOnUpLeft = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + UP_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOffUpLeft = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + UP_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOnUpRight = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + UP_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOffUpRight = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + UP_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOnDownLeft = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + DOWN_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOffDownLeft = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + DOWN_MODIFIER + LEFT_MODIFIER));
		StaticTiledMapTile turnOnDownRight = new StaticTiledMapTile(atlas.findRegion(TURN + ON_MODIFIER + DOWN_MODIFIER + RIGHT_MODIFIER));
		StaticTiledMapTile turnOffDownRight = new StaticTiledMapTile(atlas.findRegion(TURN + OFF_MODIFIER + DOWN_MODIFIER + RIGHT_MODIFIER));
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.UP_LEFT), turnOnUpLeft);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.UP_LEFT), turnOffUpLeft);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.UP_RIGHT), turnOnUpRight);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.UP_RIGHT), turnOffUpRight);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.DOWN_LEFT), turnOnDownLeft);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.DOWN_LEFT), turnOffDownLeft);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.ON, TileIDs.DOWN_RIGHT), turnOnDownRight);
		tiles.putTile(TileIDs.computeID(TileIDs.WIRE_RANGE, TileIDs.TURN, TileIDs.OFF, TileIDs.DOWN_RIGHT), turnOffDownRight);
	}

	/** Load the UI skin. */
	private void loadSkin() {
		manager.load("menu/uiskin.json", Skin.class);
		manager.finishLoadingAsset("menu/uiskin.json"); // Make sure the skin is loaded before continuing.
		skin = manager.get("menu/uiskin.json", Skin.class); // Retrieve the skin.

		// Set the fonts. This is not done in the .json in order to allow easier adjusting of font sizes.
		skin.get(LabelStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TextButtonStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TextFieldStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(WindowStyle.class).titleFont = getFont(SANS_REGULAR, SMALL_FONT_SIZE);
	}

	/**
	 * Return the given font, if it is loaded.
	 * The font will be lazily loaded if it is not loaded already.
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
		fontParams.fontParameters.size = (int) (fontSize * Gdx.graphics.getDensity()); // Make sure font size scales correctly on different monitors.
		manager.load(fontName + fontSize + ".ttf", BitmapFont.class, fontParams);
		manager.finishLoadingAsset(fontName + fontSize + ".ttf");
		return manager.get(fontName + fontSize + ".ttf");
	}

	@Override
	public void dispose() {
		manager.dispose();
	}
}
