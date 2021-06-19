package mc322.macaconautas.Loja;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class MontadorLoja {

	final static int WIDTH = Controle.WIDTH; //criar classe superior
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	final static JFrame f  = Controle.f;

	final static int SKIN_QUANTITY = 3;
	final static int SKIN_SPRITE_X = 0;
	final static int SKIN_SPRITE_Y = 8;
	final static String SKIN_NAMES[] = {"Macaco", "Macaco 47", "Mico Leão Dourado"};
	final static int SKIN_PRICES[] = {0, 2, 4};

	boolean[] skinsLiberadas = {true, false, false};
	char lojaState; //N normal, M para ir para o menu
	int selectedSkin;
	boolean isRunning;
	Thread thread;
	int currentOption;
	boolean lojaRight;
	boolean lojaLeft;
	boolean enter;
	int skinQuantity;
	BufferedImage skinSprites[];
	String skinNames[];
	int skinPrices[];

	public MontadorLoja(SpriteSheet spriteSheet) {
		lojaState = 'N';
		isRunning = true;
		lojaLeft = false;
		lojaRight = false;
		selectedSkin = 0;
		this.skinQuantity = SKIN_QUANTITY;
		this.skinSprites = new BufferedImage[this.skinQuantity];
		for (int i = 0; i < this.skinQuantity; i++) {
			this.skinSprites[i] = spriteSheet.getSprite(SKIN_SPRITE_X, SKIN_SPRITE_Y + i);
		}
		this.skinNames = SKIN_NAMES;
		this.skinPrices = SKIN_PRICES;
	}
}
