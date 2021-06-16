package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IModo;

public class MenuView implements IModo{
	private ControleMenu conMenu;
	
	public MenuView(JFrame f) {
		conMenu = new ControleMenu(f);
	}
	
	public void mostrar() throws InterruptedException {
		conMenu.start();
	}
	
	public char getState() {
		return conMenu.getMenuState();
	}
}
