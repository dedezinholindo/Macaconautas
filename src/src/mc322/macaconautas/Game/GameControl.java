package mc322.macaconautas.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import mc322.macaconautas.app.SpriteSheet;

public class GameControl extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1182488851602350942L;

	private GameBuilder game;
	private JFrame f;
	private int frameWidth;
	private int frameHeight;
	private int frameBorder;

	/**
	 * Inicializa um GameControl.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param record recorde de distância percorrida.
	 * @param selectedSkin skin selecionada.
	 * @param f JFrame utilizado.
	 * @param spriteSheet sprite sheet do jogo.
	 * @throws InterruptedException
	 */
	public GameControl(int bananaQuantity, long record, int selectedSkin, @SuppressWarnings("exports") JFrame f, SpriteSheet spriteSheet) throws InterruptedException {
		this.f = f;
		this.f.addKeyListener(this);
		this.frameWidth = this.f.getContentPane().getWidth();
		this.frameHeight = this.f.getContentPane().getHeight();
		this.frameBorder = this.f.getHeight() - this.frameHeight;
		this.game = new GameBuilder(bananaQuantity, record, selectedSkin, this.frameWidth, this.frameHeight, this.frameBorder, spriteSheet);
	}

	/**
	 * Retorna o estado do game.
	 * @return estado do game.
	 */
	char getState() {
		return this.game.state;
	}

	/**
	 * Retorna a quantidade de bananas possuídas.
	 * @return quantidade de bananas possuídas.
	 */
	int getBananaQuantity() {
		return this.game.bananaQuantity;
	}

	/**
	 * Retorna o recorde de distância percorrida.
	 * @return recorde de distância percorrida.
	 */
	long getRecord() {
		if (this.game.distance > this.game.record) {
			this.game.record = this.game.distance;
		}
		return this.game.record;
	}

	/**
	 * Atualiza o game em um frame.
	 */
	private void tick() {
		switch (this.game.state) {
		case 'N':
			this.game.bananaQuantity += this.game.space.tick();
			if (this.game.monkey.isDefeated()) {
				this.game.state = 'G';
			}
			tickDistance(this.game.slowness); 
			break;
		case 'P':
			tickPause(); 
			break;
		case 'G':
			tickGameOver();
			break;
		case 'O':
			stop();
			break;
		default:
			break;
		}
	}

	/**
	 * Atualiza a distância percorrida em um frame.
	 * @param slowness lentidão do game.
	 */
	private void tickDistance(int slowness) {
		if (slowness == this.game.counter) {
			this.game.distance += 1;
			this.game.counter = 0;
			return;
		}
		this.game.counter += 1;
	}

	/**
	 * Atualiza o jogo pausado em um frame.
	 */
	private synchronized void tickPause() {
		if (this.game.goUp) {
			this.game.goUp = false;
			this.game.currentPauseMenuOption--;
			if (this.game.currentPauseMenuOption < 0) {
				this.game.currentPauseMenuOption = this.game.pauseMenuOptions.length - 1;
			}
		}
		if (this.game.goDown) {
			this.game.goDown = false;
			this.game.currentPauseMenuOption++;
			if (this.game.currentPauseMenuOption >= this.game.pauseMenuOptions.length) {
				this.game.currentPauseMenuOption = 0;
			}
		}
		if (this.game.select) {
			this.game.select = false;
			switch (this.game.currentPauseMenuOption) {
			case 0:
				this.game.state = 'N';
				break;
			case 1:
				this.game.state = 'O';
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Atualiza o game over em um frame.
	 */
	private void tickGameOver() {
		this.game.framesMessageGameOver++;
		if (this.game.framesMessageGameOver >= 25) {
			this.game.framesMessageGameOver = 0;
			this.game.showMessageGameOver = !this.game.showMessageGameOver;
		}
	}

	/**
	 * Renderiza o game.
	 */
	private void render() {
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { 
			this.createBufferStrategy(3);//sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; 
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); 
		g.setColor(Color.black);
		g.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight); 
		//renderespaco
		this.game.space.render(g);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.setColor(Color.lightGray);
		g.drawString("Bananas: " + this.game.bananaQuantity, this.frameWidth / 2 - 45, this.frameHeight + this.frameBorder - 15);
		//render distance
		g.drawString(this.game.distance + " m", this.frameWidth / 2, this.frameBorder + 15);
		
		switch (this.game.state) {
		case 'P':
			renderPauseMenu(g);
			renderPauseMenuArrow(g);
			break;
		case 'G':
			renderGameOver(g);
			break;
		default:
			break;
		}

		bs.show();
	}

	/**
	 * Renderiza o menu de pause.
	 * @param g gráficos utilizados.
	 */
	private void renderPauseMenu(Graphics g) {
		//render fundo
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(180, 180, 180, 50)); //transparencia
		g2.fillRect(this.frameWidth / 2 - 150, this.frameHeight / 2 + this.frameBorder - 115, 300, 230);
		//render pause
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", this.frameWidth / 2 - (150 - 5), this.frameHeight / 2 + this.frameBorder - 115 + 45);
		//render options
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString(this.game.pauseMenuOptions[0], this.frameWidth / 2 - (150 - 30 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 110);
		g.drawString(this.game.pauseMenuOptions[1], this.frameWidth / 2 - (150 - 30 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 175);
	}

	/**
	 * Renderiza a seta de seleção do menu de pause.
	 * @param g gráficos utilizados.
	 */
	private void renderPauseMenuArrow(Graphics g) {
		switch (this.game.currentPauseMenuOption) {
		case 0:
			g.drawString(">", this.frameWidth / 2 - (150 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 110);
			break;
		case 1:
			g.drawString(">", this.frameWidth / 2 - (150 - 5),  this.frameHeight / 2 + this.frameBorder - 115 + 175);
			break;
		default:
			break;
		}
	}

	/**
	 * Renderiza o menu de game over.
	 * @param g gráficos utilizados.
	 */
	private void renderGameOver(Graphics g) {
		//TELA GAME OVER
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 180)); //transparencia
		g2.fillRect(0, this.frameBorder, this.frameWidth, this.frameHeight);
		//FRASE GAME OVER
		g.setFont(new Font("arial", Font.BOLD, 70));
		g.setColor(Color.white);
		g.drawString("GAME OVER", this.frameWidth / 2 - 230, this.frameHeight / 2 + this.frameBorder - 100);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Bananas: " + this.game.bananaQuantity, this.frameWidth / 2 - 70, this.frameHeight / 2 + this.frameBorder - 20);
		g.drawString("Distância percorrida: " + this.game.distance + " m", this.frameWidth / 2 - 170, this.frameHeight / 2 + this.frameBorder + 60);
		if (this.game.showMessageGameOver) {
			g.drawString("(Aperte ENTER para ir ao Menu Principal)", this.frameWidth / 2 - 240, this.frameHeight / 2 + this.frameBorder + 140);
		}
	}

	public void keyTyped(@SuppressWarnings("exports") KeyEvent e) {}

	/**
	 * Atualiza o game de acordo com as teclas de interesse pressionadas.
	 */
	public void keyPressed(@SuppressWarnings("exports") KeyEvent e) {
		if (this.game.state == 'N' && e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.game.monkey.setIsGoingUp(true);
		}
		//voltar menu quando perde
		if (this.game.state == 'G' && e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.game.state = 'O';
		} else if (this.game.state == 'N' && e.getKeyCode() == KeyEvent.VK_P) {
			this.game.state = 'P';
		}
		if (this.game.state == 'P') {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				game.goUp = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				game.goDown = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				game.select = true;
			}
		}
	}

	/**
	 * Atualiza o game de acordo com as teclas de interesse não clicadas.
	 */
	public void keyReleased(@SuppressWarnings("exports") KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.monkey.setIsGoingUp(false);
		}
	}

	/**
	 * Inicia a execução do game.
	 * @throws InterruptedException
	 */
	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.game.isRunning = true;
		this.game.thread = new Thread(this);
		this.game.thread.start();
	}

	/**
	 * Interrompe a execução do game.
	 */
	public synchronized void stop() {
		this.game.isRunning = false;
	}

	/**
	 * Executa o game.
	 */
	public void run() {
		while (this.game.isRunning) {
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