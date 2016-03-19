package com.pokemon.utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Importa el archivo de guardado 
 * 
 */
public class Importador {

	public static void impt() throws Exception {

		File f = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron/jugador.sav");
		FileInputStream fstream = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fstream));

		String strLine;
		while ((strLine = br.readLine()) != null) {
			// Gdx.app.log(ChromeGame.LOG, strLine);
			String[] hold = strLine.split(":");
			switch (hold[0]) {
				case "NAME":
					ArchivoGuardado.nombreJugador = hold[1];
					break;
				case "MONEY":
					ArchivoGuardado.dineroJugador = Float.parseFloat(hold[1]);
					break;
				case "POS":
					String[] pos = hold[1].split(",");
					ArchivoGuardado.posicionJugador = new Vector2(
							Float.parseFloat(pos[0]),
							Float.parseFloat(pos[1]));
					break;
				case "MAP":
					ArchivoGuardado.mapaActual = hold[1];
					break;
				case "GENDER":
					ArchivoGuardado.generoJugador = hold[1];
					break;
				case "MUSIC":
					ArchivoGuardado.nombreMusica = hold[1];
					ArchivoGuardado.musica = Gdx.audio
							.newMusic(Gdx.files.internal("music/"
									+ hold[1] + ".mp3"));
					break;
			}
		}
		br.close();
	}
}