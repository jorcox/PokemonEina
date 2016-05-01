package com.pokemon.pantallas;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import pokemon.Pokemon;

public class MenuPokemon implements Screen, InputProcessor {

	private Screen screen;
	private List<Pokemon> listaPokemon;

	private SpriteBatch batch;
	private Texture tFondo, tMainVivo, tMainVivoSel, tMainMuerto,
			tMainMuertoSel, tVivo, tVivoSel, tMuerto, tMuertoSel, tHpVivo,
			tHpMuerto;
	ArrayList<Sprite> spPokemon = new ArrayList<Sprite>();
	FreeTypeFontGenerator generator;
	BitmapFont font;

	public MenuPokemon(List<Pokemon> listaPokemon, Screen screen) {
		this.screen = screen;
		this.listaPokemon = listaPokemon;
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		font = generator.generateFont(parameter); // font size 35 pixels
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
		tFondo = new Texture("res/imgs/listaPokemon/fondo.png");
		tMainVivo = new Texture("res/imgs/listaPokemon/partyPanelRound.png");
		tMainVivoSel = new Texture(
				"res/imgs/listaPokemon/partyPanelRoundSel.png");
		tMainMuerto = new Texture(
				"res/imgs/listaPokemon/partyPanelRoundFnt.png");
		tMainMuertoSel = new Texture(
				"res/imgs/listaPokemon/partyPanelRoundSelFnt.png");
		tVivo = new Texture("res/imgs/listaPokemon/partyPanelRect.png");
		tVivoSel = new Texture("res/imgs/listaPokemon/partyPanelRectSel.png");
		tMuerto = new Texture("res/imgs/listaPokemon/partyPanelRectFnt.png");
		tMuertoSel = new Texture(
				"res/imgs/listaPokemon/partyPanelRectSelFnt.png");
		tHpVivo = new Texture("res/imgs/listaPokemon/partyHP.png");
		tHpMuerto = new Texture("res/imgs/listaPokemon/partyHPfnt.png");
		texturesPokemon();
		font.setColor(Color.WHITE);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(tFondo, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		if (listaPokemon.size() > 0) {
			/* El primer slot de pokemon es distinto (fondo redondo) */
			drawMainPokemon();
		}
		for (int i = 1; i < listaPokemon.size(); i++) {
			/* Pinta el resto de slots de pokemon */
			drawPokemon(i);
		}
		batch.end();
	}

	private void texturesPokemon() {
		for (int i = 0; i < listaPokemon.size(); i++) {
			spPokemon.add(new Sprite(new Texture("res/imgs/pokemon/"
					+ listaPokemon.get(i).getNombre().toLowerCase() + ".png")));
		}
	}

	private void drawMainPokemon() {
		Pokemon pokemon = listaPokemon.get(0);
		if (pokemon.vivo()) {
			batch.draw(tMainVivo, 3, 400, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpVivo, 120, 450, 180, tHpVivo.getHeight());

		} else {
			batch.draw(tMainMuerto, 3, 400, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / (float) 3.5);
			batch.draw(tHpVivo, 120, 450, 180, tHpVivo.getHeight());
		}
		font.draw(batch, pokemon.getNombre().toUpperCase(), 160, 500);
		font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), 190, 440);
		font.draw(batch, "Nvº " + pokemon.getNivel(), 60, 440);
		batch.draw(spPokemon.get(0), 60, 460, 60, 60);
	}

	private void drawPokemon(int i) {
		int x, y, xHp, yHp, xName, yName, xPs, yPs, xNv, yNv, xPok, yPok;
		if (i % 2 == 0) {
			x = 1;
			y = 268 - 135 * (int) (i / 4);
			xHp = 150;
			yHp = 313 - 135 * (int) (i / 4);
			xName = 160;
			yName = 360 - 135 * (int) (i / 4);
			xPs = 190;
			yPs = 300 - 135 * (int) (i / 4);
			xNv = 60;
			yNv = 300 - 135 * (int) (i / 4);
			xPok = 60;
			yPok = 320 - 135 * (int) (i / 4);
		} else {
			x = 360;
			y = 380 - 135 * (int) (i / 2);
			xHp = 500;
			yHp = 425 - 135 * (int) (i / 2);
			xName = 510;
			yName = 475 - 135 * (int) (i / 2);
			xPs = 540;
			yPs = 415 - 135 * (int) (i / 2);
			xNv = 410;
			yNv = 415 - 135 * (int) (i / 2);
			xPok = 420;
			yPok=440- 135 * (int) (i / 2);
		}
		Pokemon pokemon = listaPokemon.get(i);
		if (pokemon.vivo()) {
			batch.draw(tVivo, x, y, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / (float) 4);
			batch.draw(tHpVivo, xHp, yHp);
			font.draw(batch, pokemon.getNombre().toUpperCase(), xName, yName);
			font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), xPs,
					yPs);
			font.draw(batch, "Nvº " + pokemon.getNivel(), xNv, yNv);
			batch.draw(spPokemon.get(i), xPok, yPok, 60, 60);
		} else {
			batch.draw(tMuerto, x, y, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / (float) 4);
			batch.draw(tHpMuerto, xHp, yHp);
			font.draw(batch, pokemon.getNombre().toUpperCase(), xName, yName);
			font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), xPs,
					yPs);
			font.draw(batch, "Nvº " + pokemon.getNivel(), xNv, yNv);
			batch.draw(spPokemon.get(i), xPok, yPok, 60, 60);
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
