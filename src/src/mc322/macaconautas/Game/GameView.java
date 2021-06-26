package mc322.macaconautas.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class GameView extends Canvas implements IGame{
	
	private GameControl conGame;
	static long record;
	static int bananaQuantity;
	
	public GameView(JFrame f, SpriteSheet spriteSheet, int selectedSkin) throws InterruptedException {
		conGame = new GameControl(f, spriteSheet, selectedSkin); 
		record = Control.getRecord();
		bananaQuantity = Control.getBananaQuantity();
	}
	
	public void shows() throws InterruptedException {
		conGame.start();
	}
	
	public int getBananaQuantity() {
		return conGame.getColectedBananas();
	}
	
	public char getState() {
		return conGame.getGameState();
	}
	
	public long getDistance() {
		return conGame.getDistance();
	}
}
