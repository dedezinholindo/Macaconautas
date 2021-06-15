package mc322.macaconautas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
//MONTAR INTERFACE ENTRE MENU LOJA

//tentar criar frame aqui e quando passar pros outros estados do jogo ir mudando com base nesse frame de origem
public class AppMacaconautas extends Canvas {
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 4;
	private static char appState; //"L" para Loja, "M" para menu inicial, "J" para jogo e F de Fim
	private static boolean jogoCriado;
	private static boolean lojaCriada;
	private static boolean menuCriado;
	private static ControleJogo jogo;
	private static MenuInicial menu;
	private static Loja loja;
	private static int quantidadeBananas;
	private static int recorde;
	private static int[] skinsLiberadas;

	public AppMacaconautas() {
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
	
	public static void abrirMenu() throws InterruptedException {
		if(!menuCriado) {
			menu = new MenuInicial();
			menu.start();
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
	
	public static void abrirLoja() throws InterruptedException {
		if(!lojaCriada) {
			loja = new Loja();
			loja.start();
			lojaCriada = true;
		}
		Thread.currentThread().sleep(50); //operacoes imediatas ocasionam erros inesperaveis
		if (loja.getLojaState() == 'M') {
			appState = 'M';
			lojaCriada = false;
		}
	}
	
	public static void abrirJogo() throws InterruptedException {
		if(!jogoCriado) {
			jogo = new ControleJogo();
			jogo.start();
			jogoCriado = true;
		}
		Thread.currentThread().sleep(50); //operacoes imediatas ocasionam erros inesperaveis
		if (jogo.getJogoState() == 'O') {
			appState = 'M';
			jogoCriado = false;
			quantidadeBananas += jogo.getBananasColetadas();
		}
	}

	public static void main(String[] args) throws InterruptedException {	//throws para sleep	(aplicar try catch)
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


