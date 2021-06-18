package mc322.macaconautas.Loja;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class LojaView implements IModo{
	private ControleLoja conLoja;
	static int quantidadeBananas;
	
	public LojaView(JFrame f, SpriteSheet spriteSheet) {
		conLoja = new ControleLoja(f, spriteSheet);
		quantidadeBananas = Controle.getQuantidadeBananas();
	}
	
	public void mostrar() throws InterruptedException {
		conLoja.start();
	}
	
	public char getState() {
		return conLoja.getLojaState();
	}
}
