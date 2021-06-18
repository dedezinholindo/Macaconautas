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
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class ControleLoja extends Canvas implements Runnable, KeyListener{

	private MontadorLoja loja;
	private JFrame f;
	
	public ControleLoja(JFrame f, SpriteSheet spriteSheet) {
		loja = new MontadorLoja(spriteSheet);
		f.addKeyListener(this);
		this.f = f;
	}
	
	public char getLojaState() {
		return loja.lojaState;
	}
	
	private void executeLoja() {
		if (loja.lojaLeft) {
			loja.lojaLeft = false;
			loja.selectedSkin--;
			if(loja.selectedSkin < 0) {
				loja.selectedSkin = loja.skinQuantity - 1;
			}
		}
		if (loja.lojaRight) {
			loja.lojaRight = false;
			loja.selectedSkin++;
			if(loja.selectedSkin >= loja.skinQuantity) {
				loja.selectedSkin = 0;
			}
		}
	}
	
	
	private void tick() {
		//Update the AppMacaconautas
		switch(loja.lojaState) {
		case 'N':
			executeLoja();
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
		//f.repaint();
		loja.isRunning = false;
	}
	
	private void renderSkinInformation(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) - (180 + 60), 300, 60);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) + 180, 300, 60);
		g.setColor(Color.lightGray);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) - 180, 300, 360);

		g.setFont(new Font("arial", Font.PLAIN, 40));
		g.drawString(">", (loja.WIDTH * loja.SCALE) - 50, (loja.HEIGHT * loja.SCALE - loja.BORDA) / 2);
		g.drawString("<", 20, (loja.HEIGHT * loja.SCALE - loja.BORDA) / 2);
		//preco
		//botar um if ja comprou, entao nao mostra o preço
		//botar um certinho se ja foi comprada
		g.setFont(new Font("arial", Font.PLAIN, 25));
		g.setColor(Color.white);
		g.drawString(loja.skinNames[loja.selectedSkin], ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) - (180 + 25));
		g.drawString(loja.skinPrices[loja.selectedSkin] + " bananas", ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) + (180 + 40));
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(loja.skinSprites[loja.selectedSkin], ((loja.WIDTH * loja.SCALE) / 2) - 100, ((loja.HEIGHT * loja.SCALE) / 2) - 125, 320, 320, null);
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
		g.fillRect(0,0, loja.WIDTH * loja.SCALE, loja.HEIGHT * loja.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
		renderSkinInformation(g);
		bs.show(); //mostra o grafico
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			loja.lojaState = 'M';
		} 
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				loja.lojaLeft = true;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			loja.lojaRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			loja.enter = true;
		}
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
