package checkers;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Checker extends Circle {

	private double radius = 15; //promien rozmiar pola
	private double field_size = 50;
	private double checkerNumber;
	int xOldField, yOldField;
    
	
	/*
	 * jeœli kolor mniejszy od 13 to czerwony ,pionek jeœli nie to bia³y
	 */
	public Checker(int number, int x, int y) {

		this.checkerNumber = number; //ka¿dy pionek ma swój numer od jednego to 24

		setRadius(radius);
		setStrokeType(StrokeType.OUTSIDE); //
		setStroke(Color.BLACK);// ustwienia obwódki
        
		//funkcja ustawia pionek w odpowiednim miejscu 
		relocate(field_size * y + (field_size / 2) - radius, field_size * x + (field_size / 2) - radius);

		if (number < 13)
			setFill(Color.RED);
		else
			setFill(Color.WHITE);
		
		/*3 metody obs³ugi myszki*/

		setOnMousePressed(e -> {
			yOldField = (int) e.getSceneX() / (int) field_size; ///stare wspolrzedne pola 
	         //na którym stoi pionek
			xOldField = (int) e.getSceneY() / (int) field_size;
			setOpacity(0.5);
		});

		setOnMouseDragged(e -> {
			setOpacity(1.0);
			///przesuwanie pionka w raz z myszk¹
			relocate((e.getSceneX() - e.getSceneX() % field_size) + (field_size / 2 - radius),
					(e.getSceneY() - e.getSceneY() % field_size) + (field_size / 2 - radius));
		});

		setOnMouseReleased(e -> {
			int newX = (int) ((int) e.getSceneY() / field_size); /*
			nowe wspolrzedne pola na którym stoi pionek*/
			int newY = (int) e.getSceneX() / (int) field_size;
            
			//zmieniam polom referencje do pionków 
			GameView.changeChecker(this, newX, newY);
			GameView.changeChecker(null, xOldField, yOldField);
		});
	}

  
	
}
