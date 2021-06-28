package mc322.macaconautas.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import mc322.macaconautas.SpriteSheet.SpriteSheet;
import mc322.macaconautas.Entity.*;

public class Space extends Rectangle {

	private static final long serialVersionUID = 3013529014592626566L;

	private final static int TERMINAL_X = -40; // valor de coordenada x para o qual entities são destruídas (não aparecem mais na tela).

	private final static int HOSTILE_GEN_FRAME_STEP = 3 * 60; // aliens e obstáculos são gerados a cada HOSTILE_GEN_STEP frames.
	private final static int ALIEN_FLEET_GEN_FRAME_STEP = 30 * 60; // a frota alien é gerada a cada ALIEN_FLEET_GEN_STEP frames.
	private final static int WHEY_PROTEIN_GEN_FRAME_STEP = 5 * 60;
	private final static int FRAME_CEILING = ALIEN_FLEET_GEN_FRAME_STEP; // quantidade máxima de frames que o space conta.

	private final static int MAX_HOSTILE_PER_X = 5; // quantidade máxima de entities hostis em uma mesma coordenada x.

	private final static double OBSTACLE_GEN_PROBABILITY = 0.6;
	private final static double ALIEN_GEN_PROBABILITY = 0.5;
	private final static double BANANA_GEN_PROBABILITY = 0.01;
	private final static double WHEY_PROTEIN_GEN_PROBABILITY = 0.7; // probabilidades de geração das entities (altas para fim de demonstração).

	private int jFrameBorder; // tamanho da borda do JFrame em que o espaço será renderizado.
	private int distanceToFloor; // distância a ser considerada para não gerar entities parcialmente cobertas pelo chão.
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
	 * @param jFrameBorder tamanho da borda do JFrame em que o espaço será renderizado.
	 * @param distanceToFloor distância a ser mantida do chão para não gerar entities parcialmente escondidas.
	 * @param spriteSheet sprite sheet com sprites a serem utilizados por entities no space.
	 */
	public Space(int width, int height, int jFrameBorder, int distanceToFloor, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		super(0, jFrameBorder, width, height - distanceToFloor);
		this.jFrameBorder = jFrameBorder;
		this.distanceToFloor = distanceToFloor;
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

	/**
	 * Retorna o tamanho da borda do JFrame.
	 * @return tamanho da borda do JFrame.
	 */
	public int getJFrameBorder () {
		return this.jFrameBorder;
	}

	/**
	 * Adiciona um monkey ao space.
	 * @param monkey Monkey a adicionar.
	 */
	public void setMonkey(Monkey monkey) {
		this.monkey = monkey;
	}

	/**
	 * Gera um número de 0 a 1 aleatório com até 3 casas decimais.
	 * @return número aleatório.
	 */
	private double getRandomPercentage() {
		return ((double) this.randomGenerator.nextInt(1001)) / 1000;
	}

	/**
	 * Gera uma coordenada y aleatória dentro do space.
	 * @return coordenada y aleatória.
	 */
	private int getRandomY() {
		return this.randomGenerator.nextInt(this.height - this.distanceToFloor) + this.jFrameBorder;
	}

	/**
	 * Gera novas regular entities na zona de geração do space randomicamente.
	 */
	private void generateRegularEntities() {
		while (getRandomPercentage() < BANANA_GEN_PROBABILITY) {
			this.peacefulRegularEntities.add(new Banana(this.width, getRandomY(), this, this.spriteSheet));
		}
		if ((this.frame % WHEY_PROTEIN_GEN_FRAME_STEP == 0) && (getRandomPercentage() < WHEY_PROTEIN_GEN_PROBABILITY)) {
			this.peacefulRegularEntities.add(new WheyProtein(this.width, getRandomY(), this, this.spriteSheet));
		}
		if (this.frame % HOSTILE_GEN_FRAME_STEP == 0) {
			int hostileQuantity = 0;
			while (getRandomPercentage() < OBSTACLE_GEN_PROBABILITY && hostileQuantity < MAX_HOSTILE_PER_X) {
				this.hostileRegularEntities.add(new Asteroid(this.width, getRandomY(), this, this.spriteSheet));
				hostileQuantity++;
			}
			while (getRandomPercentage() < ALIEN_GEN_PROBABILITY && hostileQuantity < MAX_HOSTILE_PER_X) {
				this.hostileRegularEntities.add(new Alien(this.width, getRandomY(), this, this.spriteSheet));
				hostileQuantity++;
			}
		}
	}

	/**
	 * Gera um laser atirado por um alien (método chamado por aliens).
	 * @param gunX coordenada x da arma do alien.
	 * @param gunY coordenada y da arma do alien.
	 */
	public void generateLaser(int gunX, int gunY) {
		Laser laser = new Laser(gunX, gunY, this, this.spriteSheet);
		this.lasers.add(laser);
	}

	/**
	 * Gera uma alien fleet no space.
	 */
	private void generateAlienFleet() {
		this.alienFleet = new AlienFleet(this.width - 40, this.jFrameBorder, this, this.spriteSheet);
	}

	/**
	 * Gera um huge laser atirado por uma alien fleet (método chamado por alien
	 * fleet).
	 * @param cannonX coordenada x do canhão da alien fleet.
	 * @param cannonY coordenada y do canhão da alien fleet.
	 */
	public void generateHugeLaser(int cannonX, int cannonY) {
		this.hugeLasers.add(new HugeLaser(cannonX, cannonY, this, this.spriteSheet));
	}

	/**
	 * Remove entities que estão na zona de destruição do space.
	 */
	private void destroyOutsiders() {
		for (int i = 0; i < this.peacefulRegularEntities.size(); i++) {
			if (this.peacefulRegularEntities.get(i).getX() <= TERMINAL_X) { // está na zona de destruição
				this.peacefulRegularEntities.remove(i);
				i--;
			} else { // regular entities da zona de destruição estão sempre juntas nas primeiras posições.
				break;
			}
		}
		for (int i = 0; i < this.hostileRegularEntities.size(); i++) {
			if (this.hostileRegularEntities.get(i).getX() <= TERMINAL_X) { // está na zona de destruição
				this.hostileRegularEntities.remove(i);
				i--;
			} else { // regular entities da zona de destruição estão sempre juntas nas primeiras posições.
				break;
			}
		}
		for (int i = 0; i < this.lasers.size(); i++) {
			if (this.lasers.get(i).getX() <= TERMINAL_X) {
				this.lasers.remove(i);
				i--;
			} else {
				break;
			}
		}
	}

	/**
	 * Remove os huge lasers.
	 */
	public void destroyHugeLasers() {
		this.hugeLasers.clear();
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
	 * Atualiza o estado das entities em um frame.
	 */
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
	 * Renderiza as entities contidas no space na tela.
	 * @param g
	 */
	public void render(@SuppressWarnings("exports") Graphics g) {
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
		g.setColor(Color.gray);
		g.fillRect(0, this.height + this.jFrameBorder, this.width, 40);
	}
}