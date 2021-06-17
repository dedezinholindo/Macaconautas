package mc322.macaconautas.Componentes;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mc322.macaconautas.app.SpriteSheet;

public class Componente extends Rectangle{

	protected int speed;
	protected boolean isVisible;
	protected int quantidadeSprites;
	protected BufferedImage sprites[];
	protected int frame;

	/**
	 * Inicializa um componente.
	 * @param x coordenada x do componente.
	 * @param y coordenada y do componente.
	 * @param width largura do componente.
	 * @param height altura do componente.
	 */
	public Componente(int x, int y, int width, int height, SpriteSheet spriteSheet) {
		super(x, y, width, height);
		this.quantidadeSprites = 0;
		this.sprites = null;
		this.frame = 0;
		this.isVisible = true;
	}

	/**
	 * Atualiza o estado do componente em um frame.
	 */
	public void tick() {}

	/**
	 * Renderiza o componente na tela.
	 */
	public void render() {}

}