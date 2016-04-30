package com.pokemon.pantallas;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pokemon.Pokemon;

public class MenuPokemon implements Screen, InputProcessor {

	private MenuPlay menuPlay;
	private Pokemon[] listaPokemon;
	
	private SpriteBatch batch;
	private Texture tFondo, tMainVivo, tMainVivoSel, tMainMuerto, tMainMuertoSel,
			tVivo, tVivoSel, tMuerto, tMuertoSel, tHpVivo, tHpMuerto;
	
	public MenuPokemon(Pokemon[] listaPokemon, MenuPlay menuPlay) {
		this.menuPlay = menuPlay;
		this.listaPokemon = listaPokemon;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
		batch = new SpriteBatch();
		tFondo = new Texture("res/imgs/listaPokemon/fondo.png");
		tMainVivo = new Texture("res/imgs/listaPokemon/partyPanelRound.png");
		tMainVivoSel = new Texture("res/imgs/listaPokemon/partyPanelRoundSel.png");
		tMainMuerto = new Texture("res/imgs/listaPokemon/partyPanelRoundFnt.png");
		tMainMuertoSel = new Texture("res/imgs/listaPokemon/partyPanelRoundSelFnt.png");
		tVivo = new Texture("res/imgs/listaPokemon/partyPanelRect.png");
		tVivoSel = new Texture("res/imgs/listaPokemon/partyPanelRectSel.png");
		tMuerto = new Texture("res/imgs/listaPokemon/partyPanelRectFnt.png");
		tMuertoSel = new Texture("res/imgs/listaPokemon/partyPanelRectSelFnt.png");
		tHpVivo = new Texture("res/imgs/listaPokemon/partyHP.png");
		tHpMuerto = new Texture("res/imgs/listaPokemon/partyHPfnt.png");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(tFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (listaPokemon.length > 0) {
			/* El primer slot de pokemon es distinto (fondo redondo) */
			drawMainPokemon();
		}
		for (int i=1; i<listaPokemon.length; i++) {
			/* Pinta el resto de slots de pokemon */
			//drawPokemon(i);
		}
		batch.end();
	}

	private void drawMainPokemon() {
		Pokemon pokemon = listaPokemon[0];
		if (pokemon.vivo()) {
			batch.draw(tMainVivo, 5, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpVivo, 130, 450, 200, tHpVivo.getHeight()*2);
		} else {
			batch.draw(tMainMuerto, 5, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpVivo, 130, 450, 200, tHpVivo.getHeight()*2);
		}
	}
	
	private void drawPokemon(int i) {
		Pokemon pokemon = listaPokemon[i];
		if (pokemon.vivo()) {
			batch.draw(tVivo, 5, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpVivo, 130, 380);
		} else {
			batch.draw(tMuerto, 5, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpMuerto, 130, 380);
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
			((Game) Gdx.app.getApplicationListener()).setScreen(menuPlay);
			break;
		case Keys.ENTER:
			break;
		case Keys.DOWN:
			break;
		case Keys.UP:
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
