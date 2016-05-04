package com.pokemon.mochila;

import pokemon.Pokemon;

public class Pocion extends Objeto {

	public Pocion() {
		super("Poción");
	}

	@Override
	public void use(Pokemon pokemon) {
		pokemon.setPs(pokemon.getPs() + 20);
	}

}
