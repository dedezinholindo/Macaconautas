package mc322.macaconautas.Store;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class StoreControl extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = -7035779102428278060L;

	private StoreBuilder store;
	private JFrame f;
	private int frameWidth;
	private int frameHeight;
	private int frameBorder;

	/**
	 * Inicializa um StoreControl.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param ownedSkins indica skins possuídas.
	 * @param selectedSkin skin selecionada.
	 * @param f JFrame utilizado.
	 * @param spriteSheet sprite sheet do jogo.
	 */
	public StoreControl(int bananaQuantity, boolean ownedSkins[], int selectedSkin, @SuppressWarnings("exports") JFrame f, @SuppressWarnings("exports") SpriteSheet spriteSheet) {
		this.f = f;
		this.f.addKeyListener(this);
		this.frameWidth = this.f.getContentPane().getWidth();
		this.frameHeight = this.f.getContentPane().getHeight();
		this.frameBorder = this.f.getHeight() - this.frameHeight;
		this.store = new StoreBuilder(bananaQuantity, ownedSkins, selectedSkin, spriteSheet);
	}

	/**
	 * Retorna o estado da store.
	 * @return estado da store.
	 */
	char getState() {
		return this.store.state;
	}

	/**
	 * Retorna a quantidade de bananas possuídas.
	 * @return quantidade de bananas possuídas.
	 */
	int getBananaQuantity() {
		return this.store.bananaQuantity;
	}

	/**
	 * Retorna as skins possuídas.
	 * @return skins possuídas.
	 */
	boolean[] getOwnedSkins() {
		return this.store.ownedSkins;
	}

	/**
	 * Retorna a skin selecionada.
	 * @return skin selecionada.
	 */
	int getSelectedSkin() {
		return this.store.selectedSkin;
	}

	/**
	 * Compra uma skin, se possível.
	 * @param skinIndex skin desejada.
	 * @return true, caso a compra tenha sucesso.
	 */
	private boolean buySkin(int skinIndex) {
		if (!this.store.ownedSkins[skinIndex] && this.store.bananaQuantity >= this.store.skinPrices[skinIndex]) {
			this.store.ownedSkins[skinIndex] = true;
			this.store.bananaQuantity -= this.store.skinPrices[skinIndex];
			return true;
		}
		return false;
	}

	/**
	 * Seleciona uma skin.
	 * @param skinIndex skin desejada.
	 */
	private void selectSkin(int skinIndex) {
		if (this.store.ownedSkins[skinIndex] || buySkin(skinIndex)) {
			this.store.selectedSkin = skinIndex;
		}
	}

	/**
	 * Atualiza a store em um frame.
	 */
	private void tick() {
		//Update the AppMacaconautas
		switch(this.store.state) {
		case 'N':
			tickStore();
			break;
		case 'M':
			//ir para o menu
			stop();
			break;
		default:
			break;
		}
	}

	/**
	 * Atualiza a store normal em um frame.
	 */
	private void tickStore() {
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

	/**
	 * Renderiza a loja.
	 */
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { 
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao 	
			return; 
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); 
		g.setColor(Color.black);
		g.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight); 
		//render skins
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

	/**
	 * Renderiza as informações da skin.
	 * @param g gráficos utilizados.
	 */
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

	public void keyTyped(@SuppressWarnings("exports") KeyEvent e) {}

	/**
	 * Atualiza o menu de acordo com as teclas de interesse pressionadas.
	 */
	public void keyPressed(@SuppressWarnings("exports") KeyEvent e) {
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

	public void keyReleased(@SuppressWarnings("exports") KeyEvent e) {}

	/**
	 * Inicia a execução da store.
	 * @throws InterruptedException
	 */
	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.store.isRunning = true;
		this.store.thread = new Thread(this);
		this.store.thread.start();
	}

	/**
	 * Interrompe a execução do menu.
	 */
	private synchronized void stop() {
		this.store.isRunning = false;
	}

	/**
	 * Executa a store.
	 */
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