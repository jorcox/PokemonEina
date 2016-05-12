package pokemon;

import java.sql.SQLException;
import java.util.Random;

import db.BaseDatos;
import habilidad.Habilidad;

public class AparicionPokemon {

	private BaseDatos bd;

	private Pokemon[] aparicion;

	public AparicionPokemon() {
		try {
			bd = new BaseDatos("pokemon_base");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			bd.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pokemon[] setPokemonSalvaje(String mapa) {
		double r;
		Habilidad[] habs;
		mapa = mapa.replace(".tmx", "");
		aparicion = new Pokemon[5];
		if (mapa.equals("Tranvia_n")) {

			aparicion[0] = bd.getPokemonTipo(19);
			habs = setHabilidades(1, 2, 3, 4);
			aparicion[0].setHabilidades(habs);

			aparicion[1] = bd.getPokemonTipo(16);
			habs = setHabilidades(1, 2, 3, 4);
			aparicion[1].setHabilidades(habs);

			aparicion[2] = bd.getPokemonTipo(19);
			habs = setHabilidades(1, 2, 3, 4);
			aparicion[2].setHabilidades(habs);

			aparicion[3] = bd.getPokemonTipo(16);
			habs = setHabilidades(1, 2, 3, 4);
			aparicion[3].setHabilidades(habs);

			aparicion[4] = bd.getPokemonTipo(19);
			habs = setHabilidades(1, 2, 3, 4);
			aparicion[4].setHabilidades(habs);

			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < 4; j++) {
					r = new Random().nextDouble();
					if (r > 0.35) {
						aparicion[i].subirNivel(0, 0);
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Terraza")) {

		} else if (mapa.equals("Sotano")) {

		} else if (mapa.equals("Redes")) {

		} else if (mapa.equals("Pasillo")) {

		} else if (mapa.equals("Liga")) {

		} else if (mapa.equals("Lab1")) {

		} else if (mapa.equals("Hendrix")) {

		} else if (mapa.equals("Hardware")) {

		} else if (mapa.equals("Hall")) {

		} else if (mapa.equals("Giga")) {

		} else if (mapa.equals("Geoslab")) {

		} else if (mapa.equals("Estudios")) {

		} else if (mapa.equals("Cueva")) {

		} else if (mapa.equals("Cafeteria")) {

		} else if (mapa.equals("Bosque")) {

		} else if (mapa.equals("Aulas")) {

		}
		return aparicion;
	}

	public Habilidad[] setHabilidades(int hab0, int hab1, int hab2, int hab3) {
		Habilidad[] habilidades = new Habilidad[4];
		if (hab0 <= 0) {
			habilidades[0] = bd.getHabilidad(hab0);
		}
		if (hab1 <= 0) {
			habilidades[1] = bd.getHabilidad(hab1);
		}
		if (hab2 <= 0) {
			habilidades[2] = bd.getHabilidad(hab2);
		}
		if (hab3 <= 0) {
			habilidades[3] = bd.getHabilidad(hab3);
		}
		return habilidades;
	}
}
