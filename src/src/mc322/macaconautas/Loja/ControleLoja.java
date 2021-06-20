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
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Menu.MenuView;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class ControleLoja extends Canvas implements Runnable, KeyListener{

	private MontadorLoja loja;
	private JFrame f;
	private int bananaQuantity;
	private boolean ownedSkins[];
	private int selectedSkin;
	
	public ControleLoja(JFrame f, int bananaQuantity, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		loja = new MontadorLoja(spriteSheet);
		f.addKeyListener(this);
		this.f = f;
		this.bananaQuantity = bananaQuantity;
		this.ownedSkins = ownedSkins;
		this.selectedSkin = selectedSkin;
	}
	
	char getLojaState() {
		return loja.lojaState;
	}

	int getBananaQuantity() {
		return this.bananaQuantity;
	}

	int getSelectedSkin() {
		return this.selectedSkin;
	}
	
	boolean[] getOwnedSkins() {
		return this.ownedSkins;
	}
	
	void setBananaQuantity(int bananaQuantity) {
		this.bananaQuantity = bananaQuantity;
	}

	private boolean buySkin(int skinIndex) {
		if (!this.ownedSkins[skinIndex] && this.bananaQuantity >= loja.SKIN_PRICES[skinIndex]) {
			this.ownedSkins[skinIndex] = true;
			this.bananaQuantity -= loja.skinPrices[skinIndex];
			return true;
		}
		return false;
	}

	private void selectSkin(int skinIndex) {
		if (this.ownedSkins[skinIndex] || buySkin(skinIndex)) {
			this.selectedSkin = skinIndex;
		}
	}
	
	private void executeLoja() {
		if (loja.lojaLeft) {
			loja.lojaLeft = false;
			loja.currentOption--;
			if(loja.currentOption < 0) {
				loja.currentOption = loja.skinQuantity - 1;
			}
		}
		if (loja.lojaRight) {
			loja.lojaRight = false;
			loja.currentOption++;
			if(loja.currentOption >= loja.skinQuantity) {
				loja.currentOption = 0;
			}
		}
		if(loja.enter) {
			loja.enter = false;
			selectSkin(loja.currentOption);
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
		//preco
		//botar um if ja comprou, entao nao mostra o preço
		//botar um certinho se ja foi comprada
		g.setFont(new Font("arial", Font.PLAIN, 25));
		g.setColor(Color.white);
		g.drawString(loja.skinNames[loja.currentOption], ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) - (180 + 25));
		if(!ownedSkins[loja.currentOption]) {
			g.drawString(loja.skinPrices[loja.currentOption] + " bananas", ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) + (180 + 40));
		} else {
			g.setColor(Color.green);
			g.drawString("LIBERADO :)", ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) + (180 + 40));
		}
		if(loja.currentOption == this.selectedSkin) {
			g.setColor(Color.blue);
			g.drawString(">>>SELECTED<<<", ((loja.WIDTH * loja.SCALE) / 2) - (150 - 10), ((loja.HEIGHT * loja.SCALE) / 2) - 150);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(loja.skinSprites[loja.currentOption], ((loja.WIDTH * loja.SCALE) / 2) - 100, ((loja.HEIGHT * loja.SCALE) / 2) - 125, 320, 320, null);
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
		g.setColor(Color.gray);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) - (180 + 60), 300, 60);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) + 180, 300, 60);
		g.setColor(Color.lightGray);
		g.fillRect(((loja.WIDTH * loja.SCALE) / 2) - 150, ((loja.HEIGHT * loja.SCALE) / 2) - 180, 300, 360);
		g.setFont(new Font("arial", Font.PLAIN, 40));
		g.drawString(">", (loja.WIDTH * loja.SCALE) - 50, (loja.HEIGHT * loja.SCALE - loja.BORDA) / 2);
		g.drawString("<", 20, (loja.HEIGHT * loja.SCALE - loja.BORDA) / 2);
		g.setFont(new Font("herhehdfhdf",Font.PLAIN, 30));
		g.setColor(Color.yellow);
		g.drawString("Bananas: " + this.bananaQuantity,  0, loja.HEIGHT * loja.SCALE);
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
