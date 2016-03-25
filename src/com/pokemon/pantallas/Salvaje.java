package com.pokemon.pantallas;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.tween.SpriteAccessor;

public class Salvaje implements Screen, InputProcessor {

	private float x;
	private float y;
	private float lastPressed;
	private TweenManager tweenManager;

	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	SpriteBatch batch;
	Sprite bg, base, baseEnemy, message;
	Texture baseT, baseEnemyT,messageT;

	public Salvaje(float x, float y, float lastPressed) {
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		bg.draw(batch);
		bg.setSize( 720, 540);
		base.draw(batch);
		baseEnemy.draw(batch);
		message.draw(batch);
		message.setSize(720, 120);
		batch.end();

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tweenManager=new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		batch = new SpriteBatch();
		bg = new Sprite(new Texture("res/imgs/batallas/battlebgForestEve.png"));
		baseT = new Texture("res/imgs/batallas/playerbaseForestGrassEve.png");
		baseEnemyT = new Texture("res/imgs/batallas/enemybaseFieldGrassEve.png");
		messageT =new Texture("res/imgs/batallas/battleMessage.png");
		base = new Sprite(baseT);
		baseEnemy = new Sprite(baseEnemyT);
		message = new Sprite(messageT);
		Tween.set(bg, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(bg, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
		Tween.set(base, SpriteAccessor.SLIDE).target(500,120).start(tweenManager);
		Tween.to(base, SpriteAccessor.SLIDE, 2).target(-50,120).start(tweenManager);
		Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250,300).start(tweenManager);
		Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350,300).start(tweenManager);
		
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
