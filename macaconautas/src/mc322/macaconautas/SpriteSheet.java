package mc322.macaconautas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage spriteSheet;

	/**
	 * Inicializa uma sprite sheet.
	 * @param path caminho para o arquivo da sprite sheet.
	 */
	public SpriteSheet(String path) {
		try {
			this.spriteSheet = ImageIO.read(getClass().getResource("/res/spritesheet.png"));;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna um determinado sprite da sprite sheet.
	 * @param x coordenada x do sprite.
	 * @param y coordenada y do sprite.
	 * @param width largura do sprite.
	 * @param height altura do sprite.
	 * @return determinado sprite.
	 */
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return this.spriteSheet.getSubimage(x, y, width, height);
	}
}