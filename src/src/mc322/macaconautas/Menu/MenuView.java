package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IMenu;

public class MenuView implements IMenu {

	private MenuControl conMenu;

	public MenuView(JFrame f, int bananaQuantity, long record) {
		this.conMenu = new MenuControl(f, bananaQuantity, record);
	}

	public void shows() throws InterruptedException {
		this.conMenu.start();
	}

	public char getState() {
		return this.conMenu.getMenuState();
	}
}
