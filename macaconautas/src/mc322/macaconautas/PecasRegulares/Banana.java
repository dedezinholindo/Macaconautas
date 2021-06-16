package mc322.macaconautas.PecasRegulares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mc322.macaconautas.app.SpriteSheet;

public class Banana extends PecaRegular {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 2;
	private final static int QUANTIDADE_SPRITES = 1;

	/**
	 * Inicializa uma banana.
	 * @param x coordenada x da banana.
	 * @param y coordenada y da banana.
	 */
	public Banana(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y);
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