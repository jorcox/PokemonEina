package com.pokemon.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;
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
	public int lastPressed; // A=1, W=2, S=3, D=4
	
	public Mochila mochila;

	public Player(Animation cara, Animation izquierda, Animation derecha,
			Animation espalda, TiledMapTileLayer collisionLayer,
			MapLayer objectLayer, MapLayer transLayer, Dialogo dialogo,
			Play play) {
		super(cara.getKeyFrame(0));
		this.cara = cara;
		this.izquierda = izquierda;
		this.derecha = derecha;
		this.espalda = espalda;
		this.collisionLayer = collisionLayer;
		this.setTransLayer(transLayer);
		this.objectLayer = objectLayer;
		this.transLayer = transLayer;
		this.dialogo = dialogo;
		this.play = play;
		
		mochila = new Mochila();
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
		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer
				.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

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
						.getCell((int) (getX() / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");

			// Bottom left
			if (!collisionX)
				collisionX |= collisionLayer
						.getCell((int) (getX() / tileWidth),
								(int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

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
						.getCell((int) ((getX() + getWidth()) / tileWidth),
								(int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

		}
		/*
		 * Reaccion a colision en X
		 */
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		/*
		 * Movimiento en Y
		 */
		setY(getY() + velocity.y * delta);
		if (velocity.y < 0) {
			// Bottom left
			collisionY = collisionLayer
					.getCell((int) (getX() / tileWidth),
							(int) (getY() / tileHeight)).getTile()
					.getProperties().containsKey("blocked");
			// Bottom middle
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY()) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

			// Bottom right
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth),
								(int) (getY() / tileHeight)).getTile()
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
		 * Reacci√≥n al colisionar en Y
		 */
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}

		/*
		 * Actualizar animacion
		 */
		animationTime += delta;
		if ((WPressed || APressed || SPressed || DPressed)
				&& (!collisionX && !collisionY)
				&& (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
					: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
							: velocity.y > 0 ? espalda
									.getKeyFrame(animationTime) : cara
									.getKeyFrame(animationTime));
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

		/* Eventos con objetos */
		if (collisionX || collisionY) {
			for (MapObject o : objectLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;
				Gdx.app.log("cartel", "distancia " + distance(t));

				/* Dispara evento si estan muy cerca jugador y objeto */
				if (distance(t) < 50) {
					interact(t);
					break;
				}
			}
			for (MapObject o : transLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;

				/* Dispara evento si estan muy cerca jugador y objeto */
				if (distance(t) < 50) {
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Play(Integer.parseInt((String) t
									.getProperties().get("x")), Integer
									.parseInt((String) t.getProperties().get(
											"y")), getLastPressed(), t
									.getProperties().get("mapa") + ".tmx"));
					break;
				}
			}
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
		double dx = Math.pow(getX() - aux, 2);
		double dy = Math.pow(getY() - t.getY(), 2);
		return (int) (Math.sqrt(dx + dy));
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
			play.optionsVisible = true;

			/* Leer cartel */
			String value = (String) obj.getProperties().get("cartel");
			dialogo.procesarDialogo("cartel_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(),
					dialogo.siguienteLinea());
		} else if (obj.getProperties().containsKey("item")) {
			play.optionsVisible = true;
			
			/* Leer objeto recogido */
			String value = (String) obj.getProperties().get("item");
			dialogo.procesarDialogo("item_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(),
					dialogo.siguienteLinea());
			
			/* Introduce en mochila */
			if (value.equals("PociÛn")) {
				mochila.add(new Pocion());
			} else if (value.equals("AntÌdoto")) {
				mochila.add(new Antidoto());
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

}