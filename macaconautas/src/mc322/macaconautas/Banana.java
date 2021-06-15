package mc322.macaconautas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Banana extends PecaRegular {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;
	
	private final static String SPRITE_SHEET_PATH = "/spritesheet.png";
	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 2;
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;
	private final static int QUANTIDADE_SPRITES = 1;

	/**
	 * Inicializa uma banana.
	 * @param x coordenada x da banana.
	 * @param y coordenada y da banana.
	 */
	public Banana(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		SpriteSheet spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH);
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X * SPRITE_WIDTH + (i * SPRITE_WIDTH), SPRITE_Y * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
		}
	}

	/**
	 * Renderiza a banana na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		if (this.isVisible) {
			g.drawImage(this.sprites[0], this.x, this.y, null);
			
//			g.setColor(Color.YELLOW);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}