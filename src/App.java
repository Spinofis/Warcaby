
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application implements Runnable {

	private static Pane pane = new Pane();
	static Connection connection; 
	private static Thread connectionThread; 
	protected static boolean isServer;
	
	

	@Override
	public void run() {
		launch("Main.java");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		createLoginView();
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
	   connection.closeConnection();
       connectionThread.stop();
	}

	public static void changeSceneToGameView() {
		GameView game = new GameView();
		pane.getChildren().clear();
		pane.getChildren().add(game.createBoard());
	}

	
	
	public static void createLoginView() {
		pane.setPrefSize(400, 400);
		Button server=new Button("Server");
		Button client=new Button("Client");
		server.setLayoutX(190);
		server.setLayoutY(190);
		client.setLayoutX(190);
		client.setLayoutY(240);
		
		server.setOnMouseClicked(e->{
			isServer=true;
			changeSceneToGameView();
			startConnection();
		});
		
		client.setOnMouseClicked(e->{
			isServer=false;
			changeSceneToGameView();
            startConnection();
		});
		
		pane.getChildren().addAll(server,client);
	}
    
	private static void startConnection(){
		connection = new Connection(1264);
		connectionThread = new Thread(connection);
		connectionThread.start();
	}
	
}
