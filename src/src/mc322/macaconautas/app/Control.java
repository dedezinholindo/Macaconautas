package mc322.macaconautas.app;

import mc322.macaconautas.Game.*;
import mc322.macaconautas.Interface.*;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import mc322.macaconautas.Menu.MenuView;
import mc322.macaconautas.Store.StoreView;

public class Control extends Canvas implements IInit {

	private final static int FRAME_WIDTH = 160;
	private final static int FRAME_HEIGHT = 120;
	private final static int FRAME_SCALE = 5;

	private final static int THREAD_SLEEP_TIME = 400;

	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private final static int SKIN_QUANTITY = 5;
	private final static int INITIAL_SKIN = 0;

	private JFrame f;
	private int frameWidth;
	private int frameHeigth;
	private int frameScale;
	private char appState; // 'S' para Store, 'M' para Menu, 'G' para Game e 'O' de Fim.
	private boolean isGameCreated;
	private boolean isMenuCreated;
	private boolean isStoreCreated;
	private ISaveGame save;
	private IMenu menu;
	private IGame game;
	private IStore store;
	private SpriteSheet spriteSheet;
	private boolean[] ownedSkins;
	private int selectedSkin;
	private int bananaQuantity;
	private long record;

	public Control() {
		initFrame();
		initAtributos();
	}
	
	private void initAtributos() {
		this.appState = 'M';
		this.isGameCreated = false;
		this.isMenuCreated = false;
		this.isStoreCreated = false;
		this.game = null;
		this.menu = null;
		this.store = null;
		this.spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.save = new SaveGame();
		loadSavedGame();
	}

	private void initFrame() {
		this.frameWidth = FRAME_WIDTH;
		this.frameHeigth = FRAME_HEIGHT;
		this.frameScale = FRAME_SCALE;
		f = new JFrame("MACACONAUTAS"); //titulo do game ou setTitle()
		f.add(this); //adicionar o que criamos para ficar visível
		f.setLayout(null);
		f.setSize(this.frameWidth * this.frameScale, this.frameHeigth * this.frameScale);
		f.setResizable(false); //nao pode redimensionar 
		f.setLocationRelativeTo(null); //centro (tem que estar depois do pack)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar quando clicar no x e parar de vez
		f.setVisible(true); //deixar ele visivel
	}
	
	private boolean[] numbersToBoolean(String numbers) {
		boolean b[] = new boolean[numbers.length()];
		for (int i = 0; i < b.length; i++) {			
			b[i] = ((numbers.charAt(i) == '1') ? true : false);
		}
		return b;
	}

	private void loadSavedGame() {
		String info[] = save.getSavedInfo();
		if (info != null) { // carregar game salvo.
			this.ownedSkins = numbersToBoolean(info[0]);
			this.selectedSkin = Integer.parseInt(info[1]);
			this.bananaQuantity = Integer.parseInt(info[2]);
			this.record = Integer.parseInt(info[3]); 
		} else { // criar novo game.
			this.ownedSkins = new boolean[SKIN_QUANTITY];
			Arrays.fill(this.ownedSkins, false);
			this.ownedSkins[INITIAL_SKIN] = true;
			this.selectedSkin = INITIAL_SKIN;
			this.bananaQuantity = 0; 
			this.record = 0; 
		}
	}

	public void init() throws InterruptedException {	//throws para sleep	(aplicar try catch)
		while (appState != 'O') {
			switch(appState) {
			case 'M':
				openMenu();
				break;
				
			case 'S':
				openStore();
				break;
				
			case 'G':
				openJogo();
				break;
			default:
				break;
			}
		}
		save.saveGame(this.ownedSkins, this.selectedSkin, this.bananaQuantity, this.record);
		System.exit(0);
	}	
	
	public void openMenu() throws InterruptedException {
		if (!isMenuCreated) {
			menu = new MenuView(f, this.bananaQuantity, this.record);
			menu.shows();
			isMenuCreated = true;
		}
		Thread.currentThread().sleep(THREAD_SLEEP_TIME); // alterar baseado no processamento do computador (erro de renderizacao).
		switch(menu.getState()) {
		case 'S':
			appState = 'S';
			isMenuCreated = false;
			break;
		case 'G':
			appState = 'G';
			isMenuCreated = false;
			break;
		case 'O':
			appState = 'O';
			isMenuCreated = false;
			break;
		default:
			break;
		}		
	}
	
	public void openStore() throws InterruptedException {
		if (!isStoreCreated) {
			store = new StoreView(f, this.ownedSkins, this.selectedSkin, this.spriteSheet, this.bananaQuantity);
			store.shows();
			isStoreCreated = true;
		} 
		Thread.currentThread().sleep(THREAD_SLEEP_TIME); //operacoes imediatas ocasionam erros inesperaveis
		if (store.getState() == 'M') {
			appState = 'M';
			isStoreCreated = false;
			this.bananaQuantity = store.getBananaQuantity();
			this.selectedSkin = store.getSelectedSkin();
			this.ownedSkins = store.getOwnedSkins();
		}
	}
	
	public void openJogo() throws InterruptedException {
		if (!isGameCreated) {
			game = new GameView(f, this.selectedSkin, this.spriteSheet, this.bananaQuantity, this.record);
			game.shows();
			isGameCreated = true;
		} 
		Thread.currentThread().sleep(THREAD_SLEEP_TIME); //operacoes imediatas ocasionam erros inesperaveis
		if (game.getState() == 'O') {
			appState = 'M';
			isGameCreated = false;
			this.bananaQuantity = game.getBananaQuantity();
			this.record = game.getRecord();
		}
	}
}              