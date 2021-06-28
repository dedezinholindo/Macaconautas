package mc322.macaconautas.Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import mc322.macaconautas.Game.Space;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class AlienFleet extends Entity {

	private static final long serialVersionUID = 667672651225816724L;

	private final static int SHIP_QUANTITY = 6; // quantidade de nave na alien fleet.

	private final static int SHIP_WIDTH = 40;
	private final static int SHIP_HEIGHT = 40; // dimensões de cada nave.
	private final static int WIDTH = SHIP_WIDTH; // largura total da alien fleet.
	private final static int CANNON_HEIGHT = 26; // coordenada y do centro de um cannon relativa à coordenada y da nave respectiva.

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 5;
	private final static int SPRITE_QUANTITY = 5;

	private final static int CHARGING_ANIMATION_PERIOD = 60; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_CHARGING_ANIMATION_FRAMES = 4;

	private final static int MAX_SHOOTER_QUANTITY = 3; // quantidade de máxima naves que atiram simultaneamente.
	private final static int REFRESH_PERIOD = 2 * 60; // quantidade de frames do jogo antes de atirar.
	private final static int SHOOTING_PERIOD = 5 * 60; // quantidade de frames do jogo atirando.
	private final static int MAX_SHOT_QUANTITY = 3; // quantidade de máxima de tiros que a alien fleet dispara antes de destruir.

	private Random randomGenerator; // gerador randômico utilizado em algumas escolhas.
	private int shipSpacing;
	private boolean isDestroyed; // indica se a alien fleet está destruída.
	private boolean isCharging; // indica se há naves carregando.
	private boolean isShooting; // indica se há naves atirando.
	private boolean shooters[]; // indica quais naves estão atirando/carregando (sempre duas).
	private int shotsQuantity; // indica quantas vezes a frota atirou.

	/**
	 * Inicializa uma alien fleet.
	 * @param x coordenada x da alien fleet.
	 * @param y coordenada y da alien fleet.
	 * @param space space no qual está inserido.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public AlienFleet(int x, int y, Space space, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		super(x, y, WIDTH, 0, space, spriteSheet, SPRITE_X, SPRITE_Y, SPRITE_QUANTITY);
		this.shipSpacing = (((int) this.space.getHeight()) - (SHIP_QUANTITY * SHIP_HEIGHT)) / (SHIP_QUANTITY - 1); // espaço entre duas naves consecutivas.
		this.setSize(WIDTH, (SHIP_HEIGHT + this.shipSpacing) * (SHIP_QUANTITY) - this.shipSpacing); // atualiza a altura total da alien fleet (subtração ao final, pois há espaço apenas entre naves).
		this.randomGenerator= new Random();
		this.isDestroyed = false;
		this.isCharging = false;
		this.isShooting = false;
		this.shooters = new boolean[SHIP_QUANTITY];
		Arrays.fill(this.shooters, false); // inicializa todos como false.
		this.shotsQuantity = 0;
	}

	/**
	 * Indica se a alien fleet está destruída.
	 * @return true, caso esteja destruída.
	 */
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
	 * Atualiza o atributo shooters com atiradores aleatoriamente escolhidos.
	 */
	private void chooseShooters() {
		for (int i = 0; i < MAX_SHOOTER_QUANTITY; i++) { // note que é possível (propositalmente) apenas uma nave ser escolhida.
			this.shooters[this.randomGenerator.nextInt(SHIP_QUANTITY)] = true;
		}
	}

	/**
	 * Atira um HugeLaser.
	 */
	private void shoot() {
		for (int i = 0; i < shooters.length; i++) {
			if (this.shooters[i]) {
				this.space.generateHugeLaser(this.x, (this.y + (i * (SHIP_HEIGHT + this.shipSpacing)) + CANNON_HEIGHT));
			}
		}
		this.isCharging = false;
		this.isShooting = true;
	}

	/**
	 * Atualiza a alien fleet em um frame.
	 */
	public void tick() {
		if (this.shotsQuantity == MAX_SHOT_QUANTITY) {
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
	 * Renderiza a alien fleet.
	 * @param g gráficos utilizados.
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {
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
			g.drawImage(sprite, this.x, (this.y + (i * (SHIP_HEIGHT + this.shipSpacing))), null);
		}
	}
}