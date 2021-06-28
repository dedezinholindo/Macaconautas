# Projeto Macaconautas

# Descrição Resumida do Projeto/Jogo

 Em _Macaconautas_ o jogador controla um macaco com uma mochila a jato que deve desviar de aliens, lasers e obstáculos para coletar bananas e percorrer a maior distância possível. Ele pode comprar skins diferentes para o macaco com as bananas em nossa magnífica loja e tentar quebrar seus recordes de distância! <br>
<b>OBS:</b> whey protein de banana pode ser um ótimo suplemento para um primata!

# Equipe
* `André Ricardo Pereira Silva` - `231391`
* `Pedro Henrique Pinheiro Gadêlha` - `186985`

# Vídeos do Projeto

## Vídeo da Prévia

 [Link para o vídeo.](https://drive.google.com/file/d/1qr5MKYpSwSPLfJ39C_fErArR71p2KT8u/view?usp=sharing)

## Vídeo do Jogo
[Link para o vídeo.](https://drive.google.com/file/d/1RhbMGJKTdTPvcQ3jO_ZZ_jCUwFCiFkVQ/view?usp=sharing)

# Slides do Projeto

## Slides da Prévia
[Link para os slides.](https://drive.google.com/file/d/1KMDmPCyxy-PK8IQgqy-QHjUKPX5Bcj9d/view?usp=sharing)

## Slides da Apresentação Final
[Link para os slides.](https://drive.google.com/file/d/1QIK0mggxyQRj-dlKePUOX19Kj72D0mBQ/view?usp=sharing)

## Relatório de Evolução

**Evolução:** Inicialmente desenvolvemos o código sem ligar muito para a arquitetura e organização, apenas tentando fazer o jogo rodar. Contudo, conseguimos nos estruturar de forma eficaz para montar uma estrutura de “dupla camada” do pattern MVC (Model-View-Controller), tornando o código mais limpo, legível e de fácil extensão. Ao longo do jogo, desenvolvemos novas ideias, como o Whey Protein de Banana para o macaco ficar quase imortal; novas ideias de skins para o herói utilizar; spawner de entidades baseado em um modelo probabilístico, para nunca gerar de uma forma que fique impossível para o macaco e muitas melhorias na arquitetura baseado nos modelos aprendidos em POO.<br> 
**Lições:** Aprendemos que estruturar o jogo antes de começar a programar é extremamente importante e poderia ter nos poupado muito mais tempo, pois ficamos “consertando” o código para obedecer corretamente as regras básicas de POO (encapsulamento, polimorfismo, heranças, etc). Aprendemos também que é muito importante a ferramenta de pesquisa individual, pesquisando vários assuntos e estudando e buscando informações encontramos várias técnicas e sugestões que nos ajudaram a desenvolver o nosso código de forma eficiente e totalmente profissional!

# Destaques de Código

## Heranças e Classes Abstratas
Utilizamos o artifício da herança e da classe abstrata para facilitar a expansão e compreensão do código.

~~~java
public abstract class Entity extends Rectangle {
...
public class RegularEntity extends Entity {
...
public class Banana extends RegularEntity {
...
public class HugeLaser extends Entity {
...
~~~

## Polimorfismo
Com o polimorfismo, facilitamos a adição de novas Entities no jogo através do Space.

~~~java
public class Space extends Rectangle {
  ...
	private ArrayList<RegularEntity> peacefulRegularEntities;
	private ArrayList<RegularEntity> hostileRegularEntities;
  ...
~~~

## Interfaces
Por meio de interfaces, facilitamos a comunicação entre os componentes do programa. Além disso a forma que as organizamos facilita a compreensão do código.

~~~java
public interface IMode {
	char getState();
	void shows() throws InterruptedException;
}
...
public interface IGame extends IGameInformation, IMode{}
~~~

## Codificação
Utilizamos o código abaixo para codificação do nosso arquivo de save para evitar manipulação do usuário. Criamos algumas chaves internas e, aleatoriamente colocamos uma delas no início e outra no final e depois encodamos em base64. Para verificar se houve ou não manipulação verificamos se as chaves estão na nossa lista (ou se foram manipuladas).

~~~java
private void writeSaveFile(BufferedWriter write) {
		String content = (selectKey() +
				" " + "ownedSkins:" + booleanArrayToNumbersString(this.ownedSkins) +
				" " + "selectedSkin:" + this.selectedSkin +
				" " + "bananas:" + this.bananaQuantity +
				" " + "record:" + this.record +
				" " + selectKey());
		byte[] encodedBytes = Base64.getEncoder().encode(content.getBytes()); // encodamento do save.
		try {
			write.write(new String(encodedBytes));
	...
}
~~~

## Visibilidade de package
Como criamos uma estrutura bem definida para cada funcionalidade (dividida em pacotes), decidimos deixar a visibilidade de todos os Builder como package, para que as classes que tem que manipular o mesmo componente possa manipulá-lo de forma livre.

~~~java
public class StoreBuilder {
	...
	char state; // N normal, M para ir para o menu
	boolean isRunning;
	Thread thread;
	int bananaQuantity;
	boolean ownedSkins[];
	int selectedSkin;
	...
}
~~~

## Threading dentro de loops
Conseguimos administrar com excelência os modos do jogo e as mudanças de estado, por meio de threadings dentro de um loop while principal. O while sempre fazia a verificação do estado do jogo, para, assim, saber e decidir qual ação ele deve tomar. <br>
**OBS:** em cada modo (game, store e menu) é gerada uma thread, a qual é terminada após a mudança de estado do app.

~~~java
public void init() throws InterruptedException {	
	while (this.appState != 'O') {
		switch(this.appState) {
		case 'M':
			openMenu();
			break;

		case 'S':
			openStore();
			break;

		case 'G':
			openJogo();
			break;
		...
	}
~~~

## Dinamicidade
Utilizando constantes, tornamos o jogo mais fácil de ser alterado, evitando a necessidade de alterar vários trechos apenas para aplicar uma mudança.

~~~java
public class AlienFleet extends Entity {
	private final static int SHIP_QUANTITY = 6;
	private final static int SHIP_WIDTH = 40;
	private final static int SHIP_HEIGHT = 40;
	private final static int WIDTH = SHIP_WIDTH;
	private final static int CANNON_HEIGHT = 26;
	private final static int SPRITE_X = 0;
	private final static int SPRITE_Y = 5;
	private final static int SPRITE_QUANTITY = 5;
	private final static int CHARGING_ANIMATION_PERIOD = 60;
	private final static int MAX_CHARGING_ANIMATION_FRAMES = 4;
	private final static int MAX_SHOOTER_QUANTITY = 3;
	private final static int REFRESH_PERIOD = 2 * 60;
	private final static int SHOOTING_PERIOD = 5 * 60;
	private final static int MAX_SHOT_QUANTITY = 3;
  ~~~

# Destaques de Pattern

## Diagrama do Pattern
![Diagrama Game](assets/diagrama-componentes-Menu.png)

## Código do Pattern
~~~java
public class Control extends Canvas implements IInit {
	...
	public void openMenu() throws InterruptedException {
		if (!this.isMenuCreated) {
			this.menu = new MenuView(this.bananaQuantity, this.record, this.f);
			this.isMenuCreated = true;
			this.menu.shows();
		}
		...
		switch(this.menu.getState()) {
		....
		}		
	}
...
public class MenuView implements IMenu {
	private MenuControl conMenu;
	public MenuView(int bananaQuantity, long record, @SuppressWarnings("exports") JFrame f) {
		this.conMenu = new MenuControl(bananaQuantity, record, f);
	}
...
public class MenuControl extends Canvas implements Runnable, KeyListener{
	private MenuBuilder menu;
	private JFrame f;
	public MenuControl(int bananaQuantity, long record, @SuppressWarnings("exports") JFrame f) {
		this.f = f;
		...
		this.menu = new MenuBuilder(bananaQuantity, record);
	}
...
public class MenuBuilder {
	char state;
	boolean isRunning;
	Thread thread;
	int bananaQuantity;
	long record;
	...
	public MenuBuilder(int bananaQuantity, long record) {
		this.state = 'N';
		this.isRunning = true;
		this.bananaQuantity = bananaQuantity;
		this.record = record;
		...
	}
	...
~~~

Os componentes Menu, Game e Store adotam um design pattern semelhante ao model-view-controller alterado para nossa implementação. O model, representado pelas classes Builder, apenas armazena informações úteis ao componente. O control, representado pelas classes Control, é responsável pela lógica do componente e recebimento de comandos, utilizando os dados do model e alterando-os conforme. Já o view, representado pelas classes View, é adotado para realizar a conversação do componente com a classe superior Control, apenas mostrando (renderizando) o componente e fornecendo algumas informações necessárias para atualizar o Control ao final do uso do view.

# Conclusões e Trabalhos Futuros

Neste trabalho, chegamos a conclusão de que é sempre importante montar a estrutura, principalmente de uma forma visual, para ter uma noção concreta da arquitetura do jogo antes de começar a “botar a mão na massa”.
Para projetos futuros tivemos incontáveis ideias:
* **Aumento da dificuldade do jogo:** cada vez que o herói passa por uma frota, o número de entidades inimigas aumentam e a velocidade delas também… (tendendo sempre a um valor máximo).
* **Melhorar codificação:** aumentar a dificuldade de codificação para evitar manipulação do usuário nos dados salvos.
* **Mudar derrota da frota:** disponibilizar armas para o macaco poder utilizar em sua batalha com a frota alien.
* **Mudar spawner de entidades:** evitar sobreposição de elementos no jogo e melhorar a lógica de surgimento deles.
* **Responsividade do jogo:** alterar para que o jogo seja responsivo, isto é, mantenha-se nas mesmas proporções para todo formato de telas (incluindo de celular).
* **Novos itens na loja:** novos itens além de skins, como chapéus, calças, trocar apenas o jetpack, etc.
* **Melhorar design:** Melhorar o design no geral, como um jogo com gráficos melhores, uma melhor apresentação das informações (como informar quando se obteve um novo recorde, entre outros), além de deixar o jogo mais interativo (eventos com o mouse, digitar e salvar o nome de quem está jogando, etc).
* **Múltiplos saves:** permitir o usuário ter vários saves para escolher qual ele vai querer iniciar.

# Documentação dos Componentes

# Diagramas

## Diagrama Geral do Projeto

![Diagrama Geral](assets/diagrama-componentes-Geral.png)

O AppMacaconautas inicializa o Control. Então o Control inicializa algum dos três componentes: Game, Store ou Menu, de acordo com o estado atual.

## Diagrama Geral de Componentes

### Componente Game

![Diagrama Game](assets/diagrama-componentes-Game.png)

### Componente Menu

![Diagrama Menu](assets/diagrama-componentes-Menu.png)

### Componente Store

![Diagrama Loja](assets/diagrama-componentes-Store.png)

## Componente Control

O Control é responsável pela administração total do jogo, comunicando-se com os outros três componentes de acordo com o estado do jogo.

**Ficha Técnica**
item | detalhamento
----- | -----
Classe |  mc322.macaconautas.Control.* 
Autores | André Ricardo e Pedro Gadêlha
Interfaces | IInit

### Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](assets/diagrama-interfaces-Control.png)

Interface agregadora do componente em Java:

~~~java
public interface IInit {
	void init() throws InterruptedException;
}
~~~

## Componente Game

O Game é responsável pela administração, visualização e criação de elementos e do ambiente de jogo. Foi utilizado o pattern Model-View-Control para a sua arquitetura interna.

![Componente](assets/diagrama-componentes-Game.png)
![Componente](assets/diagrama-componentes-Game-detalhado.png)

**Ficha Técnica**
item | detalhamento
----- | -----
Classe |  mc322.macaconautas.Game.* 
Autores | André Ricardo e Pedro Gadêlha
Interfaces | IGame

### Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](assets/diagrama-interfaces-GameView.png)

Interface agregadora do componente em Java:

~~~java
public interface IGame extends IGameInformation, IMode{}
~~~

## Componente Menu

O Menu é responsável pela administração e visualização do ambiente de navegação entre game e store, e para sair do programa. Foi utilizado o pattern Model-View-Control para a sua arquitetura interna.

![Componente](assets/diagrama-componentes-Menu.png)

**Ficha Técnica**
item | detalhamento
----- | -----
Classe |  mc322.macaconautas.Menu.* 
Autores | André Ricardo e Pedro Gadêlha
Interfaces | IMenu

### Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](assets/diagrama-interfaces-MenuView.png)

Interface agregadora do componente em Java:

~~~java
public interface IMenu extends IMode {}
~~~

## Componente Store

A Store é responsável pela administração, visualização e criação de elementos e do ambiente de jogo. Foi utilizado o pattern Model-View-Control para a sua arquitetura interna.

![Componente](assets/diagrama-componentes-Store.png)

**Ficha Técnica**
item | detalhamento
----- | -----
Classe |  mc322.macaconautas.Store.* 
Autores | André Ricardo e Pedro Gadêlha
Interfaces | IStore

### Interfaces

Interfaces associadas a esse componente:

![Diagrama Interfaces](assets/diagrama-interfaces-StoreView.png)

Interface agregadora do componente em Java:

~~~java
public interface IStore extends IMode, IStoreInformation{}
~~~

## Detalhamento das Interfaces

### Interface IMode

Responsável pela visualização do jogo e por devolver o estado do jogo para alterar o estado do App.

~~~java
public interface IMode {
  void shows() throws InterruptedException;
  char getState();
}
~~~

Método | Objetivo
-------| --------
void shows() throws InterruptedException | mostra na tela o serviço que o componente que o implementa oferece (o game, o menu ou a store).
char getState() | devolve o estado atual do do Componente para realizar as devidas ações no app.

### Interface IBanana

Responsável por devolver a quantidade de bananas em posse.

~~~java
public interface IBanana {
	int getBananaQuantity();
}
~~~

Método | Objetivo
-------| --------
int getBananaQuantity() | devolve a quantidade de bananas após certas mudanças.

### Interface IGameInformation

Responsável por devolver ao controle informações sobre resultados do jogo.

~~~java
public interface IGameInformation extends IBanana{
long getRecord();
}
~~~

Método | Objetivo
-------| --------
long getRecord() | devolve a distância percorrida do jogador para setar, se for o novo recorde, a distância do app.

### Interface IGame

Interface agregadora que une IMode e IGameInformation para interagir com o componente Jogo.

~~~java
public interface IGame extends IGameInformation, IMode {}
~~~

### Interface IMenu

Interface agregadora que usa IMode para interagir com o componente Menu.

~~~java
public interface IMenu extends IMode {
 
}
~~~

### Interface IStoreInformation

Responsável por devolver ao controle informações sobre a store.

~~~java
public interface IStoreInformation extends IBanana {
	boolean[] getOwnedSkins();
	int getSelectedSkin();
}
~~~

Método | Objetivo
-------| --------
boolean[] getOwnedSkins() | devolve todas as skins que o usuário possui.
int getSelectedSkin() | devolve a skin que o usuário selecionou por último.

### Interface IStore

Interface agregadora que une IMode e IStoreInformation para interagir com o componente Store.

~~~java
public interface IStore extends IStoreInformation, IMode {}
~~~

### Interface ISaveGameHandling

Responsável por fazer a conexão do SaveGameHandling com o Control para salvar e carregar os dados do jogo.

~~~java
public interface ISaveGameHandling {
	public String[] getSavedInfo();
	public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record);
}
~~~

Método | Objetivo
-------| --------
public String[] getSavedInfo() | retorna as informações do jogo salvo no arquivo save.txt.
public void saveGame(boolean[] ownedSkins, int selectedSkin, int bananaQuantity, long record) | realizar o salvamento no jogo com base nos dados recebidos no atributo.


### Interface IInit

Inicia o App.
	
~~~java
public interface IInit {
  void init() throws InterruptionExcepion;
}
~~~

Método | Objetivo
-------| --------
void init() | responsável por iniciar o app.

# Plano de Exceções

## Diagrama da hierarquia de exceções
Descritas abaixo.

## Descrição das classes de exceção

Não vimos a necessidade de utilizar classes muito estruturadas para plano de exceção, pois as ações do jogo são bem restritas para o usuário. Portanto, utilizamos de classes que já estão nas bibliotecas disponíveis pelo Java.

Classe | Descrição
----- | -----
InterruptedException | Ocorre quando a thread é interrompida por algum motivo.
FileNotFoundException | ocorre quando um arquivo requerido não existe.
IOException | pega todos os casos gerais de erros desconhecidos.