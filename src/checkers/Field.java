package checkers;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;

public class Field extends Rectangle {
	/*rozszerzam kwadrat*/

    public   double field_size = 50;
	private int x, y; // koordynaty planszy
	public Checker checker=null; // pionek ktory nalezy do pola
	

	public Field(int color, int x, int y) { // if 0-white , if 1-green

		
		setWidth(field_size);
		setHeight(field_size);

		relocate(y * field_size, x * field_size); //przesuwam pole w odpowiednie miejsce

		if (color == 0)
			setFill(Color.valueOf("#feb")); //ustawiam kolr
		else
			setFill(Color.valueOf("#582"));	
		
		setOnMousePressed(e->{
			/*funkcja która na klikniêcie pokazuje pionek danego pola. Mo¿na sobie sprawdzaæ czy checker 
			 * jest null czy nie*/
			System.out.println(this.checker);
		});
		this.x = x;
		this.y = y;///
	}

	public void setChecker(Checker checker){
		this.checker=checker;
	}
	
	

}
