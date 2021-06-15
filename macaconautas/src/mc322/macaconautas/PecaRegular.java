package mc322.macaconautas;

import java.awt.image.BufferedImage;

public class PecaRegular extends Componente {

	private final static int PECA_REGULAR_SPEED = 1; // todas peças regulares têm a mesma velocidade

	/**
	 * Inicializa uma peça regular.
	 * @param x coordenada x da peça regular.
	 * @param y coordenada y da peça regular.
	 * @param width largura da peça regular.
	 * @param height altura da peça regular.
	 */
	public PecaRegular(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.speed = PECA_REGULAR_SPEED;
	}

	/**
	 * Atualiza o estado da peça regular em um frame.
	 */
	public void tick() {
		this.x -= this.speed; // todas peças regulares têm o mesmo tipo de movimento.
	}
}