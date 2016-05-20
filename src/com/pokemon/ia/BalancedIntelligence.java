package com.pokemon.ia;

import pokemon.Pokemon;

public class BalancedIntelligence extends PokemonIntelligence {

	public BalancedIntelligence(Pokemon pokemon) {
		super(pokemon);
	}

	/**
	 * The nature of a balanced pokemon moves him to use attacks
	 * that are both powerful and precise, in a balanced way.
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
		
		/* Inits a movement precision array */
		int[] precisions = new int[4];
		for (int i=0; i<precisions.length; i++) {
			if (pokemon.getHabilidades()[i] != null) {
				precisions[i] = pokemon.getHabilidades()[i].getPrecision();
			} else {
				precisions[i] = -1;
			}
		}
		
		/* Calculates a F1-score based on Precision-Power */
		double[] f1 = new double[4];
		for (int i=0; i<f1.length; i++) {
			f1[i] = 2 * powers[i] * precisions[i] / (powers[i] + precisions[i]);
		}
		
		/* Chooses the most voted one */
		double max = 0;
		int iMax = -1;
		for (int i=0; i<f1.length; i++) {
			if (f1[i] > max) {
				max = f1[i];
				iMax = i;
			}
		}
		return iMax;
	}

}
