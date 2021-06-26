package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import mc322.macaconautas.Jogo.Space;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class AlienFleet extends Entity {

	private final static int SHIP_QUANTITY = 5; // quantidade de naves na frota.

	private final static int SHIP_WIDTH = 40;
	private final static int SHIP_HEIGHT = 40; // dimensões de cada nave.
	private final static int SHIP_SPACING = ((Controle.HEIGHT * Controle.SCALE) - Controle.BORDA - (SHIP_QUANTITY * SHIP_HEIGHT)) / (SHIP_QUANTITY - 1); // espaço entre duas naves consecutivas.
	private final static int WIDTH = SHIP_WIDTH; // largura total da frota.
	private final static int HEIGHT = (SHIP_HEIGHT + SHIP_SPACING) * (SHIP_QUANTITY) - SHIP_SPACING; // altura total da frota (subtração ao final, pois há espaço apenas entre naves).
	private final static int CANNON_HEIGHT = 26; // coordenada y do centro do cannon relativa à coordenada y de uma ship qualquer.

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 5;
	private final static int SPRITE_QUANTITY = 5;

	private final static int CHARGING_ANIMATION_PERIOD = 60; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_CHARGING_ANIMATION_FRAMES = 4;

	private final static int MAX_SHOOTERS_QUANTITY = 2; // quantidade de máxima naves que atiram simultaneamente.
	private final static int REFRESH_PERIOD = 2 * 60; // quantidade de frames do jogo antes de atirar.
	private final static int SHOOTING_PERIOD = 5 * 60; // quantidade de frames do jogo atirando.

	private Random randomGenerator; // gerador randômico utilizado em algumas escolhas.
	private boolean isDestroyed; // indica se a frota alien está destruída.
	private boolean isCharging; // indica se há naves carregando.
	private boolean isShooting; // indica se há naves atirando.
	private boolean shooters[]; // indica quais naves estão atirando/carregando (sempre duas).
	private int maxShotsQuantity; // indica quantas vezes a frota pode atirar.
	private int shotsQuantity; // indica quantas vezes a frota atirou.
	/**
	 * Inicializa uma frota alien.
	 * @param x coordenada x da frota alien.
	 * @param y coordenada y da frota alien.
	 */
	public AlienFleet(int x, int y, Space space, SpriteSheet spriteSheet, int maxShotsQuantity) {
		super(x, y, WIDTH, HEIGHT, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
		this.randomGenerator= new Random();
		this.isDestroyed = false;
		this.isCharging = false;
		this.isShooting = false;
		this.shooters = new boolean[SHIP_QUANTITY];
		Arrays.fill(this.shooters, false); // inicializa todos como false.
		this.maxShotsQuantity = maxShotsQuantity;
		this.shotsQuantity = 0;
	}
	
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
	 * Atualiza o atributo shooters com atiradores aleatoriamente escolhidos.
	 */
	private void chooseShooters() {
		for (int i = 0; i < MAX_SHOOTERS_QUANTITY; i++) { // note que é possível (propositalmente) apenas uma nave ser escolhida.
			this.shooters[this.randomGenerator.nextInt(SHIP_QUANTITY)] = true;
		}
	}
	
	private void shoot() {
		for (int i = 0; i < shooters.length; i++) {
			if (this.shooters[i]) {
				this.space.generateHugeLaser(this.x, (this.y + (i * (SHIP_HEIGHT + SHIP_SPACING)) + CANNON_HEIGHT));
			}
		}
		this.isCharging = false;
		this.isShooting = true;
	}

	/**
	 * Atualiza o estado da frota alien em um frame.
	 */
	public void tick() {
		if (this.shotsQuantity == this.maxShotsQuantity) {
			this.isDestroyed = true;
			return;
		}
		if (!this.isCharging && !this.isShooting && (this.frame >= REFRESH_PERIOD)) {
			this.isCharging = true;
			chooseShooters();
			this.frame = 0;
		} else if (this.isCharging && (this.frame >= MAX_CHARGING_ANIMATION_FRAMES * CHARGING_ANIMATION_PERIOD)) {
			shoot();
			this.frame = 0;
		} else if (this.frame >= SHOOTING_PERIOD) {
			this.shotsQuantity++;
			this.space.destroyHugeLasers();
			this.isShooting = false;
			Arrays.fill(this.shooters, false);
			this.frame = 0;
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
			g.drawImage(sprite, this.x, (this.y + (i * (SHIP_HEIGHT + SHIP_SPACING))), null);
		}
	}

}