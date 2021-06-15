package mc322.macaconautas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//lembrar de apagar ele quando ele sair da tela
public class Alien extends PecaRegular {

	private final static int WIDTH = 28;
	private final static int HEIGHT = 28;

	private final static int MAX_DISTANCE_WITHOUT_SHOOTING = 120; // distância máxima que o alien pode percorrer sem disparar um laser.

	private int distanceWithoutShooting; // distância percorrida pelo alien sem disparar um laser.

	/**
	 * Inicializa um alien.
	 * @param x coordenada x do alien.
	 * @param y coordenada y do alien.
	 */
	public Alien(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
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
		if (this.x <= AppMacaconautas.WIDTH * AppMacaconautas.SCALE) {
			if (this.distanceWithoutShooting == MAX_DISTANCE_WITHOUT_SHOOTING) { // atira um laser.
				Laser laser = new Laser(this.x - this.width - this.speed, this.y + (this.height / 2));
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
			g.setColor(Color.GREEN);
			g.fillRect(this.x, this.y, this.width, this.height);
		}
	}
}