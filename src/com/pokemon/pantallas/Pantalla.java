package com.pokemon.pantallas;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.pokemon.utilidades.ArchivoGuardado;

public abstract class Pantalla implements Screen, InputProcessor {
	
	private ArchivoGuardado ctx;

	public void setCtx(ArchivoGuardado ctx) {
		this.ctx = ctx;
	}

	public ArchivoGuardado getCtx() {
		return ctx;
	}
	
}
