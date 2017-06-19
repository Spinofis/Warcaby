
import java.sql.*;

/**
 * 
 * @author Bartek
 *
 *Klasa, która odpowiada za po³¹czenie siê za baz¹ danych.
 *Odczyt i zapis danych do bazy.
 */
class DataBase {
	public DataBase() {
	}

	/**
	 * Funkcja, która ³¹czy siê z baz¹ danych i wyci¹ga z bazy danych has³o dla odpowiedniego loginu.
	 * @param String url ci¹g po³¹czenia z baz¹ 
	 * @param String login login u¿ytkownika w bazie
 	 * @param String parametr parametr, który ma zostaæ wyszukany w bazie danych 
	 * @return
	 */
	public String readData(String url, String login, String parametr) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			System.out.println("connected");

			Statement statement = conn.createStatement();
			String queryString = "select " + parametr + " from [Checker].[dbo].[User] where Login=" + login;

			ResultSet rs = statement.executeQuery(queryString);
			String result = null;
			while (rs.next()) {
				result = rs.getString(1);
			}
			conn.close();

			return result;
		} catch (Exception e) {
			return "Error";
		}
	}

	
	/**
	 * Funkcja, która ³¹czy siê z baz¹ danych i zapisuje w niej dane logowania u¿ytkownika
	 * @param url ci¹g po³¹czenia z baz¹ 
	 * @param login login u¿ytkownika w bazie danych
	 * @param password has³o uzytkownika w bazie danych
	 */
	public void writeUser(String url, String login, String password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			System.out.println("connected");

			Statement statement = conn.createStatement();
			String queryString = "insert into [Checker].[dbo].[User](Login,Password,Beatens,Losts"
					+ ",Wins,Fails) values('" + login + "','" + password + "',0,0,0,0)";

			ResultSet rs = statement.executeQuery(queryString);

			conn.close();

		} catch (Exception e) {

		}
	}

};