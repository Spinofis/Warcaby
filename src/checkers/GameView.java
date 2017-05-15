package checkers;

import javafx.geometry.Insets;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {

	private Group fieldGroup = new Group(); 
	private Group checkerGroup = new Group();
	private Field field = null;
	private static Field[][] fieldTab = new Field[8][8]; // tablica obiektów typu pole planszy
	

	
	/*W funkcji tworze w dwóch pêtlach pola a pozniej w dwoch petlach pionki.
	 * (i+j)%2 okreœla kolor, bo albo zero albo 1.*/
	public Pane createBoard() { 
		Pane pane = new Pane();
		pane.setPrefSize(400, 400);
		pane.getChildren().addAll(fieldGroup, checkerGroup);
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				field = new Field((i + j) % 2, i, j);
				fieldTab[i][j] = field;
				fieldGroup.getChildren().add(field);
			}

		int chceckerNumber = 1;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {

				if ((i % 2 == 1 && j % 2 == 0 && i != 3 && i != 4) || (i % 2 == 0 && j % 2 == 1 && i != 3 && i != 4)) {
					Checker checker = new Checker(chceckerNumber++, i, j);
					fieldTab[i][j].setChecker(checker);
					checkerGroup.getChildren().add(checker);
                   /*
                    * Odpowiednio rozmieszczam pionki i przypisujê pionki odpowiednim polom w setChecker.*
                    */
				}
			}

		return pane; //zwracam kontener z plansz¹
	}
 
   /*
    * na polu wywo³ujê metode z klasy pole do zmiany pionka*/
	public static void changeChecker(Checker checker, int x, int y) {
		fieldTab[x][y].setChecker(checker);
	}

	

	
}
