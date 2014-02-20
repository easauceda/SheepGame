

import java.awt.Color;


public interface GameSettings {
	int B_WIDTH = 400;
	int B_HEIGHT = 400;
	int X_MARGIN = 20;
	int Y_MARGIN = 20;
	
	int windowSizeY = 500;
	int windowSizeX = 400;
	
	int numberOfWolfs = 2;
	int numberOfSheeps = 20;
	
	int max_X = 20;
	int max_Y = 20;
	
	int INIT_SHEEP_X = 5;
	int INIT_SHEEP_Y = 5;
	int INIT_WOLF_X  = 0;
	int INIT_WOLF_Y = 0;
	
	int GAME_SPEED = 500;
	int BOARD_REFRESH_RATE = 50;
	
	Color SHEEP_COLOR = new Color(255,255,255);
	Color WOLF_COLOR = Color.BLACK;
}
