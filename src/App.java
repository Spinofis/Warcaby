
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * @author Bartek
 *
 *Klasa aplikacji, odpowiada za uruchomienie i prze��czanie kontekstu
 *aplikacji.
 */
public class App extends Application implements Runnable {

	private static Pane pane = new Pane();
	static TCPConnection connection;
	private static Thread connectionThread;
	protected static boolean isServer, isOldUser = true;
	protected static boolean isEnd = false;
	protected static String url = "jdbc:sqlserver://localhost:1433;databseName=Checker;integratedSecurity=true;";

	/**
	 * ��czenie si� aplikakacji z logik�.
	 */
	
	@Override
	public void run() {
		launch("Game.java");
	}

	/**
	 * Start interfejsu graficznego.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginView();
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}

	/**
	 * Zamykanie wraz z aplikacj� po��czenia TCP i w�tku po��czenia.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		try {
			connection.closeConnection();
			connectionThread.stop();
		} catch (Exception e) {
			
		}
	}

/**
 * Zmiana sceny aplikacji an plansz� do gry.	
 * @param isServer
 */
	
	public static void changeSceneToGameView(boolean isServer) {
		GameView game = new GameView(isServer);
		pane.getChildren().clear();
		pane.getChildren().add(game.createBoard());
	}

	
	/**
	 *Funkcja tworzy widok, w kt�rym u�ytkownik decyduj� czy chce stworzy�
	 * serwer na swoim komputerze czy po��czy� si� ju� z istniej�cym. 
	 */
	
	private static void decideIsServer() {
		pane.setPrefSize(400, 400);
		Button server = new Button("Server");
		Button client = new Button("Client");
		server.setLayoutX(190);
		server.setLayoutY(190);
		client.setLayoutX(190);
		client.setLayoutY(240);

		server.setOnMouseClicked(e -> {
			isServer = true;
			changeSceneToGameView(isServer);
			startConnection(isServer);
		});

		client.setOnMouseClicked(e -> {
			isServer = false;
			changeSceneToGameView(isServer);
			startConnection(isServer);
		});

		pane.getChildren().addAll(server, client);
	}

	/**
	 * Funkcja, kt�ra tworzy i umieszcza w interfejsie widok logowania si� 
	 * do aplikacji.
	 */
	private static void LoginView() {
		pane.setPrefSize(400, 400);
		Label loginLabel = new Label("Login");
		Label passwordLabel = new Label("Has�o");
	
		TextField loginText = new TextField();
		TextField passwordText = new TextField();
		Button oldUser = new Button("Zaloguj si�");
		Button newUser = new Button("Zarejestruj si�");
		oldUser.setLayoutX(50);
		oldUser.setLayoutY(270);
		newUser.setLayoutX(50);
		newUser.setLayoutY(300);
       
		newUser.setOnMouseClicked(e -> {
			DataBase db = new DataBase();
			db.writeUser(url, loginText.getText(), passwordText.getText());
		});
		oldUser.setOnMouseClicked(e -> {
			String password = null;
			DataBase db = new DataBase();
			password = db.readData(url, "'" + loginText.getText() + "'", "Password");
			if (password.equals(passwordText.getText())) {
				pane.getChildren().clear();
				decideIsServer();
			}
		});
		loginLabel.setLayoutX(50);
		loginLabel.setLayoutY(70);
		loginLabel.setFont(Font.font(22));
		passwordLabel.setLayoutX(50);
		passwordLabel.setLayoutY(200);
		passwordLabel.setFont(Font.font(22));
		loginText.setLayoutX(50);
		loginText.setLayoutY(110);
		passwordText.setLayoutX(50);
		passwordText.setLayoutY(240);
		pane.getChildren().addAll(loginLabel, passwordLabel, loginText, passwordText, oldUser, newUser);
	}

	/**
	 * Start po��czenia TCP.
	 * @param boolean isServer- okre�la czy aplikacja jest serwerem czy klientem  
	 */
	private static void startConnection(boolean isServer) {
		connection = new TCPConnection(1234, isServer);
		connectionThread = new Thread(connection);
		connectionThread.start();
	}

	/**
	 * Funkcja tworzy i wrzuca do interfejsu widok ko�ca gry.
	 */
	public void gameOver() {
		pane.getChildren().clear();
		Image img = new Image("/gameOver.jpg");
		ImageView imgW = new ImageView(img);
		pane.getChildren().addAll(imgW);
	}
	
	

}
