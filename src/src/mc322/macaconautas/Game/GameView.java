package mc322.macaconautas.Game;

import java.awt.Canvas;
import javax.swing.JFrame;

import mc322.macaconautas.Interface.IGame;
import mc322.macaconautas.SpriteSheet.SpriteSheet;

public class GameView extends Canvas implements IGame{

	private static final long serialVersionUID = 4883349660039876621L;

	private GameControl conGame;

	/**
	 * Inicializa um GameView.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param record recorde de distância percorrida.
	 * @param selectedSkin skin selecionada.
	 * @param f JFrame utilizado.
	 * @param spriteSheet sprite sheet do jogo.
	 * @throws InterruptedException
	 */
	public GameView(int bananaQuantity, long record, int selectedSkin, @SuppressWarnings("exports") JFrame f, @SuppressWarnings("exports") SpriteSheet spriteSheet) throws InterruptedException {
		this.conGame = new GameControl(bananaQuantity, record, selectedSkin, f, spriteSheet); 
	}

	/**
	 * Retorna o estado do game.
	 */
	public char getState() {
		return this.conGame.getState();
	}

	/**
	 * Mostra o game.
	 */
	public void shows() throws InterruptedException {
		this.conGame.start();
	}

	/**
	 * Retorna a quantidade de bananas possuídas.
	 */
	public int getBananaQuantity() {
		return this.conGame.getBananaQuantity();
	}

	/**
	 * Retorna o recorde de distância percorrida.
	 */
	public long getRecord() {
		return this.conGame.getRecord();
	}
}