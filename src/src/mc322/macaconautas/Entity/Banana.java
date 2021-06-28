package mc322.macaconautas.Entity;

import java.awt.Graphics;
import mc322.macaconautas.Game.Space;
import mc322.macaconautas.Control.SpriteSheet;

public class Banana extends RegularEntity {

	private static final long serialVersionUID = -4937067153117014098L;

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 0;
	private final static int SPRITE_QUANTITY = 1;

	/**
	 * Inicializa uma banana.
	 * @param x coordenada x da banana.
	 * @param y coordenada y da banana.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public Banana(int x, int y, Space space, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
	}

	/**
	 * Renderiza a banana.
	 * @param g gráficos utilizados.
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}