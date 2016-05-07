package com.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.pantallas.Bienvenida;
import com.pokemon.pantallas.Evolucion;
import com.pokemon.pantallas.Menu;
import com.pokemon.pantallas.PantallaInicio;
import com.pokemon.pantallas.Play;
import com.pokemon.utilidades.ArchivoGuardado;
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
		ArchivoGuardado ctx = new ArchivoGuardado();

		//setScreen(new Salvaje(280,280,3));
		//setScreen(new Play(1200, 2600, 3, "Bosque.tmx"));
		//setScreen(new Play(1200, 1800, 3, "Tranvia_n.tmx"));
		//setScreen(new Play(ctx, 600, 800, 3, "Hall.tmx"));
		//setScreen(new Bienvenida(ctx, this));
		//setScreen(new Play(1200, 500, 3, "Prueba.tmx"));
		//setScreen(new Play(ctx, 2800, 2000, 3, "Cueva.tmx"));
		//setScreen(new Play(ctx, 970, 3000, 3, "Lab1.tmx"));
		//setScreen(new Play(ctx, 700, 2400, 3, "Pasillo.tmx"));
		//setScreen(new Play(ctx, 500, 2500, 3, "Hardware.tmx"));
		//setScreen(new Play(ctx, 150, 150, 3, "GimMena.tmx"));
		//setScreen(new Play(ctx, 300, 2800, 3, "Aulas.tmx"));
		//setScreen(new Play(ctx, 1000, 2800, 3, "Redes.tmx"));
		//setScreen(new Play(ctx, 400, 2900, 3, "Terraza.tmx"));
		//setScreen(new Play(ctx, 500, 500, 3, "Geoslab.tmx"));
		//setScreen(new Play(ctx, 500, 200, 3, "GimGiga.tmx"));
		setScreen(new Play(ctx, 500, 500, 3, "Estudios.tmx"));
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