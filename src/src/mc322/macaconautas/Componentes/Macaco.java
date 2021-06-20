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
	private final static int SPRITE_Y = 8; // indica onde começam as skins do macaco.
	private final static int QUANTIDADE_SPRITES = 5;

	private final static int GORILA_WIDTH = 32;
	private final static int GORILA_HEIGHT = 40;

	private final static int GORILA_SPRITE_X = 0;
	private final static int GORILA_SPRITE_Y = 7;

	private final static int WALKING_ANIMATION_PERIOD = 60 / 5; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_WALKING_ANIMATION_FRAMES = 4;

	private final static int GOING_UP_SPEED = 5;
	private final static int GOING_DOWN_SPEED = 3;
	
	private final static int TEMPO_DE_GORILA = 10 * 60; //<segundos> * 60 (pois 60 frames/seg)

	private int selectedSkin;
	private boolean isGoingUp; // indica se o macaco está indo para cima.
	private boolean isWalking; // indica se o macaco está andando no chão.
	private BufferedImage gorilaSprites[];
	private boolean isGorila;
	private int contadorGorila;

	/**
	 * Inicializa um macaco.
	 * @param x coordenada x do macaco.
	 * @param y coordenada y do macaco.
	 */
	public Macaco(int x, int y, SpriteSheet spriteSheet, int selectedSkin) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		this.selectedSkin = selectedSkin;
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y + this.selectedSkin);
		}
		this.gorilaSprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.gorilaSprites[i] = spriteSheet.getSprite(GORILA_SPRITE_X + i, GORILA_SPRITE_Y);
		}
		this.isGoingUp = false;
		this.isWalking = false;
		this.isGorila = false;
		this.contadorGorila = 0;
	}
	
	public static int getTempoDeGorila() {
		return TEMPO_DE_GORILA;
	}

	public static int getMacacoWidth() {
		return WIDTH;
	}

	public static int getMacacoHeight() {
		return HEIGHT;
	}

	public static int getGorilaWidth() {
		return GORILA_WIDTH;
	}

	public static int getGorilaHeight() {
		return GORILA_HEIGHT;
	}
	
	public int getContadorGorila() {
		return contadorGorila;
	}

	public void setContadorGorila(int contadorGorila) {
		this.contadorGorila = contadorGorila;
	}

	public boolean IsGorila() {
		return isGorila;
	}

	public void setIsGorila(boolean gorila) {
		this.isGorila = gorila;
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
			this.frame++;
		} else if (this.y <= Controle.BORDA) { // está no limite superior (teto).
			this.y = Controle.BORDA;
		}
		if (!this.isWalking || (this.frame >= MAX_WALKING_ANIMATION_FRAMES * WALKING_ANIMATION_PERIOD)) {
			this.frame = 0; // reinicia contagem de frames caso não esteja andando ou precise reiniciar a animação.
		}
	}

	/**
	 * Renderiza o macaco na tela.
	 * @param g
	 */
	public void render (Graphics g) {
		BufferedImage sprites[] = (this.isGorila ? this.gorilaSprites : this.sprites);
		if (this.isVisible) {
			BufferedImage sprite;
			if (this.isGoingUp) {
				sprite = sprites[1]; // sprite com mochila a jato ativada.
			} else if (this.isWalking) {
				int frameAnimacao = this.frame / WALKING_ANIMATION_PERIOD;
				if (frameAnimacao % 2 == 1) {
					sprite = sprites[4]; // sprite de estado intermediário na corrida.
				} else {
					sprite = sprites[2 + (frameAnimacao / 2)]; // sprite de passo direito (2) ou esquerdo (3).
				}
			} else {
				sprite = sprites[0]; // sprite de queda livre.
			}
			g.drawImage(sprite, this.x, this.y, null);

//			g.setColor(Color.black);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}