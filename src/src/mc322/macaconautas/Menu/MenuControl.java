package mc322.macaconautas.Menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class MenuControl extends Canvas implements Runnable, KeyListener{
	
	private MenuBuilder menu;
	private JFrame f;
	private int frameWidth;
	private int frameHeight;
	private int frameBorder;
	
	public MenuControl(JFrame f, int bananaQuantity, long record) {
		this.f = f;
		this.f.addKeyListener(this);
		this.frameWidth = this.f.getContentPane().getWidth();
		this.frameHeight = this.f.getContentPane().getHeight();
		this.frameBorder = this.f.getHeight() - this.frameHeight;
		this.menu = new MenuBuilder(bananaQuantity, record);
	}
	
	char getMenuState() {
		return this.menu.state;
	}
	
	private void executeMenu() {
		if (this.menu.goUp) {
			this.menu.goUp = false;
			this.menu.currentOption--;
			if(this.menu.currentOption < 0) {
				this.menu.currentOption = this.menu.options.length - 1;
			}
		}
		if (this.menu.goDown) {
			this.menu.goDown = false;
			this.menu.currentOption++;
			if (this.menu.currentOption >= this.menu.options.length) {
				this.menu.currentOption = 0;
			}
		}
		if (this.menu.select) {
			this.menu.select = false;
			switch (this.menu.currentOption) {
			case 0:
				this.menu.state = 'G';
				break;
			case 1:
				this.menu.state = 'S';
				break;
			case 2:
				this.menu.state = 'O';
				break;
			default:
				break;
			}
		}
	}
	
	private void tick() {
		//Update the AppMacaconautas
		switch(this.menu.state) {
		case 'N':
			executeMenu();
			break;
		case 'S':
			stop();
			break;
		case 'G':
			stop();
			break;
		case 'O':
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.menu.isRunning = true;
		this.menu.thread = new Thread(this);
		this.menu.thread.start();
	}
	
	
	private synchronized void stop() {
		this.menu.isRunning = false;
	}
	
	private void renderOptions(Graphics g) {
		// título do jogo
		g.setFont(new Font("oslo", Font.BOLD, 85));
		g.setColor(Color.BLUE);
		g.drawString("MACACONAUTAS", 0, this.frameHeight / 2 + this.frameBorder - 90);
		//opcoes
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString(this.menu.options[0], this.frameWidth / 2 - 57, this.frameHeight / 2 + this.frameBorder - 10);
		g.drawString(this.menu.options[1], this.frameWidth / 2 - 52, this.frameHeight / 2 + this.frameBorder + 70);
		g.drawString(this.menu.options[2], this.frameWidth / 2 - 120, this.frameHeight / 2 + this.frameBorder + 150); 
	}

	private void renderArrow(Graphics g) {
		switch (this.menu.currentOption) {
		case 0:
			g.drawString(">", this.frameWidth / 2 - (57 + 30), this.frameHeight / 2 + this.frameBorder - 10);
			break;
		case 1:
			g.drawString(">", this.frameWidth / 2 - (52 + 30), this.frameHeight / 2 + this.frameBorder + 70);
			break;
		case 2:
			g.drawString(">", this.frameWidth / 2 - (120 + 30), this.frameHeight / 2 + this.frameBorder + 150);
			break;
		default:
			break;
		}
	}
	
	private void renderInfo(Graphics g){
		g.setFont(new Font("arial",Font.PLAIN, 30));
		g.setColor(Color.yellow);
		g.drawString("Recorde: " + this.menu.record + " m",  0, this.frameHeight + this.frameBorder - 30);
		g.drawString("Bananas: " + this.menu.bananaQuantity,  0, this.frameHeight + this.frameBorder);
	}
	
	private void render() {
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { 
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; 
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); 
		g.setColor(Color.BLACK);
		g.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight); 
		// options
		renderOptions(g);
		renderArrow(g);
		renderInfo(g);
		bs.show(); 
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(this.menu.state == 'N') {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				this.menu.goUp = true;
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.menu.goDown = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.menu.select = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void run() {
		while (this.menu.isRunning) {
			tick();
			render();
			try {
				Thread.sleep(1000/60); //60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		} 
		
	}
}
