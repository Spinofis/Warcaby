
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;


/**
 * 
 * @author Bartek
 *
 *Klasa, która tworzy i obs³uguje po³¹czenie internetowe.
 */
public class TCPConnection implements Runnable {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private String IP = "192.168.43.223";
	private boolean isServer;

	/**
	 * Konstruktor klasy TCPConection.
	 * @param port
	 * @param isServer
	 */
	public TCPConnection(int port, boolean isServer) {
		this.isServer = isServer;
		try {
			if (isServer)
				serverSocket = new ServerSocket(port);
			else
				clientSocket = new Socket(IP, port);

		} catch (IOException e) {
			System.out.println("Blad tworzenia polaczenia!");
		}
	}

	/**
	 * Metoda wysy³a za pomoc¹ protoko³u TCP informacjê o ruchu pionka.
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @param moveType
	 */
	public void send(int oldX, int oldY, int newX, int newY, String moveType) {
		String message = toString(oldX, oldY, newX, newY, moveType);
		PrintStream printStream;
		try {
			printStream = new PrintStream(clientSocket.getOutputStream());
			printStream.println(message);
			Game.decCheckerNumber(moveType);
			Game.changeTurn();
		} catch (Exception e) {
			System.out.println("blad wyslania wiadomosci!");
		}

	}

	/**
	 * Metoda zamyka po³¹czenie TCP
	 */
	public void closeConnection() {
		try {
			if (isServer)
				serverSocket.close();
			else
				clientSocket.close();
			System.out.println("Zamknieto polaczenie internetowe!");
		} catch (Exception e) {
			System.out.println("Blad zakmniecia polaczenia!");
		}
	}
    
	/**
	 * W metodzie run jest zaimplementowany TCP s³uchacz, który 
	 * po otrzymaniu informacji o ruchu pionka nanosi odpowiednie zmieny na planszê i do stanu gry.
	 */
	@Override
	public void run() {

		String moveType;
		try {
			if (isServer)
				clientSocket = serverSocket.accept();
		} catch (Exception e) {
			System.out.println("Blad polaczenia z klientem!");
		}

		while (true) {
			try {
				InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(inputStream);
				String message = null;
				final int oldX, oldY, newX, newY;
				message = bufferedReader.readLine();
				System.out.println("Received:" + message);
				oldX = Integer.parseInt(message.substring(0, 1));
				oldY = Integer.parseInt(message.substring(2, 3));
				newX = Integer.parseInt(message.substring(4, 5));
				newY = Integer.parseInt(message.substring(6, 7));
				moveType = message.substring(8);
				tryMove(oldX, oldY, newX, newY, moveType);
				if (7 - newX == 7)
					GameView.setKing(7 - newX, 7 - newY);
			} catch (IOException e) {
				System.out.println("blad odczytu wiadomosci!");
			}
		}

	}
    /**
     * Metoda zamienia informacjê o koordynatach pionka i typie ruchu na na String.
     * 
     * @param oldX
     * @param oldY
     * @param newX
     * @param newY
     * @param moveType
     * @return
     */
	private static String toString(int oldX, int oldY, int newX, int newY, String moveType) {
		String string = Integer.toString(oldX);
		string += ",";
		string += Integer.toString(oldY);
		string += ",";
		string += Integer.toString(newX);
		string += ",";
		string += Integer.toString(newY);
		string += ",";
		string += moveType;
		return string;
	}
    
	/**
	 * Metoda, która rozpoznaje jaki typ ruchu zosta³ wykonany i w zale¿noœci od tego dokonuje 
	 * odpowiednich kroków.
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @param moveType
	 */
	private void tryMove(int oldX, int oldY, int newX, int newY, String moveType) {
		if (moveType.equals("NORMAL")) {
			// System.out.println(moveType);
			GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			Game.changeTurn();
		} else if (moveType.equals("KILL1")) {
			GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GameView.removeChecker(7 - newX - 1, 7 - newY + 1);
					Game.decCheckerNumber(moveType);
					Game.changeTurn();
				}
			});

		} else if (moveType.equals("KILL2")) {
			GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GameView.removeChecker(7 - newX - 1, 7 - newY - 1);
					Game.decCheckerNumber(moveType);
					Game.changeTurn();
				}
			});

		} else if (moveType.equals("KILL3")) {
			GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GameView.removeChecker(7 - newX + 1, 7 - newY - 1);
					Game.decCheckerNumber(moveType);
					Game.changeTurn();
				}
			});

		} else if (moveType.equals("KILL4")) {
			GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GameView.removeChecker(7 - newX + 1, 7 - newY + 1);
					Game.decCheckerNumber(moveType);
					Game.changeTurn();
				}
			});

		} else
			System.out.println("OTHER");
	}

}
