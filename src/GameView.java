import javafx.scene.Group;
import javafx.scene.layout.Pane;
/**
 * Klasa tworzy plansz� i umieszcza na niej pionki
 */
public class GameView extends App {

	private Group fieldGroup = new Group();
	static Group checkerGroup = new Group();
	private Field field = null;
	private static Field[][] fieldTab = new Field[8][8]; // tablica p�l planszy
	private static double field_size = 50;
	private static double radius = 15;
	private boolean isServer;

	/**
	 * Konstruktor klasy, ustala czy dany gracz jest serwerem czy klientem
	 */
	public GameView(boolean isServer) {
		this.isServer = isServer;
	}
	/**
	 * Kontener tworz�cy now� plansz� oraz ustawia na niej pionki w zele�no�ci od koloru 
	 */
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

		CheckerType checkerType;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {

				if ((i % 2 == 1 && j % 2 == 0 && i != 3 && i != 4) || (i % 2 == 0 && j % 2 == 1 && i != 3 && i != 4)) {
					if (isServer) {
						if (i < 3)
							checkerType = CheckerType.RED;
						else
							checkerType = CheckerType.WHITE;
					} else {
						if (i > 4)
							checkerType = CheckerType.RED;
						else
							checkerType = CheckerType.WHITE;

					}
					Checker checker = new Checker(checkerType, i, j, isServer);
					fieldTab[i][j].setChecker(checker);
					checkerGroup.getChildren().add(checker);

				}
			}

		return pane;
	}

	/**
	 * Metoda s�u��ca do przesuwania pionk�w po planszy
	 */
	public static void changeChecker(int oldX, int oldY, int newX, int newY, boolean isRelocated) {
		Checker checker = fieldTab[oldX][oldY].getChecker();
		fieldTab[oldX][oldY].setChecker(null);
		fieldTab[newX][newY].setChecker(checker);
		if (isRelocated) {
			checker.relocate(field_size * newY + (field_size / 2 - radius),
					field_size * newX + (field_size / 2 - radius));
		}
	}
	
	/**
	 * Metoda, kt�ra usuwa pionek z danego pola i ustawia je na puste
	 */
	static void removeChecker(int newX, int newY) {
		checkerGroup.getChildren().remove(fieldTab[newX][newY].getChecker());
		fieldTab[newX][newY].setChecker(null);
	}
	
	/**
	 * Metoda, kt�ra zmienia pionek na damk� po wej�ciu na ostatnie pole
	 */
	static void setKing(int newX, int newY) {
		Checker checker = fieldTab[newX][newY].getChecker();
		checker.setKing();
	}
	/**
	 * Metoda zwracaj�ca wskazane pole
	 */
	public static Field getField(int x, int y) {
		return fieldTab[x][y];
	}
}
