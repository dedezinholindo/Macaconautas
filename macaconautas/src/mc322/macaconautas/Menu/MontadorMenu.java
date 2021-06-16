package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;

public class MontadorMenu {
	final static int WIDTH = Controle.WIDTH; //criar classe superior
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	
	char menuState; //N normal, L ir loja, J ir jogo, F para sair
	boolean isRunning;
	Thread thread;
	
	public MontadorMenu() {
		menuState = 'N';
		isRunning = true;
	}
}
