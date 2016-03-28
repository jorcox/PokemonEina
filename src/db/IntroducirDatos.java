package db;

import java.sql.SQLException;

public class IntroducirDatos {

	BaseDatos db;

	public IntroducirDatos() {
		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void introducirPokemon(int i, int nivel) {
		try {
			db.update("INSERT INTO pokemon(nivel,nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado)"
					+ " VALUES(100, 'Mew', 1000, 1000, 1000, 1000, 1000, 1000,100,25,53,400,0, 0)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void crearPokemon() {
		try {
			db.update("CREATE TABLE pokemon ( id INTEGER IDENTITY,nivel INTEGER, nombre VARCHAR(256), "
					+ "ps INTEGER, ataque INTEGER, defensa INTEGER, ataque_esp INTEGER, "
					+ "defensa_esp INTEGER, velocidad INTEGER, mov1 INTEGER, mov2 INTEGER, mov3 INTEGER, mov4 INTEGER, experiencia INTEGER, estado INTEGER)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			db.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
