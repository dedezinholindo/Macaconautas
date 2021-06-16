package mc322.macaconautas.Loja;

import javax.swing.JFrame;

public class LojaView {
private ControleLoja conLoja;
	
	public LojaView(JFrame f) {
		conLoja = new ControleLoja(f);
	}
	
	public void mostrarLoja() throws InterruptedException {
		conLoja.start();
	}
	
	public char getLojaState() {
		return conLoja.getLojaState();
	}
}
