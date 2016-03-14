package com.pokemon.desktop;
 
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pokemon.PokemonAdaByron;
 
public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Pokemon Ada Byron";
        config.useGL30 = false;
        config.width = 1000;
        config.height = 700;
       
        new LwjglApplication(new PokemonAdaByron(), config);
    }
}