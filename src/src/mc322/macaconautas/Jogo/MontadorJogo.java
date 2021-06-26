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

import mc322.macaconautas.Entity.Monkey;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class MontadorJogo {
	
	final static int TAMANHO_STRING_JOGO = 17;
	final static int WIDTH = Controle.WIDTH; 
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	final static String[] OPTIONS = {"Resume", "Menu"};
	final static int MAX_OPTIONS = OPTIONS.length - 1;
	
	SpriteSheet spriteSheet;

	int lentidao;
	char jogoState; //N para normal, P para pausado (uso do pause), G para game over parcial e O para Game Over
	boolean isRunning;
	boolean pause;
	Thread thread;
	Monkey monkey;
	Space space;
	int bananasColetadas;
	long distancia;
	int contador;
	boolean showMessageGameOver;
	int framesMessageGameOver;
	int currentOption;
	boolean gameUp;
	boolean gameDown;
	boolean enter;

	public MontadorJogo(SpriteSheet spriteSheet, int selectedSkin){
		this.spriteSheet = spriteSheet;
		space = new Space(Controle.WIDTH * Controle.SCALE, Controle.HEIGHT * Controle.SCALE, 40, this.spriteSheet);
		monkey = new Monkey(10 * Controle.SCALE, Controle.HEIGHT / 2, this.space, this.spriteSheet, selectedSkin);
		space.setMonkey(monkey);
		jogoState = 'N';
		isRunning = true;
		pause = false;
		bananasColetadas = 0;
		lentidao = 50;
		distancia = 0;
		contador = 0;
		showMessageGameOver = true;
		framesMessageGameOver = 0;
		currentOption = 0;
		gameUp = false;
		gameDown = false;
		enter = false;
		
	}	
	
}
