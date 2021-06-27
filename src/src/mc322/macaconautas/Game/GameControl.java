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

	private final static int SIZE_STRING_GAME = 17;

	private GameBuilder game;
	private JFrame f;
	private int frameWidth;
	private int frameHeight;
	private int frameBorder;

	public GameControl(JFrame f, int selectedSkin, SpriteSheet spriteSheet, int bananaQuantity, long record) throws InterruptedException {
		this.f = f;
		this.f.addKeyListener(this);
		this.frameWidth = this.f.getContentPane().getWidth();
		this.frameHeight = this.f.getContentPane().getHeight();
		this.frameBorder = this.f.getHeight() - this.frameHeight;
		this.game = new GameBuilder(this.frameWidth, this.frameHeight, this.frameBorder, selectedSkin, spriteSheet, bananaQuantity, record);
	}	
	
	int getBananaQuantity() {
		return this.game.bananaQuantity;
	}
	
	char getState() {
		return this.game.state;
	}
	
	long getRecord() {
		if (this.game.distance > this.game.record) {
			this.game.record = this.game.distance;
		}
		return this.game.record;
	}
	
	private void slownessGame(int slowness) {
		if (slowness == game.counter) {
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
		this.game.framesMessageGameOver++;
		if (this.game.framesMessageGameOver >= 25) {
			this.game.framesMessageGameOver = 0;
			this.game.showMessageGameOver = !this.game.showMessageGameOver;
		}
	}

	private void tick() {
		switch (this.game.state) {
		case 'N':
			this.game.bananaQuantity += this.game.space.tick();
			if (this.game.monkey.isDefeated()) {
				this.game.state = 'G';
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
		default:
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.game.isRunning = true;
		this.game.thread = new Thread(this);
		this.game.thread.start();
	}

	private synchronized void pause() {
		if (this.game.goUp) {
			this.game.goUp = false;
			this.game.currentPauseMenuOption--;
			if (this.game.currentPauseMenuOption < 0) {
				this.game.currentPauseMenuOption = this.game.pauseMenuOptions.length - 1;
			}
		}
		if (this.game.goDown) {
			this.game.goDown = false;
			this.game.currentPauseMenuOption++;
			if (this.game.currentPauseMenuOption >= this.game.pauseMenuOptions.length) {
				this.game.currentPauseMenuOption = 0;
			}
		}
		if (this.game.select) {
			this.game.select = false;
			switch (this.game.currentPauseMenuOption) {
			case 0:
				this.game.state = 'N';
				break;
			case 1:
				this.game.state = 'O';
				break;
			default:
				break;
			}
		}
	}

	public synchronized void stop() {
		this.game.isRunning = false;
	}

	private void renderPauseMenu(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(180, 180, 180, 50)); //transparencia
		g2.fillRect(this.frameWidth / 2 - 150, this.frameHeight / 2 + this.frameBorder - 115, 300, 230);
		
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", this.frameWidth / 2 - (150 - 5), this.frameHeight / 2 + this.frameBorder - 115 + 45);
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString(this.game.pauseMenuOptions[0], this.frameWidth / 2 - (150 - 30 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 110);
		g.drawString(this.game.pauseMenuOptions[1], this.frameWidth / 2 - (150 - 30 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 175);
	}
	
	private void renderPauseMenuArrow(Graphics g) {
		switch (this.game.currentPauseMenuOption) {
		case 0:
			g.drawString(">", this.frameWidth / 2 - (150 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 110);
			break;
		case 1:
			g.drawString(">", this.frameWidth / 2 - (150 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 175);
			break;
		default:
			break;
		}
	}
	
	private void renderGameOver(Graphics g) {
		//TELA GAME OVER
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 180)); //transparencia
		g2.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight);

		//FRASE GAME OVER
		g.setFont(new Font("arial", Font.BOLD, 70));
		g.setColor(Color.white);
		g.drawString("GAME OVER", this.frameWidth / 2 - 230, this.frameHeight / 2 + this.frameBorder - 100);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Bananas: " + this.game.bananaQuantity, this.frameWidth / 2 - 70, this.frameHeight / 2 + this.frameBorder - 20);
		g.drawString("Distância percorrida: " + this.game.distance + " m", this.frameWidth / 2 - 170, this.frameHeight / 2 + this.frameBorder + 60);
		//fazer comparacao do recorde aqui
		if (this.game.showMessageGameOver) {
			g.drawString("(Aperte ENTER para ir ao Menu Principal)", this.frameWidth / 2 - 240, this.frameHeight / 2 + this.frameBorder + 140);
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
		g.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight); //aparece um retangulo na tela (x,y,largura,altura)
		
		this.game.space.render(g);

		g.setColor(Color.gray);
		g.fillRect(0, this.frameHeight + this.frameBorder - 40, this.frameWidth, 40);

		g.setFont(new Font("arial", Font.BOLD, 15));
		g.setColor(Color.lightGray);
		//banana
		g.drawString("Bananas: " + this.game.bananaQuantity, this.frameWidth / 2 - 45, this.frameHeight + this.frameBorder - 15);
		
		//distance
		g.drawString(this.game.distance + " m", this.frameWidth / 2, this.frameBorder + 15);
		
		switch (this.game.state) {
		case 'P':
			renderPauseMenu(g);
			renderPauseMenuArrow(g);
			break;
		case 'G':
			renderGameOver(g);
			break;
		default:
			break;
		}

		bs.show();
	}

	public void run() {
		while (this.game.isRunning) {
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
		if (this.game.state == 'N' && e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.game.monkey.setIsGoingUp(true);
		}
		//voltar menu quadno perde
		if (this.game.state == 'G' && e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.game.state = 'O';
		} else if (this.game.state == 'N' && e.getKeyCode() == KeyEvent.VK_P) {
			this.game.state = 'P';
		}
		if (this.game.state == 'P') {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				game.goUp = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				game.goDown = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				game.select = true;
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