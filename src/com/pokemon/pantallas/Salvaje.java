package com.pokemon.pantallas;

import habilidad.Habilidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import core.Combate;
import db.BaseDatos;
import entrenadores.Entrenador;
import entrenadores.Jugador;

public class Salvaje extends Dialogo implements Screen, InputProcessor {

	private float x;
	private float y;
	private float lastPressed;
	private int fase = 1;
	private int vida = 100;
	private int vidaS = 100;
	private int actualPs;
	private int actualPsS;
	private Pokemon pkmn, pkmnSalvaje;
	private int tamanoPokemon = 1;
	private int xPokemon = 100;
	private Habilidad[] habilidades;
	FreeTypeFontGenerator generator;
	private TweenManager tweenManager;
	private Combate combate;
	private Entrenador en;
	private boolean orden;
	private float trans = 1;
	private int intervalo = 4;
	private int veces = 8;
	private int seleccion = 1;
	private int seleccionAtaque = 1;
	BitmapFont font, fontC;
	BaseDatos db;

	SpriteBatch batch;
	Texture tipos, barraVida;
	TextureRegion[] regionesTipo, regionesTipoSel, barrasVida;
	Sprite bg, base, baseEnemy, message, salvaje, pokemon, bgOp, bgOpTrans,
			boton, luchar, mochila, pokemonOp, huir, dedo, cajaLuchar, tipo1,
			tipo2, tipo3, tipo4, cajaPkmn, cajaPkmnSalvaje;

