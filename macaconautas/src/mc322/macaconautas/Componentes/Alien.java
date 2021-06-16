package mc322.macaconautas.Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc322.macaconautas.Jogo.ControleJogo;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

//lembrar de apagar ele quando ele sair da tela
public class Alien extends PecaRegular {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 2;
	private final static int QUANTIDADE_SPRITES = 1;
	
	private final static int GUN_HEIGHT_LASER_ADJUST = 8; // ajuste da coordenada y do laser a fim de que este seja gerado a partir da boca do cano da arma.

	private final static int MAX_DISTANCE_WITHOUT_SHOOTING = 120; // distância máxima que o alien pode percorrer sem disparar um laser.

	private SpriteSheet spriteSheet;
	private int distanceWithoutShooting; // distância percorrida pelo alien sem disparar um laser.

	/**
	 * Inicializa um alien.
	 * @param x coordenada x do alien.
	 * @param y coordenada y do alien.
	 */
	public Alien(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, WIDTH, HEIGHT, spriteSheet);
		this.spriteSheet = spriteSheet;
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SPRITE_X + i, SPRITE_Y);
		}
		this.distanceWithoutShooting = MAX_DISTANCE_WITHOUT_SHOOTING; // atira a partir do momento que chegar na tela
	}

	/**
	 * Retorna a largura constante estática do alien.
	 * @return largura do alien.
	 */
	public static int getStaticWidth() {
		return WIDTH;
	}

	/**
	 * Retorna a altura constante estática do alien.
	 * @return altura do alien.
	 */
	public static int getStaticHeight() {
		return HEIGHT;
	}

	/**
	 * Atualiza o estado do alien em um frame.
	 */
	public void tick() {
		super.tick();
		if (this.x <= Controle.WIDTH * Controle.SCALE) {
			if (this.distanceWithoutShooting == MAX_DISTANCE_WITHOUT_SHOOTING) { // atira um laser.
				Laser laser = new Laser(this.x - this.width - this.speed, this.y + GUN_HEIGHT_LASER_ADJUST, this.spriteSheet);
				ArrayList <Laser> l = ControleJogo.getLasers(); 
				l.add(laser);
				ControleJogo.setLasers(l);
				this.distanceWithoutShooting = 0;
			}
			this.distanceWithoutShooting += this.speed;
		}
	}

	/**
	 * Renderiza o alien na tela.
	 * @param g
	 */
	public void render(Graphics g) {
		if (this.isVisible) {
			g.drawImage(this.sprites[0], this.x, this.y, null);

//			g.setColor(Color.GREEN);
//			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}