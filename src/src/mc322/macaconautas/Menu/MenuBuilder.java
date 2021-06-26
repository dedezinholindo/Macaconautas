package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.app.Control;

public class MenuBuilder {
	final static int WIDTH = Control.WIDTH; //criar classe superior
	final static int HEIGHT = Control.HEIGHT;
	final static int BORDER = Control.BORDER;
	final static int SCALE = Control.SCALE;
	final static String[] OPTIONS = {"Play", "Store", " Save and exit"};
	final static int MAX_OPTIONS = OPTIONS.length - 1;
	
	char menuState; //N normal, L ir loja, J ir jogo, F para sair
	boolean isRunning;
	Thread thread;
	int currentOption;
	boolean menuUp;
	boolean menuDown;
	boolean enter;
	
	public MenuBuilder() {
		menuState = 'N';
		isRunning = true;
		currentOption = 0;
		menuUp = false;
		menuDown = false;
		enter = false;
	}
}
