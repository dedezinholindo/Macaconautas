package mc322.macaconautas.Menu;

public class MenuBuilder {

	private final static String[] MENU_OPTIONS = {"Jogar", "Loja", "Salvar e Sair"};

	char state; // N normal, S ir loja, G ir jogo, O para sair
	boolean isRunning;
	Thread thread;
	int bananaQuantity;
	long record;
	String options[];
	int currentOption;
	boolean goUp;
	boolean goDown;
	boolean select;
	
	public MenuBuilder(int bananaQuantity, long record) {
		this.state = 'N';
		this.isRunning = true;
		this.bananaQuantity = bananaQuantity;
		this.record = record;
		this.options = MENU_OPTIONS;
		this.currentOption = 0;
		this.goUp = false;
		this.goDown = false;
		this.select = false;
	}
}
