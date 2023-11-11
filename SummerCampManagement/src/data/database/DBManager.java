package data.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Clase para realizar las conexiones a base de datos
 */

public class DBManager {
	private String dbUrl;
	private String username;
	private String password;
	Map<String, String> queries;
	protected Connection connection = null;
	private static DBManager instance;

	/**
	 * Este método se utiliza para obtener la instancia de DBManager.
	 * 
	 * @return DBManager La instancia de DBManager.
	 */
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/**
	 * Este es el constructor de la clase DBManager.
	 */
	private DBManager() {
		this.queries = new HashMap<String, String>();
		Properties prop = new Properties();
		String filename = "config.properties";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			prop.load(reader);

			this.dbUrl = prop.getProperty("DB_URL");
			this.username = prop.getProperty("USERNAME");
			this.password = prop.getProperty("PASSWORD");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		filename = "sql.properties";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			prop.load(reader);

			for (Object key : prop.keySet()) {
				this.queries.put((String) key, prop.getProperty((String) key));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Este método se utiliza para obtener una consulta SQL a partir de una clave.
	 * 
	 * @param queryKey La clave de la consulta SQL.
	 * @return String La consulta SQL.
	 */
	public String getQuery(String queryKey) {
		return this.queries.get(queryKey);
	}

	/**
	 * Este método se utiliza para obtener una conexión a la base de datos.
	 * 
	 * @return Connection La conexión a la base de datos.
	 */
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(this.dbUrl, this.username, this.password);
		} catch (SQLException e) {
			System.err.println("Connection to MySQL has failed!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found.");
			e.printStackTrace();
		}
		return this.connection;
	}

	/**
	 * Este método se utiliza para cerrar la conexión a la base de datos.
	 */
	public void closeConnection() {
		try {
			if (this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}