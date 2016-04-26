package com.pokemon.mochila;

import pokemon.Pokemon;

public class Pocion extends Objeto {

	private int ps;
	
	public Pocion(String nombre, int ps) {
		super(nombre);
		this.ps = ps;
	}

	@Override
	public void use(Pokemon pokemon) {
		pokemon.setPs(pokemon.getPs() + ps);
	}

}
