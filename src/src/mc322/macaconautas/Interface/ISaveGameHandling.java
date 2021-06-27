package mc322.macaconautas.Interface;

public interface ISaveGameHandling {
	public String[] getSavedInfo();
	public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record);
}
