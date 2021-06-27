package mc322.macaconautas.Game;

import java.awt.Canvas;
import javax.swing.JFrame;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.app.SpriteSheet;

public class GameView extends Canvas implements IGame{
	
	private GameControl conGame;
	
	public GameView(JFrame f, int selectedSkin, SpriteSheet spriteSheet, int bananaQuantity, long record) throws InterruptedException {
		this.conGame = new GameControl(f, selectedSkin, spriteSheet, bananaQuantity, record); 
	}
	
	public void shows() throws InterruptedException {
		this.conGame.start();
	}
	
	public int getBananaQuantity() {
		return this.conGame.getBananaQuantity();
	}
	
	public char getState() {
		return this.conGame.getState();
	}
	
	public long getRecord() {
		return this.conGame.getRecord();
	}
}
