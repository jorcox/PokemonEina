package com.pokemon.pantallas;

import java.util.ArrayList;

import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.entities.NPC;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.Pocion;
import com.pokemon.render.TextureMapObjectRenderer;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import db.BaseDatos;
import entrenadores.Jugador;

public class Play implements Screen, InputProcessor {

	public TiledMap map;
	private TextureMapObjectRenderer renderer;
	private OrthographicCamera camera;
	private float x, y;
	private int lastPressed;
	private String map_;
	private Dialogo dialogo;
	private SpriteBatch batch;
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private TweenManager tweenManager;

	private TextureAtlas playerAtlas;

	private Sprite box;
	public boolean optionsVisible = false;

	// private Player player = new Player(new Sprite(new
	// Texture("assets/maps/tilesInterior.png")));
	private Player player;
	public Jugador jugador;
	private Stage stage;
	private ArrayList<NPC> npcs = new ArrayList<>();
	private boolean dialogando;

	public Play(float x, float y, int lastPressed, String mapa) {
		dialogo = new Dialogo("es", "ES");
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;
		this.map_ = mapa;
		dialogando = false;

		Gdx.input.setInputProcessor(this);

		/* Prepara fuente para escritura */
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter);

		setJugador();
	}

	@Override
	public void show() {
		TmxMapLoader loader = new TmxMapLoader();
		tweenManager = new TweenManager();
		// map = loader.load("res/mapas/Tranvia_n.tmx");
		map = loader.load("res/mapas/" + map_);
		// map = loader.load("res/mapas/Cueva.tmx");
		// map = loader.load("res/mapas/Hall.tmx");
		// map = loader.load("res/mapas/Bosque.tmx");

		renderer = new TextureMapObjectRenderer(map);

		camera = new OrthographicCamera();

		playerAtlas = new TextureAtlas("res/imgs/protagonista.pack");

		/* Carga de NPCs */
		MapLayer npcLayer = map.getLayers().get("Personajes");
		for (MapObject o : npcLayer.getObjects()) {
			TextureMapObject t = (TextureMapObject) o;
			String dirVista = (String) t.getProperties().get("dir"); // Cara
																		// Derecha
																		// Izquierda
																		// Espalda
			int disVista = Integer.parseInt((String) t.getProperties().get(
					"dis"));
			TextureAtlas personajePack = new TextureAtlas("res/imgs/"
					+ (String) t.getProperties().get("pack") + ".pack");
			NPC npc = new NPC(personajePack, new Animation(1 / 10f,
					playerAtlas.findRegions(dirVista)), disVista, this);
			npc.setPosition(t.getX(), t.getY());
			npcs.add(npc);
		}

		/* Player */
		player = new Player(playerAtlas, (TiledMapTileLayer) map.getLayers()
				.get("Entorno"), map.getLayers().get("Objetos"), map
				.getLayers().get("Trans"), npcs, dialogo, this);
		player.setPosition(x, y);
		player.setLastPressed(lastPressed);
		equipoPokemon();

		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		box = new Sprite(new Texture("res/imgs/OptionBox.png"));
		box.setScale((float) 4.5, (float) 1.5);
		box.setX(box.getX() + 160);

		font.setColor(Color.BLACK);

		/* Mostrar objetos */
		renderer.getBatch().begin();
		for (MapObject o : map.getLayers().get("Objetos").getObjects()) {
			renderer.renderObject(o);
		}
		renderer.getBatch().end();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);

		camera.position.set(player.getX() + (player.getWidth() / 2),
				player.getY() + (player.getHeight() / 2), 0);
		camera.zoom = (float) 1;
		camera.update();
		renderer.setView(camera);
		renderer.render();

		renderer.getBatch().begin();
		/* Player */
		player.draw(renderer.getBatch());
		if (player.getSpacePressed()) {
			// Show menu
			openMenuPlay();
		}
		/* NPC */
		for (NPC npc : npcs) {
			npc.draw(renderer.getBatch());
		}
		renderer.getBatch().end();

		if (optionsVisible) {
			batch.begin();
			box.draw(batch);
			font.setColor(Color.BLACK);
			font.draw(batch, dialogo.getLinea1(), 50, 125);
			font.draw(batch, dialogo.getLinea2(), 50, 75);
			batch.end();
		}

	}

	public void openMenuPlay() {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPlay(player
				.getX(), player.getY(), player.getLastPressed(), map_,
				player.mochila, jugador.getEquipo()));
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

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
		map.dispose();
		renderer.dispose();

		playerAtlas.dispose();

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode) {
		player.checkCombat();
		switch (keycode) {
		case Keys.W:
			player.velocity.y = player.speed;
			player.velocity.x = 0;
			player.animationTime = 0;
			player.setLastPressed(2);
			if (lastPressed == 0) {
				lastPressed = 2;
			}
			player.WPressed = true;
			break;
		case Keys.A:
			player.velocity.x = -player.speed;
			player.velocity.y = 0;
			player.animationTime = 0;
			player.setLastPressed(1);
			if (lastPressed == 0) {
				lastPressed = 1;
			}
			player.APressed = true;
			break;
		case Keys.S:
			player.velocity.y = -player.speed;

			player.velocity.x = 0;
			player.animationTime = 0;
			player.setLastPressed(3);
			if (lastPressed == 0) {
				lastPressed = 3;
			}
			player.SPressed = true;
			break;
		case Keys.D:
			player.velocity.x = player.speed;
			player.velocity.y = 0;
			player.animationTime = 0;
			player.setLastPressed(4);
			if (lastPressed == 0) {
				lastPressed = 4;
			}
			player.DPressed = true;
			break;
		case Keys.SPACE:
			player.SpacePressed = true;
			break;
		case Keys.ENTER:
			if (dialogando) {
				/* Esta en pleno dialogo, Enter lo va avanzando */
				optionsVisible = true;

				if (!dialogo.isWriting()) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}

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
							ArchivoGuardado.nombreJugador = "Sara";
						}

						/* Escribe letra a letra el dialogo */
						dialogo.setLineas(l1, l2);

						/*
						 * if (script[counter].contains("(OPTION)")) {
						 * script[counter] = script[counter].replace(
						 * "(OPTION)", ""); optionsVisible = true; }
						 */
					} else {
						dialogo.limpiar();
						optionsVisible = false;
						dialogando = false;
					}
				}
			} else {
				/* Busca interactuar con algo */
				for (MapObject o : map.getLayers().get("Objetos").getObjects()) {
					TextureMapObject t = (TextureMapObject) o;
					Gdx.app.log("cartel", "distancia " + distance(t));

					/* Dispara evento si estan muy cerca jugador y objeto */
					if (distance(t) < 50) {
						interact(t);
						break;
					}
				}
			}
			break;
		case Keys.C:
			((Game) Gdx.app.getApplicationListener()).setScreen(new CombateP(
					player, jugador, 1));
			break;
		case Keys.V:
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new CombateEntrenador(player, jugador,
							"reverte", 0));
			break;
		}

		return false;
	}

	/**
	 * Gestiona las interacciones entre el jugador y el objeto obj, que puede
	 * ser un cartel, pokeball, etc.
	 * 
	 * @param obj
	 *            el objeto declarado en la capa de objetos.
	 */
	private void interact(TextureMapObject obj) {
		if (obj.getProperties().containsKey("cartel")) {
			optionsVisible = true;
			dialogando = true;

			/* Leer cartel */
			String value = (String) obj.getProperties().get("cartel");
			dialogo.procesarDialogo("cartel_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
		} else if (obj.getProperties().containsKey("item") && 
				obj.getProperties().containsKey("used") && 
				obj.getProperties().get("used").equals("false")) {
			optionsVisible = true;
			dialogando = true;

			/* Leer objeto recogido */
			String value = (String) obj.getProperties().get("item");
			dialogo.procesarDialogo("item_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(),
					dialogo.siguienteLinea());

			/* Introduce en mochila */
			if (value.equals("Poción")) {
				player.mochila.add(new Pocion());
			} else if (value.equals("Antídoto")) {
				player.mochila.add(new Antidoto());
			}
			
			/* Asi no se puede volver a coger ese item */
			obj.getProperties().put("used", "true");
		}
	}

	/**
	 * Calcula la distancia entre el jugador y el objeto de texturas t.
	 * 
	 * @param t
	 *            el objeto de texturas.
	 * @return la distancia euclidea.
	 */
	public int distance(TextureMapObject t) {
		double aux = t.getX();
		double dx = Math.pow(player.getX() - aux, 2);
		double dy = Math.pow(player.getY() - t.getY(), 2);
		return (int) (Math.sqrt(dx + dy));
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			if (player.SPressed) {
				player.velocity.x = 0;
				player.velocity.y = -player.speed;
			} else if (player.APressed) {
				player.velocity.y = 0;
				player.velocity.x = -player.speed;
			} else if (player.DPressed) {
				player.velocity.y = 0;
				player.velocity.x = player.speed;
			} else {
				player.velocity.y = 0;
			}
			player.animationTime = 0;
			lastPressed = 2;
			player.WPressed = false;
			break;
		case Keys.A:
			if (player.DPressed) {
				player.velocity.y = 0;
				player.velocity.x = player.speed;
			} else if (player.WPressed) {
				player.velocity.x = 0;
				player.velocity.y = player.speed;
			} else if (player.SPressed) {
				player.velocity.x = 0;
				player.velocity.y = -player.speed;
			} else {
				player.velocity.x = 0;
			}
			player.animationTime = 0;
			lastPressed = 1;
			player.APressed = false;
			break;
		case Keys.S:
			if (player.WPressed) {
				player.velocity.x = 0;
				player.velocity.y = player.speed;
			} else if (player.APressed) {
				player.velocity.y = 0;
				player.velocity.x = -player.speed;
			} else if (player.DPressed) {
				player.velocity.y = 0;
				player.velocity.x = player.speed;
			} else {
				player.velocity.y = 0;
			}
			player.animationTime = 0;
			lastPressed = 3;
			player.SPressed = false;
			break;
		case Keys.D:
			if (player.APressed) {
				player.velocity.y = 0;
				player.velocity.x = -player.speed;
			} else if (player.WPressed) {
				player.velocity.x = 0;
				player.velocity.y = player.speed;
			} else if (player.SPressed) {
				player.velocity.x = 0;
				player.velocity.y = -player.speed;
			} else {
				player.velocity.x = 0;
			}
			player.animationTime = 0;
			lastPressed = 4;
			player.DPressed = false;
			break;
		case Keys.SPACE:
			player.SpacePressed = false;
			break;
		}
		return false;
	}

	public void setJugador() {
		jugador = new Jugador("Sara", false);
		equipoPokemon();
	}

	public void equipoPokemon() {
		ArrayList<Pokemon> arrayP = new ArrayList<Pokemon>();
		try {
			BaseDatos db = new BaseDatos("pokemon_base");
			arrayP.add(db.getPokemon(0));
			arrayP.add(db.getPokemon(1));
			arrayP.add(db.getPokemon(2));
			arrayP.add(db.getPokemon(3));
			arrayP.add(db.getPokemon(4));
			arrayP.add(db.getPokemon(5));
			jugador.setEquipo(arrayP);
			db.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
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