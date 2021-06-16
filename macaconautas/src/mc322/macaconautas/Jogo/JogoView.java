package mc322.macaconautas.Jogo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.Interface.IModo;
import mc322.macaconautas.app.SpriteSheet;

public class JogoView extends Canvas implements IGame{
	
	private ControleJogo conJogo;
	
	public JogoView(SpriteSheet spriteSheet, JFrame f) throws InterruptedException {
		conJogo = new ControleJogo(spriteSheet, f); 
	}
	
	public void mostrar() throws InterruptedException {
		conJogo.start();
	}
	
	public int getBananasColetadas() {
		return conJogo.getBananasColetadas();
	}
	
	public char getState() {
		return conJogo.getJogoState();
	}
}
