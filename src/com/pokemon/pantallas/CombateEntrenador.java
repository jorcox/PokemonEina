package com.pokemon.pantallas;

import java.util.ArrayList;

import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pokemon.entities.Player;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import db.BaseDatos;
import entrenadores.Entrenador;
import entrenadores.Jugador;

public class CombateEntrenador extends Enfrentamiento {

	private String idEntrenador;
	private Jugador entrenadorE;

	TextureRegion[] spritesEntrenador;

	public CombateEntrenador(ArchivoGuardado ctx, Player player,
			Jugador jugador, String idEntrenador, Screen screen) {
		super(ctx, player, jugador, screen);
		this.fase = 0;
		this.idEntrenador = idEntrenador;
		pkmn = jugador.getPokemon(iPokemon);
		/*
		 * Base de datos?
		 */
		setEntrenador();

		dialogo.procesarDialogo("combate_entrenador");
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

		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 35);
		if (fase == 0) {

			entrenador.draw(batch);
			protagonista.draw(batch);

		}
		/*
		 * Aparacion de pokemon enemigo
		 */
		if (fase == 1) {

			aparicionPokemonEnemigo(pokemonEnemigo);
			protagonista.setPosition(100, 120);
			base.setPosition(-70, 120);
			baseEnemy.setPosition(350, 300);
			protagonista.draw(batch);
			pokemonEnemigo.draw(batch);

		}
		/*
		 * Aparicion de pokemon nuestro
		 */
		if (fase == 2) {
			aparicionPokemon(pokemon);
			pokemonEnemigo.draw(batch);
			pokemon.draw(batch);
		}
		if (fase > 2) {
			baseEnemy.setPosition(350, 300);
			base.setPosition(-70, 120);
			pokemonEnemigo.setSize(tamanoPokemon, tamanoPokemon);
			pokemon.setSize(tamanoPokemon, tamanoPokemon);
			pokemonEnemigo.setPosition(400, 350);
			pokemon.setPosition(50, 99);
			pokemonEnemigo.draw(batch);
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
			dibujarPokeballs();
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
			dibujarPokeballs();
			vida = 100;
			vidaS = 100;
		}
		if (fase == 5 || fase == 7) {
			/*
			 * Dialogo Ataque
			 */
			pokemon.setAlpha(1);
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
		}
		if (fase == 6 || fase == 8) {
			/*
			 * Ataque, vida y comprobación
			 */
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
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
			pokemon.setAlpha(1);
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
			if (pkmnpokemonEnemigo.getPs() <= 0) {
				pokemonEnemigo.setAlpha(0);
			} else if (pkmn.getPs() <= 0) {
				pokemon.setAlpha(0);
			}
		}
		/*
		 * Mensajes
		 */
		if (fase == 10 || fase == 11) {
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
		}
		if (fase > 2)
			dibujarExp();
		batch.end();
	}

	@Override
	public void show() {
		super.show();
		entrenador = new Sprite(new Texture("res/imgs/entrenadores/"
				+ idEntrenador + ".png"));
		pokemonEnemigo = new Sprite(new Texture("res/imgs/pokemon/"
				+ entrenadorE.getEquipo().get(iPokemonEnemigo).getNombre()
				+ ".png"));
		protagonista = new Sprite(
				new Texture("res/imgs/entrenadores/prota.png"));
		protagonista.setSize(150, 240);
		if (fase < 1) {
			Tween.set(bg, SpriteAccessor.ALPHA).target(0).start(tweenManager);
			Tween.to(bg, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
			Tween.set(base, SpriteAccessor.SLIDE).target(500, 120)
					.start(tweenManager);
			Tween.to(base, SpriteAccessor.SLIDE, 2).target(-70, 120)
					.start(tweenManager);
			Tween.set(protagonista, SpriteAccessor.SLIDE).target(500, 120)
					.start(tweenManager);
			Tween.to(protagonista, SpriteAccessor.SLIDE, 2).target(100, 120)
					.start(tweenManager);
			Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250, 300)
					.start(tweenManager);
			Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350, 300)
					.start(tweenManager);
			Tween.set(entrenador, SpriteAccessor.SLIDE).target(-250, 350)
					.start(tweenManager);
			Tween.to(entrenador, SpriteAccessor.SLIDE, 2).target(400, 350)
					.start(tweenManager);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			switch (keycode) {
			case (Keys.ENTER):

				if (fase == 0 || fase == 1 || fase == 2) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (dialogo.getId().equals("combate_entrenador")
								|| dialogo.getId().equals("adelante")) {
							if (l1.contains("${ENTRENADOR}")) {
								l1 = l1.replace("${ENTRENADOR}", "ENTRENADOR "
										+ idEntrenador.toUpperCase());
							}
							if (l2.contains("${ENTRENADOR}")) {
								l2 = l2.replace("${ENTRENADOR}", "ENTRENADOR "
										+ idEntrenador.toUpperCase());
							}
							if (l1.contains("${POKEMONE}")) {
								l1 = l1.replace("${POKEMONE}", entrenadorE
										.getEquipo().get(iPokemonEnemigo)
										.getNombre());

								fase++;
								// dialogo.procesarDialogo("combate");
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", jugador
										.getEquipo().get(iPokemon).getNombre());
								tamanoPokemon = 1;
								fase++;
								dialogo.procesarDialogo("combate");
							}
							if (l1.equals(" ")) {
								fase++;
							}
						} else if (dialogo.getId().equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", jugador
										.getEquipo().get(iPokemon).getNombre());

							} else {
								fase++;
							}
						}
						/* Escribe letra a letra el dialogo */
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 3) {
					elegirOpcion();
				} else if (fase == 4) {
					/*
					 * Primer ataque
					 */
					fraseAtaque();
				} else if (fase == 5) {
					combate();
				} else if (fase == 6) {
					veces = 8;
					if (jugador.getEquipo().get(iPokemon).getPs() <= 0
							|| entrenadorE.getEquipo().get(iPokemonEnemigo)
									.getPs() <= 0) {
						fase = 9;
						dialogo.procesarDialogo("pokemon_muerto");
					} else {
						fraseAtaque();
					}
				} else if (fase == 7) {
					combate();
				} else if (fase == 8) {
					veces = 8;
					if (pkmn.getPs() <= 0 || pkmnpokemonEnemigo.getPs() <= 0) {
						fase = 9;
						dialogo.procesarDialogo("pokemon_muerto");
						if(pkmnpokemonEnemigo.getPs() <= 0){
							updateExperience(true);
						}
					} else {
						fase = 3;
						seleccionAtaque = 1;
						dialogo.limpiar();
					}
				} else if (fase == 9) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (!entrenadorE.vivo()) {
						fase = 12;
						dialogo.procesarDialogo("combate_ganado");
					}
					if (!jugador.vivo()) {
						fase = 12;
						dialogo.procesarDialogo("combate_perdido");
					}
					if (l1 == null) {
						((Game) Gdx.app.getApplicationListener())
								.setScreen(new MenuPokemon(jugador.getEquipo(),
										this, true));
					} else {
						if (l1.contains("${POKEMON}")) {
							if (pkmn.getPs() <= 0) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							} else {
								l1 = l1.replace("${POKEMON}",
										pkmnpokemonEnemigo.getNombre());
							}
						}

						dialogo.setLineas(l1, l2);
					}

				} else if (fase == 10) {
					fase = 7;
					dialogo.limpiar();

				} else if (fase == 11) {
					fase = 3;
					dialogo.limpiar();
				} else if (fase == 12) {
					String l1, l2;
					l1 = dialogo.siguienteLinea();
					l2 = dialogo.siguienteLinea();
					if (l1 != null) {
						/*
						 * Muere enemigo
						 */

						dialogo.setLineas(l1, l2);
					} else {
						((Game) Gdx.app.getApplicationListener())
								.setScreen(screen);
					}
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
			case Keys.ESCAPE:
				if (fase == 4) {
					fase = 3;
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

	public void setEntrenador() {
		entrenadorE = new Jugador(idEntrenador, true);
		ArrayList<Pokemon> lPoke = new ArrayList<Pokemon>();
		try {
			db = new BaseDatos("pokemon_base");
			pkmnpokemonEnemigo = db.getPokemon(2);
			lPoke.add(pkmnpokemonEnemigo);
			// lPoke.add(db.getPokemon(3));
			db.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

		entrenadorE.setEquipo(lPoke);
		pkmnpokemonEnemigo = entrenadorE.getEquipo().get(iPokemonEnemigo);
	}

	public void dibujarPokeballs() {
		int nPoke = jugador.getEquipo().size();
		int nPokeEnemigo = entrenadorE.getEquipo().size();
		int xP = 650;
		int xE = 180;
		Sprite[] balls = new Sprite[6];
		Sprite[] ballsEnemigo = new Sprite[6];
		for (int i = 0; i < 6; i++) {
			if (i < nPoke) {
				if (jugador.getEquipo().get(i).vivo()) {
					balls[i] = new Sprite(new Texture(
							"res/imgs/batallas/ballnormal.png"));
				} else {
					balls[i] = new Sprite(new Texture(
							"res/imgs/batallas/ballfainted.png"));
				}
			} else {
				balls[i] = new Sprite(new Texture(
						"res/imgs/batallas/ballempty.png"));

			}
			balls[i].setPosition(xP, 130);
			balls[i].draw(batch);
			xP = xP - 30;
			if (i < nPokeEnemigo) {
				if (entrenadorE.getEquipo().get(i).vivo()) {
					ballsEnemigo[i] = new Sprite(new Texture(
							"res/imgs/batallas/ballnormal.png"));
				} else {
					ballsEnemigo[i] = new Sprite(new Texture(
							"res/imgs/batallas/ballfainted.png"));
				}
			} else {
				ballsEnemigo[i] = new Sprite(new Texture(
						"res/imgs/batallas/ballempty.png"));

			}
			ballsEnemigo[i].setPosition(xE, 370);
			ballsEnemigo[i].draw(batch);
			xE = xE - 30;
		}
		/*
		 * pokeball = pokeballVacio = new Sprite(new Texture(
		 * "res/imgs/batallas/ballempty.png")); pokeballMuerto = new Sprite(new
		 * Texture( "res/imgs/batallas/ballfainted.png"));
		 */
	}

}
