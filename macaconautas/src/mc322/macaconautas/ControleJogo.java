package mc322.macaconautas;

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

public class ControleJogo extends Canvas implements Runnable, KeyListener, IModo {

	private final static int TAMANHO_STRING_JOGO = 17;
	private int lentidao;
	private char jogoState; //N para normal, P para pausado (uso do pause) e O para Game Over
	private boolean isRunning;
	//private boolean estaSuspensa;
	private Thread thread;
	private JFrame f;
	private Macaco macaco;
	private Espaco espaco;
	private static ArrayList <Laser> lasers; 
	private int bananasColetadas;
	private long distancia;
	private int contador;

	public ControleJogo() throws InterruptedException {
		this.setPreferredSize(new Dimension(AppMacaconautas.WIDTH * AppMacaconautas.SCALE, AppMacaconautas.HEIGHT * AppMacaconautas.SCALE)); //setar size do JFrame
		initFrame();
		this.addKeyListener(this);
		macaco = new Macaco(15, 0);
		espaco = new Espaco();
		lasers = new ArrayList<Laser>();
		jogoState = 'N';
		isRunning = true;
		//estaSuspensa = false;
		bananasColetadas = 0;
		lentidao = 50;
		distancia = 0;
		contador = 0;
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

	public int getBananasColetadas() {
		return bananasColetadas;
	}
	
	public char getJogoState() {
		return jogoState;
	}

	public static ArrayList<Laser> getLasers() {
		return lasers;
	}

	public static void setLasers(ArrayList<Laser> lasers) {
		ControleJogo.lasers = lasers;
	}
	
	private void lentidaoJogo(int lentidao) {
		if(lentidao == contador) {
			distancia += 1;
			contador = 0;
			return;
		}
		contador += 1;
	}

	private void tick() {
		//Update the AppMacaconautas
		switch(jogoState) {
		case 'N':
			macaco.tick();
			espaco.tick();
			checarColisoes();
			for (int i = 0; i < lasers.size(); i++) {
				lasers.get(i).tick();
			}
			lentidaoJogo(lentidao); 
			break;

		case 'P':
			//pause(); //tecla do teclado
			break;

		case 'O':
			// Mostrar resultados por alguns segundos e voltar pro menu
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stop();
			break;
		}
	}

	public synchronized void start() throws InterruptedException { //synchronized para evitar que a thread use/mude o mesmo recurso ao mesmo tempo
		this.isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	/*private synchronized void pause() {
		estaSuspensa = true;
	}*/

	public synchronized void stop() {
		f.dispose();
		this.isRunning = false;
	}
	
	private void renderStringsEspaco(Graphics g, String s, int p_x, int p_y, Color c) {
		g.setFont(new Font("arial", Font.BOLD, TAMANHO_STRING_JOGO));
		g.setColor(c);
		g.drawString(s, p_x, p_y);	
	}
	
	private void renderGameOver(Graphics g) {
		//TELA GAME OVER
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100)); //transparencia
		g2.fillRect(0, 0, AppMacaconautas.WIDTH * AppMacaconautas.SCALE, AppMacaconautas.HEIGHT * AppMacaconautas.SCALE);

		//FRASE GAME OVER
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("GAME OVER", AppMacaconautas.WIDTH * AppMacaconautas.SCALE/2 - 120 , AppMacaconautas.HEIGHT * AppMacaconautas.SCALE/2 - 100);
		//mostrar recorde e bananas coletadas[CRIAR]
	}

