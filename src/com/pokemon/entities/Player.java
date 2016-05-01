package com.pokemon.entities;

import pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;
import com.pokemon.mochila.Pokeball;
import com.pokemon.mochila.Superball;
import com.pokemon.pantallas.CombateP;
import com.pokemon.pantallas.Play;
import com.pokemon.utilidades.ArchivoGuardado;

public class Player extends Sprite {

	public Vector2 velocity = new Vector2();

	public float speed = 60 * 2;

	private float gravity = 60 * 1.8f;

	public float animationTime = 0;

	private Animation cara, izquierda, derecha, espalda;
	private TiledMapTileLayer collisionLayer;
	private MapLayer objectLayer;
	private MapLayer transLayer;
	private Dialogo dialogo;
	private Play play;

	public boolean APressed = false;

	public boolean WPressed = false;

	public boolean SPressed = false;

	public boolean DPressed = false;

	public boolean SpacePressed = false;

	private int p;
	
	ArrayList<NPC> npcs;

	private int lastPressed; // A=1, W=2, S=3, D=4
	
	private ArchivoGuardado ctx;

	public Player(ArchivoGuardado ctx, TextureAtlas playerAtlas, TiledMapTileLayer collisionLayer, MapLayer objectLayer, MapLayer transLayer,
			ArrayList<NPC> npcs, Dialogo dialogo, Play play) {
		super(new Animation(1 / 10f, playerAtlas.findRegions("cara")).getKeyFrame(0));
		cara = new Animation(1 / 10f, playerAtlas.findRegions("cara"));
		derecha = new Animation(1 / 10f, playerAtlas.findRegions("derecha"));
		izquierda = new Animation(1 / 10f, playerAtlas.findRegions("izquierda"));
		espalda = new Animation(1 / 10f, playerAtlas.findRegions("espalda"));
		cara.setPlayMode(Animation.PlayMode.LOOP);
		derecha.setPlayMode(Animation.PlayMode.LOOP);
		izquierda.setPlayMode(Animation.PlayMode.LOOP);
		espalda.setPlayMode(Animation.PlayMode.LOOP);
		this.collisionLayer = collisionLayer;
		this.setTransLayer(transLayer);
		this.objectLayer = objectLayer;
		this.transLayer = transLayer;
		this.dialogo = dialogo;
		this.play = play;
		this.npcs = npcs;
		this.ctx = ctx;

		this.ctx.mochila = new Mochila();
		p = 0;

		// Objetos de prueba metidos a pelo
		this.ctx.mochila.add(new Pocion());
		this.ctx.mochila.add(new Antidoto());
		this.ctx.mochila.add(new Pokeball());
		this.ctx.mochila.add(new Superball());
		this.ctx.mochila.add(new Pokeball());
		
	}

