package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public abstract class Entity extends Rectangle {

	private static final long serialVersionUID = 4244516020791898234L;

	protected Space space;
	protected int speed;
	protected int spriteQuantity;
	protected BufferedImage sprites[];
	protected int frame;

	/**
	 * Inicializa uma entity.
	 * @param x coordenada x da entity.
	 * @param y coordenada y da entity.
	 * @param width largura da entity.
	 * @param height altura da entity.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 * @param spriteX coordenada x do sprite inicial na sprite sheet.
	 * @param spriteY corrdenada y do sprite inicial na sprite sheet.
	 * @param spriteQuantity quantidade de sprites.
	 */
	public Entity(int x, int y, int width, int height, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet, int spriteX, int spriteY, int spriteQuantity) {
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

	/**
	 * Atualiza a entity em um frame.
	 */
	public void tick() {}

	/**
	 * Renderiza a entity.
	 * @param g gráficos utilizados.
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {}
}