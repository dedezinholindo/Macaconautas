package mc322.macaconautas.Loja;

import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.ILoja;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class LojaView implements ILoja{
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

	@Override
	public int getSkinSelected() {
		return conLoja.getSelectedSkin();
	}
	
	public int getBananas() {
		return quantidadeBananas;
	}

	@Override
	public boolean[] getSkinsLiberadas() {
		return conLoja.getSkinsLIberadas();
	}
}
