package mc322.macaconautas.Interface;

public interface ISaveGameHandling {

	/**
	 * Retorna os dados do jogo salvo.
	 * @return dados do jogo salvo. null, caso não exista um.
	 */
	public String[] getSavedInfo();

	/**
	 * Salva um jogo.
	 * @param ownedSkins skins possuídas.
	 * @param selectedSkin skin selecionada.
	 * @param bananaQuantity quantidade de bananas possuídas.
	 * @param record recorde de distância percorrida.
	 */
	public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record);
}
