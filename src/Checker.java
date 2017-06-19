
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Klasa tworz�ca pionek, definicja jego wygl�du
 */

public class Checker extends Circle {

	private double radius = 15;
	private double field_size = 50;
	private int oldX, oldY, newX, newY;
	private CheckerType checkerType;
	private boolean isKing = false, isServer;

	/**
	 * Konstruktor przypisuj�cy w�a�ciciela pionka, jego pozycj� oraz kolor
	 * Ni�ej zdefiniowane s� metody przemieszczania pionka oraz dzia�anie 
	 * przycisk�w myszy w konfrontacji z pionkiem
	 */
	public Checker(CheckerType checkerType, int x, int y, boolean isServer) {

		this.checkerType = checkerType;
		this.isServer = isServer;

		if (checkerType == CheckerType.RED) {
			setFill(Color.RED);

		} else {
			setFill(Color.WHITE);
		}
		setRadius(radius);
		setStrokeType(StrokeType.OUTSIDE);
		setStroke(Color.BLACK);

		relocate(field_size * y + (field_size / 2) - radius, field_size * x + (field_size / 2) - radius);

		if (this.checkerType == CheckerType.WHITE && isServer) { // je�li bia�y
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
				if (!isKing)
					tryMove();
				else
					tryMoveKing();
				if (newX == 0 && newY%2==1 && !isKing) {
					this.isKing = true;
					Image img = new Image("/korona.jpg");
					setFill(new ImagePattern(img));
				}
			});
		}

		if (this.checkerType == CheckerType.RED && !isServer) { //// je�li
																//// czerwony
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
				if (!isKing)
					tryMove();
				else
					tryMoveKing();
				if (newX == 0 && newY%2==1 && !isKing) {
					this.isKing = true;
					Image img = new Image("/korona2.jpg");
					setFill(new ImagePattern(img));
				}
			});
		}
	}
	
	/**
	 * Utawia flag� "kr�la" - pionek staje si� "damk�"
	 * Ustawiany jest obraz na pionku w zale�no�ci od koloru pionka
	 */
	public void setKing() {
		this.isKing = true;
		if (isServer) {
			Image img = new Image("/korona2.jpg");
			setFill(new ImagePattern(img));
		} else {
			Image img = new Image("/korona.jpg");
			setFill(new ImagePattern(img));
		}
	}
	/**
	 * Funkcja opowiedzialna za przesuni�cie pionka w miejsce w zale�no�ci od tego jaki
	 * ruch gracz wykona� i czy jest to mo�liwe
	 */
	private void tryMove() {
		if (Game.MoveResult(oldX, oldY, newX, newY, this.checkerType) == MoveType.NORMAL) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			// GameView.removeChecker(newX, newY);
			App.connection.send(oldX, oldY, newX, newY, "NORMAL");
		} else if (Game.MoveResult(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL1) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			GameView.removeChecker(newX + 1, newY - 1);
			App.connection.send(oldX, oldY, newX, newY, "KILL1");
		} else if (Game.MoveResult(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL2) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			GameView.removeChecker(newX + 1, newY + 1);
			App.connection.send(oldX, oldY, newX, newY, "KILL2");
		} else if (Game.MoveResult(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL3) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			GameView.removeChecker(newX - 1, newY + 1);
			App.connection.send(oldX, oldY, newX, newY, "KILL3");
		} else if (Game.MoveResult(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL4) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			GameView.removeChecker(newX - 1, newY - 1);
			App.connection.send(oldX, oldY, newX, newY, "KILL4");
		} else
			relocate(field_size * oldY + (field_size / 2 - radius), field_size * oldX + (field_size / 2 - radius));
	}
	
	/**
	 * Funkcja opowiedzialna za przesuni�cie pionka "damki" w miejsce w zale�no�ci od tego jaki
	 * ruch gracz wykona� i czy jest to mo�liwe
	 */
	private void tryMoveKing() {
		if (Game.moveKing(oldX, oldY, newX, newY, this.checkerType) == MoveType.NORMAL) {
			GameView.changeChecker(oldX, oldY, newX, newY, false);
			if (Game.captureKing(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL1) {
				GameView.removeChecker(newX + 1, newY - 1);
				App.connection.send(oldX, oldY, newX, newY, "KILL1");
			} else if (Game.captureKing(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL2) {
				GameView.removeChecker(newX + 1, newY + 1);
				App.connection.send(oldX, oldY, newX, newY, "KILL2");
			} else if (Game.captureKing(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL3) {
				GameView.removeChecker(newX - 1, newY + 1);
				App.connection.send(oldX, oldY, newX, newY, "KILL3");
			} else if (Game.captureKing(oldX, oldY, newX, newY, this.checkerType) == MoveType.KILL4) {
				GameView.removeChecker(newX - 1, newY - 1);
				App.connection.send(oldX, oldY, newX, newY, "KILL4");
			} else {
				App.connection.send(oldX, oldY, newX, newY, "NORMAL");
			}
		} else
			relocate(field_size * oldY + (field_size / 2 - radius), field_size * oldX + (field_size / 2 - radius));
	}

}
