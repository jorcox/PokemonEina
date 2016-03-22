package com.pokemon.utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Guarda el juego
 * 
 */
public class Guardador {

	// NAME:Ash
	// MONEY:999999
	// POS:10,10
	// MAP:Exitium
	// GENDER:Male
	// MUSIC:NimbasaCity
	private static String NAME = ArchivoGuardado.nombreJugador;

	private static String MONEY = ArchivoGuardado.dineroJugador + "";

	private static String POS = ArchivoGuardado.posicionJugador.x + ","
			+ ArchivoGuardado.posicionJugador.y;

	private static String MAP = ArchivoGuardado.mapaActual;

	private static String GENDER = ArchivoGuardado.nombreJugador;

	private static String MUSIC = ArchivoGuardado.nombreMusica;

	public static void create() {
		
	}

	public static void guardar() {
		int counter = 0;
		PrintWriter out = null; // variable representing the PrintWriter
		String line = ""; // variable to read each line from the data file
		// Open the data file
		File f = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron/jugador.sav");
		try {
			// in general we have substituted the word Writer for Reader
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					f, false)), false);
			// add ,true parameter to the PrintWriter to FLUSH Buffer with every
			// write line
			// add ,true parameter to the FileWriter for opening as APPEND
			System.out.println("Guardando");
		} catch (IOException e) {
			System.out.println("Problema al guardar");
		}
		while (counter < 6) {
			line = write(counter, line);
			out.println(line); // outputs the contents of the variable 'line' to
								// the
								// file
			counter++;
		}
		out.close(); // Close the data file
		System.out.println("Guardado");
	}

	private static String write(int c, String l) {
		switch (c) {
			case 0:
				return "NAME:" + NAME;
			case 1:
				return "MONEY:" + MONEY;
			case 2:
				return "POS:" + POS;
			case 3:
				return "MAP:" + MAP;
			case 4:
				return "GENDER:" + GENDER;
			case 5:
				return "MUSIC:" + MUSIC;
		}
		return null;

	}
}