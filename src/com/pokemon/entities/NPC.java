package com.pokemon.entities;

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
import com.pokemon.pantallas.Play;

public class NPC extends Sprite {

	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2;

	private float gravity = 60 * 1.8f;

	public float animationTime = 0;

	private Animation cara, izquierda, derecha, espalda;
	private TiledMapTileLayer collisionLayer;
	private MapLayer objectLayer;
	private MapLayer transLayer;
	private Dialogo dialogo;
	private Play play;

	private boolean APressed = false;

	private boolean WPressed = false;

	private boolean SPressed = false;

	private boolean DPressed = false;

	private boolean SpacePressed = false;
	
	private int lastPressed; // A=1, W=2, S=3, D=4

	private Mochila mochila;
	
	private int distanciaVision;
	
	private String direccionVision;
	
	private Player player;
	

	public NPC(TextureAtlas playerAtlas, Animation face, String dir, int disVista, Play play) {
		super(face.getKeyFrame(0));
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
		this.distanciaVision = disVista;
		this.direccionVision = dir;
		this.player = player;
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
		float oldX = getX(), oldY = getY();
		//tileWidth = collisionLayer.getTileWidth(),
		//		tileHeight = collisionLayer.getTileHeight();
		//boolean collisionX = false, collisionY = false;

		/*
		 * Movimiento en X
		 */
		setX(getX() + velocity.x * delta);

//		if (velocity.x < 0) {
//			// Top left
//			// collisionX = collisionLayer.getCell((int) (getX() / tileWidth),
//			// (int) ((getY() + getHeight()) / tileHeight))
//			// .getTile().getProperties().containsKey("blocked");
//			// Middle left
//			if (!collisionX)
//				collisionX |= collisionLayer
//						.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile()
//						.getProperties().containsKey("blocked");
//
//			// Bottom left
//			if (!collisionX)
//				collisionX |= collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
//						.getProperties().containsKey("blocked");
//
//		} else if (velocity.x > 0) {
//			// Top right
//			// collisionX = collisionLayer
//			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
//			// ((getY() + getHeight()) / tileHeight))
//			// .getTile().getProperties().containsKey("blocked");
//			// Middle right
//			if (!collisionX)
//				collisionX |= collisionLayer
//						.getCell((int) ((getX() + getWidth()) / tileWidth),
//								(int) ((getY() + getHeight() / 2) / tileHeight))
//						.getTile().getProperties().containsKey("blocked");
//
//			// Bottom right
//			if (!collisionX)
//				collisionX |= collisionLayer
//						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
//						.getProperties().containsKey("blocked");
//
//		}
//		/*
//		 * Reaccion a colision en X
//		 */
//		if (collisionX) {
//			setX(oldX);
//			velocity.x = 0;
//		}
//
//		/*
//		 * Movimiento en Y
//		 */
		setY(getY() + velocity.y * delta);
//		if (velocity.y < 0) {
//			// Bottom left
//			collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
//					.getProperties().containsKey("blocked");
//			// Bottom middle
//			if (!collisionY)
//				collisionY |= collisionLayer
//						.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile()
//						.getProperties().containsKey("blocked");
//
//			// Bottom right
//			if (!collisionY)
//				collisionY |= collisionLayer
//						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
//						.getProperties().containsKey("blocked");
//
//		} else if (velocity.y > 0) {
//			// Top left
//			// collisionY = collisionLayer.getCell((int) (getX() / tileWidth),
//			// (int) ((getY() + getHeight()) / tileHeight))
//			// .getTile().getProperties().containsKey("blocked");
//			// Top middle
//			if (!collisionY)
//				collisionY |= collisionLayer
//						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
//								(int) ((getY() + getHeight() / 2) / tileHeight))
//						.getTile().getProperties().containsKey("blocked");
//
//			// Top right
//			// if (!collisionY)
//			// collisionY |= collisionLayer
//			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
//			// ((getY() + getHeight()) / tileHeight))
//			// .getTile().getProperties().containsKey("blocked");
//		}
//		/*
//		 * Reacción al colisionar en Y
//		 */
//		if (collisionY) {
//			setY(oldY);
//			velocity.y = 0;
//		}
//
		
		boolean collisionPlayer = false;
		 /*
		  * Colision de NPC
		  */
			int anchura = 32;
			int altura = 32;
			
			collisionPlayer |= ((player.getX() + anchura / 1.5) > getX())
					&& ((player.getX() - anchura / 1.5) < getX())
					&& ((player.getY() + altura / 1.5) > getY())
					&& ((player.getY() - altura / 0.9) < getY());
					
			if (collisionPlayer) {
				setX(oldX);
				setY(oldY);
				velocity.x = 0;
				velocity.y = 0;
				// TODO empezar combate
			}
		/*
		 * Actualizar animacion
		 */
		animationTime += delta;
		if (!collisionPlayer
				&& (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(
					velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
							: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
									: velocity.y > 0 ? espalda.getKeyFrame(animationTime)
											: cara.getKeyFrame(animationTime));
		} else {
			
			if(direccionVision.equals("cara")){
				setRegion(cara.getKeyFrame(0));
			} else if(direccionVision.equals("espalda")){
				setRegion(espalda.getKeyFrame(0));
			} else if(direccionVision.equals("izquierda")){
				setRegion(izquierda.getKeyFrame(0));
			} else if(direccionVision.equals("derecha")){
				setRegion(derecha.getKeyFrame(1));
			}	
		}
//			switch (lastPressed) {
//			case 1: // A
//				setRegion(izquierda.getKeyFrame(0));
//				break;
//			case 2: // W
//				setRegion(espalda.getKeyFrame(0));
//				break;
//			case 3: // S
//				setRegion(cara.getKeyFrame(0));
//				break;
//			case 4: // D
//				setRegion(derecha.getKeyFrame(1));
//				break;
//			}
//		}
//
//		/* Eventos con objetos */
//		if (collisionX || collisionY) {
//			for (MapObject o : objectLayer.getObjects()) {
//				TextureMapObject t = (TextureMapObject) o;
//				Gdx.app.log("cartel", "distancia " + distance(t));
//
//				/* Dispara evento si estan muy cerca jugador y objeto */
//				if (distance(t) < 50) {
//					interact(t);
//					break;
//				}
//			}
//			for (MapObject o : transLayer.getObjects()) {
//				TextureMapObject t = (TextureMapObject) o;
//
//				/* Dispara evento si estan muy cerca jugador y objeto */
//				if (distance(t) < 50) {
//					((Game) Gdx.app.getApplicationListener())
//							.setScreen(new Play(Integer.parseInt((String) t.getProperties().get("x")),
//									Integer.parseInt((String) t.getProperties().get("y")), getLastPressed(),
//									t.getProperties().get("mapa") + ".tmx"));
//					break;
//				}
//			}
//		}

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
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
		} else if (obj.getProperties().containsKey("item")) {
			play.optionsVisible = true;

			/* Leer objeto recogido */
			String value = (String) obj.getProperties().get("item");
			dialogo.procesarDialogo("item_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());

			/* Introduce en mochila */
			if (value.equals("Poci�n")) {
				mochila.add(new Pocion());
			} else if (value.equals("Ant�doto")) {
				mochila.add(new Antidoto());
			}

		}
	}
	

	public void moverAPersonaje(Player player) {
		if(direccionVision.equals("cara")){
			velocity.y = -speed;
		} else if(direccionVision.equals("espalda")){
			velocity.y = speed;
		} else if(direccionVision.equals("izquierda")){
			velocity.x = -speed;
		} else if(direccionVision.equals("derecha")){
			velocity.x = speed;
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

	public int getDistanciaVision() {
		return distanciaVision;
	}

	public void setDistanciaVision(int distanciaVision) {
		this.distanciaVision = distanciaVision;
	}

	public String getDireccionVision() {
		return direccionVision;
	}

	public void setDireccionVision(String direccionVision) {
		this.direccionVision = direccionVision;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
