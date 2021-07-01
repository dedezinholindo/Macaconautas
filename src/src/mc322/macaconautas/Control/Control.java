package mc322.macaconautas.Control;

import java.awt.Canvas;
import java.util.Arrays;
import javax.swing.JFrame;

import mc322.macaconautas.Interface.*;
import mc322.macaconautas.SaveGameHandling.SaveGameHandling;
import mc322.macaconautas.SpriteSheet.SpriteSheet;
import mc322.macaconautas.Menu.MenuView;
import mc322.macaconautas.Game.GameView;
import mc322.macaconautas.Store.StoreView;

public class Control extends Canvas implements IInit {

	private static final long serialVersionUID = -8313130418438606868L;

	private final static int FRAME_WIDTH = 800;
	private final static int FRAME_HEIGHT = 600;

	private final static int THREAD_SLEEP_TIME = 400; // alterar baseado no processamento do computador (erro de renderização).

	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private final static int SKIN_QUANTITY = 7;
	private final static int INITIAL_SKIN = 0;

	private JFrame f;
	private char appState; // 'S' para Store, 'M' para Menu, 'G' para Game e 'O' de Fim.
	private ISaveGameHandling saveGameHandling;
	private IMenu menu;
	private IGame game;
	private IStore store;
	private boolean isMenuCreated;
	private boolean isGameCreated;
	private boolean isStoreCreated;
	private SpriteSheet spriteSheet;
	private boolean[] ownedSkins;
	private int selectedSkin;
	private int bananaQuantity;
	private long record;

	/**
	 * Inicializa um Control.
	 */
	public Control() {
		initFrame();
		initAtributos();
	}

	/**
	 * Inicializa o JFrame utilizado pelo Control.
	 */
	private void initFrame() {
		f = new JFrame("MACACONAUTAS"); 
		f.add(this); 
		f.setLayout(null);
		f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		f.setResizable(false); 
		f.setLocationRelativeTo(null); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		f.setVisible(true); 
	}

	/**
	 * Inicializa os atributos do Control.
	 */
	private void initAtributos() {
		this.appState = 'M';
		this.menu = null;
		this.game = null;
		this.store = null;
		this.isMenuCreated = false;
		this.isGameCreated = false;
		this.isStoreCreated = false;
		this.saveGameHandling = new SaveGameHandling();
		this.spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		loadSavedGame();
	}

	/**
	 * Converte uma String de números em um array de booleans.
	 * @param numbers uma String de números.
	 * @return array de booleans correspondente.
	 */
	private boolean[] numbersStringToBooleanArray(String numbers) {
		boolean b[] = new boolean[numbers.length()];
		for (int i = 0; i < b.length; i++) {			
			b[i] = ((numbers.charAt(i) != '0') ? true : false);
		}
		return b;
	}

	/**
	 * Inicializa alguns atributos de acordo com o arquivo de salvamento do
	 * jogo. Caso não exista um, inicializa os atributos com valores pré-
	 * -determinados.
	 */
	private void loadSavedGame() {
		String info[] = this.saveGameHandling.getSavedInfo();
		if (info != null) { // carregar jogo salvo.
			this.ownedSkins = numbersStringToBooleanArray(info[0]);
			this.selectedSkin = Integer.parseInt(info[1]);
			this.bananaQuantity = Integer.parseInt(info[2]);
			this.record = Integer.parseInt(info[3]); 
		} else { // criar novo jogo.
			this.ownedSkins = new boolean[SKIN_QUANTITY];
			Arrays.fill(this.ownedSkins, false);
			this.ownedSkins[INITIAL_SKIN] = true;
			this.selectedSkin = INITIAL_SKIN;
			this.bananaQuantity = 0; 
			this.record = 0; 
		}
	}

	/**
	 * Inicializa o funcionamento do jogo.
	 */
	public void init() throws InterruptedException {	//throws para sleep	(aplicar try catch)
		while (this.appState != 'O') {
			switch(this.appState) {
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
		this.saveGameHandling.saveGame(this.ownedSkins, this.selectedSkin, this.bananaQuantity, this.record); // salvando o jogo.
		System.exit(0);
	}

	/**
	 * Abre um Menu.
	 * @throws InterruptedException
	 */
	public void openMenu() throws InterruptedException {
		if (!this.isMenuCreated) {
			this.menu = new MenuView(this.bananaQuantity, this.record, this.f);
			this.isMenuCreated = true;
			this.menu.shows();
		}
		Thread.currentThread();
		Thread.sleep(THREAD_SLEEP_TIME); 
		switch(this.menu.getState()) {
		case 'S': // ir para a Store.
			this.appState = 'S';
			this.isMenuCreated = false;
			break;
		case 'G': // ir para o Game.
			this.appState = 'G';
			this.isMenuCreated = false;
			break;
		case 'O': // sair do app.
			this.appState = 'O';
			this.isMenuCreated = false;
			break;
		default:
			break;
		}		
	}

	/**
	 * Abre um Game.
	 * @throws InterruptedException
	 */
	public void openJogo() throws InterruptedException {
		if (!this.isGameCreated) {
			this.game = new GameView(this.bananaQuantity, this.record, this.selectedSkin, this.f, this.spriteSheet);
			this.isGameCreated = true;
			this.game.shows();
		} 
		Thread.currentThread();
		Thread.sleep(THREAD_SLEEP_TIME); 
		if (this.game.getState() == 'O') { // fim de jogo, ir para o Menu.
			this.appState = 'M';
			this.isGameCreated = false;
			this.bananaQuantity = game.getBananaQuantity();
			this.record = game.getRecord();
		}
	}

	/**
	 * Abre uma Store.
	 * @throws InterruptedException
	 */
	public void openStore() throws InterruptedException {
		if (!this.isStoreCreated) {
			this.store = new StoreView(this.bananaQuantity, this.ownedSkins, this.selectedSkin, this.f, this.spriteSheet);
			this.store.shows();
			this.isStoreCreated = true;
		}
		Thread.currentThread();
		Thread.sleep(THREAD_SLEEP_TIME); 
		if (this.store.getState() == 'M') { // ir para o Menu.
			this.appState = 'M';
			this.isStoreCreated = false;
			this.bananaQuantity = store.getBananaQuantity();
			this.selectedSkin = store.getSelectedSkin();
			this.ownedSkins = store.getOwnedSkins();
		}
	}
}              