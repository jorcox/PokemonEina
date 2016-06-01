package com.pokemon.ia;

import pokemon.Pokemon;

public class RecklessIntelligence extends PokemonIntelligence {

	public RecklessIntelligence(Pokemon pokemon) {
		super(pokemon);
	}

	/**
	 * The nature of a reckless pokemon moves him to use the
	 * most powerful attack.
	 */
	@Override
	public int votePerNature() {
		/* Inits a movement power array */
		int[] powers = new int[4];
		for (int i=0; i<powers.length; i++) {
			if (pokemon.getHabilidades()[i] != null) {
				powers[i] = pokemon.getHabilidades()[i].getPoder();
			} else {
				powers[i] = -1;
			}
		}
		
		/* Chooses the most voted one */
		double max = 0;
		int iMax = -1;
		for (int i=0; i<powers.length; i++) {
			if (powers[i] > max) {
				max = powers[i];
				iMax = i;
			}
		}
		return iMax;
	}

}
