package mc322.macaconautas.Loja;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;

public class MontadorLoja {
	final static int WIDTH = Controle.WIDTH; //criar classe superior
	final static int HEIGHT = Controle.HEIGHT;
	final static int BORDA = Controle.BORDA;
	final static int SCALE = Controle.SCALE;
	final static JFrame f  = Controle.f;
	
	char lojaState; //N normal, M para ir para o menu
	boolean isRunning;
	Thread thread;
	
	public MontadorLoja() {
		lojaState = 'N';
		isRunning = true;
	}
}
