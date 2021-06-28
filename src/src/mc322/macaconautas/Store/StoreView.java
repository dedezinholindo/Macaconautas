package mc322.macaconautas.Store;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IStore;
import mc322.macaconautas.Control.SpriteSheet;

public class StoreView implements IStore{

	private StoreControl conStore;

	/**
	 * Inicializa um StoreView.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param ownedSkins indica skins possuídas.
	 * @param selectedSkin skin selecionada.
	 * @param f JFrame utilizado.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public StoreView(int bananaQuantity, boolean ownedSkins[], int selectedSkin, @SuppressWarnings("exports") JFrame f, SpriteSheet spriteSheet) {
		this.conStore = new StoreControl(bananaQuantity, ownedSkins, selectedSkin, f, spriteSheet);
	}

	/**
	 * Retorna o estado da store.
	 */
	public char getState() {
		return this.conStore.getState();
	}

	/**
	 * Mostra a store.
	 */
	public void shows() throws InterruptedException {
		this.conStore.start();
	}

	/**
	 * Retorna a quantidade de bananas possuídas.
	 */
	public int getBananaQuantity() {
		return this.conStore.getBananaQuantity();
	}

	/**
	 * Retorna as skins possuídas.
	 */
	public boolean[] getOwnedSkins() {
		return this.conStore.getOwnedSkins();
	}

	/**
	 * Retorna a skin selecionada.
	 */
	public int getSelectedSkin() {
		return this.conStore.getSelectedSkin();
	}
}