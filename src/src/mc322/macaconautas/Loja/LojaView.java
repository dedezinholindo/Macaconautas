package mc322.macaconautas.Loja;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IModo;

public class LojaView implements IModo{
private ControleLoja conLoja;
	
	public LojaView(JFrame f) {
		conLoja = new ControleLoja(f);
	}
	
	public void mostrar() throws InterruptedException {
		conLoja.start();
	}
	
	public char getState() {
		return conLoja.getLojaState();
	}
}
