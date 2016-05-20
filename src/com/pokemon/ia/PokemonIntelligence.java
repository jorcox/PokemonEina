package com.pokemon.ia;

import habilidad.Habilidad;
import logica.MatrizEfectividad;
import pokemon.Pokemon;

/**
 * A pokemon intelligence defines how it chooses its attacks
 * on combat.
 */
public abstract class PokemonIntelligence {
	
	protected Pokemon pokemon;
	
	public PokemonIntelligence(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	
	public abstract int votePerNature();
	
	/**
	 * Effective attacks against the opponent have more chances to
	 * be chosen.
	 */
	public int votePerType(Pokemon opponent) {
		/* Inits an effectivity array for every movement */
		double[] values = new double[4];
		
		Habilidad[] habs = pokemon.getHabilidades();
		for (int i=0; i<habs.length; i++) {
			if (habs[i] != null) {
				values[i] = MatrizEfectividad.getEfectividad(habs[i], opponent);
			}
		}
		
		/* Chooses the most voted one */
		double max = 0;
		int iMax = -1;
		for (int i=0; i<values.length; i++) {
			if (values[i] > max) {
				max = values[i];
				iMax = i;
			}
		}
		return iMax;
	}
	
}
