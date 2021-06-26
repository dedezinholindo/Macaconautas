package mc322.macaconautas.Store;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class StoreBuilder {

	final static int WIDTH = Control.WIDTH; //criar classe superior
	final static int HEIGHT = Control.HEIGHT;
	final static int BORDER = Control.BORDER;
	final static int SCALE = Control.SCALE;
	final static JFrame f  = Control.f;

	final static int SKIN_QUANTITY = 5;
	final static int SKIN_SPRITE_X = 0;
	final static int SKIN_SPRITE_Y = 8;
	final static String SKIN_NAMES[] = {"Macaco", "Macaco 47", "Mico Leão Dourado", "Ninjacaco", "Macaconauta"};
	final static int SKIN_PRICES[] = {0, 2, 4, 7, 15};

	char storeState; //N normal, M para ir para o menu
	boolean isRunning;
	Thread thread;
	int currentOption;
	boolean storeRight;
	boolean storeLeft;
	boolean enter;
	int skinQuantity;
	BufferedImage skinSprites[];
	String skinNames[];
	int skinPrices[];

	public StoreBuilder(SpriteSheet spriteSheet) {
		storeState = 'N';
		isRunning = true;
		storeLeft = false;
		storeRight = false;
		currentOption = 0;
		this.skinQuantity = SKIN_QUANTITY;
		this.skinSprites = new BufferedImage[this.skinQuantity];
		for (int i = 0; i < this.skinQuantity; i++) {
			this.skinSprites[i] = spriteSheet.getSprite(SKIN_SPRITE_X, SKIN_SPRITE_Y + i);
		}
		this.skinNames = SKIN_NAMES;
		this.skinPrices = SKIN_PRICES;
	}
}
