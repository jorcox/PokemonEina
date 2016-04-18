package com.pokemon.entities;

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
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.utilidades.ArchivoGuardado;

public class Player extends Sprite implements InputProcessor {

	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2, gravity = 60 * 1.8f, animationTime = 0;

	private Animation cara, izquierda, derecha, espalda;
	private TiledMapTileLayer collisionLayer;
	private MapLayer objectLayer;
	private Dialogo dialogo;

	private boolean APressed = false, WPressed = false, SPressed = false, DPressed = false, SpacePressed=false;
	private int lastPressed; //A=1, W=2, S=3, D=4

	public Player(Animation cara, Animation izquierda, Animation derecha, Animation espalda,
			TiledMapTileLayer collisionLayer, MapLayer objectLayer, Dialogo dialogo) {
		super(cara.getKeyFrame(0));
		this.cara = cara;
		this.izquierda = izquierda;
		this.derecha = derecha;
		this.espalda = espalda;
		this.collisionLayer = collisionLayer;
		this.objectLayer = objectLayer;
		this.dialogo = dialogo;
		
		Gdx.input.setInputProcessor(this);
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
		boolean collisionX = false, collisionY = false;

		/*
		 * Movimiento en X
		 */
		setX(getX() + velocity.x * delta);

		if (velocity.x < 0) {
			// Top left
			//collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
			//		.getTile().getProperties().containsKey("blocked");
			// Middle left
			if (!collisionX)
				collisionX |= collisionLayer
						.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

			// Bottom left
			if (!collisionX)
				collisionX |= collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");

		} else if (velocity.x > 0) {
			// Top right
			//collisionX = collisionLayer
			//		.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
			//		.getTile().getProperties().containsKey("blocked");
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
			//collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
			//		.getTile().getProperties().containsKey("blocked");
			// Top middle
			if (!collisionY)
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");

			// Top right
			//if (!collisionY)
			//	collisionY |= collisionLayer
			//			.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
			//			.getTile().getProperties().containsKey("blocked");
		}
		/*
		 * Reacción al colisionar en Y
		 */
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		/*
		 * Actualizar animacion
		 */
		animationTime += delta;
		if ((WPressed || APressed || SPressed || DPressed) && (!collisionX && !collisionY) && (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(
					velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
							: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
									: velocity.y > 0 ? espalda.getKeyFrame(animationTime)
											: cara.getKeyFrame(animationTime));
		} else {
			switch (lastPressed){
			case 1 : //A
				setRegion(izquierda.getKeyFrame(0));
				break;
			case 2 : //W
				setRegion(espalda.getKeyFrame(0));
				break;
			case 3 : //S
				setRegion(cara.getKeyFrame(0));
				break;
			case 4 : //D
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
		}
		
	}
	
	/**
	 * Calcula la distancia entre el jugador y el objeto de texturas t.
	 * 
	 * @param t el objeto de texturas.
	 * @return la distancia euclidea.
	 */
	private int distance(TextureMapObject t) {
		double aux = t.getX() - 160;
		double dx = Math.pow(getX() - aux, 2);
		double dy = Math.pow(getY() - t.getY(), 2);
		return (int) (Math.sqrt(dx + dy));
	}

	/**
	 * Gestiona las interacciones entre el jugador y el objeto obj, que
	 * puede ser un cartel, pokeball, etc.
	 * 
	 * @param obj el objeto declarado en la capa de objetos.
	 */
	private void interact(TextureMapObject obj) {
		if (obj.getProperties().containsKey("cartel")) {
			String value = (String) obj.getProperties().get("cartel");
			dialogo.procesarDialogo("cartel_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
		}
	}

	public boolean getSpacePressed(){
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

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			velocity.y = speed;
			velocity.x = 0;
			animationTime = 0;
			if(lastPressed==0)
				lastPressed = 2;
			WPressed = true;
			break;
		case Keys.A:
			velocity.x = -speed;
			velocity.y = 0;
			animationTime = 0;
			if(lastPressed==0)
				lastPressed = 1;
			APressed = true;
			break;
		case Keys.S:
			velocity.y = -speed;

			velocity.x = 0;
			animationTime = 0;
			if(lastPressed==0)
				lastPressed = 3;
			SPressed = true;
			break;
		case Keys.D:
			velocity.x = speed;
			velocity.y = 0;
			animationTime = 0;
			if(lastPressed==0)
				lastPressed = 4;
			DPressed = true;
			break;
		case Keys.SPACE:
			SpacePressed=true;
			break;
		case Keys.ENTER:
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
					 * script[counter] = script[counter].replace( "(OPTION)",
					 * ""); optionsVisible = true; }
					 */
				} else {
					dialogo.limpiar();
				}
			}
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			if (SPressed) {
				velocity.x = 0;
				velocity.y = -speed;
			} else if(APressed) {
				velocity.y = 0;
				velocity.x = -speed;				
			} else if(DPressed) {
				velocity.y = 0;
				velocity.x = speed;				
			} else{
				velocity.y = 0;
			}
			animationTime = 0;
			lastPressed = 2;
			WPressed = false;
			break;
		case Keys.A:
			if (DPressed) {
				velocity.y = 0;
				velocity.x = speed;
			} else if(WPressed) {
				velocity.x = 0;
				velocity.y = speed;				
			} else if(SPressed) {
				velocity.x = 0;
				velocity.y = -speed;				
			} else{
				velocity.x = 0;
			}
			animationTime = 0;
			lastPressed = 1;
			APressed = false;
			break;
		case Keys.S:
			if (WPressed) {
				velocity.x = 0;
				velocity.y = speed;
			} else if(APressed) {
				velocity.y = 0;
				velocity.x = -speed;				
			} else if(DPressed) {
				velocity.y = 0;
				velocity.x = speed;				
			} else{
				velocity.y = 0;
			}
			animationTime = 0;
			lastPressed = 3;
			SPressed = false;
			break;
		case Keys.D:
			if (APressed) {
				velocity.y = 0;
				velocity.x = -speed;
			} else if(WPressed) {
				velocity.x = 0;
				velocity.y = speed;				
			} else if(SPressed) {
				velocity.x = 0;
				velocity.y = -speed;				
			} else{
				velocity.x = 0;
			}
			animationTime = 0;
			lastPressed = 4;
			DPressed = false;
			break;
		case Keys.SPACE:
			SpacePressed=false;
			break;
		}
		return true;
	}
	
	public int getLastPressed(){
		return lastPressed;
	}
	
	public void setLastPressed(int lastPressed){
		this.lastPressed=lastPressed;
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