package mc322.macaconautas;

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

public class MenuInicial extends Canvas implements Runnable, KeyListener, IModo {
	private JFrame f;
	private char menuState; //N normal, L ir loja, J ir jogo, F para sair
	private boolean isRunning;
	private Thread thread;
	
	public MenuInicial() {
		this.setPreferredSize(new Dimension(AppMacaconautas.WIDTH*AppMacaconautas.SCALE, AppMacaconautas.HEIGHT*AppMacaconautas.SCALE)); //setar size do JFrame
		initFrame();
		this.addKeyListener(this);
		menuState = 'N';
		isRunning = true;
	}
	
	public char getMenuState() {
		return menuState;
	}
	
	private void initFrame() {
		f = new JFrame("MACACONAUTAS"); //titulo do jogo ou setTitle()
		f.add(this); //adicionar o que criamos para ficar visível
		f.setResizable(false); //nao pode redimensionar 
		f.pack(); //fazer o setPreferredSize funcionar de forma correta
		f.setLocationRelativeTo(null); //centro (tem que estar depois do pack)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar quando clicar no x e parar de vez
		f.setVisible(true); //deixar ele visivel
	}
	
	private void tick() {
		//Update the AppMacaconautas
		switch(menuState) {
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
		this.isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	private synchronized void stop() {
		f.dispose();
		this.isRunning = false;
	}
	
	
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.PINK);
		g.fillRect(0,0, AppMacaconautas.WIDTH*AppMacaconautas.SCALE,AppMacaconautas.HEIGHT*AppMacaconautas.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
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
		while (this.isRunning) {
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
