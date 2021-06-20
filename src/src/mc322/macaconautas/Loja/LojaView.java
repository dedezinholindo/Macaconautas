package mc322.macaconautas.Loja;

import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.ILoja;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class LojaView implements ILoja{
	private ControleLoja conLoja;
	
	public LojaView(JFrame f, int bananaQuantity, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		conLoja = new ControleLoja(f, bananaQuantity, ownedSkins, selectedSkin, spriteSheet);
	}
	
	public void mostrar() throws InterruptedException {
		conLoja.start();
	}
	
	public char getState() {
		return conLoja.getLojaState();
	}

	@Override
	public int getSelectedSkin() {
		return conLoja.getSelectedSkin();
	}
	
	public int getBananaQuantity() {
		return conLoja.getBananaQuantity();
	}

	@Override
	public void setBananaQuantity(int bananaQuantity) {
		conLoja.setBananaQuantity(bananaQuantity);
	}

	@Override
	public boolean[] getOwnedSkins() {
		return conLoja.getOwnedSkins();
	}
}
