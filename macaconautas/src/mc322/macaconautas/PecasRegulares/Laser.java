package mc322.macaconautas.PecasRegulares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc322.macaconautas.Componentes.Componente;
import mc322.macaconautas.Jogo.ControleJogo;
import mc322.macaconautas.app.SpriteSheet;

public class Laser extends Componente {

	private final static int WIDTH = 36;
	private final static int HEIGHT = 8;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 3;
	private final static int QUANTIDADE_SPRITES = 1;

	private final static int SPEED = 2;

	/**
	 * Inicializa um laser.
	 * @param x coordenada x do laser.
	 * @param y coordenada y do laser.
	 */
	public Laser(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.speed = SPEED;
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y);
		}
	}

	/**
	 * Atualiza o estado do laser em um frame.
	 */
	public void tick() {
		this.x -= this.speed;
		if (this.x < 0) { //sumir quando sir da tela
			ArrayList <Laser> l = ControleJogo.getLasers();
			l.remove(this);
			ControleJogo.setLasers(l);
			return; // sempre retornar quando elimina o proprio objeto 
		}
	}

	/**
	 * Renderiza o laser na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		if(this.isVisible) {
			g.drawImage(this.sprites[0], this.x, this.y, null);
			
//			g.setColor(Color.red);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}