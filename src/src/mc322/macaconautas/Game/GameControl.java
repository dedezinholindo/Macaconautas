package mc322.macaconautas.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Entity.*;
import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class GameControl extends Canvas implements Runnable, KeyListener {

	private GameBuilder game;
	private JFrame f;

	public GameControl(JFrame f, SpriteSheet spriteSheet, int selectedSkin) throws InterruptedException {
		game = new GameBuilder(spriteSheet, selectedSkin);
		f.addKeyListener(this);
		this.f = f;
	}	
	
	int getColectedBananas() {
		return game.colectedBananas;
	}
	
	char getGameState() {
		return game.gameState;
	}
	
	long getDistance() {
		if(game.distance < GameView.record) {
			return GameView.record;
		}
		//renderizar new record
		return game.distance;
	}
	
	private void slownessGame(int slowness) {
		if(slowness == game.counter) {
			game.distance += 1;
			game.counter = 0;
			return;
		}
		game.counter += 1;
	}
	
//	private void gorillaTime() {
//		game.monkey.setContadorGorila(game.monkey.getContadorGorila() + 1);
//	}
	
	private void framesGameOver() {
		game.framesMessageGameOver++;
		if(game.framesMessageGameOver == 25) {
			game.framesMessageGameOver = 0;
			if(game.showMessageGameOver) {
				game.showMessageGameOver = false;
			} else {
				game.showMessageGameOver = true;
			}
		}
	}

	private void tick() {
		//Update the AppMonkeynautas
		switch(game.gameState) {
		case 'N':
			game.colectedBananas += game.space.tick();
			if (game.monkey.isDefeated()) {
				game.gameState = 'G';
			}
			slownessGame(game.slowness); 
			break;

		case 'P':
			pause(); 
			break;

			
		case 'G':
			framesGameOver();
			break;
			
		case 'O':
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		game.isRunning = true;
		game.thread = new Thread(this);
		game.thread.start();
	}

	private synchronized void pause() {
		if(game.gameUp) {
			game.gameUp = false;
			game.currentOption--;
			if(game.currentOption < 0) {
				game.currentOption = game.MAX_OPTIONS;
			}
		}
		if(game.gameDown) {
			game.gameDown = false;
			game.currentOption++;
			if(game.currentOption > game.MAX_OPTIONS) {
				game.currentOption = 0;
			}
		}
		if(game.enter) {
			game.enter = false;
			if(game.currentOption == 0) {
				game.gameState = 'N';
			} else if(game.currentOption == 1) {
				game.gameState = 'O';
			}
		}
	}

	public synchronized void stop() {
		game.isRunning = false;
	}
	
	private void renderStringsEspaco(Graphics g, String s, int p_x, int p_y, Color c) {
		g.setFont(new Font("arial", Font.BOLD, game.SIZE_STRING_GAME));
		g.setColor(c);
		g.drawString(s, p_x, p_y);	
	}
	
	private void renderPause(Graphics g) {
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", game.WIDTH * game.SCALE/2 - 100, (game.HEIGHT * game.SCALE - game.BORDER)/2 - 90);
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("Resume", game.WIDTH * game.SCALE/2 - 57, (game.HEIGHT * game.SCALE - game.BORDER)/2 - 10);
		
		g.setColor(Color.WHITE);
		g.drawString("Menu", game.WIDTH * game.SCALE/2 - 50, (game.HEIGHT * game.SCALE - game.BORDER)/2 + 70);
	}
	
	private void moveArrow(Graphics g) {
		if(game.currentOption == 0) {
			g.drawString(">", game.WIDTH * game.SCALE/2 - 57 - 30 , (game.HEIGHT * game.SCALE - game.BORDER)/2 - 10);
		} else if(game.currentOption == 1) {
			g.drawString(">", game.WIDTH * game.SCALE/2 - 50 - 30 , (game.HEIGHT * game.SCALE - game.BORDER)/2 + 70);
		}
	}
	
	private void renderGameOver(Graphics g) {
		//TELA GAME OVER
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,180)); //transparencia
		g2.fillRect(0, game.BORDER, game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE - game.BORDER);

		//FRASE GAME OVER
		g.setFont(new Font("arial", Font.BOLD, 70));
		g.setColor(Color.WHITE);
		g.drawString("GAME OVER", game.WIDTH * game.SCALE/2 - 220 , (game.HEIGHT * game.SCALE - game.BORDER)/2 - 100);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Collected bananas: " + game.colectedBananas, game.WIDTH * game.SCALE/2 - 120 , (game.HEIGHT * game.SCALE - game.BORDER)/2 - 20);
		g.drawString("Traveled distance: " + game.distance + " m", game.WIDTH * game.SCALE/2 - 120 , (game.HEIGHT * game.SCALE - game.BORDER)/2 + 60);
		//fazer comparacao do recorde aqui
		if(game.showMessageGameOver) {
			g.drawString(">> Press ENTER to go to the Menu <<", game.WIDTH * game.SCALE/2 - 200 , (game.HEIGHT * game.SCALE - game.BORDER)/2 + 140);
		}
	}

	//TIRAR DAQUI??
	private void render() {
		//renderizar the AppMonkeynautas
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3);//sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.black);
		g.fillRect(0, game.BORDER, game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE - game.BORDER); //aparece um retangulo na tela (x,y,largura,altura)
		game.space.render(g);
		//banana
		String stringBanana = "Bananas: " + (game.colectedBananas + GameView.bananaQuantity);
		renderStringsEspaco(g, stringBanana, game.WIDTH * game.SCALE - (stringBanana.length())*game.SIZE_STRING_GAME, game.HEIGHT * game.SCALE, Color.LIGHT_GRAY);
		
		//distance
		String stringDistance = game.distance + " m";
		renderStringsEspaco(g, stringDistance, (game.WIDTH * game.SCALE - (stringDistance.length())*game.SIZE_STRING_GAME)/2, game.SIZE_STRING_GAME + game.BORDER, Color.LIGHT_GRAY);
		
		if(game.gameState == 'P') {
			renderPause(g);
			moveArrow(g);
		}
		
		if (game.gameState == 'G') {
			renderGameOver(g);
		} 
		bs.show();
	}
	
