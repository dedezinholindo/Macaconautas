package mc322.macaconautas.Componentes;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import mc322.macaconautas.app.SpriteSheet;

public class FrotaAlien extends Componente{

	private final static int SHIP_QUANTITY = 3; // quantidade de naves na frota.
	private final static int SHIP_SPACING = 80; // espaço entre duas naves consecutivas.
	private final static int TOTAL_HEIGHT = (SHIP_HEIGHT + SHIP_SPACING) * (SHIP_QUANTITY) - SHIP_SPACING; // altura total da frota (subtração ao final, pois há espaço apenas entre naves).

	private final static int SHIP_WIDTH = 40;
	private final static int SHIP_HEIGHT = 40;

	private final static int SHIP_SPRITE_X = 0;
	private final static int SHIP_SPRITE_Y = 5;
	private final static int QUANTIDADE_SPRITES = 5;

	private final static int ANIMATION_PERIOD = 15; // quantidade de frames do jogo para cada frame da animação.
	private final static int MAX_ANIMATION_FRAMES = 5;

	private final static int CHARGE_PERIOD = 120; // quantidade de frames do jogo antes de atirar.
	private final static int SHOOTING_PERIOD = 300; // quantidade de frames do jogo atirando.

	private boolean isShooting[]; // indica quais naves estão atirando/carregando (sempre duas).

	public FrotaAlien(int x, int y, SpriteSheet spriteSheet) {
		super(x, y, SHIP_WIDTH, TOTAL_HEIGHT, spriteSheet);
		this.quantidadeSprites = QUANTIDADE_SPRITES;
		this.sprites = new BufferedImage[this.quantidadeSprites];
		for (int i = 0; i < this.quantidadeSprites; i++) {
			this.sprites[i] = spriteSheet.getSprite(SHIP_SPRITE_X + i, SHIP_SPRITE_Y);
		}
		this.isShooting = new boolean[SHIP_QUANTITY];
		Arrays.fill(this.isShooting, false); // inicializa todos como false.
	}

}
