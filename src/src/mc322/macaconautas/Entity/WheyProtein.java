package mc322.macaconautas.Entity;

import java.awt.Graphics;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class WheyProtein extends RegularEntity {

	private static final long serialVersionUID = 5222187095765262021L;

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 4;
	private final static int SPRITE_QUANTITY = 1;

	/**
	 * Inicializa um whey protein.
	 * @param x coordenada x do whey protein.
	 * @param y coordenada y do whey protein.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public WheyProtein(int x, int y, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
	}

	/**
	 * Renderiza o whey protein.
	 * @param g gráficos utilizados.
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}