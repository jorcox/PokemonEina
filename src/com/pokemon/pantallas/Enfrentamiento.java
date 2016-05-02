package com.pokemon.pantallas;

import java.util.ArrayList;
import java.util.List;

import habilidad.Habilidad;
import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Mochila;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import core.Combate;
import db.BaseDatos;
import entrenadores.Entrenador;
import entrenadores.Jugador;

public class Enfrentamiento extends Pantalla {

	protected int fase;
	protected int vida = 100;
	protected int vidaS = 100;
	protected int actualPs;
	protected int actualPsS;
	protected Pokemon pkmn;
	protected Pokemon pkmnpokemonEnemigo;
	protected int iPokemonEnemigo = 0;
	protected int iPokemon = 0;
	protected int tamanoPokemon = 1;
	protected int xPokemon = 100;
	protected int xPokemonEnemigo = 450;
	protected Habilidad[] habilidades;
	FreeTypeFontGenerator generator;
	protected TweenManager tweenManager;
	protected Combate combate;
	protected Entrenador en;
	protected boolean orden;
	protected float trans = 1;
	protected int intervalo = 4;
	protected int veces = 8;
	protected int seleccion = 1;
	protected int seleccionAtaque = 1;
	protected Dialogo dialogo;
	protected Jugador jugador;
	protected Player player;
	BitmapFont font, fontC;
	BaseDatos db;
	protected Screen screen;

	SpriteBatch batch;
	Texture tipos, barraVida;
	TextureRegion[] regionesTipo, regionesTipoSel, barrasVida;
	protected Sprite bg, base, baseEnemy, message, pokemonEnemigo, pokemon,
			bgOp, bgOpTrans, boton, luchar, mochilaS, pokemonOp, huir, dedo,
			cajaLuchar, tipo1, tipo2, tipo3, tipo4, cajaPkmn,
			cajaPkmnpokemonEnemigo, entrenador, protagonista;

