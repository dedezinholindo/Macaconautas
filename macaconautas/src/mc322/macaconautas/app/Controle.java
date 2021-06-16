package mc322.macaconautas.app;

import mc322.macaconautas.Jogo.*;
import java.awt.Canvas;

import javax.swing.JFrame;

import mc322.macaconautas.Jogo.ControleJogo;
import mc322.macaconautas.Loja.LojaView;
import mc322.macaconautas.Menu.MenuView;

public class Controle extends Canvas{
	public final static int BORDA = 37; //grossura da borda
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 5;
	public static JFrame f;
	
	private final static String SPRITE_SHEET_PATH = "/res/spritesheet.png";
	private final static int SPRITE_WIDTH = 40;
	private final static int SPRITE_HEIGHT = 40;

	private char appState; //"L" para Loja, "M" para menu inicial, "J" para jogo e F de Fim
	private boolean jogoCriado;
	private boolean lojaCriada;
	private boolean menuCriado;
	private JogoView jogo;
	private MenuView menu;
	private LojaView loja;
	private int quantidadeBananas;
	private int recorde;
	private int[] skinsLiberadas;
	private SpriteSheet spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);

	public Controle() {
		initFrame();
		initAtributos();
		try {
			iniciarJogo();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void initAtributos() {
		spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, SPRITE_WIDTH, SPRITE_HEIGHT);
		appState = 'M';
		jogoCriado = false;
		lojaCriada = false;
		menuCriado = false;
		jogo = null;
		menu = null;
		loja = null;
		quantidadeBananas = 0; //ou de acordo com o jogo salvo
		recorde = 0; //ou o jogo salvo
		skinsLiberadas = null; //ou jogo salvo
	}
	
	private void initFrame() {
		f = new JFrame("MACACONAUTAS"); //titulo do jogo ou setTitle()
		f.add(this); //adicionar o que criamos para ficar visível
		f.setLayout(null);
		f.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		f.setResizable(false); //nao pode redimensionar 
		//f.pack(); //fazer o setPreferredSize funcionar de forma correta (erro) -apagar
		f.setLocationRelativeTo(null); //centro (tem que estar depois do pack)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar quando clicar no x e parar de vez
		f.setVisible(true); //deixar ele visivel
		//f.setUndecorated(true);//tirar barra de titulo
	}
		
	public void abrirMenu() throws InterruptedException {
		if(!menuCriado) {
			menu = new MenuView(f);
			menu.mostrarMenu();
			menuCriado = true;
		}
		Thread.currentThread().sleep(50);
		switch(menu.getMenuState()) {
		case 'L':
			appState = 'L';
			menuCriado = false;
			break;
		
		case 'J':
			appState = 'J';
			menuCriado = false;
			break;
		
		case 'F':
			appState = 'F';
			menuCriado = false;
			break;
		}		
	}
	
	public void abrirLoja() throws InterruptedException {
		if(!lojaCriada) {
			loja = new LojaView(f);
			loja.mostrarLoja();
			lojaCriada = true;
		}
		Thread.currentThread().sleep(50); //operacoes imediatas ocasionam erros inesperaveis
		if (loja.getLojaState() == 'M') {
			appState = 'M';
			lojaCriada = false;
		}
	}
	
	public void abrirJogo() throws InterruptedException {
		if(!jogoCriado) {
			jogo = new JogoView(spriteSheet, f);
			jogo.mostrarJogo();
			jogoCriado = true;
		}
		Thread.currentThread().sleep(50); //operacoes imediatas ocasionam erros inesperaveis
		if (jogo.getJogoState() == 'O') {
			appState = 'M';
			jogoCriado = false;
			quantidadeBananas += jogo.getBananasColetadas();
		}
	}

	private void iniciarJogo() throws InterruptedException {	//throws para sleep	(aplicar try catch)
		AppMacaconautas app = new AppMacaconautas();
		appState = 'J'; //TESTE PARA INICIAR O JOGO
		while(appState != 'F') {
			switch(appState) {
			case 'M':
				abrirMenu();
				Thread.currentThread().sleep(3000);
				System.out.println("FUNCIONOOOOOOOOOOOOOOU");
				appState = 'L'; //TESTE- simular ida a loja
				break;
				
			case 'L':
				abrirLoja();
				Thread.currentThread().sleep(3000);
				appState = 'F'; //simular saida da loja (TESTE - no jogo só sai pelo menu)
				break;
				
			case 'J':
				abrirJogo();
				break;
			}
		}
		//salvar jogo
		System.exit(0);
	}
	

}              