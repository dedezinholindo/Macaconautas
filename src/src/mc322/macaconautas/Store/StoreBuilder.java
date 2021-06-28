package mc322.macaconautas.Store;

import java.awt.image.BufferedImage;

import mc322.macaconautas.app.SpriteSheet;

public class StoreBuilder {

	private final static int SKIN_SPRITE_X = 0;
	private final static int SKIN_SPRITE_Y = 8;
	private final static String SKIN_OPTIONS[] = {"Albert", "Macaco 47", "Mico Leão Dourado", "Ninjacaco", "Macaconauta"};
	private final static int SKIN_PRICES[] = {0, 5, 10, 18, 30};

	char state; // N normal, M para ir para o menu
	boolean isRunning;
	Thread thread;
	int bananaQuantity;
	boolean ownedSkins[];
	int selectedSkin;
	String skinOptions[];
	int skinPrices[];
	BufferedImage skinSprites[];
	int currentOption;
	boolean goRight;
	boolean goLeft;
	boolean select;

	/**
	 * Inicializa um StoreBuilder.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param ownedSkins indica skins possuídas.
	 * @param selectedSkin skin selecionada.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public StoreBuilder(int bananaQuantity, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		this.state = 'N';
		this.isRunning = true;
		this.bananaQuantity = bananaQuantity;
		this.ownedSkins = ownedSkins;
		this.selectedSkin = selectedSkin;
		this.skinOptions = SKIN_OPTIONS;
		this.skinPrices = SKIN_PRICES;
		this.skinSprites = new BufferedImage[this.skinOptions.length];
		for (int i = 0; i < this.skinSprites.length; i++) {
			this.skinSprites[i] = spriteSheet.getSprite(SKIN_SPRITE_X, SKIN_SPRITE_Y + i);
		}
		this.currentOption = 0;
		this.goLeft = false;
		this.goRight = false;
		this.select = false;
	}
}