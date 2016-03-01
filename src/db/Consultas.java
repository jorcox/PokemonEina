package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {
	public static void getPokemonTipo(Connection con, String dbName,
			String condition) throws SQLException {

		Statement stmt = null;
		String query = "select * where " + condition + " from " + dbName
				+ ".pokemon_tipo";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				// nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				int ps = rs.getInt("ps");
				int ataque = rs.getInt("ataque");
				int defensa = rs.getInt("defensa");
				int ataque_esp = rs.getInt("ataque_esp");
				int defensa_esp = rs.getInt("defensa_esp");
				int velocidad = rs.getInt("velocidad");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
