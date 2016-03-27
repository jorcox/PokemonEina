package com.pokemon.pantallas;

import habilidad.Habilidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import core.Combate;
import db.BaseDatos;
import entrenadores.Entrenador;

public class Salvaje extends Dialogo implements Screen, InputProcessor {

	private float x;
	private float y;
	private float lastPressed;
	private int fase = 1;
	private double alfa = 0;
	private Pokemon pkmn;
	private Habilidad[] habilidades;
	FreeTypeFontGenerator generator;
	private TweenManager tweenManager;
	private Combate combate;
	private Entrenador e;
	private int seleccion = 1;
	BitmapFont font;
	BaseDatos db;

	SpriteBatch batch;
	Texture tipos;
	TextureRegion[] regionesTipo;
	Sprite bg, base, baseEnemy, message, salvaje, pokemon, bgOp, bgOpTrans,
			boton, luchar, mochila, pokemonOp, huir, dedo, cajaLuchar, tipo1,
			tipo2, tipo3, tipo4;

	public Salvaje(float x, float y, float lastPressed) {
		super("es", "ES");
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;

		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
		pkmn = db.getPokemon(0);

		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter); // font size 35 pixels
		frases = getDialogo("salvaje");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.BLACK);
		tweenManager.update(delta);

		batch.begin();

		bg.draw(batch);

		bg.setSize(720, 540);
		base.draw(batch);
		baseEnemy.draw(batch);
		message.draw(batch);
		message.setSize(720, 120);
		salvaje.draw(batch);
		salvaje.setSize(120, 120);
		font.draw(batch, lineaUno, 50, 85);
		font.draw(batch, lineaDos, 50, 35);
		if (fase == 2) {
			pokemon.setSize(180, 180);
			pokemon.setPosition(50, 99);
			pokemon.draw(batch);
		}
		if (fase == 3) {
			pokemon.draw(batch);
			updateSelection();
			luchar.draw(batch);
			mochila.draw(batch);
			pokemonOp.draw(batch);
			huir.draw(batch);
			dedo.draw(batch);
		}
		if (fase == 4) {
			pokemon.draw(batch);
			cajaLuchar.setSize(720, 120);
			cajaLuchar.draw(batch);
			dibujarTipos();

		}
		batch.end();

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		batch = new SpriteBatch();

		bg = new Sprite(new Texture("res/imgs/batallas/battlebgForestEve.png"));
		base = new Sprite(new Texture(
				"res/imgs/batallas/playerbaseForestGrassEve.png"));
		baseEnemy = new Sprite(new Texture(
				"res/imgs/batallas/enemybaseFieldGrassEve.png"));
		bgOp = new Sprite(new Texture("res/imgs/batallas/fondoOpciones.png"));
		bgOpTrans = new Sprite(new Texture("res/imgs/batallas/bgOpTrans.png"));
		dedo = new Sprite(new Texture("res/imgs/batallas/dedo.png"));
		luchar = new Sprite(new Texture("res/imgs/batallas/luchar.png"));
		mochila = new Sprite(new Texture("res/imgs/batallas/mochila.png"));
		pokemonOp = new Sprite(new Texture("res/imgs/batallas/pokemon.png"));
		huir = new Sprite(new Texture("res/imgs/batallas/huir.png"));
		message = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		salvaje = new Sprite(new Texture("res/imgs/pokemon/151.png"));
		pokemon = new Sprite(new Texture("res/imgs/pokemon/espalda/25.png"));
		cajaLuchar = new Sprite(
				new Texture("res/imgs/batallas/battleFight.png"));
		tipos = new Texture("res/imgs/batallas/battleFightButtons.png");
		tipo1 = new Sprite(tipos);
		regionesTipos();
		Tween.set(bg, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(bg, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
		Tween.set(base, SpriteAccessor.SLIDE).target(500, 120)
				.start(tweenManager);
		Tween.to(base, SpriteAccessor.SLIDE, 2).target(-70, 120)
				.start(tweenManager);
		Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250, 300)
				.start(tweenManager);
		Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350, 300)
				.start(tweenManager);
		Tween.set(salvaje, SpriteAccessor.SLIDE).target(-250, 350)
				.start(tweenManager);
		Tween.to(salvaje, SpriteAccessor.SLIDE, 2).target(400, 350)
				.start(tweenManager);
		font.setColor(Color.BLACK);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!writing) {
			switch (keycode) {
			case (Keys.ENTER):

				if (fase == 1 || fase == 2) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = siguienteLinea();
					String l2 = siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (id.equals("salvaje")) {
							if (l1.contains("${SALVAJE}")) {
								l1 = l1.replace("${SALVAJE}", "Mew");
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", "Pikachu");
								fase++;
								frases = getDialogo("combate");
							}
						} else if (id.equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", "Pikachu");
							}
							if (l1.equals(" ")) {
								fase++;
							}
						}

						/* Escribe letra a letra el dialogo */
						setLineas(l1, l2);
						/*
						 * if (script[counter].contains("(OPTION)")) {
						 * script[counter] = script[counter].replace(
						 * "(OPTION)", ""); optionsVisible = true; }
						 */
					}
				} else if (fase == 3) {
					switch (seleccion) {
					case 1: // luchar
						fase = 4;
						break;
					case 2: // mochila
						break;
					case 3: // pokemon
						break;
					case 4: // huir
						break;
					default:
						break;
					}
				}
				break;
			case Keys.LEFT:
				if (seleccion != 1) {
					seleccion -= 1;
				}
				break;
			case Keys.RIGHT:
				if (seleccion != 4) {
					seleccion += 1;
				}
				break;
			}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	private void updateSelection() {
		resetSelection();
		switch (seleccion) {
		case 1:
			dedo.setPosition(115, 120);
			font.draw(batch, "Luchar", 100, 35);
			luchar.setPosition(80, 40);
			break;
		case 2:
			dedo.setPosition(265, 120);
			font.draw(batch, "Mochila", 250, 35);
			mochila.setPosition(230, 40);
			break;
		case 3:
			dedo.setPosition(415, 120);
			font.draw(batch, "Pokemon", 400, 35);
			pokemonOp.setPosition(380, 40);
			break;
		case 4:
			dedo.setPosition(565, 120);
			font.draw(batch, "Huir", 570, 35);
			huir.setPosition(530, 40);
			break;

		}

	}

	private void resetSelection() {
		luchar.setPosition(80, 30);
		luchar.setSize(120, 50);
		mochila.setPosition(230, 30);
		mochila.setSize(120, 50);
		pokemonOp.setPosition(380, 30);
		pokemonOp.setSize(120, 50);
		huir.setPosition(530, 30);
		huir.setSize(120, 50);
	}

	public void regionesTipos() {
		habilidades = pkmn.getHabilidades();
		regionesTipo = new TextureRegion[4];
		for (int i = 0; i < habilidades.length; i++) {
			if (habilidades[i] != null) {
				regionesTipo[i] = new TextureRegion(tipos, 0,
						45 * habilidades[i].getIndiceTextura(), 192, 47);
			} else {
				regionesTipo[i] = null;
			}
		}
	}

	public void dibujarTipos() {
		
		batch.draw(regionesTipo[0], 8, 62, 265, 52);
		font.draw(batch, habilidades[0].getNombre(), 60, 90);
		if (regionesTipo[1] != null) {
			batch.draw(regionesTipo[1], 272, 62, 266, 52);
			font.draw(batch, habilidades[1].getNombre(), 325, 90);
		}
		if (regionesTipo[2] != null) {
			batch.draw(regionesTipo[2], 8, 10, 265, 52);
			font.draw(batch, habilidades[2].getNombre(), 60, 40);
		}
		if (regionesTipo[3] != null) {
			batch.draw(regionesTipo[3], 272, 10, 266, 52);
			font.draw(batch, habilidades[3].getNombre(), 325, 40);

		}
	}
}
