package mc322.macaconautas.Entity;

import java.awt.Graphics;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class HugeLaser extends Entity {

	private static final long serialVersionUID = 1615442277742158914L;

	private final static int HEIGHT = 16;
	private final static int UNIT_WIDTH = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 6;
	private final static int SPRITE_QUANTITY = 2;

	private int unitQuantity; // quantidade de unidades de laser que formam o laser total.

	/**
	 * Inicializa um huge laser.
	 * @param cannonX coordenada x do canhão que dispara o huge laser.
	 * @param cannonY coordenada y do canhão que dispara o huge laser.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public HugeLaser(int cannonX, int cannonY, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		super(0, (cannonY - (HEIGHT / 2)), cannonX, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY); // a largura é o cannonX, pois vai do cannon até o lado esquerdo da tela (x = 0).
		this.unitQuantity = this.width / UNIT_WIDTH;
	}

	public void tick() {};
	
	public void render(@SuppressWarnings("exports") Graphics g) {
		for (int i = 0; i < this.unitQuantity - 1; i++) {
			g.drawImage(this.sprites[1], (this.x + (i * UNIT_WIDTH)), this.y, null); // sprite genérico do huge laser.
		}
		g.drawImage(this.sprites[0], (this.x + ((this.unitQuantity - 1) * UNIT_WIDTH)), this.y, null); // sprite inicial do huge laser.
	}
}
