package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteUtil {

	public static void main(String[] args) {
		try {
			new SQLiteUtil().performCreateTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void performCreateTable() throws Exception {

		// Loading the SQLite JDBC driver for the current thread
		Class.forName("org.sqlite.JDBC");

		// Handler for the database connection
		Connection connection = null;

		try {
			String script = "CREATE TABLE Pedido ( " 
					+ "    numero INTEGER NOT NULL, " 
					+ "	   cpf VARCHAR(14) NOT NULL, "
					+ "	   address VARCHAR(255) NOT NULL, "
					+ "	   cep VARCHAR(10) NOT NULL, "
					+ "	   valor NUMERIC NOT NULL, " 
					+ "    status INTEGER NOT NULL, " 
					+ "    dataPedido DATE NOT NULL, "
					+ "    dataExpedição DATE NULL, " 
					+ "    dataEntrega DATE NULL, "
					+ "    CONSTRAINT Pedido_pk PRIMARY KEY (numero) " 
					+ " ); ";

			// Opening database connection
			// TODO: cria o arquivo do banco no home do usuário
			connection = DriverManager.getConnection("jdbc:sqlite:/home/renan/delivery.sqlite");
			Statement statement = connection.createStatement();

			// Creating database table
			// statement.executeUpdate("DROP TABLE IF EXISTS product");
			statement.executeUpdate(script);
			System.out.println("Criou o banco sqlite.");

//			 // Adding contents into the database statement
//			 statement.executeUpdate("INSERT INTO pedido VALUES(1, '111.111.111-11', 'Rua X', '37540-000', 540, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
//			 statement.executeUpdate("INSERT INTO pedido VALUES(2, '111.111.111-11', 'Rua X', '37540-000', 540, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
//			 statement.executeUpdate("INSERT INTO pedido VALUES(3, '111.111.111-11', 'Rua X', '37540-000', 540, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
//			
//			 // Querying the contents of the database using native SQLite query
//			 ResultSet results = statement.executeQuery("SELECT * FROM pedido");
//			
//			 System.out.println("List of Orders");
//			
//			 while (results.next()) {
//			 	System.out.println(results.getString("cep") + " (numero=" + results.getInt("numero") + ")");
//			 }
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				// Closing database connection
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

				System.err.println(e);
			}
		}
	}

}
