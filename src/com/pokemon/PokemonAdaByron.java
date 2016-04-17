package com.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.pantallas.Menu;
import com.pokemon.pantallas.PantallaInicio;
import com.pokemon.pantallas.Play;
import com.pokemon.pantallas.Salvaje;

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
		setScreen(new Play(280, 280, 3));
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