	@Override
	public void draw(Batch spriteBach) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBach);

	}

	public void update(float delta) {
		/*
		 * Nosotros no necesitamos la gravedad
		 */
		// velocity.y -= gravity * delta;

		/*
		 * Limite de velocidad
		 */
		// if (velocity.y > speed) {
		// velocity.y = speed;
		// } else if (velocity.y < speed) {
		// velocity.y = -speed;
		// }

		/*
		 * Guardar la posicion anterior
		 */
		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(),
				tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false, collisionObj = false, collisionNpc = false;
		
		/*
		 * Movimiento en X
		 */
		setX(getX() + velocity.x * delta);

		if (velocity.x < 0) {
			// Top left
			// collisionX = collisionLayer.getCell((int) (getX() / tileWidth),
			// (int) ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Middle left
			if (!collisionX)
				collisionX |= collisionLayer
						.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

			// Bottom left
			if (!collisionX)
				collisionX |= collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
//			/* Objetos */
//			if (!collisionX) {
//				for (MapObject object : objectLayer.getObjects()) {
//					TextureMapObject texture = (TextureMapObject) object;
//					collisionX = ((texture.getX() + texture.getTextureRegion().getRegionWidth() / 2.5) > getX())
//							&& ((texture.getX() - texture.getTextureRegion().getRegionWidth() / 2.5) < getX())
//							&& ((texture.getY() + texture.getTextureRegion().getRegionHeight() / 2.5) > getY())
//							&& ((texture.getY() - texture.getTextureRegion().getRegionHeight() / 2.5) < getY());
//				}
//			}
		} else if (velocity.x > 0) {
			// Top right
			// collisionX = collisionLayer
			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
			// ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Middle right
			if (!collisionX)
				collisionX |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");

			// Bottom right
			if (!collisionX)
				collisionX |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");			
//			/* Objetos */
//			if (!collisionX) {
//				for (MapObject object : objectLayer.getObjects()) {
//					TextureMapObject texture = (TextureMapObject) object;
//					collisionX = ((texture.getX() + texture.getTextureRegion().getRegionWidth() / 2.5) < getX())
//							&& ((texture.getX() - texture.getTextureRegion().getRegionWidth() / 2.5) < getX())
//							&& ((texture.getY() + texture.getTextureRegion().getRegionHeight() / 2.5) > getY())
//							&& ((texture.getY() - texture.getTextureRegion().getRegionHeight() / 2.5) < getY());
//				}
//			}

		}
		/*
		 * Reaccion a colision en X
		 */
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		
		if (collisionX || collisionY) {
			for (MapObject o : transLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;

				/* Dispara evento si estan muy cerca jugador y objeto */
				if (play.distance(t) < 50) {
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Play(ctx, Integer.parseInt((String) t.getProperties().get("x")),
									Integer.parseInt((String) t.getProperties().get("y")), getLastPressed(),
									t.getProperties().get("mapa") + ".tmx"));
					break;
				}
			}
		}

		/*
		 * Movimiento en Y
		 */
		setY(getY() + velocity.y * delta);
		if (velocity.y < 0) {
			// Bottom left
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
					.getProperties().containsKey("blocked");
			// Bottom middle
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

			// Bottom right
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

		} else if (velocity.y > 0) {
			// Top left
			// collisionY = collisionLayer.getCell((int) (getX() / tileWidth),
			// (int) ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Top middle
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");

			// Top right
			// if (!collisionY)
			// collisionY |= collisionLayer
			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
			// ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
		}
		/*
		 * Reacción al colisionar en Y
		 */
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		 /*
		  * Colision de objetos
		  */
		for (MapObject object : objectLayer.getObjects()) {
			TextureMapObject texture = (TextureMapObject) object;
			collisionObj |= ((texture.getX() + texture.getTextureRegion().getRegionWidth() / 2) > getX())
					&& ((texture.getX() - texture.getTextureRegion().getRegionWidth() / 2) < getX())
					&& ((texture.getY() + texture.getTextureRegion().getRegionHeight() / 1.5) > getY())
					&& ((texture.getY() - texture.getTextureRegion().getRegionHeight() / 2) < getY());
		}
		
		 /*
		  * Colision de NPC
		  */
		int anchura = 32;
		int altura = 32;
		for (NPC npc : npcs) {
			collisionNpc |= ((npc.getX() + anchura / 1.5) > getX())
					&& ((npc.getX() - anchura / 1.5) < getX())
					&& ((npc.getY() + altura / 1.5) > getY())
					&& ((npc.getY() - altura / 0.9) < getY());
		}
		
		if (collisionObj || collisionNpc) {
			setX(oldX);
			setY(oldY);
			velocity.x = 0;
			velocity.y = 0;
		}

		/*
		 * Actualizar animacion
		 */
		animationTime += delta;
		if ((WPressed || APressed || SPressed || DPressed) && (!collisionX && !collisionY)
				&& (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(
					velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
							: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
									: velocity.y > 0 ? espalda.getKeyFrame(animationTime)
											: cara.getKeyFrame(animationTime));
		} else {
			switch (lastPressed) {
			case 1: // A
				setRegion(izquierda.getKeyFrame(0));
				break;
			case 2: // W
				setRegion(espalda.getKeyFrame(0));
				break;
			case 3: // S
				setRegion(cara.getKeyFrame(0));
				break;
			case 4: // D
				setRegion(derecha.getKeyFrame(1));
				break;
			}
		}
	}

	/**
	 * Si el jugador esta en casilla de combate, elige aleatoriamente si
	 * le aparece un pokemon salvaje con una probabilidad del 20%.
	 * 
	 * Por ahora solo comprueba al cambiar de tecla de moverse (arreglar).
	 */
	public void checkCombat() {
		if (collisionLayer.getCell((int) ((getX() + getWidth()) / collisionLayer.getTileWidth()), 
						(int) ((getY() + getHeight()) / collisionLayer.getTileHeight()))
						.getTile().getProperties().containsKey("combat")) {
			double combatOdds = new Random().nextDouble();
			if (combatOdds < 0.02) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new CombateP(ctx, 
						this, play.jugador, 1, play));
			}
		}
	}
	
	public boolean getSpacePressed() {
		return SpacePressed;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public int getLastPressed() {
		return lastPressed;
	}

	public void setLastPressed(int lastPressed) {
		this.lastPressed = lastPressed;
	}

	public MapLayer getTransLayer() {
		return transLayer;
	}

	public void setTransLayer(MapLayer transLayer) {
		this.transLayer = transLayer;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

}