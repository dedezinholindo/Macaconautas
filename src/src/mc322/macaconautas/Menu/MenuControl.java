package mc322.macaconautas.Menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import mc322.macaconautas.app.Control;

public class MenuControl extends Canvas implements Runnable, KeyListener{
	
	private MenuBuilder menu;
	private JFrame f;
	
	public MenuControl(JFrame f) {
		menu = new MenuBuilder();
		f.addKeyListener(this);
		this.f = f;
	}
	
	char getMenuState() {
		return menu.menuState;
	}
	
	private void executeMenu() {
		if(menu.menuUp) {
			menu.menuUp = false;
			menu.currentOption--;
			if(menu.currentOption < 0) {
				menu.currentOption = menu.MAX_OPTIONS;
			}
		}
		if(menu.menuDown) {
			menu.menuDown = false;
			menu.currentOption++;
			if(menu.currentOption > menu.MAX_OPTIONS) {
				menu.currentOption = 0;
			}
		}
		if(menu.enter) {
			menu.enter = false;
			if(menu.currentOption == 0) {
				menu.menuState = 'J';
			} else if(menu.currentOption == 1) {
				menu.menuState = 'L';
			} else if(menu.currentOption == 2) {
				menu.menuState = 'F';
			}
		}
	}
	
	private void tick() {
		//Update the AppMacaconautas
		switch(menu.menuState) {
		case 'N':
			executeMenu();
			//normal
			break;

		case 'L':
			Control.setAppState('L');
			stop();
			break;

		case 'J':
			Control.setAppState('L');
			stop();
			break;
			
		case 'F':
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		menu.isRunning = true;
		menu.thread = new Thread(this);
		menu.thread.start();
	}
	
	
	private synchronized void stop() {
		menu.isRunning = false;
	}
	
	private void renderOptions(Graphics g) {
		g.setFont(new Font("oslo", Font.BOLD, 85));
		g.setColor(Color.BLUE);
		g.drawString("MACACONAUTAS", 0, (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 - 90);
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString(menu.OPTIONS[0], menu.WIDTH * menu.SCALE/2 - 57, (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 - 10);
		g.drawString(menu.OPTIONS[1], menu.WIDTH * menu.SCALE/2 - 67, (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 + 70);
		g.drawString(menu.OPTIONS[2], menu.WIDTH * menu.SCALE/2 - 130, (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 + 150);
	}
	
	private void moveArrow(Graphics g) {
		if(menu.currentOption == 0) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - (57 + 30), (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 - 10);
		} else if(menu.currentOption == 1) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - (67 + 30), (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 + 70);
		} else if(menu.currentOption == 2) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - (130 + 30), (menu.HEIGHT * menu.SCALE - menu.BORDER)/2 + 150);
		}
	}
	
	private void renderBottom(Graphics g){
		g.setFont(new Font("arial",Font.PLAIN, 30));
		g.setColor(Color.yellow);
		g.drawString("Record: " + MenuView.record + " m",  0, menu.HEIGHT * menu.SCALE - 30);
		g.drawString("Bananas: " + MenuView.bananaQuantity,  0, menu.HEIGHT * menu.SCALE);
	}
	
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, menu.WIDTH * menu.SCALE, menu.HEIGHT * menu.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
		
		//options
		renderOptions(g);
		moveArrow(g);
		renderBottom(g);
		bs.show(); //mostra o grafico
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(menu.menuState == 'N') {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.menuUp = true;
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.menuDown = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while (menu.isRunning) {
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
