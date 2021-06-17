package mc322.macaconautas.Componentes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import mc322.macaconautas.app.SpriteSheet;

public class FrotaAlien extends Componente{

	private final static int SHIP_QUANTITY = 3; // quantidade de naves na frota.
	private final static int SHIP_SPACING = 80; // espaço entre duas naves consecutivas.

	private final static int SHIP_WIDTH = 40;
	private final static int SHIP_HEIGHT = 40;
	private final static int TOTAL_HEIGHT = (SHIP_HEIGHT + SHIP_SPACING) * (SHIP_QUANTITY) - SHIP_SPACING; // altura total da frota (subtração ao final, pois há espaço apenas entre naves).

	private final static int SHIP_SPRITE_X = 0;
	private final static int SHIP_SPRITE_Y = 5;
	private final static int QUANTIDADE_SPRITES = 5;

	private final static int CHARGING_ANIMATION_PERIOD = 60 / 4; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_CHARGING_ANIMATION_FRAMES = 4;

	private final static int MAX_SHOOTERS_QUANTITY = 2; // quantidade de máxima naves que atiram simultaneamente.
	private final static int REFRESH_PERIOD = 2 * 60; // quantidade de frames do jogo antes de atirar.
	private final static int SHOOTING_PERIOD = 5 * 60; // quantidade de frames do jogo atirando.

	private boolean isCharging; // indica se há naves carregando.
	private boolean isShooting; // indica se há naves atirando.
	private boolean shooters[]; // indica quais naves estão atirando/carregando (sempre duas).

	/**
	 * Inicializa uma frota alien.
	 * @param x coordenada x da frota alien.
	 * @param y coordenada y da frota alien.
	 */
	public FrotaAlien(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, SHIP_WIDTH, TOTAL_HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SHIP_SPRITE_X + i, SHIP_SPRITE_Y);
		}
		this.isCharging = false;
		this.isShooting = false;
		this.shooters = new boolean[SHIP_QUANTITY];
		Arrays.fill(this.shooters, false); // inicializa todos como false.
	}

	/**
	 * Atualiza o atributo shooters com atiradores aleatoriamente escolhidos.
	 */
	private void chooseShooters() {
		Random generator = new Random();
		for (int i = 0; i < MAX_SHOOTERS_QUANTITY; i++) { // note que é possível (propositalmente) apenas uma nave ser escolhida.
			this.shooters[generator.nextInt(SHIP_QUANTITY)] = true;
		}
	}

	/**
	 * Atualiza o estado da frota alien em um frame.
	 */
	public void tick() {
		if (!this.isCharging && !this.isShooting) {
			if (this.frame >= REFRESH_PERIOD) {
				this.isCharging = true;
				chooseShooters();
				this.frame = 0;
			}
		} else if (this.isCharging) {
			if (this.frame >= MAX_CHARGING_ANIMATION_FRAMES * CHARGING_ANIMATION_PERIOD) {
				this.isShooting = true;
				this.isCharging = false;
				this.frame = 0;
			}
		} else {
			if (this.frame >= SHOOTING_PERIOD) {
				this.isShooting = false;
				Arrays.fill(this.shooters, false);
				this.frame = 0;
			}
		}
		this.frame++;
	}

	/**
	 * Renderiza a frota alien na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		BufferedImage sprite;
		for (int i = 0; i < SHIP_QUANTITY; i++) {
			if (!this.shooters[i]) {
				sprite = this.sprites[0]; // sprite com arma fria.
			} else {
				if (this.isCharging) {
					int frameAnimacao = this.frame / CHARGING_ANIMATION_PERIOD;
					sprite = this.sprites[frameAnimacao]; // sprite com arma cada vez mais carregada.
				} else {
					sprite = this.sprites[4]; // sprite com arma atirando
				}
			}
			g.drawImage(sprite, this.x, this.y + (i * (SHIP_HEIGHT + SHIP_SPACING)), null);
		}
	}

}