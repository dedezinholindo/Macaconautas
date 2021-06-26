package mc322.macaconautas.Jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;
import mc322.macaconautas.Entity.*;

public class Space {

	private final static int TERMINAL_X = -40; // valor de coordenada x para o qual entities são destruídas (não aparecem mais na tela).

	private final static int HOSTILE_GEN_FRAME_STEP = 3 * 60; // aliens e obstáculos são gerados a cada HOSTILE_GEN_STEP frames.
	private final static int ALIEN_FLEET_GEN_FRAME_STEP = 10 * 60; // a frota alien é gerada a cada ALIEN_FLEET_GEN_STEP frames.
	private final static int WHEY_PROTEIN_GEN_FRAME_STEP = 10 * 60;
	private final static int FRAME_CEILING = ALIEN_FLEET_GEN_FRAME_STEP; // quantidade máxima de frames que o space conta.

	private final static int MAX_HOSTILE_PER_X = 5; // quantidade máxima de entities hostis em uma mesma coordenada x.

	private final static double OBSTACLE_GEN_PROBABILITY = 0.6;
	private final static double ALIEN_GEN_PROBABILITY = 0.5;
	private final static double BANANA_GEN_PROBABILITY = 0.01;
	private final static double WHEY_PROTEIN_GEN_PROBABILITY = 0.7; // probabilidades de geração das entities.

	private int width;
	private int height;
	private int frame;
	private Monkey monkey;
	private ArrayList<RegularEntity> peacefulRegularEntities;
	private ArrayList<RegularEntity> hostileRegularEntities;
	private ArrayList<Laser> lasers;
	private AlienFleet alienFleet;
	private ArrayList<HugeLaser> hugeLasers;
	private boolean isAlienFleetOperating; // indica que a alien fleet está vindo.
	private SpriteSheet spriteSheet;
	private Random randomGenerator; // gerador randômico utilizado em algumas escolhas.

	/**
	 * Inicializa um novo space.
	 * @param width largura do space.
	 * @param height altura do space.
	 * @param distanceToFloor distância a ser mantida do chão para não gerar entities parcialmente escondidas.
	 * @param spriteSheet sprite sheet com sprites a serem utilizados por entities no space.
	 */
	public Space(int width, int height, int distanceToFloor, SpriteSheet spriteSheet) {
		this.width = width;
		this.height = height - distanceToFloor;
		this.frame = 0;
		this.monkey = null;
		this.peacefulRegularEntities = new ArrayList<RegularEntity>();
		this.hostileRegularEntities = new ArrayList<RegularEntity>();
		this.lasers = new ArrayList<Laser>();
		this.alienFleet = null;
		this.hugeLasers = new ArrayList<HugeLaser>();
		this.isAlienFleetOperating = false;
		this.spriteSheet = spriteSheet;
		this.randomGenerator = new Random();
	}
	
	public void setMonkey(Monkey monkey) {
		this.monkey = monkey;
	}

	/**
	 * Gera novas regular entities na zona de geração do space randomicamente.
	 */
	private void generateRegularEntities() {
		double n; // número sorteado.
		int y; // coordenada y da entity gerada.
		n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
		while (n < BANANA_GEN_PROBABILITY) {
			y = this.randomGenerator.nextInt(this.height);
			this.peacefulRegularEntities.add(new Banana(this.width, y, this, this.spriteSheet));
			n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
		}
		n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
		if ((this.frame % WHEY_PROTEIN_GEN_FRAME_STEP == 0) && (n < WHEY_PROTEIN_GEN_PROBABILITY)) {
			y = this.randomGenerator.nextInt(this.height);
			this.peacefulRegularEntities.add(new WheyProtein(this.width, y, this, this.spriteSheet));
		}
		if (this.frame % HOSTILE_GEN_FRAME_STEP == 0) {
			int hostileQuantity = 0;
			n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
			while (n < OBSTACLE_GEN_PROBABILITY && hostileQuantity < MAX_HOSTILE_PER_X) {
				y = this.randomGenerator.nextInt(this.height);
				this.hostileRegularEntities.add(new Asteroid(this.width, y, this, this.spriteSheet));
				n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
				hostileQuantity++;
			}
			n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
			while (n < ALIEN_GEN_PROBABILITY && hostileQuantity < MAX_HOSTILE_PER_X) {
				y = this.randomGenerator.nextInt(this.height);
				this.hostileRegularEntities.add(new Alien(this.width, y, this, this.spriteSheet));
				n = ((double) this.randomGenerator.nextInt(1001)) / 1000;
				hostileQuantity++;
			}
		}
	}

	/**
	 * Gera um laser atirado por um alien (método chamado por aliens).
	 * @param x coordenada x do laser.
	 * @param y coordenada y do laser.
	 */
	public void generateLaser(int x, int y) {
		Laser laser = new Laser(x, y, this, this.spriteSheet);
		this.lasers.add(laser);
	}

	/**
	 * Gera uma alien fleet no space.
	 */
	private void generateAlienFleet() {
		this.alienFleet = new AlienFleet(this.width - 40, Controle.BORDA, this, this.spriteSheet, 3);
	}
	
	public void generateHugeLaser(int cannonX, int cannonY) {
		this.hugeLasers.add(new HugeLaser(cannonX, cannonY, this, this.spriteSheet));
	}
	
	public void destroyHugeLasers() {
		this.hugeLasers.clear();
	}

