package mc322.macaconautas.app;

import mc322.macaconautas.Interface.IInit;

public class AppMacaconautas {

	public static void main(String[] args) throws InterruptedException {	//throws para sleep	(aplicar try catch)
		IInit controle = new Control();
		controle.init();
	}
		
}


