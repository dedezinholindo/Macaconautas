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

import mc322.macaconautas.Interface.ISaveGame;

public class SaveGame implements ISaveGame {

	private final static String[] KEYS = {"u9mf9we98fmwf9mweunqenhwqds9k90dk", "984rh3hr9384r9834j89rj49r3jj894j9jfj9", "dsft2e36e7g23e56re5f32fe5f6", "23tfe67g3e7geg3g26eg732e", "67g3466g7g67fgf63ge6d", "783nenf7n38nfe7nnenf7eu", "dnyed73nd783ne7nfn8nef", "du3ned3n8enn83473rfnufhhfhe8", "di202dwjj298denfb2hswhj"};
	private long record;
	private boolean[] ownedSkins;//trocar controle
	private int selectedSkin;
	private int bananaQuantity;
	private String gameInfo;

	public SaveGame(){
		gameInfo = ""; 
	}

	private String selectKey() {
		Random aleatorio = new Random();
		int index = aleatorio.nextInt(KEYS.length);
		return KEYS[index];
	}

	private String ownedSkinsToString() {
		String ownedSkinsString = "";
		for (int i = 0; i < this.ownedSkins.length; i++) {
			ownedSkinsString += (this.ownedSkins[i] ? "1" : "0");
		}
		return ownedSkinsString;
	}

	private void writeSaveFile(BufferedWriter write) {
		String content = (selectKey() + " " + "ownedSkins:" + ownedSkinsToString() +
							" " + "selectedSkin:" + this.selectedSkin +
							" " + "bananas:" + this.bananaQuantity +
							" " + "record:" + this.record + " " + selectKey());
		byte[] encodedBytes = Base64.getEncoder().encode(content.getBytes()); // encodamento do save.
		try {
			write.write(new String(encodedBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record) {
		this.ownedSkins = ownedSkins;
		this.selectedSkin = selectedSkin;
		this.bananaQuantity = bananaQuantity;
		this.record = record;
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
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

	private boolean fileExists() {
		File f = new File("save.txt");
		if(f.exists()) {
			return true;
		}
		return false;
	}

	private void loadGame() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
			this.gameInfo += reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gameInfo = new String(Base64.getDecoder().decode(this.gameInfo));
	}

//	public boolean checkFile();

	public String[] getSavedInfo() {
		String info[];
		if (fileExists()) {
			loadGame();
			String s[] = this.gameInfo.split(" ");
			info = new String[s.length - 2];
			for (int i = 0; i < info.length; i++) {
				info[i] = s[i + 1].split(":")[1]; // ignora o nome do tipo da informação.
			}
		} else {
			info = null;
		}
		return info;
	}
}
