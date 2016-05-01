package com.pokemon.utilidades;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.PokemonAdaByron;
import com.pokemon.mochila.Mochila;

/**
 * Contiene los datos de la partida del jugador
 */
public class ArchivoGuardado {

	/*
	 * Coordenadas del jugador en 2D
	 */
	public static Vector2 posicionJugador;

	/*
	 * Indica la musica que estaba sonando
	 */
	public static Music musica;

	/*
	 * El nombre de la pieza de musica que estaba sonando
	 */
	public static String nombreMusica;

	/*
	 * Mapa en el que estaba el jugador
	 */
	public static String mapaActual;

	/*
	 * Indica si el archivo existia antes de jugar
	 */
	public static Boolean existe = false;

	/*
	 * Genero del jugador
	 */
	public static String generoJugador;

	/*
	 * Nombre del jugador
	 */
	public static String nombreJugador;

	/*
	 * Dinero actual del jugador
	 */
	public static float dineroJugador;
	
	/*
	 * Mochila del jugador
	 */
	public Mochila mochila;
	
	/*
	 * Pantalla de retorno
	 */
	public static Screen pantallaRetorno;

	public static void cargar() {
		comprobarExistencia();
	}

	private static void comprobarExistencia() {
		File f = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron/jugador.sav");
		if (f.exists()) {
			// Gdx.app.log(ChromeGame.LOG, "Exists");
			existe = true;
			try {
				Importador.impt();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Gdx.app.log(PokemonAdaByron.LOG, "No existe archivo de guardafo");
			File carpeta = new File(System.getenv("APPDATA")
					+ "//.pokemonAdaByron");
			carpeta.mkdir();
			existe = false;
		}
	}
	
	public ArchivoGuardado() {
		mochila = new Mochila();
	}

}