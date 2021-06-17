package mc322.macaconautas.Loja;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.app.Controle;

public class LojaView implements IModo{
	private ControleLoja conLoja;
	static int quantidadeBananas;
	
	public LojaView(JFrame f) {
		conLoja = new ControleLoja(f);
		quantidadeBananas = Controle.getQuantidadeBananas();
	}
	
	public void mostrar() throws InterruptedException {
		conLoja.start();
	}
	
	public char getState() {
		return conLoja.getLojaState();
	}
}
