package mc322.macaconautas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Laser extends Componente {

	private final static int WIDTH = 36;
	private final static int HEIGHT = 12;

	private final static String SPRITE_SHEET_PATH = "/spritesheet.png";
	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 4;
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;
	private final static int QUANTIDADE_SPRITES = 1;

	private final static int SPEED = 2;

	/**
	 * Inicializa um laser.
	 * @param x coordenada x do laser.
	 * @param y coordenada y do laser.
	 */
	public Laser(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		this.speed = SPEED;
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		SpriteSheet spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH);
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X * SPRITE_WIDTH + (i * SPRITE_WIDTH), SPRITE_Y * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
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