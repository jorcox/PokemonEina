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

	private Mochila mochila;
	private MenuPlay menuPlay;
	
	private SpriteBatch batch;
	private Texture tFondo;
	private Sprite fondo;
	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);
	
	public MenuMochila(Mochila mochila, MenuPlay menuPlay) {
		this.mochila = mochila;
		this.menuPlay = menuPlay;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tFondo = new Texture("res/imgs/mochila/bag_objetos.png");
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
		batch.end();
	}

	private void drawItems() {
		font.setColor(Color.BLACK);
		for (int i=0; i<mochila.getObjetos().size(); i++) {
			font.draw(batch, mochila.getObjetos().get(i).getNombre(),
					300, Gdx.graphics.getHeight() - (50+50*i));
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
