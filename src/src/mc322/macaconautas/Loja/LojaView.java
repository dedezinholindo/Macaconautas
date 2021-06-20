package mc322.macaconautas.Loja;

import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.ILoja;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class LojaView implements ILoja{
	private ControleLoja conLoja;
	static int bananaQuantity;
	
	public LojaView(JFrame f, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		conLoja = new ControleLoja(f, ownedSkins, selectedSkin, spriteSheet);
		this.bananaQuantity = Controle.getBananaQuantity();
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
		return bananaQuantity;
	}

	@Override
	public boolean[] getOwnedSkins() {
		return conLoja.getOwnedSkins();
	}
}
