package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mc322.macaconautas.Jogo.Space;
import mc322.macaconautas.app.SpriteSheet;

public class Entity extends Rectangle {

	protected Space space;
	protected int speed;
	protected int spriteQuantity;
	protected BufferedImage sprites[];
	protected int frame;

	/**
	 * Inicializa um componente.
	 * @param x coordenada x do componente.
	 * @param y coordenada y do componente.
	 * @param width largura do componente.
	 * @param height altura do componente.
	 */
	public Entity(int x, int y, int width, int height, Space space, SpriteSheet spriteSheet, int spriteX, int spriteY, int spriteQuantity) {
		super(x, y, width, height);
		this.space = space;
		this.speed = 0;
		this.spriteQuantity = spriteQuantity;
		this.sprites = new BufferedImage[this.spriteQuantity];
		for (int i = 0; i < this.spriteQuantity; i++) {
			this.sprites[i] = spriteSheet.getSprite(spriteX + i, spriteY);
		}
		this.frame = 0;
	}
	
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Atualiza o estado do componente em um frame.
	 */
	public void tick() {}

	/**
	 * Renderiza o componente na tela.
	 * @param g
	 */
	public void render(Graphics g) {}

}