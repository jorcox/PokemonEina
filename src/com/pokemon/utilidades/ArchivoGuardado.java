package com.pokemon.utilidades;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.PokemonAdaByron;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.MO;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;
import com.pokemon.mochila.Pokeball;
import com.pokemon.mochila.Superball;

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
	
	private int teclaUp;
	private int teclaDown;
	private int teclaLeft;
	private int teclaRight;
	private int teclaA;
	private int teclaB;
	
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
		setDefaultKeys();
		
		// Objetos de prueba metidos a pelo
		mochila.add(new Pocion());
		mochila.add(new Antidoto());
		mochila.add(new Pokeball());
		mochila.add(new Superball());
		mochila.add(new Pokeball());
		//mochila.add(new MO("Surf"));
	}
	
	private void setDefaultKeys() {
		teclaUp = Keys.UP;
		teclaDown = Keys.DOWN;
		teclaLeft = Keys.LEFT;
		teclaRight = Keys.RIGHT;
		teclaA = Keys.ENTER;
		teclaB = Keys.SPACE;
	}
	
	public int getTeclaUp() {
		return teclaUp;
	}

	public void setTeclaUp(int teclaUp) {
		this.teclaUp = teclaUp;
	}

	public int getTeclaDown() {
		return teclaDown;
	}

	public void setTeclaDown(int teclaDown) {
		this.teclaDown = teclaDown;
	}

	public int getTeclaLeft() {
		return teclaLeft;
	}

	public void setTeclaLeft(int teclaLeft) {
		this.teclaLeft = teclaLeft;
	}

	public int getTeclaRight() {
		return teclaRight;
	}

	public void setTeclaRight(int teclaRight) {
		this.teclaRight = teclaRight;
	}

	public int getTeclaA() {
		return teclaA;
	}

	public void setTeclaA(int teclaA) {
		this.teclaA = teclaA;
	}

	public int getTeclaB() {
		return teclaB;
	}

	public void setTeclaB(int teclaB) {
		this.teclaB = teclaB;
	}

}