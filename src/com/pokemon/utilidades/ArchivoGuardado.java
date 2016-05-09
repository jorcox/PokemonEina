package com.pokemon.utilidades;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.PokemonAdaByron;
import com.pokemon.dialogo.Dialogo;
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
	
	/* 
	 * Vector de tecla asignada a cada accion.
	 * 0: UP. 1: DOWN. 2: LEFT. 3: RIGHT. 4: A. 5: B.
	 */
	private List<Integer> teclas;
	
	public Dialogo dialogo;
	
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
		teclas = new ArrayList<>(6);
		setDefaultKeys();
		dialogo = new Dialogo("es", "ES");
		
		// Objetos de prueba metidos a pelo
		mochila.add(new Pocion());
		mochila.add(new Antidoto());
		mochila.add(new Pokeball());
		mochila.add(new Superball());
		mochila.add(new Pokeball());
		mochila.add(new MO("Surf"));
	}
	
	private void setDefaultKeys() {
		teclas.add(Keys.UP);
		teclas.add(Keys.DOWN);
		teclas.add(Keys.LEFT);
		teclas.add(Keys.RIGHT);
		teclas.add(Keys.ENTER);
		teclas.add(Keys.SPACE);
	}
	
	public int getTeclaUp() {
		return teclas.get(Tecla.UP.ordinal());
	}

	public void setTeclaUp(int teclaUp) {
		setAndSwap(teclaUp, Tecla.UP.ordinal());
	}

	public int getTeclaDown() {
		return teclas.get(Tecla.DOWN.ordinal());
	}

	public void setTeclaDown(int teclaDown) {
		setAndSwap(teclaDown, Tecla.DOWN.ordinal());
	}

	public int getTeclaLeft() {
		return teclas.get(Tecla.LEFT.ordinal());
	}

	public void setTeclaLeft(int teclaLeft) {
		setAndSwap(teclaLeft, Tecla.LEFT.ordinal());
	}

	public int getTeclaRight() {
		return teclas.get(Tecla.RIGHT.ordinal());
	}

	public void setTeclaRight(int teclaRight) {
		setAndSwap(teclaRight, Tecla.RIGHT.ordinal());
	}

	public int getTeclaA() {
		return teclas.get(Tecla.A.ordinal());
	}

	public void setTeclaA(int teclaA) {
		setAndSwap(teclaA, Tecla.A.ordinal());
	}

	public int getTeclaB() {
		return teclas.get(Tecla.B.ordinal());
	}

	public void setTeclaB(int teclaB) {
		setAndSwap(teclaB, Tecla.B.ordinal());
	}
	
	/**
	 * Antes de asignar una tecla, comprueba si no se ha asignado ya.
	 * En ese caso, se intercambian las teclas en conflicto.
	 * @param value tecla a asignar.
	 * @param pos accion (posicion de vector teclas) asignada.
	 */
	private void setAndSwap(int value, int pos) {
		if (teclas.contains(value)) {
			/* Swap es necesario, esa tecla ya esta asociada */
			int index = teclas.indexOf(value);
			int aux = teclas.get(pos);
			teclas.set(index, aux);
		}
		/* Asigna la tecla a la accion indicada */
		teclas.set(pos, value);
	}
	
	/* En teclas se almacenan en este orden */
	public enum Tecla { UP, DOWN, LEFT, RIGHT, A, B }

}