package mc322.macaconautas.Entity;

import java.awt.Graphics;
import mc322.macaconautas.Game.Space;
import mc322.macaconautas.app.SpriteSheet;

public class WheyProtein extends RegularEntity {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 4;
	private final static int SPRITE_QUANTITY = 1;
	
	public WheyProtein(int x, int y, Space space, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
	}

	/**
	 * Renderiza a banana na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}

