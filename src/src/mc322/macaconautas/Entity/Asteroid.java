package mc322.macaconautas.Entity;

import java.awt.Graphics;
import mc322.macaconautas.Game.Space;
import mc322.macaconautas.app.SpriteSheet;

public class Asteroid extends RegularEntity {

	public final static int WIDTH = 40;
	public final static int HEIGHT = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 1;
	private final static int SPRITE_QUANTITY = 1;

	/**
	 * Inicializa um obstáculo.
	 * @param x coordenada x do obstáculo.
	 * @param y coordenada y do obstáculo.
	 */
	public Asteroid(int x, int y, Space space, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
	}

	/**
	 * Renderiza o obstáculo na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}