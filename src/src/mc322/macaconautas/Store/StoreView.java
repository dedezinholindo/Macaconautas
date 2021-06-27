package mc322.macaconautas.Store;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IStore;
import mc322.macaconautas.app.SpriteSheet;

public class StoreView implements IStore{
	private StoreControl conStore;
	
	public StoreView(JFrame f, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet, int bananaQuantity) {
		this.conStore = new StoreControl(f, ownedSkins, selectedSkin, spriteSheet, bananaQuantity);
	}
	
	public void shows() throws InterruptedException {
		this.conStore.start();
	}
	
	public char getState() {
		return this.conStore.getState();
	}

	@Override
	public int getSelectedSkin() {
		return this.conStore.getSelectedSkin();
	}
	
	public int getBananaQuantity() {
		return this.conStore.getBananaQuantity();
	}

	@Override
	public boolean[] getOwnedSkins() {
		return this.conStore.getOwnedSkins();
	}
}
