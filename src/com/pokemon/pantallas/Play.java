package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.pokemon.entities.Player;
import com.pokemon.render.TextureMapObjectRenderer;

public class Play implements Screen {

	private TiledMap map;
	private TextureMapObjectRenderer renderer;
	private OrthographicCamera camera;
	private float x, y;
	private int lastPressed;

	private TextureAtlas playerAtlas;

	// private Player player = new Player(new Sprite(new
	// Texture("assets/maps/tilesInterior.png")));
	private Player player;

	public Play(float x, float y, int lastPressed) {
		this.x = x;
		this.y = y;
		this.lastPressed=lastPressed;
	}

	@Override
	public void show() {
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("res/mapas/Tranvia_n.tmx");

		renderer = new TextureMapObjectRenderer(map);

		camera = new OrthographicCamera();

		playerAtlas = new TextureAtlas("res/imgs/protagonista.pack");

		Animation cara, derecha, izquierda, espalda;
		cara = new Animation(1 / 10f, playerAtlas.findRegions("cara"));
		derecha = new Animation(1 / 10f, playerAtlas.findRegions("derecha"));
		izquierda = new Animation(1 / 10f, playerAtlas.findRegions("izquierda"));
		espalda = new Animation(1 / 10f, playerAtlas.findRegions("espalda"));
		cara.setPlayMode(Animation.PlayMode.LOOP);
		derecha.setPlayMode(Animation.PlayMode.LOOP);
		izquierda.setPlayMode(Animation.PlayMode.LOOP);
		espalda.setPlayMode(Animation.PlayMode.LOOP);

		player = new Player(cara, izquierda, derecha, espalda,
				(TiledMapTileLayer) map.getLayers().get("Entorno"));
		player.setPosition(x, y);
		player.setLastPressed(lastPressed);
		Gdx.input.setInputProcessor(player);
		
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

		camera.position.set(player.getX() + (player.getWidth() / 2),
				player.getY() + (player.getHeight() / 2), 0);
		camera.zoom = (float) 1;
		camera.update();
		renderer.setView(camera);
		renderer.render();

		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		if (player.getSpacePressed()) {
			// Show menu
			openMenuPlay();
		}
		renderer.getBatch().end();
	}

	public void openMenuPlay() {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPlay(player
				.getX(), player.getY(), player.getLastPressed()));
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

}