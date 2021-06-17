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

import mc322.macaconautas.app.Controle;

public class ControleMenu extends Canvas implements Runnable, KeyListener{
	
	private MontadorMenu menu;
	private JFrame f;
	
	public ControleMenu(JFrame f) {
		menu = new MontadorMenu();
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
			if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[0]) {
				menu.menuState = 'J';
			} else if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[1]) {
				menu.menuState = 'L';
			} else if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[2]) {
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
			Controle.setAppState('L');
			stop();
			break;

		case 'J':
			Controle.setAppState('L');
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
		f.repaint();
		menu.isRunning = false;
	}
	
	private void renderOptions(Graphics g) {
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.setColor(Color.CYAN);
		g.drawString("MACACONAUTAS", menu.WIDTH * menu.SCALE/2 - 220 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 - 90);
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("Jogar", menu.WIDTH * menu.SCALE/2 - 57 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 - 10);
		
		g.setColor(Color.WHITE);
		g.drawString("Loja", menu.WIDTH * menu.SCALE/2 - 50 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 + 70);
		
		g.setColor(Color.WHITE);
		g.drawString("Sair do Jogo", menu.WIDTH * menu.SCALE/2 - 100 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 + 150);
	}
	
	private void moveArrow(Graphics g) {
		if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[0]) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - 57 - 30 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 - 10);
		} else if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[1]) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - 50 - 30 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 + 70);
		} else if(menu.OPTIONS[menu.currentOption] == menu.OPTIONS[2]) {
			g.drawString(">", menu.WIDTH * menu.SCALE/2 - 100 - 30 , (menu.HEIGHT * menu.SCALE - menu.BORDA)/2 + 150);
		}
	}
	
	private void renderBottom(Graphics g){
		g.setFont(new Font("arial",Font.PLAIN, 15));
		g.setColor(Color.yellow);
		g.drawString("Record: " + MenuView.record,  0, menu.HEIGHT * menu.SCALE);
		g.drawString("Bananas: " + MenuView.quantidadeBananas,  0, menu.HEIGHT * menu.SCALE - 15);
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
