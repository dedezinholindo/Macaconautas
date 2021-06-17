package mc322.macaconautas.Loja;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;

public class MontadorLoja {
	final static int WIDTH = Controle.WIDTH; //criar classe superior
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	final static JFrame f  = Controle.f;
	final static String[] OPTIONS = {"skin1", "skin2", "skin3", "skin4", "skin5", "skin6"};
	final static int MAX_OPTIONS = OPTIONS.length - 1;
	
	char lojaState; //N normal, M para ir para o menu
	int selectedSkin;
	boolean isRunning;
	Thread thread;
	int currentOption;
	boolean lojaRight;
	boolean lojaLeft;
	boolean enter;
	
	public MontadorLoja() {
		lojaState = 'N';
		isRunning = true;
		lojaLeft = false;
		lojaRight = false;
		selectedSkin = 0;
	}
}
