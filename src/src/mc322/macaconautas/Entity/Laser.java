package mc322.macaconautas.Entity;

import java.awt.Graphics;
import mc322.macaconautas.Game.Space;
import mc322.macaconautas.app.SpriteSheet;

public class Laser extends Entity {

	private final static int WIDTH = 36;
	private final static int HEIGHT = 8;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 3;
	private final static int SPRITE_QUANTITY = 1;

	private final static int SPEED = 2;

	/**
	 * Inicializa um laser.
	 * @param x coordenada x do laser.
	 * @param y coordenada y do laser.
	 */
	public Laser(int gunX, int gunY, Space space, SpriteSheet spriteSheet) {
		super(gunX, (gunY - (HEIGHT / 2)), WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
		this.speed = SPEED;
	}

	/**
	 * Atualiza o estado do laser em um frame.
	 */
	public void tick() {
		this.x -= this.speed;
	}

	/**
	 * Renderiza o laser na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}