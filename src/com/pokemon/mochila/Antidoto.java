package com.pokemon.mochila;

import pokemon.Pokemon;

public class Antidoto extends Objeto {

	public Antidoto() {
		super("Ant�doto");
	}

	@Override
	public void use(Pokemon pokemon) {
		pokemon.setEstado(0);
	}
	
	
	
}
