package com.pokemon.ia;

import pokemon.Pokemon;

public class CautiousIntelligence extends PokemonIntelligence {

	public CautiousIntelligence(Pokemon pokemon) {
		super(pokemon);
	}

	/**
	 * The nature of a cautious pokemon moves him to use the most
	 * precise attack.
	 */
	@Override
	public int votePerNature() {
		/* Inits a movement precision array */
		int[] precisions = new int[4];
		for (int i=0; i<precisions.length; i++) {
			if (pokemon.getHabilidades()[i] != null) {
				precisions[i] = pokemon.getHabilidades()[i].getPrecision();
			} else {
				precisions[i] = -1;
			}
		}
		
		/* Chooses the most voted one */
		double max = 0;
		int iMax = -1;
		for (int i=0; i<precisions.length; i++) {
			if (precisions[i] > max) {
				max = precisions[i];
				iMax = i;
			}
		}
		return iMax;
	}

}
