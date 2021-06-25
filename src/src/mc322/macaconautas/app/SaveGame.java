package mc322.macaconautas.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class SaveGame {

	private final static String[] KEYS = {"u9mf9we98fmwf9mweunqenhwqds9k90dk", "984rh3hr9384r9834j89rj49r3jj894j9jfj9", "dsft2e36e7g23e56re5f32fe5f6", "23tfe67g3e7geg3g26eg732e", "67g3466g7g67fgf63ge6d", "783nenf7n38nfe7nnenf7eu", "dnyed73nd783ne7nfn8nef", "du3ned3n8enn83473rfnufhhfhe8", "di202dwjj298denfb2hswhj"};
	private long record;
	private boolean[] ownedSkins;//trocar controle
	private int bananas;
	private String gameInfo;
	
	public SaveGame(){
		gameInfo = ""; 
		ownedSkins = Controle.getSkinsLiberadas();
	}
	
	private String selectKey() {
		Random aleatorio = new Random();
		int index = aleatorio.nextInt(KEYS.length);
		return KEYS[index];
	}
	
	private String skinsToNumbers() {
		String skins = "";
		for (int i = 0; i < this.ownedSkins.length; i++) {
			System.out.println(ownedSkins[i]);
			if(this.ownedSkins[i]) {
				skins += "1";
			} else {
				skins += "0";
			}
		}
		System.out.println(skins);
		return skins;
	}
	
	private void writeFile(BufferedWriter write) {
		String content = selectKey() + " " + "record:"+this.record + " " + "ownedSkins:"+skinsToNumbers() + " " + "bananas:"+this.bananas + " " + selectKey();
		byte[] encodedBytes = Base64.getEncoder().encode(content.getBytes());
		try {
			write.write(new String(encodedBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveGame(long record, boolean[] ownedSkins, int bananas) {
		this.ownedSkins = ownedSkins;
		this.record = record;
		this.bananas = bananas;
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeFile(write);
		try {
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean fileExists() {
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gameInfo = new String(Base64.getDecoder().decode(this.gameInfo));
	}
	
	//public boolean checkFile();
	
	public ArrayList<String> applySave() {
		loadGame();
		String[] s = this.gameInfo.split(" ");
		ArrayList<String> info = new ArrayList<String>();
		for (int i = 1; i < 4; i++) {
			info.add(s[i].split(":")[1]);
		}
		return info;
	}
}
