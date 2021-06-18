package mc322.macaconautas.Jogo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import mc322.macaconautas.Componentes.Alien;
import mc322.macaconautas.Componentes.Banana;
import mc322.macaconautas.Componentes.Laser;
import mc322.macaconautas.Componentes.Macaco;
import mc322.macaconautas.Componentes.Obstaculo;
import mc322.macaconautas.Componentes.WheyProtein;
import mc322.macaconautas.app.Controle;
import mc322.macaconautas.app.SpriteSheet;

public class ControleJogo extends Canvas implements Runnable, KeyListener {
	
	private MontadorJogo jogo;
	private JFrame f;

	public ControleJogo(SpriteSheet spriteSheet, JFrame f) throws InterruptedException {
		jogo = new MontadorJogo(spriteSheet);
		f.addKeyListener(this);
		this.f = f;
	}	
	
	int getBananasColetadas() {
		return jogo.bananasColetadas;
	}
	
	char getJogoState() {
		return jogo.jogoState;
	}
	
	long getDistancia() {
		if(jogo.distancia < JogoView.record) {
			return JogoView.record;
		}
		//renderizar new record
		return jogo.distancia;
	}
	
	private void lentidaoJogo(int lentidao) {
		if(lentidao == jogo.contador) {
			jogo.distancia += 1;
			jogo.contador = 0;
			return;
		}
		jogo.contador += 1;
	}
	
	private void tempoDeGorila() {
		jogo.macaco.setContadorGorila(jogo.macaco.getContadorGorila() + 1);
	}
	
	private void framesGameOver() {
		jogo.framesMessageGameOver++;
		if(jogo.framesMessageGameOver == 25) {
			jogo.framesMessageGameOver = 0;
			if(jogo.showMessageGameOver) {
				jogo.showMessageGameOver = false;
			} else {
				jogo.showMessageGameOver = true;
			}
		}
	}

	private void tick() {
		//Update the AppMacaconautas
		switch(jogo.jogoState) {
		case 'N':
			jogo.macaco.tick();
			jogo.espaco.tick();
			checarColisoes();
			for (int i = 0; i < Alien.getLasers().size(); i++) {
				Alien.getLasers().get(i).tick();
			}
			lentidaoJogo(jogo.lentidao); 
			transformaGorila();
			break;

		case 'P':
			pause(); 
			break;

			
		case 'G':
			framesGameOver();
			break;
			
		case 'O':
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		jogo.isRunning = true;
		jogo.thread = new Thread(this);
		jogo.thread.start();
	}

	private synchronized void pause() {
		if(jogo.gameUp) {
			jogo.gameUp = false;
			jogo.currentOption--;
			if(jogo.currentOption < 0) {
				jogo.currentOption = jogo.MAX_OPTIONS;
			}
		}
		if(jogo.gameDown) {
			jogo.gameDown = false;
			jogo.currentOption++;
			if(jogo.currentOption > jogo.MAX_OPTIONS) {
				jogo.currentOption = 0;
			}
		}
		if(jogo.enter) {
			jogo.enter = false;
			if(jogo.OPTIONS[jogo.currentOption] == jogo.OPTIONS[0]) {
				jogo.jogoState = 'N';
			} else if(jogo.OPTIONS[jogo.currentOption] == jogo.OPTIONS[1]) {
				jogo.jogoState = 'O';
			}
		}
	}

	public synchronized void stop() {
		//f.repaint();
		jogo.isRunning = false;
	}
	
	private void renderStringsEspaco(Graphics g, String s, int p_x, int p_y, Color c) {
		g.setFont(new Font("arial", Font.BOLD, jogo.TAMANHO_STRING_JOGO));
		g.setColor(c);
		g.drawString(s, p_x, p_y);	
	}
	
	private void renderPause(Graphics g) {
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", jogo.WIDTH * jogo.SCALE/2 - 100 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 - 90);
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("Resume", jogo.WIDTH * jogo.SCALE/2 - 57 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 - 10);
		
		g.setColor(Color.WHITE);
		g.drawString("Menu", jogo.WIDTH * jogo.SCALE/2 - 50 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 + 70);
	}
	
	private void moveArrow(Graphics g) {
		if(jogo.OPTIONS[jogo.currentOption] == jogo.OPTIONS[0]) {
			g.drawString(">", jogo.WIDTH * jogo.SCALE/2 - 57 - 30 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 - 10);
		} else if(jogo.OPTIONS[jogo.currentOption] == jogo.OPTIONS[1]) {
			g.drawString(">", jogo.WIDTH * jogo.SCALE/2 - 50 - 30 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 + 70);
		}
	}
	
	private void renderGameOver(Graphics g) {
		//TELA GAME OVER
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,180)); //transparencia
		g2.fillRect(0, jogo.BORDA, jogo.WIDTH * jogo.SCALE, jogo.HEIGHT * jogo.SCALE - jogo.BORDA);

