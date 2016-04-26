package com.pokemon.mochila;

import pokemon.Pokemon;

public abstract class Item {
	
	private String nombre;
	
	public Item(String nombre) {
		this.nombre= nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public abstract void use(Pokemon pokemon);
	
}
