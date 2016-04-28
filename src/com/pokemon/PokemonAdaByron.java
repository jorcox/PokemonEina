package com.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.pantallas.Bienvenida;
import com.pokemon.pantallas.Menu;
import com.pokemon.pantallas.PantallaInicio;
import com.pokemon.pantallas.Play;
import com.pokemon.pantallas.CombateP;

import entrenadores.Entrenador;

public class PokemonAdaByron extends Game {
	
	/*
	 * Log
	 */
	public static final String LOG = "Pokemon Ada Byron"; 
	
	public int WIDTH = 1000;
	
	public int HEIGHT = 700;
	
	private SpriteBatch batch;
	private Texture img;

	@Override
	public void create() {
		//setScreen(new Salvaje(280,280,3));
		//setScreen(new Play(1200, 2600, 3, "Bosque.tmx"));
		//setScreen(new Play(1200, 1800, 3, "Tranvia_n.tmx"));
		setScreen(new Play(100, 100, 3, "Hall.tmx"));
		//setScreen(new Bienvenida(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}