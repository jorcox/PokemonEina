package com.pokemon.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

	public TextureMapObjectRenderer(TiledMap map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderObject(MapObject object) {
		if (object instanceof TextureMapObject) {
			TextureMapObject texture = (TextureMapObject) object;
			if (texture.getProperties().containsKey("mostrar")
					&& texture.getProperties().get("mostrar").equals("false")) {
				return;
			}
			batch.draw(texture.getTextureRegion(), texture.getX(), texture.getY());
		}
	}
}
