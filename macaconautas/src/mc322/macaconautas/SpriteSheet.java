package mc322.macaconautas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage spriteSheet;
	private int spriteWidth;
	private int spriteHeight;

	/**
	 * Inicializa uma sprite sheet.
	 * @param path caminho para o arquivo da sprite sheet.
	 */
	public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
		try {
			this.spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	/**
	 * Retorna um determinado sprite da sprite sheet.
	 * @param x coordenada x do sprite.
	 * @param y coordenada y do sprite.
	 * @return determinado sprite.
	 */
	public BufferedImage getSprite(int x, int y) {
		return this.spriteSheet.getSubimage(x * this.spriteWidth, y * this.spriteHeight, this.spriteWidth, this.spriteHeight);
	}
}