
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Checker extends Circle {

	private double radius = 15;
	private double field_size = 50;
	private double checkerNumber;
	int oldX, oldY, newX, newY;

	/*
	 * jeœli kolor mniejszy od 13 to czerwony ,pionek jeœli nie to bia³y TAK JEST W SERWERZE
	 * w cliencie jest na odwórt 
	 * Po tych numerach mo¿na rozró¿niæ kolory
	 * chcia³em zrobiæ ENUMA ale mi coœ nie wychodzi³ :(
	 */
	public Checker(int number, int x, int y, boolean isServer) {

		this.checkerNumber = number;

		setRadius(radius);
		setStrokeType(StrokeType.OUTSIDE);
		setStroke(Color.BLACK);

		relocate(field_size * y + (field_size / 2) - radius, field_size * x + (field_size / 2) - radius);

		if (isServer) {
			if (number < 13)
				setFill(Color.RED);
			else
				setFill(Color.WHITE);
		} else {
			if (number >= 13)
				setFill(Color.WHITE);
			else
				setFill(Color.RED);
		}

		if (number > 13 && isServer) {
			setOnMousePressed(e -> {
				oldY = (int) e.getSceneX() / (int) field_size;
				oldX = (int) e.getSceneY() / (int) field_size;
				setOpacity(0.5);
			});

			setOnMouseDragged(e -> {
				setOpacity(1.0);
				relocate((e.getSceneX() - e.getSceneX() % field_size) + (field_size / 2 - radius),
						(e.getSceneY() - e.getSceneY() % field_size) + (field_size / 2 - radius));
			});

			setOnMouseReleased(e -> {
				newX = (int) ((int) e.getSceneY() / field_size);
				newY = (int) e.getSceneX() / (int) field_size;

				GameView.changeChecker(oldX, oldY, newX, newY, false);

				App.connection.send(oldX, oldY, newX, newY);
			});
		}

		if (number < 13 && !isServer) {
			setOnMousePressed(e -> {
				oldY = (int) e.getSceneX() / (int) field_size;
				oldX = (int) e.getSceneY() / (int) field_size;
				setOpacity(0.5);
			});

			setOnMouseDragged(e -> {
				setOpacity(1.0);
				relocate((e.getSceneX() - e.getSceneX() % field_size) + (field_size / 2 - radius),
						(e.getSceneY() - e.getSceneY() % field_size) + (field_size / 2 - radius));
			});

			setOnMouseReleased(e -> {
				newX = (int) ((int) e.getSceneY() / field_size);
				newY = (int) e.getSceneX() / (int) field_size;

				GameView.changeChecker(oldX, oldY, newX, newY, false);
				App.connection.send(oldX, oldY, newX, newY);
			});
		}
	}
}
