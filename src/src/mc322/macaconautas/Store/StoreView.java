package mc322.macaconautas.Store;

import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IStore;
import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class StoreView implements IStore{
	private StoreControl conStore;
	static int bananaQuantity;
	
	public StoreView(JFrame f, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		conStore = new StoreControl(f, ownedSkins, selectedSkin, spriteSheet);
		this.bananaQuantity = Control.getBananaQuantity();
	}
	
	public void shows() throws InterruptedException {
		conStore.start();
	}
	
	public char getState() {
		return conStore.getStoreState();
	}

	@Override
	public int getSelectedSkin() {
		return conStore.getSelectedSkin();
	}
	
	public int getBananaQuantity() {
		return bananaQuantity;
	}

	@Override
	public boolean[] getOwnedSkins() {
		return conStore.getOwnedSkins();
	}
}
