package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.mochila.Mochila;

public class MenuMochila implements Screen, InputProcessor {

	private static final int MAX_ITEMS = 6;	/* Items que caben a la vez en pantalla */
	private static final int NUM_SECCIONES = 3; /* Secciones de la mochila */
	
	private Mochila mochila;
	private Screen screen;
	private int first;	/* Indice del primer elemento de la lista en mostrarse */
	private int pointer;	/* Indice del seleccionado de los MAX_ITEMS que caben */
	private int seccion;
	
	private SpriteBatch batch;
	private Texture tFondoObj, tFondoBalls, tFondoMOs, tSelected;
	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);
	
	public MenuMochila(Mochila mochila, Screen screen) {
		this.mochila = mochila;
		this.screen = screen;
		
		/* Empieza mostrando primera seccion (objeto) marcando primer objeto */
		seccion = 0;
		first = 0;
		pointer = 0;
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tFondoObj = new Texture("res/imgs/mochila/bag_objetos.png");
		tFondoBalls = new Texture("res/imgs/mochila/bag_pokeball.png");
		tFondoMOs = new Texture("res/imgs/mochila/bag_mo.png");
		tSelected = new Texture("res/imgs/mochila/bag_selected.png");
		
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		/* La imagen de fondo ocupa toda la pantalla */
		if (seccion == 0) {
			batch.draw(tFondoObj, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de Objetos", 150, 100);
		} else if (seccion == 1) {
			batch.draw(tFondoBalls, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de Pokeballs", 150, 100);
		} else if (seccion == 2) {
			batch.draw(tFondoMOs, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de MO", 150, 100);
		}
		font.draw(batch, "Pulsa IZDA o DCHA para cambiar de bolsillo", 150, 50);
		
		drawItems();
		drawSelection();
		batch.end();
	}

	private void drawSelection() {
		if (mochila.size(seccion) > 0) {
			batch.draw(tSelected, 280, Gdx.graphics.getHeight() - (80+50*pointer));
		} else {
			font.draw(batch, "No hay items", 300, Gdx.graphics.getHeight() - 200);
		}
	}

	/**
	 * Renderiza hasta 5 elementos de la mochila, empezando por aquel que se
	 * esta recorriendo actualmente.
	 */
	private void drawItems() {
		/* MaxItems es el maximo de objetos a renderizar */
		int maxItems = (mochila.size(seccion) < MAX_ITEMS) ? 
				mochila.size(seccion) : MAX_ITEMS;
		font.setColor(Color.BLACK);
		
		/* Dibuja el texto de cada objeto que quepa */
		int pos = 50;
		for (int i=first; i<first + maxItems; i++) {
			font.draw(batch, mochila.get(seccion, i).getNombre(),
					300, Gdx.graphics.getHeight() - pos);
			pos += 50;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			/* Vuelve al menu */
			((Game) Gdx.app.getApplicationListener()).setScreen(screen);
			break;
		case Keys.ENTER:
			break;
		case Keys.DOWN:
			/* Desciende en la lista de la mochila */
			if (pointer < MAX_ITEMS -1 && pointer < mochila.size(seccion) -1) {
				/* Baja el puntero si quedan posiciones por bajar */
				pointer++;
			} else if ((first+MAX_ITEMS-1) < mochila.size(seccion) -1) {
				/* Mueve la lista abajo si el puntero esta ya abajo */
				first++;
			}
			break;
		case Keys.UP:
			/* Asciende en la lista de la mochila */
			if (pointer > 0) {
				/* Si el puntero no esta arriba, se sube */
				pointer--;
			} else if (first > 0){
				/* Si el puntero ya esta arriba, se sube la lista */
				first--;
			}
			break;
		case Keys.RIGHT:
			/* Pasa a la siguiente lista de la mochila */
			seccion = (seccion + 1) % NUM_SECCIONES;
			first = 0;
			pointer = 0;
			break;
		case Keys.LEFT:
			/* Pasa a la anterior lista de la mochila */
			seccion = (seccion - 1) % NUM_SECCIONES;
			if (seccion < 0) {
				seccion += NUM_SECCIONES;
			}
			first = 0;
			pointer = 0;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
