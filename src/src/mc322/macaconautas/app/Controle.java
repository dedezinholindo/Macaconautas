package mc322.macaconautas.app;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.Interface.IInit;
import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.Jogo.*;
import java.awt.Canvas;

import javax.swing.JFrame;

import mc322.macaconautas.Jogo.ControleJogo;
import mc322.macaconautas.Loja.LojaView;
import mc322.macaconautas.Menu.MenuView;

public class Controle extends Canvas implements IInit{
	public final static int BORDA = 37; //grossura da borda
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 5;
	public static JFrame f;
	
	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private static char appState; //"L" para Loja, "M" para menu inicial, "J" para jogo e F de Fim
	private static long record;
	private static int quantidadeBananas;
	private boolean jogoCriado;
	private boolean lojaCriada;
	private boolean menuCriado;
	private IGame jogo;
	private IModo menu;
	private IModo loja;
	private int[] skinsLiberadas;
	private SpriteSheet spriteSheet;

	public Controle() {
		initFrame();
		initAtributos();
	}
	
	private void initAtributos() {
		spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		appState = 'M';
		jogoCriado = false;
		lojaCriada = false;
		menuCriado = false;
		jogo = null;
		menu = null;
		loja = null;
		quantidadeBananas = 0; //ou de acordo com o jogo salvo
		record = 0; //ou o jogo salvo
		skinsLiberadas = null; //ou jogo salvo
		appState = 'M';
	}

	private void initFrame() {
		f = new JFrame("MACACONAUTAS"); //titulo do jogo ou setTitle()
		f.add(this); //adicionar o que criamos para ficar visível
		f.setLayout(null);
		f.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		f.setResizable(false); //nao pode redimensionar 
		f.setLocationRelativeTo(null); //centro (tem que estar depois do pack)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar quando clicar no x e parar de vez
		f.setVisible(true); //deixar ele visivel
	}
		
	public static long getRecord() {
		return record;
	}
	
	public static void setAppState(char state) {
		appState = state;
	}
	
	public static int getQuantidadeBananas() {
		return quantidadeBananas;
	}
	
	public void abrirMenu() throws InterruptedException {
		if(!menuCriado) {
			menu = new MenuView(f);
			menu.mostrar();
			menuCriado = true;
		}
		Thread.currentThread().sleep(100); //alterar baseado no processamento do computador(error de renderizacao)
		switch(menu.getState()) {
		case 'L':
			appState = 'L';
			menuCriado = false;
			break;
		
		case 'J':
			appState = 'J';
			menuCriado = false;
			break;
		
		case 'F':
			appState = 'F';
			menuCriado = false;
			break;
		}		
	}
	
	public void abrirLoja() throws InterruptedException {
		if(!lojaCriada) {
			loja = new LojaView(f);
			loja.mostrar();
			lojaCriada = true;
		}
		Thread.currentThread().sleep(100); //operacoes imediatas ocasionam erros inesperaveis
		if (loja.getState() == 'M') {
			appState = 'M';
			lojaCriada = false;
		}
	}
	
	public void abrirJogo() throws InterruptedException {
		if(!jogoCriado) {
			jogo = new JogoView(spriteSheet, f);
			jogo.mostrar();
			jogoCriado = true;
		}
		Thread.currentThread().sleep(100); //operacoes imediatas ocasionam erros inesperaveis
		if (jogo.getState() == 'O') {
			appState = 'M';
			jogoCriado = false;
			record = jogo.getDistancia();
			quantidadeBananas += jogo.getBananasColetadas();
		}
	}

	public void init() throws InterruptedException {	//throws para sleep	(aplicar try catch)
		while(appState != 'F') {
			switch(appState) {
			case 'M':
				abrirMenu();
				break;
				
			case 'L':
				abrirLoja();
				break;
				
			case 'J':
				abrirJogo();
				break;
			}
		}
		//salvar jogo
		System.exit(0);
	}	

}              