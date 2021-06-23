package mc322.macaconautas.app;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.Interface.IInit;
import mc322.macaconautas.Interface.ILoja;
import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.Jogo.*;
import java.awt.Canvas;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import mc322.macaconautas.Jogo.ControleJogo;
import mc322.macaconautas.Loja.LojaView;
import mc322.macaconautas.Menu.MenuView;

public class Controle extends Canvas implements IInit{
	public final static int BORDA = 37; //grossura da borda
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 5;
	
	private final static int TEMPO_BUG = 400;
	
	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private final static int SKIN_QUANTITY = 5;
	private final static int INITIAL_SKIN = 0;

	public static JFrame f;
	private static char appState; //"L" para Loja, "M" para menu inicial, "J" para jogo e F de Fim
	private boolean jogoCriado;
	private boolean menuCriado;
	private boolean lojaCriada;
	private IGame jogo;
	private IModo menu;
	private ILoja loja;
	private SpriteSheet spriteSheet;
	private static int bananaQuantity;
	private static long record;
	private static boolean[] ownedSkins; //ou jogo salvo
	private static int selectedSkin;

	public Controle() {
		initFrame();
		initAtributos();
	}
	
	private void initAtributos() {
		this.appState = 'M';
		this.jogoCriado = false;
		this.menuCriado = false;
		this.lojaCriada = false;
		this.jogo = null;
		this.menu = null;
		this.loja = null;
		this.spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.bananaQuantity = 0; //ou de acordo com o jogo salvo
		this.record = 0; //ou o jogo salvo
		this.ownedSkins = new boolean[SKIN_QUANTITY];
		Arrays.fill(this.ownedSkins, false);
		this.ownedSkins[INITIAL_SKIN] = true;
		this.selectedSkin = INITIAL_SKIN;
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
	
	public static int getBananaQuantity() {
		return bananaQuantity;
	}
	
	public static boolean[] getSkinsLiberadas() {
		return ownedSkins;
	}
	
	public void abrirMenu() throws InterruptedException {
		if(!menuCriado) {
			menu = new MenuView(f);
			menu.mostrar();
			menuCriado = true;
		}
		Thread.currentThread().sleep(TEMPO_BUG);//alterar baseado no processamento do computador(error de renderizacao)
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
			loja = new LojaView(f, this.ownedSkins, this.selectedSkin, this.spriteSheet);
			loja.mostrar();
			lojaCriada = true;
		} 
		Thread.currentThread().sleep(TEMPO_BUG); //operacoes imediatas ocasionam erros inesperaveis
		if (loja.getState() == 'M') {
			appState = 'M';
			lojaCriada = false;
			this.bananaQuantity = loja.getBananaQuantity();
			this.selectedSkin = loja.getSelectedSkin();
			this.ownedSkins = loja.getOwnedSkins();
		}
	}
	
	public void abrirJogo() throws InterruptedException {
		if(!jogoCriado) {
			jogo = new JogoView(f, this.spriteSheet, this.selectedSkin);
			jogo.mostrar();
			jogoCriado = true;
		} 
		Thread.currentThread().sleep(TEMPO_BUG); //operacoes imediatas ocasionam erros inesperaveis
		if (jogo.getState() == 'O') {
			appState = 'M';
			jogoCriado = false;
			record = jogo.getDistancia();
			this.bananaQuantity += jogo.getBananaQuantity();
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