		//FRASE GAME OVER
		g.setFont(new Font("arial", Font.BOLD, 70));
		g.setColor(Color.WHITE);
		g.drawString("GAME OVER", jogo.WIDTH * jogo.SCALE/2 - 220 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 - 100);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Colected bananas: " + jogo.bananasColetadas, jogo.WIDTH * jogo.SCALE/2 - 120 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 - 20);
		g.drawString("Distance: " + jogo.distancia + " m", jogo.WIDTH * jogo.SCALE/2 - 120 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 + 60);
		//fazer comparacao do recorde aqui
		if(jogo.showMessageGameOver) {
			g.drawString(">> Press enter to Menu <<", jogo.WIDTH * jogo.SCALE/2 - 200 , (jogo.HEIGHT * jogo.SCALE - jogo.BORDA)/2 + 140);
		}
	}

	//TIRAR DAQUI??
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = f.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3);//sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.black);
		g.fillRect(0, jogo.BORDA, jogo.WIDTH * jogo.SCALE, jogo.HEIGHT * jogo.SCALE - jogo.BORDA); //aparece um retangulo na tela (x,y,largura,altura)
		jogo.macaco.render(g);
		jogo.espaco.render(g);
		for (int i = 0; i < Alien.getLasers().size(); i++) {
			Alien.getLasers().get(i).render(g);
		}
		//banana
		String stringBanana = "Bananas: " + (jogo.bananasColetadas + JogoView.quantidadeBananas);
		renderStringsEspaco(g, stringBanana, jogo.WIDTH * jogo.SCALE - (stringBanana.length())*jogo.TAMANHO_STRING_JOGO, jogo.HEIGHT * jogo.SCALE, Color.LIGHT_GRAY);
		
		//distancia
		String stringDistancia = jogo.distancia + " m";
		renderStringsEspaco(g, stringDistancia, (jogo.WIDTH * jogo.SCALE - (stringDistancia.length())*jogo.TAMANHO_STRING_JOGO)/2, jogo.TAMANHO_STRING_JOGO + jogo.BORDA, Color.LIGHT_GRAY);
		
		if(jogo.jogoState == 'P') {
			renderPause(g);
			moveArrow(g);
		}
		
		if (jogo.jogoState == 'G') {
			renderGameOver(g);
		} 
		bs.show();
	}
	
	private void transformaGorila() {
		tempoDeGorila();
		returnToMonkey();
	}
	
	private void virarGorila() {
		jogo.macaco.setContadorGorila(0);
		jogo.macaco.setIsGorila(true);
		jogo.macaco.setSize(jogo.macaco.getGorilaWidth(), jogo.macaco.getGorilaHeight());
	}
	
	private void returnToMonkey() {
		if (jogo.macaco.IsGorila() && jogo.macaco.getContadorGorila() == jogo.macaco.getTempoDeGorila()) {
			jogo.macaco.setContadorGorila(0);
			jogo.macaco.setIsGorila(false);
			jogo.macaco.setSize(jogo.macaco.getMacacoWidth(), jogo.macaco.getMacacoHeight());
		}
	}

	private boolean checarColisaoObstaculo() {
		Rectangle formaMacaco = jogo.macaco.getBounds();
		Rectangle formaObstaculo = null;
		for (int i = 0; i < jogo.espaco.getObstaculosNaSessao(); i++) {
			formaObstaculo = jogo.espaco.getObstaculos().get(i).getBounds();
			if (formaMacaco.intersects(formaObstaculo)) {
				ArrayList<Obstaculo> obstaculos = jogo.espaco.getObstaculos();
				obstaculos.remove(i);
				jogo.espaco.setObstaculos(obstaculos); //remocao do whey
				jogo.espaco.setObstaculosNaSessao(jogo.espaco.getObstaculosNaSessao() - 1);
				return true;
			}
		}
		return false;
	}

	private boolean checarColisaoAlien() {
		Rectangle formaMacaco = jogo.macaco.getBounds();
		Rectangle formaAlien = null;
		for (int i = 0; i < jogo.espaco.getAliensNaSessao(); i++) {
			formaAlien = jogo.espaco.getAliens().get(i).getBounds();
			if (formaMacaco.intersects(formaAlien)) {
				ArrayList<Alien> aliens = jogo.espaco.getAliens();
				aliens.remove(i);
				jogo.espaco.setAliens(aliens); //remocao do whey
				jogo.espaco.setAliensNaSessao(jogo.espaco.getAliensNaSessao() - 1);
				return true;
			}
		}
		return false;
	}

	private boolean checarColisaoLaser() {
		Rectangle formaMacaco = jogo.macaco.getBounds();
		Rectangle formaLaser = null;
		for (int i = 0; i < Alien.getLasers().size(); i++) {
			formaLaser = Alien.getLasers().get(i).getBounds();
			if (formaMacaco.intersects(formaLaser)) {
				ArrayList<Laser> lasers = Alien.getLasers();
				lasers.remove(i);
				Alien.setLasers(lasers); //remocao da banana
				return true;
			}
		}
		return false;
	}

	private void checarColisaoBanana() {
		Rectangle formaMacaco = jogo.macaco.getBounds();
		Rectangle formaBanana = null;
		for (int i = 0; i < jogo.espaco.getBananasNaSessao(); i++) {
			formaBanana = jogo.espaco.getBananas().get(i).getBounds();
			if (formaMacaco.intersects(formaBanana)) {
				ArrayList<Banana> bananas = jogo.espaco.getBananas();
				bananas.remove(i);
				jogo.espaco.setBananas(bananas); //remocao da banana
				jogo.espaco.setBananasNaSessao(jogo.espaco.getBananasNaSessao() - 1); //sem isso o jogo pifa
				jogo.bananasColetadas += 1;
			}
		}
	}
	
	private boolean checarColisaoWhey() {
		Rectangle formaMacaco = jogo.macaco.getBounds();
		Rectangle formaWhey = null;
		for (int i = 0; i < jogo.espaco.getWheyNaSessao(); i++) {
			formaWhey = jogo.espaco.getWhey().get(i).getBounds();
			if (formaMacaco.intersects(formaWhey)) {
				ArrayList<WheyProtein> whey = jogo.espaco.getWhey();
				whey.remove(i);
				jogo.espaco.setWhey(whey); //remocao do whey
				jogo.espaco.setWheyNaSessao(jogo.espaco.getWheyNaSessao() - 1);
				return true;
			}
		}
		return false;
	}

	private void checarColisoes() { 
		if((checarColisaoObstaculo() || checarColisaoAlien() || checarColisaoLaser()) && !jogo.macaco.IsGorila()){
			jogo.jogoState = 'G';
		}
		if (checarColisaoWhey() && !jogo.macaco.IsGorila()) {
			virarGorila();
		}
		checarColisaoBanana();
	}

	public void run() {

		while (jogo.isRunning) {
			tick();
			render();
			try {
				Thread.sleep(1000/60); //60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		} 

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jogo.macaco.setIsGoingUp(true);
		}
		//voltar menu quadno perde
		if(jogo.jogoState == 'G' && e.getKeyCode() == KeyEvent.VK_ENTER) {
			jogo.jogoState = 'O';
		}
		if(jogo.jogoState == 'N' && e.getKeyCode() == KeyEvent.VK_P) {
			jogo.jogoState = 'P';
		}
		if(jogo.jogoState == 'P') {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				jogo.gameUp = true;
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				jogo.gameDown = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				jogo.enter = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jogo.macaco.setIsGoingUp(false);
		}
	}

}              