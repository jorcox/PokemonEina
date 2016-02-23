package db;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class LeerDatos {

	BaseDatos db;

	public LeerDatos() {
		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Introduce los pokemon con sus estadisticas base en la base de datos
	 */
	public void introducirPokemon() {
		try {
			db.update("CREATE TABLE pokemon_tipo ( id INTEGER IDENTITY, nombre VARCHAR(256), "
					+ "ps INTEGER, ataque INTEGER, defensa INTEGER, ataque_esp INTEGER, "
					+ "defensa_esp INTEGER, velocidad INTEGER)");
			db.update("INSERT INTO pokemon_tipo(nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad)"
					+ " VALUES('Sara', 1000, 1000, 1000, 1000, 1000, 1000)");
			Scanner linea = new Scanner(new File("pokemon.txt"));
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			while (linea.hasNextLine()) {
				/*
				 * 1 - Nombre 2 - PS 3 - Ataque 4 - Defensa 5 - Ataque esp. 6 -
				 * Defensa esp. 7 - Velocidad
				 */
				int i = linea.nextInt();
				String st = "INSERT INTO pokemon_tipo(nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad)"
						+ " VALUES('"
						+ linea.next()
						+ "', "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", " + linea.nextInt() + ")";
				db.update(st);
				linea.nextLine();

			}
			linea.close();
			db.query("SELECT * FROM pokemon_tipo WHERE id < 151");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void introducirMovimientos() {
		try {
			db.update("CREATE TABLE movimientos ( id INTEGER IDENTITY, name VARCHAR(256),nombre VARCHAR(256), "
					+ "poder INTEGER, tipo_mov VARCHAR(256), tipo VARCHAR(256), "
					+ "precision INTEGER, pp INTEGER, descripcion VARCHAR(256))");
			Scanner linea = new Scanner(new File("moves.txt"),"UTF-8");
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			while (linea.hasNextLine()) {
				/*
				 * 1 - name 2 - nombre 3 - poder 4 - tipo_mov 5 - tipo 6 -
				 * precision 7 - pp 8 - descripcion
				 */
				String a=linea.next();
				String name=linea.next();
				String nombre=linea.next();
				linea.next();
				int poder=linea.nextInt();
				String tipo_mov=linea.next();
				String tipo=linea.next();
				int precision=linea.nextInt();
				int pp=linea.nextInt();
				linea.next();linea.next();linea.next();linea.next();
				String descripcion=linea.next();
				String st = "INSERT INTO movimientos(name,nombre,poder,tipo_mov,tipo,precision,pp,descripcion)"
						+ " VALUES('"
						+ name
						+ "', '"
						+ nombre
						+ "', "
						+ poder
						+ ", '"
						+ tipo_mov
						+ "', '"
						+ tipo
						+ "', "
						+ precision
						+ ", " + pp 
						+ ", '" + descripcion 
						+ "')";
				db.update(st);
				linea.nextLine();
			}
			linea.close();
			db.query("SELECT * FROM pokemon_tipo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public void query(String query) {
		try {
			db.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}