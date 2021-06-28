package mc322.macaconautas.Entity;

import java.awt.Graphics;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class Asteroid extends RegularEntity {

	private static final long serialVersionUID = 748335128935111472L;

	public final static int WIDTH = 40;
	public final static int HEIGHT = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 1;
	private final static int SPRITE_QUANTITY = 1;

	/**
	 * Inicializa um asteroid.
	 * @param x coordenada x do asteroid.
	 * @param y coordenada y do asteroid.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public Asteroid(int x, int y, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
	}

	/**
	 * Renderiza o asteroid.
	 * @param g gráficos utilizados.
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}