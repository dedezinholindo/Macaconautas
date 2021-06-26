package mc322.macaconautas.app;

import mc322.macaconautas.Game.*;
import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.Interface.IInit;
import mc322.macaconautas.Interface.IStore;
import mc322.macaconautas.Interface.IMode;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import mc322.macaconautas.Menu.MenuView;
import mc322.macaconautas.Store.StoreView;

public class Control extends Canvas implements IInit{
	public final static int BORDER = 37; //grossura da borda
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 5;
	
	private final static int TIME_BUG = 400;
	
	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private final static int SKIN_QUANTITY = 5;
	private final static int INITIAL_SKIN = 0;

	public static JFrame f;
	private static char appState; //"L" para Store, "M" para menu inicial, "J" para game e F de Fim
	private boolean gameCreated;
	private boolean menuCreated;
	private boolean storeCreated;
	private SaveGame save;
	private IGame game;
	private IMode menu;
	private IStore store;
	private SpriteSheet spriteSheet;
	private boolean[] ownedSkins; //ou game salvo
	private int selectedSkin;
	private static int bananaQuantity;
	private static long record;

	public Control() {
		initFrame();
		initAtributos();
	}
	
	private void initAtributos() {
		this.appState = 'M';
		this.gameCreated = false;
		this.menuCreated = false;
		this.storeCreated = false;
		this.game = null;
		this.menu = null;
		this.store = null;
		this.spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.save = new SaveGame();
		loadSavedGame();
	}

	private void initFrame() {
		f = new JFrame("MACACONAUTAS"); //titulo do game ou setTitle()
		f.add(this); //adicionar o que criamos para ficar visível
		f.setLayout(null);
		f.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		f.setResizable(false); //nao pode redimensionar 
		f.setLocationRelativeTo(null); //centro (tem que estar depois do pack)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar quando clicar no x e parar de vez
		f.setVisible(true); //deixar ele visivel
	}
	
	private boolean[] NumbersToBoolean(String numbers) {
		boolean b[] = new boolean[numbers.length()];
		for (int i = 0; i < b.length; i++) {			
			b[i] = ((numbers.charAt(i) == '1') ? true : false);
		}
		return b;
	}
	
	private void loadSavedGame() {
		if(this.save.fileExists()) { // carregar game salvo.
			String info[] = save.getInfo();
			this.ownedSkins = NumbersToBoolean(info[0]);
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
		
	public static long getRecord() {
		return record;
	}
	
	public static void setAppState(char state) {
		appState = state;
	}
	
	public static int getBananaQuantity() {
		return bananaQuantity;
	}
	
	public boolean[] getSkinsLiberadas() {
		return this.ownedSkins;
	}

	public void init() throws InterruptedException {	//throws para sleep	(aplicar try catch)
		while(appState != 'F') {
			switch(appState) {
			case 'M':
				openMenu();
				break;
				
			case 'L':
				openStore();
				break;
				
			case 'J':
				openJogo();
				break;
			}
		}
		save.saveGame(this.ownedSkins, this.selectedSkin, this.bananaQuantity, this.record);
		System.exit(0);
	}	
	
	public void openMenu() throws InterruptedException {
		if(!menuCreated) {
			menu = new MenuView(f);
			menu.shows();
			menuCreated = true;
		}
		Thread.currentThread().sleep(TIME_BUG);//alterar baseado no processamento do computador(error de renderizacao)
		switch(menu.getState()) {
		case 'L':
			appState = 'L';
			menuCreated = false;
			break;
		
		case 'J':
			appState = 'J';
			menuCreated = false;
			break;
		
		case 'F':
			appState = 'F';
			menuCreated = false;
			break;
		}		
	}
	
	public void openStore() throws InterruptedException {
		if(!storeCreated) {
			store = new StoreView(f, this.ownedSkins, this.selectedSkin, this.spriteSheet);
			store.shows();
			storeCreated = true;
		} 
		Thread.currentThread().sleep(TIME_BUG); //operacoes imediatas ocasionam erros inesperaveis
		if (store.getState() == 'M') {
			appState = 'M';
			storeCreated = false;
			this.bananaQuantity = store.getBananaQuantity();
			this.selectedSkin = store.getSelectedSkin();
			this.ownedSkins = store.getOwnedSkins();
		}
	}
	
	public void openJogo() throws InterruptedException {
		if(!gameCreated) {
			game = new GameView(f, this.spriteSheet, this.selectedSkin);
			game.shows();
			gameCreated = true;
		} 
		Thread.currentThread().sleep(TIME_BUG); //operacoes imediatas ocasionam erros inesperaveis
		if (game.getState() == 'O') {
			appState = 'M';
			gameCreated = false;
			record = game.getDistance();
			this.bananaQuantity += game.getBananaQuantity();
		}
	}
}              