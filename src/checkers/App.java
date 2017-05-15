package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application implements Runnable {
    /*Nasza klasa rozszerza Application. Chyba nie da siê inaczej uruchomiæ interfejsu.*/
	private Pane pane;//kontener do którego najpierw wrzucê panel logowania a potem planszê.
	
	public App() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		launch("Main.java"); //³¹czenie aplikacji z Maine
	}

	
	/*Start metoda, któr¹ trzeba zaimplementowaæ kiedy rozszerza siê Aplikacje.
	 * Tutaj decydujê siê co wyœwietlaæ w okienku.
	 * Dzia³a to tak, ¿e g³ównym elementem jest Stage do niego dodaje siê Scene, a do sceny 
	 * kontener np. nasz Pane. Trochê pojebane :P*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginView login; //tworzê panel logowania
		pane=login=new LoginView(); //tworze panel logowania
		login.setApp(this); /*przekazuje panelowi referencje to tego obiekty /*
		dziêki temu z panelu logwania bêdê móg³ przejœæ do planszy.
		*/
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}
	
	/*Tu jest metoda w której przechodzê z logowania do planysz*/
	public void changeScene(Pane pane){  
		this.pane.getChildren().clear();  
		this.pane.getChildren().add(pane);
	}
}
