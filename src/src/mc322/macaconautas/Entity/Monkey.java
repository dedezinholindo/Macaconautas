package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class Monkey extends Entity {

	private static final long serialVersionUID = 4085755067974167449L;

	private final static int WIDTH = 24;
	private final static int HEIGHT = 32;
	private final static int GORILLA_WIDTH = 32;
	private final static int GORILLA_HEIGHT = 40;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 8; // indica onde começam as skins do monkey.
	private final static int GORILLA_SPRITE_X = 0;
	private final static int GORILLA_SPRITE_Y = 7;

	private final static int SPRITE_QUANTITY = 5;

	private final static int WALKING_ANIMATION_PERIOD = 60 / 5; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_WALKING_ANIMATION_FRAMES = 4;

	private final static int GOING_UP_SPEED = 5;
	private final static int GOING_DOWN_SPEED = 3;
	
	private final static int GORILLA_PERIOD = 10 * 60;

	private boolean isDefeated; // indica se o monkey está derrotado.
	private boolean isGoingUp; // indica se o monkey está indo para cima.
	private boolean isWalking; // indica se o monkey está andando no chão.
	private BufferedImage gorillaSprites[];
	private boolean isGorilla;
	private int gorillaFrame;

	/**
	 * Inicializa um monkey.
	 * @param x coordenada x do monkey.
	 * @param y coordenada y do monkey.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 * @param selectedSkin skin selecionada.
	 */
	public Monkey(int x, int y, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet, int selectedSkin) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, (SPRITE_Y + selectedSkin), SPRITE_QUANTITY);
		this.gorillaSprites = new BufferedImage[this.spriteQuantity];
		for (int i = 0; i < this.spriteQuantity; i++) {
			this.gorillaSprites[i] = spriteSheet.getSprite(GORILLA_SPRITE_X + i, GORILLA_SPRITE_Y);
		}
		this.isDefeated = false;
		this.isGoingUp = false;
		this.isWalking = false;
		this.isGorilla = false;
		this.gorillaFrame = 0;
	}

	/**
	 * Indica se o monkey está derrotado.
	 * @return true, caso esteja derrotado.
	 */
	public boolean isDefeated() {
		return this.isDefeated;
	}

	/**
	 * Torna o monkey derrotado.
	 */
	public void admitDefeat() {
		this.isDefeated = true;
	}

	/**
	 * Indica se o monkey está no estado de monkey gorilla.
	 * @return true, caso esteja no estado de monkey gorilla.
	 */
	public boolean isGorilla() {
		return isGorilla;
	}

	/**
	 * Transforma o monkey regular em um monkey gorilla.
	 */
	public void turnIntoGorilla() {
		this.isGorilla = true;
		this.width = GORILLA_WIDTH;
		this.height = GORILLA_HEIGHT;
	}

	/**
	 * Retorna o monkey ao estado de monkey regular.
	 */
	private void returnToMonkey() {
		this.isGorilla = false;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.gorillaFrame = 0;
	}

	/**
	 * Altera o estado de movimento para cima do monkey.
	 * @param isGoingUp novo estado de movimento para cima do monkey.
	 */
	public void setIsGoingUp(boolean isGoingUp) {
		this.isGoingUp = isGoingUp;
	}

	/**
	 * Atualiza o monkey em um frame.
	 */
	public void tick() {
		this.isWalking = false;
		if (this.isGoingUp) {
			this.y -= GOING_UP_SPEED;
		} else { 
			this.y += GOING_DOWN_SPEED;
		}
		if (this.y + this.height > this.space.getHeight() + this.space.getJFrameBorder()) { // está no limite inferior (chão).
			this.y = ((int) this.space.getHeight()) + this.space.getJFrameBorder() - this.height;
			this.isWalking = true;
			this.frame++;
		} else if (this.y <= this.space.getJFrameBorder()) { // está no limite superior (teto).
			this.y = this.space.getJFrameBorder();
		}
		if (!this.isWalking || (this.frame >= MAX_WALKING_ANIMATION_FRAMES * WALKING_ANIMATION_PERIOD)) {
			this.frame = 0; // reinicia contagem de frames caso não esteja andando ou precise reiniciar a animação.
		}
		if (this.isGorilla) {
			this.gorillaFrame++;
			if (this.gorillaFrame == GORILLA_PERIOD) {
				returnToMonkey();
			}
		}
	}

	/**
	 * Renderiza o monkey na tela.
	 * @param g gráficos utilizados.
	 */
	public void render (@SuppressWarnings("exports") Graphics g) {
		BufferedImage sprites[] = (this.isGorilla ? this.gorillaSprites : this.sprites);
		BufferedImage sprite;
		if (this.isGoingUp) {
			sprite = sprites[1]; // sprite com mochila a jato ativada.
		} else if (this.isWalking) {
			int frameAnimation = this.frame / WALKING_ANIMATION_PERIOD;
			if (frameAnimation % 2 == 1) {
				sprite = sprites[4]; // sprite de estado intermediário na corrida.
			} else {
				sprite = sprites[2 + (frameAnimation / 2)]; // sprite de passo direito (2) ou esquerdo (3).
			}
		} else {
			sprite = sprites[0]; // sprite de queda livre.
		}
		g.drawImage(sprite, this.x, this.y, null);
	}
}