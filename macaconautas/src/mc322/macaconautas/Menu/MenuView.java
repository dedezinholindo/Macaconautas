package mc322.macaconautas.Menu;

import javax.swing.JFrame;

public class MenuView {
	private ControleMenu conMenu;
	
	public MenuView(JFrame f) {
		conMenu = new ControleMenu(f);
	}
	
	public void mostrarMenu() throws InterruptedException {
		conMenu.start();
	}
	
	public char getMenuState() {
		return conMenu.getMenuState();
	}
}