	/**
	 * Remove entities que estão na zona de destruição do space.
	 */
	private void destroyOutsiders() {
		for (int i = 0; i < this.peacefulRegularEntities.size(); i++) {
			if (this.peacefulRegularEntities.get(i).getX() == TERMINAL_X) { // está na zona de destruição
				this.peacefulRegularEntities.remove(i);
				i--;
			} else { // regular entities da zona de destruição estão sempre juntas nas primeiras posições.
				break;
			}
		}
		for (int i = 0; i < this.hostileRegularEntities.size(); i++) {
			if (this.hostileRegularEntities.get(i).getX() == TERMINAL_X) { // está na zona de destruição
				this.hostileRegularEntities.remove(i);
				i--;
			} else { // regular entities da zona de destruição estão sempre juntas nas primeiras posições.
				break;
			}
		}
		for (int i = 0; i < this.lasers.size(); i++) {
			if (this.lasers.get(i).getX() == TERMINAL_X) {
				this.lasers.remove(i);
				i--;
			} else {
				break;
			}
		}
	}

	/**
	 * Checa colisões do monkey com entities pacíficas e atualiza o space
	 * conforme.
	 * @return quantidade de bananas colididas.
	 */
	public int checkPeacefulCollisions() {
		int collidedBananas = 0;
		Rectangle monkeyHitBox = this.monkey.getBounds();
		Rectangle entityHitBox;
		for (int i = 0; i < this.peacefulRegularEntities.size(); i++) {
			entityHitBox = this.peacefulRegularEntities.get(i).getBounds();
			if (monkeyHitBox.intersects(entityHitBox)) {
				String entityType = this.peacefulRegularEntities.get(i).getClass().getName();
				if (entityType.equals("mc322.macaconautas.Entity.Banana")) {
					this.peacefulRegularEntities.remove(i);
					i--;
					collidedBananas++;
				} else if (entityType.equals("mc322.macaconautas.Entity.WheyProtein") &&
							!this.monkey.isGorilla()) {
					this.peacefulRegularEntities.remove(i);
					i--;
					this.monkey.turnIntoGorilla();
				}
			}
		}
		return collidedBananas;
	}

	/**
	 * Checa colisões do monkey com entities hostis e atualiza o space
	 * conforme.
	 */
	public void checkHostileCollisions() {
		Rectangle monkeyHitBox = this.monkey.getBounds();
		Rectangle entityHitBox;
		for (int i = 0; i < this.hostileRegularEntities.size(); i++) {
			entityHitBox = this.hostileRegularEntities.get(i).getBounds();
			if (monkeyHitBox.intersects(entityHitBox)) {
				if (this.monkey.isGorilla()) {
					this.hostileRegularEntities.remove(i);
					i--;
				} else {
					this.monkey.admitDefeat();
					return;
				}
			}
		}
		for (int i = 0; i < this.lasers.size(); i++) {
			entityHitBox = this.lasers.get(i).getBounds();
			if (monkeyHitBox.intersects(entityHitBox)) {
				if (this.monkey.isGorilla()) {
					this.lasers.remove(i);
					i--;
				} else {
					this.monkey.admitDefeat();
					return;
				}
			}
		}
		for (int i = 0; i < this.hugeLasers.size(); i++) {
			entityHitBox = this.hugeLasers.get(i).getBounds();
			if (monkeyHitBox.intersects(entityHitBox)) {
				this.monkey.admitDefeat();
				return;
			}
		}
	}
	
	private void tickEntities() {
		if (this.monkey != null) {
			this.monkey.tick();
		}
		for (int i = 0; i < this.peacefulRegularEntities.size(); i++) {
			this.peacefulRegularEntities.get(i).tick();
		}
		for (int i = 0; i < this.hostileRegularEntities.size(); i++) {
			this.hostileRegularEntities.get(i).tick();
		}
		for (int i = 0; i < this.lasers.size(); i++) {
			this.lasers.get(i).tick();
		}
		if (this.alienFleet != null) {
			this.alienFleet.tick();
		}
	}

	/**
	 * Atualiza o estado do space em um frame.
	 * @return quantidade de bananas coletadas.
	 */
	public int tick() {
		int collectedBananas;
		this.frame++;
		if (this.frame > FRAME_CEILING) { // ultrapassou o teto da contagem, então a reinicia.
			this.frame = 1;
		}
		destroyOutsiders();
		checkHostileCollisions();
		collectedBananas = checkPeacefulCollisions();
		if (this.alienFleet != null && this.alienFleet.isDestroyed()) {
			this.alienFleet = null;
			this.isAlienFleetOperating = false;
		}
		if (!this.isAlienFleetOperating) {
			if (this.frame % ALIEN_FLEET_GEN_FRAME_STEP == 0) {
				this.isAlienFleetOperating = true;
			} else {
				generateRegularEntities();
			}
		} else if ((this.alienFleet == null) && this.isAlienFleetOperating && (this.peacefulRegularEntities.size() == 0) && (this.hostileRegularEntities.size() == 0)) {
			generateAlienFleet();
		}
		tickEntities();
		return collectedBananas;
	}

	/**
	 * Renderiza as entities contidas no space na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		if (this.monkey != null) {
			this.monkey.render(g);
		}
		for (int i = 0; i < this.peacefulRegularEntities.size(); i++) {
			this.peacefulRegularEntities.get(i).render(g);
		}
		for (int i = 0; i < this.hostileRegularEntities.size(); i++) {
			this.hostileRegularEntities.get(i).render(g);
		}
		for (int i = 0; i < this.lasers.size(); i++) {
			this.lasers.get(i).render(g);
		}
		if (this.alienFleet != null) {
			this.alienFleet.render(g);
		}
		for (int i = 0; i < this.hugeLasers.size(); i++) {
			this.hugeLasers.get(i).render(g);
		}
	}
}

















