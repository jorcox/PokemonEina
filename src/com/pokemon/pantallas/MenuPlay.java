package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.PokemonAdaByron;
import com.pokemon.utilidades.ArchivoGuardado;

public class MenuPlay implements Screen, InputProcessor {

	PokemonAdaByron game;

	Play play;

	Texture t, button, selButton, p, pSel, b, bSel, o, oSel, s, sSel;

	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	SpriteBatch batch;

	Sprite bg, regButton, regButton2, regButton3, regButton4, poke, bag, opt,
			save;

	private boolean archivoGuardado = ArchivoGuardado.existe;

	private int seleccion = 1;

	public MenuPlay(Play play) {
		ArchivoGuardado.musica = null;
		this.play = play;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, 720, 540);
		updateSelection();
		batch.draw(regButton, 100, 350);
		batch.draw(regButton2, 100, 200);
		batch.draw(regButton3, 400, 350);
		batch.draw(regButton4, 400, 200);
		batch.draw(poke, 108, 365);
		batch.draw(bag, 108, 215);
		batch.draw(opt, 408, 365);
		batch.draw(save, 408, 215);
		font.draw(batch, "Pok�mon", 170, 395);
		font.draw(batch, "Bolsa", 170, 245);
		font.draw(batch, "Opciones", 470, 395);
		font.draw(batch, "Guardar", 470, 245);
		batch.end();
	}

	private void updateSelection() {
		resetSelection();
		switch (seleccion) {
		case 1:
			regButton = new Sprite(selButton);
			poke = new Sprite(pSel);
			break;
		case 2:
			regButton2 = new Sprite(selButton);
			bag = new Sprite(bSel);
			break;
		case 3:
			regButton3 = new Sprite(selButton);
			opt = new Sprite(oSel);
			break;
		case 4:
			regButton4 = new Sprite(selButton);
			save = new Sprite(sSel);
			break;

		}

	}

	private void resetSelection() {
		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);
		poke = new Sprite(p);
		bag = new Sprite(b);
		opt = new Sprite(o);
		save = new Sprite(s);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(this);
		bg = new Sprite(new Texture("res/imgs/background.png"));
		button = new Texture("res/imgs/panel.png");
		selButton = new Texture("res/imgs/panel2.png");
		p = new Texture("res/imgs/party.png");
		pSel = new Texture("res/imgs/party2.png");
		b = new Texture("res/imgs/bag.png");
		bSel = new Texture("res/imgs/bag2.png");
		o = new Texture("res/imgs/options.png");
		oSel = new Texture("res/imgs/options.png");
		s = new Texture("res/imgs/save.png");
		sSel = new Texture("res/imgs/save2.png");

		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);
		poke = new Sprite(p);
		bag = new Sprite(b);
		opt = new Sprite(o);
		save = new Sprite(s);
		batch = new SpriteBatch();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case (Keys.UP):
			if (seleccion != 1 && seleccion != 3) {
				seleccion -= 1;
			}
			break;
		case (Keys.DOWN):
			if (seleccion != 2 && seleccion != 4) {
				seleccion += 1;
			}
			break;
		case Keys.LEFT:
			if (seleccion != 1 && seleccion != 2) {
				seleccion -= 2;
			}
			break;
		case Keys.RIGHT:
			if (seleccion != 3 && seleccion != 4) {
				seleccion += 2;
			}
			break;
		case (Keys.ENTER):
			hacerAccion(seleccion);
			break;
		case (Keys.ESCAPE):
			Gdx.app.exit();
			break;
		case Keys.SPACE:
			((Game) Gdx.app.getApplicationListener()).setScreen(play);
			break;
		}
		return true;
	}

	private void hacerAccion(int i) {
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}