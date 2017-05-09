package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class AmazingMazeGame extends Game {

	protected static final String MONO_BOLD = "LiberationMono-Bold";
	protected static final String MONO_BOLD_ITALIC = "LiberationMono-BoldItalic";
	protected static final String MONO_ITALIC = "LiberationMono-Italic";
	protected static final String MONO_REGULAR = "LiberationMono-Regular";
	protected static final String SANS_BOLD = "LiberationSans-Bold";
	protected static final String SANS_BOLD_ITALIC = "LiberationSans-BoldItalic";
	protected static final String SANS_ITALIC = "LiberationSans-Italic";
	protected static final String SANS_REGULAR = "LiberationSans-Regular";
	protected static final String SERIF_BOLD = "LiberationSerif-Bold";
	protected static final String SERIF_BOLD_ITALIC = "LiberationSerif-BoldItalic";
	protected static final String SERIF_ITALIC = "LiberationSerif-Italic";
	protected static final String SERIF_REGULAR = "LiberationSerif-Regular";

	protected static final int SMALL_FONT_SIZE = 32;
	protected static final int REGULAR_FONT_SIZE = 64;
	protected static final int LARGE_FONT_SIZE = 128;

	/** The SpriteBatch to use for drawing. */
	protected SpriteBatch batch;
	/** Efficiently manages game assets. */
	protected AssetManager assets;
	/** The UI skin. */
	protected Skin skin;

	/** The main menu screen. */
	protected MainMenuScreen menuScreen;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new AssetManager();

		loadAssets();

		menuScreen = new MainMenuScreen(this);

		MazeScreen maze = new MazeScreen(this); // For testing only.
		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		super.dispose();
	}

	private void loadAssets() {
		// Allow loading FreeTypeFonts.
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		loadSkin();
	}

	/** Load the UI skin. */
	private void loadSkin() {
		assets.load("menu/uiskin.json", Skin.class);
		assets.finishLoadingAsset("menu/uiskin.json"); // Make sure the skin is loaded before continuing.
		skin = assets.get("menu/uiskin.json", Skin.class); // Retrieve the skin.

		// Set the fonts. This is not done in the .json in order to allow easier adjusting of font sizes.
		skin.get(LabelStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
		skin.get(TextButtonStyle.class).font = getFont(SANS_REGULAR, REGULAR_FONT_SIZE);
	}

	/**
	 * Load a new font into {@link #assets}.
	 *
	 * @param fontName the name of the font in the fonts folder, without the .ttf extension.
	 * @param fontSize the font size of the new font.
	 */
	private void loadFont(String fontName, int fontSize) {
		FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();
		fontParams.fontFileName = "fonts/" + fontName + ".ttf";
		fontParams.fontParameters.size = (int) (fontSize * Gdx.graphics.getDensity()); // Make sure font size scales correctly on different monitors.
		assets.load(fontName + fontSize + ".ttf", BitmapFont.class, fontParams);
	}

	/**
	 * Return the given font, if loaded into {@link #assets}.
	 *
	 * @param name the name of the font.
	 * @param size the size of the font.
	 * @return the desired BitMapFont.
	 */
	protected BitmapFont getFont(String name, int size) {
		if (assets.containsAsset(name + size + ".ttf")) {
			return assets.get(name + size + ".ttf");
		}
		loadFont(name, size);
		assets.finishLoading();
		return assets.get(name + size + ".ttf");
	}

}
