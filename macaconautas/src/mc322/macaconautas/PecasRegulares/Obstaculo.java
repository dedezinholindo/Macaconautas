package mc322.macaconautas.PecasRegulares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc322.macaconautas.app.SpriteSheet;

public class Obstaculo extends PecaRegular {

	public final static int WIDTH = 40;
	public final static int HEIGHT = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 1;
	private final static int QUANTIDADE_SPRITES = 1;

	/**
	 * Inicializa um obstáculo.
	 * @param x coordenada x do obstáculo.
	 * @param y coordenada y do obstáculo.
	 */
	public Obstaculo(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y);
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