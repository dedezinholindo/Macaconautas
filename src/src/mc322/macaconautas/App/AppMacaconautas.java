package mc322.macaconautas.App;

import mc322.macaconautas.Interface.IInit;
import mc322.macaconautas.Control.Control;

public class AppMacaconautas {

	public static void main(String[] args) throws InterruptedException {	//throws para sleep	(aplicar try catch)
		IInit controle = new Control();
		controle.init();
	}
}