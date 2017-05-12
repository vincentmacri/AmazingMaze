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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class to easily manage assets, furthering the capabilities of an {@link AssetManager};
 *
 * @author Vincent Macri
 */
public class Assets implements Disposable {

	/** Possible states of electrical components. */
	protected enum ElectricStatus {
		/** An electrical component which is on. */
		ON,
		/** An electrical component which is off. */
		OFF,
		/** An electrical component which the player does not know the state of. */
		UNKNOWN,
		/** An electrical component which the player thinks is on. */
		MAYBE_ON,
		/** An electrical component which the player thinks is off. */
		MAYBE_OFF
	}

	/** Possible different electrical components. */
	protected enum Component {
		/** A vertical wire. */
		VERTICAL,
		/** A horizontal wire. */
		HORIZONTAL,
		/** A cross shaped wire. */
		CROSS,
		/** An L wire that turns from up to right. */
		UP_RIGHT_TURN,
		/** An L wire that turns from down to right. */
		DOWN_RIGHT_TURN,
		/** An L wire that turns from down to left. */
		UP_LEFT_TURN,
		/** An L wire that turns from down to left. */
		DOWN_LEFT_TURN,
		/** A T wire that connects horizontally and up. */
		HORIZONTAL_UP_T,
		/** A T wire that connects horizontally and down. */
		HORIZONTAL_DOWN_T,
		/** A T wire that connects vertically and left. */
		VERTICAL_LEFT_T,
		/** A T wire that connects vertically and right. */
		VERTICAL_RIGHT_T
	}

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

	/** The font size for small text. */
	protected static final int SMALL_FONT_SIZE = 32;
	/** The font size for regular text. */
	protected static final int REGULAR_FONT_SIZE = 64;
	/** The font size for large text. */
	protected static final int LARGE_FONT_SIZE = 128;

	/** The tile for the background of the maps. */
	protected StaticTiledMapTile backgroundTile;
	protected StaticTiledMapTile wireTile; // TODO: Replace with methods for fetching electrical components.
	/** A placeholder tile to be used when testing new features. */
	protected StaticTiledMapTile placeHolderTile; // TODO: Remove

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

		manager.finishLoading();
	}

	/** Helper method for loading the map resources. */
	private void loadMapResources() {
		manager.load(TILE_ATLAS_LOCATION, TextureAtlas.class);
		manager.finishLoadingAsset(TILE_ATLAS_LOCATION);
		this.backgroundTile = new StaticTiledMapTile(manager.get(Assets.TILE_ATLAS_LOCATION, TextureAtlas.class).findRegion("empty"));
		this.placeHolderTile = new StaticTiledMapTile(manager.get(Assets.TILE_ATLAS_LOCATION, TextureAtlas.class).findRegion("placeholder"));
		this.wireTile = new StaticTiledMapTile(manager.get(Assets.TILE_ATLAS_LOCATION, TextureAtlas.class).findRegion("cross-unknown"));
	}

	/** Load the UI skin. */
	private void loadSkin() {
		manager.load("menu/uiskin.json", Skin.class);
		manager.finishLoadingAsset("menu/uiskin.json"); // Make sure the skin is loaded before continuing.
		skin = manager.get("menu/uiskin.json", Skin.class); // Retrieve the skin.

		// Set the fonts. This is not done in the .json in order to allow easier adjusting of font sizes.
		skin.get(LabelStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TextButtonStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
	}

	/**
	 * Load a new font into {@link #manager}.
	 *
	 * @param fontName the name of the font in the fonts folder, without the .ttf extension.
	 * @param fontSize the font size of the new font.
	 */
	private void loadFont(String fontName, int fontSize) {
		FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();
		fontParams.fontFileName = "fonts/" + fontName + ".ttf";
		fontParams.fontParameters.size = (int) (fontSize * Gdx.graphics.getDensity()); // Make sure font size scales correctly on different monitors.
		manager.load(fontName + fontSize + ".ttf", BitmapFont.class, fontParams);
		manager.finishLoadingAsset(fontName + fontSize + ".ttf");
	}

	/**
	 * Return the given font.
	 *
	 * @param name the name of the font.
	 * @param size the size of the font.
	 * @return the desired BitMapFont.
	 */
	protected BitmapFont getFont(String name, int size) {
		if (manager.containsAsset(name + size + ".ttf")) {
			return manager.get(name + size + ".ttf");
		}
		loadFont(name, size);
		return manager.get(name + size + ".ttf");
	}

	@Override
	public void dispose() {
		manager.dispose();
	}
}
