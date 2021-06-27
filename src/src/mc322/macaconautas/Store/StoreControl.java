package mc322.macaconautas.Store;

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
import mc322.macaconautas.app.Control;
import mc322.macaconautas.app.SpriteSheet;

public class StoreControl extends Canvas implements Runnable, KeyListener{

	private StoreBuilder store;
	private JFrame f;
	private int frameWidth;
	private int frameHeight;
	private int frameBorder;

	public StoreControl(JFrame f, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet, int bananaQuantity) {
		this.f = f;
		this.f.addKeyListener(this);
		this.frameWidth = this.f.getContentPane().getWidth();
		this.frameHeight = this.f.getContentPane().getHeight();
		this.frameBorder = this.f.getHeight() - this.frameHeight;
		this.store = new StoreBuilder(spriteSheet, ownedSkins, selectedSkin, bananaQuantity);
	}
	
	char getState() {
		return this.store.state;
	}

	int getSelectedSkin() {
		return this.store.selectedSkin;
	}
	
	boolean[] getOwnedSkins() {
		return this.store.ownedSkins;
	}
	
	int getBananaQuantity() {
		return this.store.bananaQuantity;
	}

	private boolean buySkin(int skinIndex) {
		if (!this.store.ownedSkins[skinIndex] && this.store.bananaQuantity >= this.store.skinPrices[skinIndex]) {
			this.store.ownedSkins[skinIndex] = true;
			this.store.bananaQuantity -= this.store.skinPrices[skinIndex];
			return true;
		}
		return false;
	}

	private void selectSkin(int skinIndex) {
		if (this.store.ownedSkins[skinIndex] || buySkin(skinIndex)) {
			this.store.selectedSkin = skinIndex;
		}
	}
	
	private void executeStore() {
		if (this.store.goLeft) {
			this.store.goLeft = false;
			this.store.currentOption--;
			if(this.store.currentOption < 0) {
				this.store.currentOption = this.store.skinOptions.length - 1;
			}
		}
		if (this.store.goRight) {
			this.store.goRight = false;
			this.store.currentOption++;
			if(this.store.currentOption >= this.store.skinOptions.length) {
				this.store.currentOption = 0;
			}
		}
		if(this.store.select) {
			this.store.select = false;
			selectSkin(this.store.currentOption);
		}
	}
	
	
	private void tick() {
		//Update the AppMacaconautas
		switch(this.store.state) {
		case 'N':
			executeStore();
			//normal
			break;
		case 'M':
			//ir para o menu
			stop();
			break;
		default:
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.store.isRunning = true;
		this.store.thread = new Thread(this);
		this.store.thread.start();
	}
	
	
	private synchronized void stop() {
		this.store.isRunning = false;
	}
	
	private void renderSkinInformation(Graphics g) {
		//preco
		g.setFont(new Font("arial", Font.PLAIN, 25));
		g.setColor(Color.white);
		g.drawString(this.store.skinOptions[this.store.currentOption], this.frameWidth / 2 - (150 - 10), this.frameHeight / 2 + this.frameBorder - (180 + 25));
		if(!this.store.ownedSkins[this.store.currentOption]) {
			g.drawString(this.store.skinPrices[this.store.currentOption] + " bananas", this.frameWidth / 2 - (150 - 10), this.frameHeight / 2 + this.frameBorder + (180 + 40));
		} else {
			g.setColor(Color.green);
			g.drawString("LIBERADA :)", this.frameWidth / 2 - (150 - 10), this.frameHeight / 2 + this.frameBorder + (180 + 40));
		}
		if(this.store.currentOption == this.store.selectedSkin) {
			g.setColor(Color.blue);
			g.drawString("SELECIONADA", this.frameWidth / 2 - (150 - 10), this.frameHeight / 2 + this.frameBorder - 150);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.store.skinSprites[this.store.currentOption], this.frameWidth / 2 - 100, this.frameHeight / 2 + this.frameBorder - 125, 320, 320, null);
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
		g.setColor(Color.black);
		g.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight); //aparece um retangulo na tela (x,y,largura,altura)
		g.setColor(Color.gray);
		g.fillRect(this.frameWidth / 2 - 150, this.frameHeight / 2 + this.frameBorder - (180 + 60), 300, 60);
		g.fillRect(this.frameWidth / 2 - 150, this.frameHeight / 2 + this.frameBorder + 180, 300, 60);
		g.setColor(Color.lightGray);
		g.fillRect(this.frameWidth / 2 - 150, this.frameHeight / 2 + this.frameBorder - 180, 300, 360);
		g.setFont(new Font("arial", Font.PLAIN, 40));
		g.drawString(">", this.frameWidth - 50, this.frameHeight / 2 + this.frameBorder);
		g.drawString("<", 20, this.frameHeight / 2 + this.frameBorder);
		g.setFont(new Font("arial",Font.PLAIN, 30));
		g.setColor(Color.yellow);
		g.drawString("Bananas: " + this.store.bananaQuantity,  0, this.frameHeight + this.frameBorder);
		g.setFont(new Font("arial",Font.PLAIN, 20));
		g.setColor(Color.white);;
		g.drawString("(Aperte ESC para ir ao Menu Principal)", 0, this.frameBorder + 16);
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
			this.store.state = 'M';
		} 
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.store.goLeft = true;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.store.goRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.store.select = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while (store.isRunning) {
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
