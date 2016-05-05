package com.pokemon.pantallas;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.pokemon.entities.Player;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;

public class CombateP extends Enfrentamiento {

	private boolean show = true;

	public CombateP(ArchivoGuardado ctx, Player player, Jugador jugador,
			int fase, Screen screen) {
		super(ctx, player, jugador, screen);
		this.fase = fase;
		dialogo.procesarDialogo("salvaje");
		pkmn = jugador.getEquipo().get(iPokemon);
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
		pokemonEnemigo.draw(batch);
		pokemonEnemigo.setSize(120, 120);
		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 35);
		base.setPosition(-70, 120);
		baseEnemy.setPosition(350, 300);
		pokemonEnemigo.setPosition(400, 350);

		/*
		 * Aparacion de pokemon pokemonEnemigo
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
			dibujarExp();
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
			dibujarExp();
			vida = 100;
			vidaS = 100;
		}
		if (fase == 5 || fase == 7) {
			/*
			 * Dialogo Ataque
			 */
			pokemon.setAlpha(1);
			pokemonEnemigo.setAlpha(1);
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 6 || fase == 8) {
			/*
			 * Ataque, vida y comprobación
			 */
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarExp();
			dibujarVidas();
			if ((orden && fase == 6) || (!orden && fase == 8)) {
				pokemonEnemigo.setAlpha(1);
				if (acierto)
					ataqueRecibido(true);
				animacionVida(true);
				dibujarVida(true);
			} else {
				pokemon.setAlpha(1);
				if (acierto)
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
			dibujarExp();
			dibujarVidas();
			if (pkmnpokemonEnemigo.getPs() <= 0) {
				pokemonEnemigo.setAlpha(0);
			} else if (pkmn.getPs() <= 0) {
				pokemon.setAlpha(0);
			}
		}
		if (fase == 10 || fase == 11) {
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 12) {
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 13) {
			subirNivel();
		}

		batch.end();
	}

	@Override
	public void show() {
		super.show();
		if (show) {
			show = false;
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
			Tween.set(pokemonEnemigo, SpriteAccessor.SLIDE).target(-250, 350)
					.start(tweenManager);
			Tween.to(pokemonEnemigo, SpriteAccessor.SLIDE, 2).target(400, 350)
					.start(tweenManager);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (keycode == getCtx().getTeclaA()) {

				if (fase == 1 || fase == 2) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (dialogo.getId().equals("salvaje")
								|| dialogo.getId().equals("adelante")) {
							if (l1.contains("${SALVAJE}")) {
								l1 = l1.replace("${SALVAJE}",
										pkmnpokemonEnemigo.getNombre());
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
								fase++;
								dialogo.procesarDialogo("combate");
							}
						} else if (dialogo.getId().equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							}
							if (l1.equals(" ")) {
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
					if (pkmn.getPs() <= 0 || pkmnpokemonEnemigo.getPs() <= 0) {
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
					} else {
						fase = 3;
						seleccionAtaque = 1;
						dialogo.limpiar();
					}
				} else if (fase == 9) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 == null) {
						if (pkmn.getExperiencia() > experienceToLevel(pkmn
								.getNivel() + 1)) {
							/*
							 * Subir nivel
							 */
							dialogo.procesarDialogo("subir_nivel");
							fase = 13;
						} else if (!pkmnpokemonEnemigo.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						} else {
							((Game) Gdx.app.getApplicationListener())
									.setScreen(new MenuPokemon(getCtx(),
											jugador.getEquipo(), this, true));
						}
					} else {
						if (l1.contains("debilitado")) {
							if (pkmn.getPs() <= 0) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							} else {
								l1 = l1.replace("${POKEMON}",
										pkmnpokemonEnemigo.getNombre());
							}
						} else if (l1.contains("${EXP}")) {
							l1 = l1.replace(
									"${EXP}",
									gainExperience(false,
											pkmnpokemonEnemigo.getNivel())
											+ "");
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							updateExperience(false);
						}
						if (pkmnpokemonEnemigo.vivo() && l1.contains("puntos")) {

						} else {
							dialogo.setLineas(l1, l2);
						}
					}

				} else if (fase == 10) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					dialogo.setLineas(l1, l2);
					if (l1 == null) {
						fase = 6;
					}

				} else if (fase == 11) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					dialogo.setLineas(l1, l2);
					if (l1 == null) {
						fase = 3;
					}
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
				} else if (fase == 13) {
					/*
					 * Subir nivel
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (subir) {
						if (!pkmnpokemonEnemigo.vivo()) {
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							dialogo.procesarDialogo("combate_perdido");
						}
						fase = 12;
					} else if (l1 == null) {
						pkmn.subirNivel();
						subir = true;
					} else {
						l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						l2 = l2.replace("${NIVEL}", "" + (pkmn.getNivel() + 1));
						dialogo.setLineas(l1, l2);
					}

				}
			} else if (keycode == getCtx().getTeclaLeft()) {
				if (fase == 3) {
					if (seleccion != 1) {
						seleccion -= 1;
					}
				} else if (fase == 4) {
					if (seleccionAtaque != 1 && seleccionAtaque != 3) {
						seleccionAtaque -= 1;
					}
				}

			} else if (keycode == getCtx().getTeclaRight()) {
				if (fase == 3) {
					if (seleccion != 4) {
						seleccion += 1;
					}
				} else if (fase == 4) {
					if (seleccionAtaque != 2 && seleccionAtaque != 4) {
						seleccionAtaque += 1;
					}
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				if (fase == 4) {
					if (seleccionAtaque != 1 && seleccionAtaque != 2) {
						seleccionAtaque -= 2;
					}
				}
			} else if (keycode == getCtx().getTeclaDown()) {
				if (fase == 4) {
					if (seleccionAtaque != 3 && seleccionAtaque != 4) {
						seleccionAtaque += 2;
					}
				}
			} else if (keycode == Keys.ESCAPE) {
				if (fase == 4) {
					fase = 3;
				}
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

}
