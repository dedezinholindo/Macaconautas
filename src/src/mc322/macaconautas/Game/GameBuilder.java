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

	private final static String[] PAUSE_MENU_OPTIONS = {"Voltar ao Jogo", "Menu Principal"};
	
	int slowness = 12;
	SpriteSheet spriteSheet;
	char state; //N para normal, P para pausado (uso do pause), G para game over parcial e O para Game Over
	boolean isRunning;
	Thread thread;
	int bananaQuantity;
	long record;
	Space space;
	Monkey monkey;
	boolean pause;
	long distance;
	int counter;
	boolean showMessageGameOver;
	int framesMessageGameOver;
	String[] pauseMenuOptions;
	int currentPauseMenuOption;
	boolean goUp;
	boolean goDown;
	boolean select;

	public GameBuilder(int frameWidth, int frameHeight, int frameBorder, int selectedSkin, SpriteSheet spriteSheet, int bananaQuantity, long record){
		this.spriteSheet = spriteSheet;
		this.space = new Space(frameWidth, frameHeight, frameBorder, 40, this.spriteSheet);
		this.monkey = new Monkey(frameWidth / 16, frameHeight / 4 + frameBorder, this.space, this.spriteSheet, selectedSkin);
		this.space.setMonkey(monkey);
		this.state = 'N';
		this.isRunning = true;
		this.pause = false;
		this.bananaQuantity = bananaQuantity;
		this.record = record;
		this.distance = 0;
		this.counter = 0;
		this.showMessageGameOver = true;
		this.framesMessageGameOver = 0;
		this.pauseMenuOptions = PAUSE_MENU_OPTIONS;
		this.currentPauseMenuOption = 0;
		this.goUp = false;
		this.goDown = false;
		this.select = false;
	}	
	
}
