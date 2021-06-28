package mc322.macaconautas.Interface;

public interface IMode {

	/**
	 * Retorna o estado do modo.
	 */
	char getState();

	/**
	 * Mostra o estado do modo.
	 */
	void shows() throws InterruptedException;
}