	public Salvaje(float x, float y, float lastPressed) {
		super("es", "ES");
		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
		pkmn = db.getPokemon(1);
		List<Pokemon> lPoke = new ArrayList<Pokemon>();
		lPoke.add(pkmn);
		pkmnSalvaje = db.getPokemon(0);
		actualPs = pkmn.getPs();
		actualPsS = pkmnSalvaje.getPs();
		en = new Jugador("Sara", true);
		en.setEquipo(lPoke);
		combate = new Combate(en, pkmnSalvaje);
		orden = combate.getVelocidad();
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;
		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		FreeTypeFontParameter parameterC = new FreeTypeFontParameter();
		parameterC.size = 30;
		font = generator.generateFont(parameter); // font size 35 pixels
		fontC = generator.generateFont(parameterC);
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
		/*
		 * Aparacion de pokemon salvaje
		 */
		if (fase == 2) {

			aparicionPokemon();
			pokemon.draw(batch);
		}
		/*
		 * Decidir accion (Luchar, Mochila, Pokemon, Huir)
		 */
		if (fase == 3) {
			pokemon.draw(batch);
			dibujarMenuCombate();
			dibujarCajasVida();
			dibujarVidas();
		}
		/*
		 * Decisión de ataque
		 */
		if (fase == 4) {
			pokemon.draw(batch);
			cajaLuchar.setSize(720, 120);
			cajaLuchar.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			updateSeleccionAtaque();
			vida = 100;
			vidaS = 100;
		}
		if (fase == 5 || fase == 7) {
			/*
			 * Dialogo Ataque
			 */
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
		}
		if (fase == 6 || fase == 8) {
			/*
			 * Ataque, vida y comprobación
			 */
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			if ((orden && fase == 6) || (!orden && fase == 8)) {
				ataqueRecibido(true);
				animacionVida(true);
				dibujarVida(true);
			} else {
				ataqueRecibido(false);
				animacionVida(false);
				dibujarVida(false);
			}
		}
		if (fase == 9) {
			/*
			 * Dialogo muerte o fase = 3
			 */
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			if (pkmnSalvaje.getPs() <= 0) {
				salvaje.setAlpha(0);
			} else if (pkmn.getPs() <= 0) {
				pokemon.setAlpha(0);
			}
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
		barraVida = new Texture("res/imgs/batallas/hpbars.png");
		cajaPkmn = new Sprite(new Texture("res/imgs/batallas/cajaPkmn.png"));
		cajaPkmnSalvaje = new Sprite(new Texture(
				"res/imgs/batallas/cajaPkmnSalvaje.png"));

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
		fontC.setColor(Color.BLACK);
		regionesTipos();
		regionesTiposSel();
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
								l1 = l1.replace("${SALVAJE}",
										pkmnSalvaje.getNombre());
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
								fase++;
								frases = getDialogo("combate");
							}
						} else if (id.equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							}
							if (l1.equals(" ")) {
								fase++;
							}
						}
						/* Escribe letra a letra el dialogo */
						setLineas(l1, l2);
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
				} else if (fase == 4) {
					/*
					 * Primer ataque
					 */
					fraseAtaque();
				} else if (fase == 5) {
					combate();
				} else if (fase == 6) {
					veces=8;
					if (pkmn.getPs() <= 0 || pkmnSalvaje.getPs() <= 0) {
						fase = 9;
						frases = getDialogo("pokemon_muerto");
					} else {
						fraseAtaque();
					}
				} else if (fase == 7) {
					combate();
				} else if (fase == 8) {
					veces=8;
					if (pkmn.getPs() <= 0 || pkmnSalvaje.getPs() <= 0) {
						fase = 9;
						frases = getDialogo("pokemon_muerto");
					} else {
						fase = 3;
						seleccionAtaque = 1;
						lineaUno = "";
						lineaDos = "";
					}
				} else if (fase == 9) {
					String l1 = siguienteLinea();
					String l2 = siguienteLinea();
					if (l1.contains("${POKEMON}")) {
						if (pkmn.getPs() <= 0) {
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						} else {
							l1 = l1.replace("${POKEMON}",
									pkmnSalvaje.getNombre());
						}
					}
					setLineas(l1, l2);

				}
				break;
			case Keys.LEFT:
				if (fase == 3) {
					if (seleccion != 1) {
						seleccion -= 1;
					}
				} else if (fase == 4) {
					if (seleccionAtaque != 1 && seleccionAtaque != 3) {
						seleccionAtaque -= 1;
					}
				}

				break;
			case Keys.RIGHT:
				if (fase == 3) {
					if (seleccion != 4) {
						seleccion += 1;
					}
				} else if (fase == 4) {
					if (seleccionAtaque != 2 && seleccionAtaque != 4) {
						seleccionAtaque += 1;
					}
				}
				break;
			case Keys.UP:
				if (fase == 4) {
					if (seleccionAtaque != 1 && seleccionAtaque != 2) {
						seleccionAtaque -= 2;
					}
				}
				break;
			case Keys.DOWN:
				if (fase == 4) {
					if (seleccionAtaque != 3 && seleccionAtaque != 4) {
						seleccionAtaque += 2;
					}
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

	/**
	 * 
	 */
	public void regionesTipos() {
		habilidades = pkmn.getHabilidades();
		regionesTipo = new TextureRegion[4];
		for (int i = 0; i < habilidades.length; i++) {
			if (habilidades[i] != null) {
				regionesTipo[i] = new TextureRegion(tipos, 0,
						46 * habilidades[i].getIndiceTextura(), 192, 47);
			} else {
				regionesTipo[i] = null;
			}
		}
	}

	/**
	 * 
	 */
	public void regionesTiposSel() {
		habilidades = pkmn.getHabilidades();
		regionesTipoSel = new TextureRegion[4];
		for (int i = 0; i < habilidades.length; i++) {
			if (habilidades[i] != null) {
				regionesTipoSel[i] = new TextureRegion(tipos, 192,
						46 * habilidades[i].getIndiceTextura(), 192, 47);
			} else {
				regionesTipoSel[i] = null;
			}
		}
	}

	/**
	 * 
	 */
	public void dibujarMenuCombate() {
		updateSelection();
		luchar.draw(batch);
		mochila.draw(batch);
		pokemonOp.draw(batch);
		huir.draw(batch);
		dedo.draw(batch);
	}

	/**
	 * 
	 */
	public void updateSeleccionAtaque() {
		dibujarTipos();
		switch (seleccionAtaque) {
		case 1:
			batch.draw(regionesTipoSel[0], 8, 58, 265, 52);
			font.draw(batch, habilidades[0].getNombre(), 60, 90);

			break;
		case 2:
			batch.draw(regionesTipoSel[1], 272, 58, 266, 52);
			font.draw(batch, habilidades[1].getNombre(), 325, 90);
			break;
		case 3:
			batch.draw(regionesTipoSel[2], 8, 6, 265, 52);
			font.draw(batch, habilidades[2].getNombre(), 60, 40);
			break;
		case 4:
			batch.draw(regionesTipoSel[3], 272, 6, 266, 52);
			font.draw(batch, habilidades[3].getNombre(), 325, 40);
			break;

		}
		font.draw(batch, "PP " + habilidades[seleccionAtaque - 1].getPp(), 600,
				85);
		fontC.draw(batch, "TIPO/" + habilidades[seleccionAtaque - 1].getTipo(),
				570, 50);

	}

	/**
	 * 
	 */
	public void dibujarTipos() {

		batch.draw(regionesTipo[0], 8, 58, 265, 52);
		font.draw(batch, habilidades[0].getNombre(), 60, 90);
		if (regionesTipo[1] != null) {
			batch.draw(regionesTipo[1], 272, 58, 266, 52);
			font.draw(batch, habilidades[1].getNombre(), 325, 90);
		}
		if (regionesTipo[2] != null) {
			batch.draw(regionesTipo[2], 8, 6, 265, 52);
			font.draw(batch, habilidades[2].getNombre(), 60, 40);
		}
		if (regionesTipo[3] != null) {
			batch.draw(regionesTipo[3], 272, 6, 266, 52);
			font.draw(batch, habilidades[3].getNombre(), 325, 40);

		}
	}

	/**
	 * 
	 */
	public void dibujarCajasVida() {
		cajaPkmn.setPosition(425, 160);
		cajaPkmn.setSize(305, 95);
		cajaPkmnSalvaje.setPosition(0, 400);
		cajaPkmnSalvaje.setSize(250, 70);
		cajaPkmn.draw(batch);
		cajaPkmnSalvaje.draw(batch);
		/*
		 * Atributos de nuestro pokemon
		 */
		fontC.draw(batch, pkmn.getNombre(), 480, 235);
		fontC.draw(batch, "Nv " + pkmn.getNivel(), 620, 235);
		fontC.draw(batch, pkmn.getPs() + "/" + pkmn.getPsMax(), 595, 195);
		getBarraVida();

		/*
		 * Atributos del pokemon salvaje
		 */
		fontC.draw(batch, pkmnSalvaje.getNombre(), 20, 450);
		fontC.draw(batch, "Nv " + pkmnSalvaje.getNivel(), 150, 450);

	}

	public void dibujarVida(boolean who) {
		if (who) {
			batch.draw(barrasVida[0], 582, 202,
					(int) (116 * (pkmn.getPs() / (double) pkmn.getPsMax())), 10);
		} else {
			batch.draw(barrasVida[0], 111, 416, (int) (96 * (pkmnSalvaje
					.getPs() / (double) pkmnSalvaje.getPsMax())), 10);
		}
	}

	public void dibujarVidas() {
		dibujarVida(true);
		dibujarVida(false);
	}

	public void animacionVida(boolean who) {

		if (who) {
			double diff = (actualPsS - pkmnSalvaje.getPs()) / 100.0;
			if (vidaS > 0) {
				batch.draw(
						barrasVida[0],
						111,
						416,
						(int) (96 * ((pkmnSalvaje.getPs() / (double) pkmnSalvaje
								.getPsMax()) + ((diff * vidaS) / (double) pkmnSalvaje
								.getPsMax()))), 10);
				vidaS--;
			} else {
				dibujarVida(false);
			}
		} else {
			double diff = (actualPs - pkmn.getPs()) / 100.0;

			if (vida > 0) {
				batch.draw(
						barrasVida[0],
						582,
						202,
						(int) (116 * ((pkmn.getPs() / (double) pkmn.getPsMax()) + ((diff * vida) / (double) pkmn
								.getPsMax()))), 10);
				vida--;
			} else {
				dibujarVida(true);
			}
		}
	}

	public void getBarraVida() {
		barrasVida = new TextureRegion[3];
		barrasVida[0] = new TextureRegion(barraVida, 0, 0, 180, 18);
		barrasVida[1] = new TextureRegion(barraVida, 0, 20, 180, 18);
		barrasVida[2] = new TextureRegion(barraVida, 0, 36, 180, 18);
	}

	public void fraseAtaque() {
		if ((orden && fase == 4) || (!orden && fase == 6)) {
			frases = frasesAtaque(pkmn, seleccionAtaque);
			indiceActual = 0;
			String l1 = siguienteLinea();
			String l2 = siguienteLinea();
			setLineas(l1, l2);
			fase++;
		} else {
			int seleccionEnemigo = combate.decidir(pkmnSalvaje);

			frases = frasesAtaque(pkmnSalvaje, seleccionEnemigo);
			indiceActual = 0;
			String l1 = siguienteLinea();
			String l2 = siguienteLinea();
			setLineas(l1, l2);
			fase++;
		}

	}

	public void combate() {
		if ((orden && fase == 5) || (!orden && fase == 7)) {

			if (!writing) {
				actualPsS = pkmnSalvaje.getPs();
				combate.ejecutar(pkmn, pkmnSalvaje,
						pkmn.getHabilidad(seleccionAtaque));

				fase++;
			}

		} else {
			int seleccionEnemigo = combate.decidir(pkmnSalvaje);

			if (!writing) {
				actualPs = pkmn.getPs();
				combate.ejecutar(pkmnSalvaje, pkmn,
						pkmnSalvaje.getHabilidad(seleccionEnemigo));

				fase++;
			}

		}

	}

	public String[] frasesAtaque(Pokemon pokemon, int id) {
		String[] frase = {
				"¡" + pokemon.getNombre() + " uso "
						+ pokemon.getHabilidad(id).getNombre() + "!", "" };
		return frase;
	}

	public void aparicionPokemon() {
		pokemon.setSize(tamanoPokemon, tamanoPokemon);
		pokemon.setPosition(xPokemon, 99);
		if (tamanoPokemon < 180) {
			tamanoPokemon = tamanoPokemon + 8;
		}
		if (xPokemon > 50) {
			xPokemon = xPokemon - 3;
		}

	}

	public void ataqueRecibido(boolean who) {
		if (!who) {
			if (veces > 0) {
				if (intervalo == 0) {
					pokemon.setAlpha(trans);
					trans = (trans + 1) % 2;
					intervalo = 4;
					veces--;
				} else {
					intervalo--;
				}
			} else {
				pokemon.setAlpha(1);
			}
		} else {
			if (veces > 0) {
				if (intervalo == 0) {
					salvaje.setAlpha(trans);
					trans = (trans + 1) % 2;
					intervalo = 4;
					veces--;
				} else {
					intervalo--;
				}
			} else {
				salvaje.setAlpha(1);
			}
		}
	}
}
