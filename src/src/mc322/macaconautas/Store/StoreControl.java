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
	private boolean ownedSkins[];
	private int selectedSkin;
	
	public StoreControl(JFrame f, boolean ownedSkins[], int selectedSkin, SpriteSheet spriteSheet) {
		store = new StoreBuilder(spriteSheet);
		f.addKeyListener(this);
		this.f = f;
		this.ownedSkins = ownedSkins;
		this.selectedSkin = selectedSkin;
	}
	
	char getStoreState() {
		return store.storeState;
	}

	int getSelectedSkin() {
		return this.selectedSkin;
	}
	
	boolean[] getOwnedSkins() {
		return this.ownedSkins;
	}

	private boolean buySkin(int skinIndex) {
		if (!this.ownedSkins[skinIndex] && StoreView.bananaQuantity >= store.SKIN_PRICES[skinIndex]) {
			this.ownedSkins[skinIndex] = true;
			StoreView.bananaQuantity -= store.skinPrices[skinIndex];
			return true;
		}
		return false;
	}

	private void selectSkin(int skinIndex) {
		if (this.ownedSkins[skinIndex] || buySkin(skinIndex)) {
			this.selectedSkin = skinIndex;
		}
	}
	
	private void executeStore() {
		if (store.storeLeft) {
			store.storeLeft = false;
			store.currentOption--;
			if(store.currentOption < 0) {
				store.currentOption = store.skinQuantity - 1;
			}
		}
		if (store.storeRight) {
			store.storeRight = false;
			store.currentOption++;
			if(store.currentOption >= store.skinQuantity) {
				store.currentOption = 0;
			}
		}
		if(store.enter) {
			store.enter = false;
			selectSkin(store.currentOption);
		}
	}
	
	
	private void tick() {
		//Update the AppMacaconautas
		switch(store.storeState) {
		case 'N':
			executeStore();
			//normal
			break;

		case 'M':
			//ir para o menu
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		store.isRunning = true;
		store.thread = new Thread(this);
		store.thread.start();
	}
	
	
	private synchronized void stop() {
		store.isRunning = false;
	}
	
	private void renderSkinInformation(Graphics g) {
		//preco
		g.setFont(new Font("arial", Font.PLAIN, 25));
		g.setColor(Color.white);
		g.drawString(store.skinNames[store.currentOption], ((store.WIDTH * store.SCALE) / 2) - (150 - 10), ((store.HEIGHT * store.SCALE) / 2) - (180 + 25));
		if(!ownedSkins[store.currentOption]) {
			g.drawString(store.skinPrices[store.currentOption] + " bananas", ((store.WIDTH * store.SCALE) / 2) - (150 - 10), ((store.HEIGHT * store.SCALE) / 2) + (180 + 40));
		} else {
			g.setColor(Color.green);
			g.drawString("LIBERADO :)", ((store.WIDTH * store.SCALE) / 2) - (150 - 10), ((store.HEIGHT * store.SCALE) / 2) + (180 + 40));
		}
		if(store.currentOption == this.selectedSkin) {
			g.setColor(Color.blue);
			g.drawString(">SELECIONADA<", ((store.WIDTH * store.SCALE) / 2) - (150 - 10), ((store.HEIGHT * store.SCALE) / 2) - 150);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(store.skinSprites[store.currentOption], ((store.WIDTH * store.SCALE) / 2) - 100, ((store.HEIGHT * store.SCALE) / 2) - 125, 320, 320, null);
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
		g.fillRect(0,0, store.WIDTH * store.SCALE, store.HEIGHT * store.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
		g.setColor(Color.gray);
		g.fillRect(((store.WIDTH * store.SCALE) / 2) - 150, ((store.HEIGHT * store.SCALE) / 2) - (180 + 60), 300, 60);
		g.fillRect(((store.WIDTH * store.SCALE) / 2) - 150, ((store.HEIGHT * store.SCALE) / 2) + 180, 300, 60);
		g.setColor(Color.lightGray);
		g.fillRect(((store.WIDTH * store.SCALE) / 2) - 150, ((store.HEIGHT * store.SCALE) / 2) - 180, 300, 360);
		g.setFont(new Font("arial", Font.PLAIN, 40));
		g.drawString(">", (store.WIDTH * store.SCALE) - 50, (store.HEIGHT * store.SCALE - store.BORDER) / 2);
		g.drawString("<", 20, (store.HEIGHT * store.SCALE - store.BORDER) / 2);
		g.setFont(new Font("arial",Font.PLAIN, 30));
		g.setColor(Color.yellow);
		g.drawString("Bananas: " + StoreView.bananaQuantity,  0, store.HEIGHT * store.SCALE);
		g.setFont(new Font("arial",Font.PLAIN, 20));
		g.setColor(Color.white);;
		g.drawString("Press ESC to go to the Menu", 0, store.BORDER + 16);
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
			store.storeState = 'M';
		} 
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				store.storeLeft = true;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			store.storeRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			store.enter = true;
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
