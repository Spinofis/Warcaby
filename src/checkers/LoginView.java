package checkers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.scene.text.Font;

/*Extends Pane oznacza. Nasza klasa rozszerza kontener graficzny typu Pane*/
public class LoginView extends Pane  {

	public App app;
	
	/*To jest klas , w kt�rej stowrzony jest widok logowania, to jest koncepcja na baz� danych.
	 * Stary login by� by sprawdzany czy rzeczywi�cie istnieje, a nowy te� by by� sprawdzany czy jest nowy 
	 * i ewentualnie dodawany*/
	
	/*
	 * Ca�y wygl�d ustawiam w konstruktorze.Wszystkie metody i obiekty tutaj chyba nazywaj� si�
	 * tak, �e nie trzeba ich komentowa�*/
	public LoginView() {
	    setPrefSize(400,400);  //rozmiar
	    Label newPlayerLabel= new Label("Stw�rz nowy login.");
	    Label oldPlayerLabel=new Label("Podaj stary login.");
	    TextField newLogin=new TextField();
	    TextField oldLogin=new TextField();
	    Button newLoginButton=new Button();
	    Button oldLoginButton=new Button();
	    /*Ni�ej ustawiam co robi przycisk.
	     * Przycisk tworzy obiekt klasy GameView w kt�rej jest plansza.
	     * W obiekcie app jest referencja to obiektu klasy App, kt�ry utworzy� screena.
	     * Funkcja createBoard zwraca kontener z plansz�.*/
	    newLoginButton.setOnMouseClicked(e->{ 
	    	GameView game=new GameView();
	    	app.changeScene(game.createBoard());
	    });
	    
	    newPlayerLabel.setLayoutX(50);
	    newPlayerLabel.setLayoutY(70);
	    newPlayerLabel.setFont(Font.font(22));
	    oldPlayerLabel.setLayoutX(50);
	    oldPlayerLabel.setLayoutY(200);
	    oldPlayerLabel.setFont(Font.font(22));
	    newLogin.setLayoutX(50);
	    newLogin.setLayoutY(110);
	    oldLogin.setLayoutX(50);
	    oldLogin.setLayoutY(240);
	    newLoginButton.setLayoutX(50);
	    newLoginButton.setLayoutY(140);
	    getChildren().addAll(newPlayerLabel,oldPlayerLabel,newLogin,oldLogin,newLoginButton);
	   /*getChildren dodaje wszyskie elementy panela logowania do kontenera panelu.*/
	}

	
	public void setApp(App app){
		this.app=app; 
	}

}
