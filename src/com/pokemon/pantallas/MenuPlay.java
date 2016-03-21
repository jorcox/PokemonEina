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

	Texture t, button, conButton;

	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/font/pokemon.fnt"),
			Gdx.files.internal("res/font/pokemon.png"), false);

	Texture selButton;

	SpriteBatch batch;

	Sprite bg, regButton, regButton2, regButton3, regButton4, regButton5;

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
		batch.draw(bg,0,0,720,540);
		//bg.draw(batch);
		updateSelection();

		regButton.draw(batch);
		regButton2.draw(batch);
		regButton3.draw(batch);
		regButton4.draw(batch);
		font.draw(batch, "Pokémon", 150, 275);
		font.draw(batch, "Bolsa", 150, 175);
		font.draw(batch, "Opciones", 250, 275);
		font.draw(batch, "Guardar", 250, 175);
		batch.end();

	}

	private void updateSelection() {
		resetSelection();
		switch (seleccion) {
		case 1:
			regButton = new Sprite(selButton);
			regButton.setX(150);
			regButton.setY(275);
			break;
		case 2:
			regButton2 = new Sprite(selButton);
			regButton2.setX(150);
			regButton2.setY(175);
			break;
		case 3:
			regButton3 = new Sprite(selButton);
			regButton3.setX(250);
			regButton3.setY(275);
			break;
		case 4:
			regButton4 = new Sprite(selButton);
			regButton4.setX(250);
			regButton4.setY(175);
			break;

		}

	}

	private void resetSelection() {
		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);

		regButton.setX(150);
		regButton.setY(275);

		regButton2.setX(150);
		regButton2.setY(175);

		regButton3.setX(250);
		regButton3.setY(275);

		regButton4.setX(250);
		regButton4.setY(175);
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

		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);

		regButton.setX(150);
		regButton.setY(275);

		regButton2.setX(150);
		regButton2.setY(175);

		regButton3.setX(250);
		regButton3.setY(275);

		regButton4.setX(150);
		regButton4.setY(175);

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
			if (seleccion != 1) {
				seleccion -= 1;
			}
			break;
		case (Keys.DOWN):
			if (seleccion != 4) {
				seleccion += 1;
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