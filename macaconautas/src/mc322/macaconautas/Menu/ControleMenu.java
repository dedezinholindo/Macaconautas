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
	
	private void tick() {
		//Update the AppMacaconautas
		switch(menu.menuState) {
		case 'N':
			//normal
			break;

		case 'L':
			//ir loja
			//CRIAR NO EVENTO "SE ELE CLICAR NA LOJA, O LOJASTATE VIRA L"
			stop();
			break;

		case 'J':
			//ir jogo
			//CRIAR NO EVENTO "SE ELE CLICAR NA LOJA, O LOJASTATE VIRA J"
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
	
	
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.PINK);
		g.fillRect(0, 0, menu.WIDTH * menu.SCALE, menu.HEIGHT * menu.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
		bs.show(); //mostra o grafico
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
