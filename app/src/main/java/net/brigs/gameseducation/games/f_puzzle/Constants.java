package net.brigs.gameseducation.games.f_puzzle;

import net.brigs.gameseducation.R;
import net.brigs.gameseducation.R.drawable;

public interface Constants {

	// Background resources id
	public static final int[] BACKGROUND_RES = { R.drawable.card_table_texture,
			R.drawable.background_android,
			drawable.background_sunset };

	// Default values for gameplay specification
	public static final int GAMEBOARD_N_ROWS = 4, GAMEBOARD_N_ROWS_MAX = 5,
			GAMEBOARD_N_ROWS_DEF = 4, GAMEBOARD_N_ROWS_MIN = 2;
	public static final int GAMEBOARD_N_COLUMNS = 4,
			GAMEBOARD_N_COLUMNS_DEF = 4, GAMEBOARD_N_COLUMNS_MAX = 5,
			GAMEBOARD_N_COLUMNS_MIN = 2;

	public static final int INIT_NSTEPS_DEF = 0;
	// value to enable the game initiator and save list constructor to determine
	// whether it's a fresh new game or should load from previous state
	public static final String NO_PREV_GAME = null;
	// value to enable the TileState constructor to determine whether it should
	// save current state
	public static final String SAVE_STATE_CURRENT = null;

	public static final int RANDOMIZE_MULTIPLIER = 1000;

	// specification for gameplay model
	public static final int GAME_ONGOING = 0;
	public static final int GAME_PAUSE = 1;
	public static final int GAME_WON = 2;
	public static final int EMPTY_TAG_IDENTIFIER = -1;
	public static final int NULL_TAG_IDENTIFIER = -100;

	// Tile relative positions
	public static final int TILE_ON_LEFT = 1;
	public static final int TILE_ON_RIGHT = 2;
	public static final int TILE_ABOVE = 3;
	public static final int TILE_BELOW = 4;
	public static final int TILE_NOT_AROUND = -1;

	// specification for animation translation
	public static final double MOVED_ACROSS_PERCENTAGE = 0.3;

	// save and load
	public static final char SAVE_DATA_TILE_VALUE_INTERVAL = ' ';
	public static final char SAVE_DATA_TILE_STATE_INTERVAL = ';';

	// keys for sharedpreferences
	public static final String KEY_N_ROWS = "KEY_N_ROWS";
	public static final String KEY_N_COLUMNS = "KEY_N_COLUMNS";
	public static final String KEY_N_STEPS = "KEY_N_STEPS";
	public static final String KEY_SAVE_LIST = "KEY_SAVE_LIST";

	// dialog IDs
	public static final int DIALOG_ID_GAME_PLAY_PAUSE_DIALOG = 0;
	public static final int DIALOG_ID_NEW_GAME_DIALOG = 1;

}
