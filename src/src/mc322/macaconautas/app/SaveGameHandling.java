package mc322.macaconautas.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import mc322.macaconautas.Interface.ISaveGameHandling;

public class SaveGameHandling implements ISaveGameHandling {

	private final static String[] KEYS = {"u9mf9we98fmwf9mweunqenhwqds9k90dk", "984rh3hr9384r9834j89rj49r3jj894j9jfj9", "dsft2e36e7g23e56re5f32fe5f6", "23tfe67g3e7geg3g26eg732e", "67g3466g7g67fgf63ge6d", "783nenf7n38nfe7nnenf7eu", "dnyed73nd783ne7nfn8nef", "du3ned3n8enn83473rfnufhhfhe8", "di202dwjj298denfb2hswhj"};
	private final static String SAVE_FILE_NAME = "save.txt";

	private String gameInfo;
	private String[] keys;
	private boolean[] ownedSkins;//trocar controle
	private int selectedSkin;
	private int bananaQuantity;
	private long record;

	/**
	 * Inicializa um SaveGameHandling.
	 */
	public SaveGameHandling(){
		this.gameInfo = ""; 
		this.keys = KEYS;
	}

	/**
	 * Seleciona uma chave de encodamento aleatória.
	 * @return chave de encodamento
	 */
	private String selectKey() {
		Random aleatorio = new Random();
		return this.keys[aleatorio.nextInt(this.keys.length)];
	}

	/**
	 * Converte um array de booleans em uma String de números.
	 * @param booleanArray array de booleans.
	 * @return números correspondente.
	 */
	private String booleanArrayToNumbersString(boolean[] booleanArray) {
		String numbersString = "";
		for (int i = 0; i < booleanArray.length; i++) {
			numbersString += (booleanArray[i] ? "1" : "0");
		}
		return numbersString;
	}

	/**
	 * Escreve os dados salvos encodados em um arquivo.
	 * @param write escritor correspondente ao arquivo.
	 */
	private void writeSaveFile(BufferedWriter write) {
		String content = (selectKey() +
				" " + "ownedSkins:" + booleanArrayToNumbersString(this.ownedSkins) +
				" " + "selectedSkin:" + this.selectedSkin +
				" " + "bananas:" + this.bananaQuantity +
				" " + "record:" + this.record +
				" " + selectKey());
		byte[] encodedBytes = Base64.getEncoder().encode(content.getBytes()); // encodamento do save.
		try {
			write.write(new String(encodedBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Salva um jogo.
	 * @param ownedSkins skins possuídas.
	 * @param selectedSkin skin selecionada.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param record recorde de distância percorrida.
	 */
	public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record) {
		this.ownedSkins = ownedSkins;
		this.selectedSkin = selectedSkin;
		this.bananaQuantity = bananaQuantity;
		this.record = record;
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter(SAVE_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeSaveFile(write);
		try {
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se o arquivo de save existe.
	 * @return true, caso exista.
	 */
	private boolean fileExists() {
		File f = new File(SAVE_FILE_NAME);
		if(f.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Carrega os dados de um jogo salvo.
	 */
	private void loadGame() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE_NAME));
			this.gameInfo += reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gameInfo = new String(Base64.getDecoder().decode(this.gameInfo));
	}

	private boolean findKey(String key) {
		for (int i = 0; i < KEYS.length; i++) {
			if (key.equals(KEYS[i])) {
				return true;
			}
		}
		return false;
	}

	private boolean checkedFile(){
		String s[] = this.gameInfo.split(" ");
		boolean begin = findKey(s[0]);
		boolean end = findKey(s[s.length - 1]);
		if(begin && end) {
			return true;
		}
		return false;
	}

	/**
	 * Retorna os dados do jogo salvo.
	 * @return dados do jogo salvo. null, caso não exista um.
	 */
	public String[] getSavedInfo() {
		String info[] = null;
		if (fileExists()) {
			loadGame();
			if(checkedFile()) {
				String s[] = this.gameInfo.split(" ");
				info = new String[s.length - 2];
				for (int i = 0; i < info.length; i++) {
					info[i] = s[i + 1].split(":")[1]; // ignora o nome do tipo da informação.
				}
			}
		} else {
			info = null;
		}
		return info;
	}
}