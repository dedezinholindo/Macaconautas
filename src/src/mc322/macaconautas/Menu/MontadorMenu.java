package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;

public class MontadorMenu {
	final static int WIDTH = Controle.WIDTH; //criar classe superior
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	final static String[] OPTIONS = {"Jogar", "Loja", " Sair do Jogo"};
	final static int MAX_OPTIONS = OPTIONS.length - 1;
	
	char menuState; //N normal, L ir loja, J ir jogo, F para sair
	boolean isRunning;
	Thread thread;
	int currentOption;
	boolean menuUp;
	boolean menuDown;
	boolean enter;
	
	public MontadorMenu() {
		menuState = 'N';
		isRunning = true;
		currentOption = 0;
		menuUp = false;
		menuDown = false;
		enter = false;
	}
}
