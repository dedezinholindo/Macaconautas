package mc322.macaconautas;

public interface IComponente {

	/**
	 * Atualiza o estado do componente em um frame.
	 */
	public void tick();

	/**
	 * Renderiza o componente na tela.
	 */
	public void render();
}