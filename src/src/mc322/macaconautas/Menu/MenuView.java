package mc322.macaconautas.Menu;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.app.Controle;

public class MenuView implements IModo{
	private ControleMenu conMenu;
	static long record;
	static int quantidadeBananas;
	
	public MenuView(JFrame f) {
		conMenu = new ControleMenu(f);
		record = Controle.getRecord();
		quantidadeBananas = Controle.getBananaQuantity();
	}
	
	public void mostrar() throws InterruptedException {
		conMenu.start();
	}
	
	public char getState() {
		return conMenu.getMenuState();
	}
}
