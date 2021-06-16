package mc322.macaconautas.Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle; //vai ter toda a colisão e todos os métodos necessários
import java.awt.image.BufferedImage;

import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class Macaco extends Componente {

	private final static int WIDTH = 24;
	private final static int HEIGHT = 32;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 0;
	private final static int QUANTIDADE_SPRITES = 5;

	private final static int PERIODO_ANIMACAO = 15; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_FRAMES_ANIMACAO = 4;

	private final static int GOING_UP_SPEED = 5;
	private final static int GOING_DOWN_SPEED = 3;

	private boolean isGoingUp; // indica se o macaco está indo para cima.
	private boolean isWalking; // indica se o macaco está andando no chão.

	/**
	 * Inicializa um macaco.
	 * @param x coordenada x do macaco.
	 * @param y coordenada y do macaco.
	 */
	public Macaco(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y);
		}
		this.isGoingUp = false;
		this.isWalking = false;
	}

	/**
	 * Altera o estado de movimento para cima do macaco.
	 * @param isGoingUp novo estado de movimento para cima do macaco.
	 */
	public void setIsGoingUp(boolean isGoingUp) {
		this.isGoingUp = isGoingUp;
	}

	/**
	 * Atualiza o estado do macaco em um frame.
	 */
	public void tick() {
		this.isWalking = false;
		if (this.isGoingUp) {
			this.y -= GOING_UP_SPEED;
		} else { 
			this.y += GOING_DOWN_SPEED;
		}
		if (this.y + this.height > Controle.HEIGHT * Controle.SCALE) { // está no limite inferior (chão).
			this.y = Controle.HEIGHT * Controle.SCALE - this.height;
			this.isWalking = true;
		} else if (this.y <= Controle.BORDA) { // está no limite superior (teto).
			this.y = Controle.BORDA;
		}
	}

	/**
	 * Renderiza o macaco na tela.
	 * @param g
	 */
	public void render (Graphics g) {
		if (this.isVisible) {
			BufferedImage sprite;
			if (this.isGoingUp) {
				sprite = this.sprites[1]; // sprite com mochila a jato ativada.
				this.frame = 0;
			} else if (this.isWalking) {
				int frameAnimacao = this.frame / PERIODO_ANIMACAO;
				if (frameAnimacao % 2 == 1) {
					sprite = this.sprites[4]; // sprite de estado intermediário na corrida.
				} else {
					sprite = this.sprites[2 + (frameAnimacao / 2)]; // sprite de passo direito (2) ou esquerdo (3).
				}
				this.frame++;
				if (this.frame == MAX_FRAMES_ANIMACAO * PERIODO_ANIMACAO) {
					this.frame = 0;
				}
			} else {
				sprite = this.sprites[0];
				this.frame = 0; // sprite com mochila a jato desativada.
			}
			g.drawImage(sprite, this.x, this.y, null);

//			g.setColor(Color.black);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}