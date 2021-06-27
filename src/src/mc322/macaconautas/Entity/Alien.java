package mc322.macaconautas.Entity;

import java.awt.Graphics;
import mc322.macaconautas.Game.Space;
import mc322.macaconautas.app.SpriteSheet;

public class Alien extends RegularEntity {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 2;
	private final static int SPRITE_QUANTITY = 1;
	
	private final static int GUN_HEIGHT = 11; // ajuste da coordenada y do laser a fim de que este seja gerado a partir da boca do cano da arma.

	private final static int MAX_DISTANCE_WITHOUT_SHOOTING = 120; // distância máxima que o alien pode percorrer sem disparar um laser.

	private int distanceWithoutShooting; // distância percorrida pelo alien sem disparar um laser.

	/**
	 * Inicializa um alien.
	 * @param x coordenada x do alien.
	 * @param y coordenada y do alien.
	 */
	public Alien(int x, int y, Space space, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
		this.distanceWithoutShooting = MAX_DISTANCE_WITHOUT_SHOOTING; // atira a partir do momento que chegar na tela
	}
	
	/**
	 * Pede ao space que gere um laser correspondente a um tiro do alien.
	 */
	private void shoot() {
		this.space.generateLaser(this.x - this.width - this.speed, this.y + GUN_HEIGHT);
		this.distanceWithoutShooting = 0;
	}

	/**
	 * Atualiza o estado do alien em um frame.
	 */
	public void tick() {
		super.tick();
		if (this.distanceWithoutShooting == MAX_DISTANCE_WITHOUT_SHOOTING) { // atira um laser.
			shoot();
		} else {
			this.distanceWithoutShooting += this.speed;
		}
	}

	/**
	 * Renderiza o alien na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(this.sprites[0], this.x, this.y, null);
	}
}