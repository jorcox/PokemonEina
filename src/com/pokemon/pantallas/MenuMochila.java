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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;

public class MenuMochila implements Screen, InputProcessor {

	private static final int MAX_ITEMS = 6;
	
	private Mochila mochila;
	private MenuPlay menuPlay;
	private int first;
	private int pointer;
	
	private SpriteBatch batch;
	private Texture tFondo, tSelected;
	private Sprite fondo;
	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);
	
	public MenuMochila(Mochila mochila, MenuPlay menuPlay) {
		this.mochila = mochila;
		this.menuPlay = menuPlay;
		first = 0;
		pointer = 0;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tFondo = new Texture("res/imgs/mochila/bag_objetos.png");
		tSelected = new Texture("res/imgs/mochila/bag_selected.png");
		fondo = new Sprite(tFondo);
		
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(tFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		drawItems();
		drawSelection();
		batch.end();
	}

	private void drawSelection() {
		batch.draw(tSelected, 280, Gdx.graphics.getHeight() - (80+50*pointer));
	}

	/**
	 * Renderiza hasta 5 elementos de la mochila, empezando por aquel que se
	 * esta recorriendo actualmente.
	 */
	private void drawItems() {
		/* MaxItems es el maximo de objetos a renderizar */
		int maxItems = (mochila.getObjetos().size() < MAX_ITEMS) ? 
				mochila.getObjetos().size() : MAX_ITEMS;
		font.setColor(Color.BLACK);
		
		/* Dibuja el texto de cada objeto que quepa */
		int pos = 50;
		for (int i=first; i<first + maxItems; i++) {
			font.draw(batch, mochila.getObjetos().get(i).getNombre(),
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
			((Game) Gdx.app.getApplicationListener()).setScreen(menuPlay);
			break;
		case Keys.ENTER:
			break;
		case Keys.DOWN:
			if (pointer < MAX_ITEMS -1) {
				pointer++;
			} else {
				first++;
			}
			
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
