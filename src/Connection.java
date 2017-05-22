
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends App implements Runnable {

	private int port;
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private String IP="10.0.3.216";

	public Connection(int port) {
		this.port = port;
		try {
			if (isServer)
				serverSocket = new ServerSocket(port);
			else
				clientSocket = new Socket(IP, port);

		} catch (IOException e) {
			System.out.println("Blad tworzenia polaczenia!");
		}
	}

	public void send(int oldX, int oldY, int newX, int newY) {
		String message = toString(oldX, oldY, newX, newY);
		PrintStream printStream;
		try {
			printStream = new PrintStream(clientSocket.getOutputStream());
			printStream.println(message);
		} catch (Exception e) {
			System.out.println("blad wyslania wiadomosci!");
		}

	}

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

	@Override
	public void run() {

		int oldX, oldY, newX, newY;

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
				message = bufferedReader.readLine();
				System.out.println("Received:" + message);
				oldX = Integer.parseInt(message.substring(0, 1));
				oldY = Integer.parseInt(message.substring(2, 3));
				newX = Integer.parseInt(message.substring(4, 5));
				newY = Integer.parseInt(message.substring(6, 7));
				GameView.changeChecker(7 - oldX, 7 - oldY, 7 - newX, 7 - newY, true);
			} catch (IOException e) {
				System.out.println("blad odczytu wiadomosci!");
			}
		}

	}

	private static String toString(int oldX, int oldY, int newX, int newY) {
		String string = Integer.toString(oldX);
		string += ",";
		string += Integer.toString(oldY);
		string += ",";
		string += Integer.toString(newX);
		string += ",";
		string += Integer.toString(newY);
		return string;
	}
	

}
