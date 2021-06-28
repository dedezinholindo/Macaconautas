package mc322.macaconautas.Entity;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.app.SpriteSheet;

public class RegularEntity extends Entity {

	private static final long serialVersionUID = -1289055526722849030L;

	private final static int REGULAR_ENTITY_SPEED = 1; // todas regular entities têm a mesma velocidade

	/**
	 * Inicializa uma peça regular.
	 * @param x coordenada x da peça regular.
	 * @param y coordenada y da peça regular.
	 * @param width largura da peça regular.
	 * @param height altura da peça regular.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 * @param spriteX coordenada x do sprite inicial na sprite sheet.
	 * @param spriteY corrdenada y do sprite inicial na sprite sheet.
	 * @param spriteQuantity quantidade de sprites.
	 */
	public RegularEntity(int x, int y, int width, int height, Space space, SpriteSheet spriteSheet, int spriteX, int spriteY, int spriteQuantity) {
		super(x, y, width, height, space, spriteSheet, spriteX, spriteY, spriteQuantity);
		this.speed = REGULAR_ENTITY_SPEED;
	}

	/**
	 * Atualiza a regular entity em um frame.
	 */
	public void tick() {
		this.x -= this.speed; // todas regular entities têm o mesmo tipo de movimento (horizontal para a esquerda).
	}
}