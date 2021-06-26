package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IMode;
import mc322.macaconautas.app.Control;

public class MenuView implements IMode {
	private MenuControl conMenu;
	static long record;
	static int bananaQuantity;
	
	public MenuView(JFrame f) {
		conMenu = new MenuControl(f);
		record = Control.getRecord();
		bananaQuantity = Control.getBananaQuantity();
	}
	
	public void shows() throws InterruptedException {
		conMenu.start();
	}
	
	public char getState() {
		return conMenu.getMenuState();
	}
}
