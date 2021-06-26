package mc322.macaconautas.app;

import javax.swing.JFrame;

import mc322.macaconautas.Interface.IInit;
//MONTAR INTERFACE ENTRE MENU LOJA

//tentar criar frame aqui e quando passar pros outros estados do jogo ir mudando com base nesse frame de origem
public class AppMacaconautas {

	public static void main(String[] args) throws InterruptedException {	//throws para sleep	(aplicar try catch)
		IInit controle = new Control();
		controle.init();
	}
		
}


