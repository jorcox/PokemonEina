package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.pokemon.PokemonAdaByron;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.utilidades.ArchivoGuardado;
import com.pokemon.utilidades.Importador;

public class Bienvenida implements Screen, InputProcessor {

	PokemonAdaByron game;

	Dialogo dialogo;

	String[] frases;

	private int counter = 0;

	private int len;

	private Sprite optionBox;

	BitmapFont font;

	SpriteBatch batch;

	private String lineaUno = "";

	private String lineaDos = "";
	
	private boolean writing = false;

	ArchivoGuardado guardado;

	String playerGender = "male";

	Sprite bg, box, prof;
	
	FreeTypeFontGenerator generator;

	private boolean optionsBoxVisible = false;

	// private boolean profVisible = true;
	private Music m = Gdx.audio.newMusic(Gdx.files
			.internal("res/music/ProfessorIntro.mp3"));

	private int optionsScreen = 1;

	private boolean optionsVisible = false;

	public Bienvenida(PokemonAdaByron game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		m.play();
		m.setLooping(true);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/font/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter); // font size 35 pixels
		procesarDialogo();
	}

	private void procesarDialogo() {
		dialogo = new Dialogo("es", "ES");
		len = 0;
		frases = dialogo.getDialogo("jamarro_intro");
	}

	public String siguienteLinea() {		
		return dialogo.getSiguienteLinea(frases);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		font.setColor(Color.BLACK);
		batch.begin();
		bg.draw(batch);
		if (optionsBoxVisible) {
			box.draw(batch);
		}
		prof.draw(batch);
		prof.setSize(169, (float) 417.5);
		prof.setX(540);
		prof.setY(55);
		font.draw(batch, lineaUno, 50, 125);
		font.draw(batch, lineaDos, 50, 75);

		if (optionsVisible) {
			optionBox.draw(batch);
			font.draw(batch, "Yes", 60, 255);
			font.draw(batch, "No", 60, 215);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		optionBox = new Sprite(new Texture("res/imgs/OptionBox.png"));
		optionBox.setPosition(25, 150);

		bg = new Sprite(new Texture("res/imgs/WelcomeBG.png"));
		box = new Sprite(new Texture("res/imgs/OptionBox.png"));
		prof = new Sprite(new Texture("res/imgs/jamarro.png"));
		prof.setX(500);

		batch = new SpriteBatch();
		font.setColor(Color.BLACK);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
	
	/**
	 * Muestra las dos lineas por pantalla letra a letra. Escribe una letra
	 * cada 0,05s.
	 * 
	 * @param l1
	 * @param l2
	 */
	private void setLineas(String l1, String l2) {
		/* Antes hay que borrarlas */
		lineaUno = "";
		lineaDos = "";
		
		Timer.schedule(new Task(){
				int i = 1;
				int j = 1;
				public void run() {
					writing = true;
					
					if (i < l1.length()) {
						lineaUno = l1.substring(0, i++);
					} else if (j < l2.length()) {
						lineaDos = l2.substring(0, j++);
					} else {
						writing = false;
					}
				}
			}, 0, (float) 0.05, l1.length() + l2.length() + 1);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!writing) {
			switch (keycode) {
			case (Keys.ENTER):
				String l1 = siguienteLinea();
				String l2 = siguienteLinea();
				
				if (l1 != null) {
					if (l2 == null) {
						l2 = "";
					}
					optionsVisible = false;

					if (l1.contains("${NOMBRE}")) {
						l1 = l1.replace("${NOMBRE}",
								ArchivoGuardado.nombreJugador);
					} else if (l2.contains("${NOMBRE}")) {
						l2 = l2.replace("${NOMBRE}",
								ArchivoGuardado.nombreJugador);
					} else if (l1.contains("${CREACION_NOMBRE}")
							|| l2.contains("${CREACION_NOMBRE}")) {
						l1 = l1.replace("${CREACION_NOMBRE}", "");
						l2 = l2.replace("${CREACION_NOMBRE}", "");
						ArchivoGuardado.nombreJugador = "Antonio";
					}

					/* Escribe letra a letra el dialogo */
					setLineas(l1, l2);
					
					/*
					 * if (script[counter].contains("(OPTION)")) {
					 * script[counter] = script[counter].replace( "(OPTION)",
					 * ""); optionsVisible = true; }
					 */
				} else {
					m.stop();
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Play());
				}
				break;
			case (Keys.UP):
				if (optionsScreen == 1) {

				}
				break;
			case (Keys.DOWN):
				if (optionsScreen == 1) {

				}
				break;
			}
		}

		return false;
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
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer,
			int button) {

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