//	private void transformGorilla() {
//		tempoDeGorila();
//		returnToMonkey();
//	}
	
//	private void virarGorila() {
//		game.monkey.setContadorGorila(0);
//		game.monkey.setIsGorila(true);
//		game.monkey.setSize(game.monkey.getGorilaWidth(), game.monkey.getGorilaHeight());
//	}
	
//	private void returnToMonkey() {
//		if (game.monkey.IsGorila() && game.monkey.getContadorGorila() == game.monkey.getTempoDeGorila()) {
//			game.monkey.setContadorGorila(0);
//			game.monkey.setIsGorila(false);
//			game.monkey.setSize(game.monkey.getMonkeyWidth(), game.monkey.getMonkeyHeight());
//		}
//	}
//
//	private boolean checkCollisionAsteroid() {
//		Rectangle shapeMonkey = game.monkey.getBounds();
//		Rectangle shapeAsteroid = null;
//		for (int i = 0; i < game.space.getAsteroidsInSection(); i++) {
//			shapeAsteroid = game.space.getAsteroids().get(i).getBounds();
//			if (shapeMonkey.intersects(shapeAsteroid)) {
//				ArrayList<Asteroid> obstaculos = game.space.getAsteroids();
//				obstaculos.remove(i);
//				game.space.setAsteroids(obstaculos); //remocao do whey
//				game.space.setAsteroidsInSection(game.space.getAsteroidsInSection() - 1);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean checkCollisionAlien() {
//		Rectangle shapeMonkey = game.monkey.getBounds();
//		Rectangle shapeAlien = null;
//		for (int i = 0; i < game.space.getAliensInSection(); i++) {
//			shapeAlien = game.space.getAliens().get(i).getBounds();
//			if (shapeMonkey.intersects(shapeAlien)) {
//				ArrayList<Alien> aliens = game.space.getAliens();
//				aliens.remove(i);
//				game.space.setAliens(aliens); //remocao do whey
//				game.space.setAliensInSection(game.space.getAliensInSection() - 1);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean checkCollisionLaser() {
//		Rectangle shapeMonkey = game.monkey.getBounds();
//		Rectangle shapeLaser = null;
//		for (int i = 0; i < Alien.getLasers().size(); i++) {
//			shapeLaser = Alien.getLasers().get(i).getBounds();
//			if (shapeMonkey.intersects(shapeLaser)) {
//				ArrayList<Laser> lasers = Alien.getLasers();
//				lasers.remove(i);
//				Alien.setLasers(lasers); //remocao da banana
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private void checkCollisionBanana() {
//		Rectangle shapeMonkey = game.monkey.getBounds();
//		Rectangle shapeBanana = null;
//		for (int i = 0; i < game.space.getBananasInSection(); i++) {
//			shapeBanana = game.space.getBananas().get(i).getBounds();
//			if (shapeMonkey.intersects(shapeBanana)) {
//				ArrayList<Banana> bananas = game.space.getBananas();
//				bananas.remove(i);
//				game.space.setBananas(bananas); //remocao da banana
//				game.space.setBananasInSection(game.space.getBananasInSection() - 1); //sem isso o game pifa
//				game.colectedBananas += 1;
//			}
//		}
//	}
//	
//	private boolean checkCollisionWhey() {
//		Rectangle shapeMonkey = game.monkey.getBounds();
//		Rectangle shapeWhey = null;
//		for (int i = 0; i < game.space.getWheyInSection(); i++) {
//			shapeWhey = game.space.getWhey().get(i).getBounds();
//			if (shapeMonkey.intersects(shapeWhey)) {
//				ArrayList<WheyProtein> whey = game.space.getWhey();
//				whey.remove(i);
//				game.space.setWhey(whey); //remocao do whey
//				game.space.setWheyInSection(game.space.getWheyInSection() - 1);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private void checarColisoes() { 
//		if((checkCollisionAsteroid() || checkCollisionAlien() || checkCollisionLaser()) && !game.monkey.IsGorila()){
//			game.gameState = 'G';
//		}
//		if (checkCollisionWhey() && !game.monkey.IsGorila()) {
//			virarGorila();
//		}
//		checkCollisionBanana();
//	}

	public void run() {
		while (game.isRunning) {
			tick();
			render();
			try {
				Thread.sleep(1000/60); //60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		} 

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.monkey.setIsGoingUp(true);
		}
		//voltar menu quadno perde
		if(game.gameState == 'G' && e.getKeyCode() == KeyEvent.VK_ENTER) {
			game.gameState = 'O';
		}
		if(game.gameState == 'N' && e.getKeyCode() == KeyEvent.VK_P) {
			game.gameState = 'P';
		}
		if(game.gameState == 'P') {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				game.gameUp = true;
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				game.gameDown = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				game.enter = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.monkey.setIsGoingUp(false);
		}
	}

}              