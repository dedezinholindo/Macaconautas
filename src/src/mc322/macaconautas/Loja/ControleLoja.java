package mc322.macaconautas.Loja;

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

public class ControleLoja extends Canvas implements Runnable, KeyListener{
	
	private MontadorLoja loja;
	private JFrame f;
	
	public ControleLoja(JFrame f) {
		loja = new MontadorLoja();
		f.addKeyListener(this);
		this.f = f;
	}
	
	public char getLojaState() {
		return loja.lojaState;
	}
	
	private void tick() {
		//Update the AppMacaconautas
		switch(loja.lojaState) {
		case 'N':
			//normal
			break;

		case 'M':
			//ir para o menu
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		loja.isRunning = true;
		loja.thread = new Thread(this);
		loja.thread.start();
	}
	
	
	private synchronized void stop() {
		f.repaint();
		loja.isRunning = false;
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
		g.setColor(Color.GREEN);
		g.fillRect(0,0, loja.WIDTH * loja.SCALE, loja.HEIGHT * loja.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
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
		while (loja.isRunning) {
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
