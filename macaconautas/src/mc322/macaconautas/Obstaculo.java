package mc322.macaconautas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Obstaculo extends PecaRegular {

	public final static int WIDTH = 40;
	public final static int HEIGHT = 40;

	private final static String SPRITE_SHEET_PATH = "/spritesheet.png";
	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 1;
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;
	private final static int QUANTIDADE_SPRITES = 1;

	/**
	 * Inicializa um obstáculo.
	 * @param x coordenada x do obstáculo.
	 * @param y coordenada y do obstáculo.
	 */
	public Obstaculo(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		SpriteSheet spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH);
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X * SPRITE_WIDTH + (i * SPRITE_WIDTH), SPRITE_Y * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
		}
	}

	/**
	 * Renderiza o obstáculo na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		if(this.isVisible) {
			g.drawImage(this.sprites[0], this.x, this.y, null);
			
//			g.setColor(Color.gray);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}