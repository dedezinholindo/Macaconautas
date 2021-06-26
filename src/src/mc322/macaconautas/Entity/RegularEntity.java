package mc322.macaconautas.Entity;

import java.awt.image.BufferedImage;

import mc322.macaconautas.Jogo.Space;
import mc322.macaconautas.app.SpriteSheet;

public class RegularEntity extends Entity {

	private final static int PECA_REGULAR_SPEED = 1; // todas peças regulares têm a mesma velocidade

	/**
	 * Inicializa uma peça regular.
	 * @param x coordenada x da peça regular.
	 * @param y coordenada y da peça regular.
	 * @param width largura da peça regular.
	 * @param height altura da peça regular.
	 */
	public RegularEntity(int x, int y, int width, int height, Space space, SpriteSheet spriteSheet, int spriteX, int spriteY, int spriteQuantity) {
		super(x, y, width, height, space, spriteSheet, spriteX, spriteY, spriteQuantity);
		this.speed = PECA_REGULAR_SPEED;
	}

	/**
	 * Atualiza o estado da peça regular em um frame.
	 */
	public void tick() {
		this.x -= this.speed; // todas peças regulares têm o mesmo tipo de movimento.
	}
}