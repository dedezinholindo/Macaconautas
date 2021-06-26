package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mc322.macaconautas.Jogo.Space;
import mc322.macaconautas.app.SpriteSheet;

public class HugeLaser extends Entity {
	
	private final static int HEIGHT = 16;
	private final static int UNIT_WIDTH = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 6;
	private final static int SPRITE_QUANTITY = 2;
	
	private int unitQuantity; // quantidade de unidades de laser que formam o laser total.

	/**
	 * Inicializa um laser.
	 * @param x coordenada x do laser.
	 * @param y coordenada y do laser.
	 */
	public HugeLaser(int cannonX, int cannonY, Space space, SpriteSheet spriteSheet) {
		super(0, (cannonY - (HEIGHT / 2)), cannonX, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY); // a largura é o cannonX, pois vai do cannon até o lado esquerdo da tela (x = 0).
		this.unitQuantity = this.width / UNIT_WIDTH;
	}

	public void tick() {};
	
	public void render(Graphics g) {
		for (int i = 0; i < this.unitQuantity - 1; i++) {
			g.drawImage(this.sprites[1], (this.x + (i * UNIT_WIDTH)), this.y, null); // sprite genérico do huge laser.
		}
		g.drawImage(this.sprites[0], (this.x + ((this.unitQuantity - 1) * UNIT_WIDTH)), this.y, null); // sprite inicial do huge laser.
	}
}
