package mc322.macaconautas.Jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Componentes.Macaco;
import mc322.macaconautas.PecasRegulares.Laser;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class MontadorJogo {
	final static int TAMANHO_STRING_JOGO = 17;
	final static int WIDTH = Controle.WIDTH; //tornar ?
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;

	SpriteSheet spriteSheet;

	int lentidao;
	char jogoState; //N para normal, P para pausado (uso do pause) e O para Game Over
	boolean isRunning;
	// boolean estaSuspensa;
	Thread thread;
	Macaco macaco;
	Espaco espaco;
	static ArrayList <Laser> lasers; 
	int bananasColetadas;
	long distancia;
	int contador;

	public MontadorJogo(SpriteSheet spriteSheet){
		this.spriteSheet = spriteSheet;
		macaco = new Macaco(15, 0, this.spriteSheet);
		espaco = new Espaco(this.spriteSheet);
		lasers = new ArrayList<Laser>();
		jogoState = 'N';
		isRunning = true;
		//estaSuspensa = false;
		bananasColetadas = 0;
		lentidao = 50;
		distancia = 0;
		contador = 0;
	}	
	
	

	


}