	public Enfrentamiento(ArchivoGuardado ctx, Player player, Jugador jugador, Screen screen) {
		this.setCtx(ctx);
		dialogo = new Dialogo("es", "ES");
		this.jugador = jugador;
		this.player = player;
		this.screen = screen;
		try {
			db = new BaseDatos("pokemon_base");

			pkmnpokemonEnemigo = db.getPokemon(3);
			db.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Pokemon> lPoke = new ArrayList<Pokemon>();
		actualPsS = pkmnpokemonEnemigo.getPs();
		combate = new Combate(jugador, pkmnpokemonEnemigo);
		orden = combate.getVelocidad(iPokemon);
		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		FreeTypeFontParameter parameterC = new FreeTypeFontParameter();
		parameterC.size = 30;
		font = generator.generateFont(parameter); // font size 35 pixels
		fontC = generator.generateFont(parameterC);

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		mochilaS = new Sprite(new Texture("res/imgs/batallas/mochila.png"));
		pokemonOp = new Sprite(new Texture("res/imgs/batallas/pokemon.png"));
		huir = new Sprite(new Texture("res/imgs/batallas/huir.png"));
		message = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		pokemonEnemigo = new Sprite(new Texture("res/imgs/pokemon/"
				+ pkmnpokemonEnemigo.getNombre().toLowerCase() + ".png"));
		pokemon = new Sprite(new Texture("res/imgs/pokemon/espalda/"
				+ jugador.getEquipo().get(iPokemon).getNombre().toLowerCase()
				+ ".png"));
		cajaLuchar = new Sprite(
				new Texture("res/imgs/batallas/battleFight.png"));
		tipos = new Texture("res/imgs/batallas/battleFightButtons.png");
		barraVida = new Texture("res/imgs/batallas/hpbars.png");
		cajaPkmn = new Sprite(new Texture("res/imgs/batallas/cajaPkmn.png"));
		cajaPkmnpokemonEnemigo = new Sprite(new Texture(
				"res/imgs/batallas/cajaPkmnEnemigo.png"));
		font.setColor(Color.BLACK);
		fontC.setColor(Color.BLACK);
		regionesTipos();
		regionesTiposSel();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

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
			mochilaS.setPosition(230, 40);
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
		mochilaS.setPosition(230, 30);
		mochilaS.setSize(120, 50);
		pokemonOp.setPosition(380, 30);
		pokemonOp.setSize(120, 50);
		huir.setPosition(530, 30);
		huir.setSize(120, 50);
	}

	/**
	 * 
	 */
	public void regionesTipos() {
		habilidades = jugador.getEquipo().get(iPokemon).getHabilidades();
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
		mochilaS.draw(batch);
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
		cajaPkmnpokemonEnemigo.setPosition(0, 400);
		cajaPkmnpokemonEnemigo.setSize(250, 70);
		cajaPkmn.draw(batch);
		cajaPkmnpokemonEnemigo.draw(batch);
		/*
		 * Atributos de nuestro pokemon
		 */
		fontC.draw(batch, pkmn.getNombre(), 480, 235);
		fontC.draw(batch, "Nv " + pkmn.getNivel(), 620, 235);
		fontC.draw(batch, pkmn.getPs() + "/" + pkmn.getPsMax(), 595, 195);
		getBarraVida();

		/*
		 * Atributos del pokemon pokemonEnemigo
		 */
		fontC.draw(batch, pkmnpokemonEnemigo.getNombre(), 20, 450);
		fontC.draw(batch, "Nv " + pkmnpokemonEnemigo.getNivel(), 150, 450);

	}

	public void dibujarVida(boolean who) {
		if (who) {
			batch.draw(barrasVida[0], 582, 202,
					(int) (116 * (pkmn.getPs() / (double) pkmn.getPsMax())), 10);
		} else {
			batch.draw(barrasVida[0], 111, 416, (int) (96 * (pkmnpokemonEnemigo
					.getPs() / (double) pkmnpokemonEnemigo.getPsMax())), 10);
		}
	}

	public void dibujarVidas() {
		dibujarVida(true);
		dibujarVida(false);
	}

	public void animacionVida(boolean who) {

		if (who) {
			pokemon.setAlpha(1);
			double diff = (actualPsS - pkmnpokemonEnemigo.getPs()) / 100.0;
			if (vidaS > 0) {
				batch.draw(
						barrasVida[0],
						111,
						416,
						(int) (96 * ((pkmnpokemonEnemigo.getPs() / (double) pkmnpokemonEnemigo
								.getPsMax()) + ((diff * vidaS) / (double) pkmnpokemonEnemigo
								.getPsMax()))), 10);
				vidaS--;
			} else {
				dibujarVida(false);
			}
		} else {
			pokemonEnemigo.setAlpha(1);
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
			dialogo.setFrases(frasesAtaque(pkmn, seleccionAtaque));
			String l1 = dialogo.siguienteLinea();
			String l2 = dialogo.siguienteLinea();
			dialogo.setLineas(l1, l2);
			fase++;
		} else {
			int seleccionEnemigo = combate.decidir(pkmnpokemonEnemigo);

			dialogo.setFrases(frasesAtaque(pkmnpokemonEnemigo, seleccionEnemigo));
			String l1 = dialogo.siguienteLinea();
			String l2 = dialogo.siguienteLinea();
			dialogo.setLineas(l1, l2);
			fase++;
		}

	}

	public void combate() {
		if ((orden && fase == 5) || (!orden && fase == 7)) {

			if (!dialogo.isWriting()) {
				actualPsS = pkmnpokemonEnemigo.getPs();
				if (combate.ejecutar(pkmn, pkmnpokemonEnemigo,
						pkmn.getHabilidad(seleccionAtaque))) {
					fase++;
				} else {
					// Ataque fallido
					fase = 10;
					String[] frase = {
							"¡" + jugador.getEquipo().get(iPokemon).getNombre()
									+ " falló! Vaya mierdas...", "" };
					dialogo.setFrases(frase);
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					dialogo.setLineas(l1, l2);
				}
			}
		} else {
			int seleccionEnemigo = combate.decidir(pkmnpokemonEnemigo);

			if (!dialogo.isWriting()) {
				actualPs = pkmn.getPs();
				if (combate.ejecutar(pkmnpokemonEnemigo, pkmn,
						pkmnpokemonEnemigo.getHabilidad(seleccionEnemigo))) {
					fase++;
				} else {
					fase = 11;
					String[] frase = {
							"¡" + pkmnpokemonEnemigo.getNombre()
									+ " falló! Vaya mierdas...", "" };
					dialogo.setFrases(frase);
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					dialogo.setLineas(l1, l2);
				}
			}
		}

	}

	public String[] frasesAtaque(Pokemon pokemon, int id) {
		String[] frase = {
				"¡" + pokemon.getNombre() + " uso "
						+ pokemon.getHabilidad(id).getNombre() + "!", "" };
		return frase;
	}

	public void aparicionPokemon(Sprite pokemon) {
		pokemon.setSize(tamanoPokemon, tamanoPokemon);
		pokemon.setPosition(xPokemon, 99);
		if (tamanoPokemon < 180) {
			tamanoPokemon = tamanoPokemon + 8;
		}
		if (xPokemon > 50) {
			xPokemon = xPokemon - 3;
		}

	}

	public void aparicionPokemonEnemigo(Sprite pokemon) {
		pokemon.setSize(tamanoPokemon, tamanoPokemon);
		pokemon.setPosition(xPokemonEnemigo, 350);
		if (tamanoPokemon < 180) {
			tamanoPokemon = tamanoPokemon + 8;
		}
		if (xPokemonEnemigo > 400) {
			xPokemonEnemigo = xPokemonEnemigo - 3;
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
				trans = 1;
				pokemon.setAlpha(1);
			}
		} else {
			if (veces > 0) {
				if (intervalo == 0) {
					pokemonEnemigo.setAlpha(trans);
					trans = (trans + 1) % 2;
					intervalo = 4;
					veces--;
				} else {
					intervalo--;
				}
			} else {
				trans = 1;
				pokemonEnemigo.setAlpha(1);
			}
		}
	}

	public void elegirOpcion() {
		switch (seleccion) {
		case 1: // luchar
			fase = 4;
			break;
		case 2: // mochila
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new MenuMochila(getCtx(), this));
			break;
		case 3: // pokemon
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new MenuPokemon(jugador.getEquipo(), this, true));
			break;
		case 4: // huir
			((Game) Gdx.app.getApplicationListener())
			.setScreen(screen);
			break;
		default:
			break;
		}
	}

	public void setIPokemon(int i) {
		pkmn = jugador.getPokemon(i);
		iPokemon = i;
	}

}