	//TIRAR DAQUI??
	private void render() {
		//renderizar the AppMacaconautas
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { //significa que ainda nao existe nenhum buffer strategy
			this.createBufferStrategy(3); //sequencia de buffers que colocamos na tela para otimizar a renderizacao (entre 2 ou 3)	
			return; //"break"
		}
		//fundo
		Graphics g = bs.getDrawGraphics(); //podemos gerar imagem, retangulo, string
		g.setColor(Color.black);
		g.fillRect(0,0, AppMacaconautas.WIDTH*AppMacaconautas.SCALE,AppMacaconautas.HEIGHT*AppMacaconautas.SCALE); //aparece um retangulo na tela (x,y,largura,altura)
		macaco.render(g);
		espaco.render(g);
		for (int i = 0; i < lasers.size(); i++) {
			lasers.get(i).render(g);
		}
		
		//banana
		String stringBanana = "Bananas: " + bananasColetadas;
		renderStringsEspaco(g, stringBanana, AppMacaconautas.WIDTH * AppMacaconautas.SCALE - (stringBanana.length())*TAMANHO_STRING_JOGO, AppMacaconautas.HEIGHT * AppMacaconautas.SCALE, Color.LIGHT_GRAY);
		
		//distancia
		String stringDistancia = distancia + " m";
		renderStringsEspaco(g, stringDistancia, (AppMacaconautas.WIDTH * AppMacaconautas.SCALE - (stringDistancia.length())*TAMANHO_STRING_JOGO)/2, TAMANHO_STRING_JOGO, Color.LIGHT_GRAY);
		
		if (jogoState == 'O') {
			renderGameOver(g);
			//esperar uns segundos para sair [CRIAR]
		}
		bs.show(); //mostra o grafico
	}

	private boolean checarColisaoObstaculo() {
		Rectangle formaMacaco = macaco.getBounds();
		Rectangle formaObstaculo = null;
		for (int i = 0; i < espaco.getObstaculosNaSessao(); i++) {
			formaObstaculo = espaco.getObstaculos().get(i).getBounds();
			if (formaMacaco.intersects(formaObstaculo)) {
				return true;
			}
		}
		return false;
	}

	private boolean checarColisaoAlien() {
		Rectangle formaMacaco = macaco.getBounds();
		Rectangle formaAlien = null;
		for (int i = 0; i < espaco.getAliensNaSessao(); i++) {
			formaAlien = espaco.getAliens().get(i).getBounds();
			if (formaMacaco.intersects(formaAlien)) {
				return true;
			}
		}
		return false;
	}

	private boolean checarColisaoLaser() {
		Rectangle formaMacaco = macaco.getBounds();
		Rectangle formaLaser = null;
		for (int i = 0; i < lasers.size(); i++) {
			formaLaser = lasers.get(i).getBounds();
			if (formaMacaco.intersects(formaLaser)) {
				return true;
			}
		}
		return false;
	}

	private int checarColisaoBanana() {
		Rectangle formaMacaco = macaco.getBounds();
		Rectangle formaBanana = null;
		for (int i = 0; i < espaco.getBananasNaSessao(); i++) {
			formaBanana = espaco.getBananas().get(i).getBounds();
			if (formaMacaco.intersects(formaBanana)) {
				return i;
			}
		}
		return -1;
	}
	
	private int checarColisaoWhey() {
		Rectangle formaMacaco = macaco.getBounds();
		Rectangle formaWhey = null;
		for (int i = 0; i < espaco.getWheyNaSessao(); i++) {
			formaWhey = espaco.getWhey().get(i).getBounds();
			if (formaMacaco.intersects(formaWhey)) {
				return i;
			}
		}
		return -1;
	}

	private void checarColisoes() { 
		if(checarColisaoObstaculo() || checarColisaoAlien() || checarColisaoLaser()){
			jogoState = 'O';
		}
		int b = checarColisaoBanana();
		if (b != -1) {
			ArrayList<Banana> bananas = espaco.getBananas();
			bananas.remove(b);
			espaco.setBananas(bananas); //remocao da banana
			espaco.setBananasNaSessao(espaco.getBananasNaSessao() - 1); //sem isso o jogo pifa
			bananasColetadas += 1;
		}
		int w = checarColisaoWhey();
		if (w != -1) {
			ArrayList<WheyProtein> whey = espaco.getWhey();
			whey.remove(w);
			espaco.setWhey(whey); //remocao do whey
			espaco.setWheyNaSessao(espaco.getWheyNaSessao() - 1);
		}
	}

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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			macaco.setIsGoingUp(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			macaco.setIsGoingUp(false);
		}
	}


}
