package mc322.macaconautas.Interface;

public interface IStoreInformation extends IBanana {

	/**
	 * Retorna as skins possuídas.
	 */
	boolean[] getOwnedSkins();

	/**
	 * Retorna a skin selecionada.
	 */
	int getSelectedSkin();
}