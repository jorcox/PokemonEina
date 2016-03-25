package com.pokemon.pantallas;

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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

public class Salvaje extends Dialogo implements Screen, InputProcessor {

	private float x;
	private float y;
	private float lastPressed;
	FreeTypeFontGenerator generator;
	private TweenManager tweenManager;

	BitmapFont font;

	SpriteBatch batch;
	Sprite bg, base, baseEnemy, message,pokemon;

	public Salvaje(float x, float y, float lastPressed) {
		super("es","ES");
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;
		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
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
		bg.setSize( 720, 540);
		base.draw(batch);
		baseEnemy.draw(batch);
		message.draw(batch);
		message.setSize(720, 120);
		pokemon.draw(batch);
		pokemon.setSize(120,120);
		font.draw(batch, lineaUno, 50, 85);
		font.draw(batch, lineaDos, 50, 35);
		batch.end();

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tweenManager=new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		batch = new SpriteBatch();
		bg = new Sprite(new Texture("res/imgs/batallas/battlebgForestEve.png"));
		base = new Sprite(new Texture("res/imgs/batallas/playerbaseForestGrassEve.png"));
		baseEnemy = new Sprite(new Texture("res/imgs/batallas/enemybaseFieldGrassEve.png"));
		message = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		pokemon = new Sprite(new Texture("res/imgs/pokemon/Mew.png"));
		Tween.set(bg, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(bg, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
		Tween.set(base, SpriteAccessor.SLIDE).target(500,120).start(tweenManager);
		Tween.to(base, SpriteAccessor.SLIDE, 2).target(-50,120).start(tweenManager);
		Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250,300).start(tweenManager);
		Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350,300).start(tweenManager);
		Tween.set(pokemon, SpriteAccessor.SLIDE).target(-250,350).start(tweenManager);
		Tween.to(pokemon, SpriteAccessor.SLIDE, 2).target(400,350).start(tweenManager);
		font.setColor(Color.BLACK);
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

					if (l1.contains("${POKEMON}")) {
						l1 = l1.replace("${POKEMON}",
								"Mew");
					} 

					/* Escribe letra a letra el dialogo */
					setLineas(l1, l2);
					
					/*
					 * if (script[counter].contains("(OPTION)")) {
					 * script[counter] = script[counter].replace( "(OPTION)",
					 * ""); optionsVisible = true; }
					 */
				} else {
					//m.stop();
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Play(60,60,3));
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

}
