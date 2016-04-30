package com.pokemon.pantallas;

import java.util.ArrayList;

import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pokemon.entities.Player;
import com.pokemon.tween.SpriteAccessor;

import entrenadores.Entrenador;
import entrenadores.Jugador;

public class CombateEntrenador extends Enfrentamiento {

	private String idEntrenador;
	private Jugador entrenadorE;
	private int iPokemonEnemigo = 0;
	private Sprite pokemonEnemigo;
	TextureRegion[] spritesEntrenador;

	public CombateEntrenador(Player player, Jugador jugador, String idEntrenador) {
		super(player, jugador);
		this.idEntrenador = idEntrenador;
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
		if (fase == 1) {
			entrenador.draw(batch);
			protagonista.draw(batch);
		}
		/*
		 * Aparacion de pokemon enemigo
		 */
		if (fase == 2) {

			aparicionPokemonEnemigo(pokemonEnemigo);
			protagonista.draw(batch);
			pokemonEnemigo.draw(batch);

		}
		/*
		 * Aparicion de pokemon nuestro
		 */
		if (fase == 3) {
			aparicionPokemon(pokemon);
			pokemonEnemigo.draw(batch);
			pokemon.draw(batch);
		}

		/*
		 * Decidir accion (Luchar, Mochila, Pokemon, Huir)
		 */
		if (fase == 4) {
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
			pokemon.setAlpha(1);
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
			pokemon.setAlpha(1);
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			if (pkmnSalvaje.getPs() <= 0) {
				salvaje.setAlpha(0);
			} else if (pkmn.getPs() <= 0) {
				pokemon.setAlpha(0);
			}
		}
		if (fase == 10 || fase == 11) {
			pokemon.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
		}
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

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			switch (keycode) {
			case (Keys.ENTER):

				if (fase == 1 || fase == 2 || fase == 3) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (dialogo.getId().equals("combate_entrenador")) {
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
										.getEquipo().get(0).getNombre());
								tamanoPokemon=1;
								fase++;
								// dialogo.procesarDialogo("combate");
							}
						} else if (dialogo.getId().equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", entrenadorE
										.getEquipo().get(iPokemonEnemigo)
										.getNombre());
							}
							if (l1.equals(" ")) {
								fase++;
							}
						}
						/* Escribe letra a letra el dialogo */
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 4) {
					switch (seleccion) {
					case 1: // luchar
						fase = 5;
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
					veces = 8;
					if (pkmn.getPs() <= 0 || pkmnSalvaje.getPs() <= 0) {
						fase = 9;
						dialogo.procesarDialogo("pokemon_muerto");
					} else {
						fraseAtaque();
					}
				} else if (fase == 7) {
					combate();
				} else if (fase == 8) {
					veces = 8;
					if (pkmn.getPs() <= 0 || pkmnSalvaje.getPs() <= 0) {
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
					if (l1.contains("${POKEMON}")) {
						if (pkmn.getPs() <= 0) {
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						} else {
							l1 = l1.replace("${POKEMON}",
									pkmnSalvaje.getNombre());
						}
					}
					dialogo.setLineas(l1, l2);

				} else if (fase == 10) {
					fase = 7;
					dialogo.limpiar();

				} else if (fase == 11) {
					fase = 3;
					dialogo.limpiar();
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

	public void setEntrenador() {
		entrenadorE = new Jugador(idEntrenador, true);
		ArrayList<Pokemon> lPoke = new ArrayList<Pokemon>();
		lPoke.add(pkmnSalvaje);
		entrenadorE.setEquipo(lPoke);
	}

}
