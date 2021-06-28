package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IMenu;

public class MenuView implements IMenu {

	private MenuControl conMenu;

	/**
	 * Inicializa um MenuView.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param record recorde de distância percorrida.
	 * @param f JFrame utilizado.
	 */
	public MenuView(int bananaQuantity, long record, @SuppressWarnings("exports") JFrame f) {
		this.conMenu = new MenuControl(bananaQuantity, record, f);
	}

	/**
	 * Retorna o estado do menu.
	 */
	public char getState() {
		return this.conMenu.getMenuState();
	}

	/**
	 * Mostra o menu.
	 */
	public void shows() throws InterruptedException {
		this.conMenu.start();
	}
}