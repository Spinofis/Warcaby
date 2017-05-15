package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application implements Runnable {
    /*Nasza klasa rozszerza Application. Chyba nie da si� inaczej uruchomi� interfejsu.*/
	private Pane pane;//kontener do kt�rego najpierw wrzuc� panel logowania a potem plansz�.
	
	public App() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		launch("Main.java"); //��czenie aplikacji z Maine
	}

	
	/*Start metoda, kt�r� trzeba zaimplementowa� kiedy rozszerza si� Aplikacje.
	 * Tutaj decyduj� si� co wy�wietla� w okienku.
	 * Dzia�a to tak, �e g��wnym elementem jest Stage do niego dodaje si� Scene, a do sceny 
	 * kontener np. nasz Pane. Troch� pojebane :P*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginView login; //tworz� panel logowania
		pane=login=new LoginView(); //tworze panel logowania
		login.setApp(this); /*przekazuje panelowi referencje to tego obiekty /*
		dzi�ki temu z panelu logwania b�d� m�g� przej�� do planszy.
		*/
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}
	
	/*Tu jest metoda w kt�rej przechodz� z logowania do planysz*/
	public void changeScene(Pane pane){  
		this.pane.getChildren().clear();  
		this.pane.getChildren().add(pane);
	}
}
