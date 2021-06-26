package mc322.macaconautas.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Entity.Monkey;
import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class GameBuilder {
	
	final static int SIZE_STRING_GAME = 17;
	final static int WIDTH = Control.WIDTH; 
	final static int HEIGHT = Control.HEIGHT;
	final static int BORDER = Control.BORDER;
	final static int SCALE = Control.SCALE;
	final static String[] OPTIONS = {"Resume", "Menu"};
	final static int MAX_OPTIONS = OPTIONS.length - 1;
	
	SpriteSheet spriteSheet;

	int slowness;
	char gameState; //N para normal, P para pausado (uso do pause), G para game over parcial e O para Game Over
	boolean isRunning;
	boolean pause;
	Thread thread;
	Monkey monkey;
	Space space;
	int colectedBananas;
	long distance;
	int counter;
	boolean showMessageGameOver;
	int framesMessageGameOver;
	int currentOption;
	boolean gameUp;
	boolean gameDown;
	boolean enter;

	public GameBuilder(SpriteSheet spriteSheet, int selectedSkin){
		this.spriteSheet = spriteSheet;
		space = new Space(Control.WIDTH * Control.SCALE, Control.HEIGHT * Control.SCALE, 40, this.spriteSheet);
		monkey = new Monkey(10 * Control.SCALE, Control.HEIGHT / 2, this.space, this.spriteSheet, selectedSkin);
		space.setMonkey(monkey);
		gameState = 'N';
		isRunning = true;
		pause = false;
		colectedBananas = 0;
		slowness = 50;
		distance = 0;
		counter = 0;
		showMessageGameOver = true;
		framesMessageGameOver = 0;
		currentOption = 0;
		gameUp = false;
		gameDown = false;
		enter = false;
		
	}	
	
}
