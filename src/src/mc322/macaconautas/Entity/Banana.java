package mc322.macaconautas.Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mc322.macaconautas.Jogo.Space;
import mc322.macaconautas.app.SpriteSheet;

public class Banana extends RegularEntity {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 0;
	private final static int SPRITE_QUANTITY = 1;

	/**
	 * Inicializa uma banana.
	 * @param x coordenada x da banana.
	 * @param y coordenada y da banana.
	 */
	public Banana(int x, int y, Space space, SpriteSheet spriteSheet) {
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