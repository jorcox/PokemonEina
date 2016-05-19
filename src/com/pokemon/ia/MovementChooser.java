package com.pokemon.ia;

import java.util.Arrays;
import java.util.Random;

import logica.Tipo;
import pokemon.Pokemon;

/**
 * Given an NPC pokemon, chooses an intelligence for it and
 * makes decisions regarding its movements in combat.
 */
public class MovementChooser {
	
	/* Types are grouped into categories regarding its nature */
	private static final Tipo[] TIPOS_TEMERARIOS = 
			{Tipo.DRAGON, Tipo.FUEGO, Tipo.VOLADOR, Tipo.SINIESTRO, 
					Tipo.LUCHA, Tipo.VENENO, Tipo.ELECTRICO};
	private static final Tipo[] TIPOS_EQUILIBRADOS = 
		{Tipo.AGUA, Tipo.NORMAL, Tipo.PSIQUICO, Tipo.HIELO, Tipo.FANTASMA};
	private static final Tipo[] TIPOS_CAUTOS = 
		{Tipo.PLANTA, Tipo.ROCA, Tipo.TIERRA, Tipo.HADA, 
				Tipo.BICHO, Tipo.ACERO};
	
	private Pokemon pokemon;
	private Pokemon opponent;
	private PokemonIntelligence intelligence;
	
	public MovementChooser(Pokemon pokemon, Pokemon opponent) {		
		this.pokemon = pokemon;
		this.opponent = opponent;
		
		Tipo tipo = pokemon.getTipo();
		if (Arrays.asList(TIPOS_TEMERARIOS).contains(tipo)) {
			intelligence = new RecklessIntelligence(pokemon);
		} else if (Arrays.asList(TIPOS_EQUILIBRADOS).contains(tipo)) {
			intelligence = new BalancedIntelligence(pokemon);
		} else if (Arrays.asList(TIPOS_CAUTOS).contains(tipo)) {
			intelligence = new CautiousIntelligence(pokemon);
		}
	}

	/**
	 * The pokemon uses chance and intelligence to choose an
	 * attack.
	 */
	public int choose() {
		/* Inits a votes array for every movement */
		int[] votes = new int[4];
		for (int i=0; i<votes.length; i++) {
			votes[i] = 1;
		}
		
		/* 
		 * Votes are influenced by pokemon nature, the opponent type
		 * and randomness.
		 */
		int x = intelligence.votePerNature();
		votes[x]++;
		int y = intelligence.votePerType(opponent);
		votes[y]++;
		int z = randomVote();
		votes[z]++;
		
		/* 
		 * Creates a vote distribution array where every appearance
		 * is one vote for an attack.
		 */
		int total = 0;
		for (int v : votes) {
			total += v;
		}
		int[] voteDistribution = new int[total];
		int actual = 0;
		for (int i=0; i<votes.length; i++) {
			for (int j=0; j<votes[i]; j++) {
				voteDistribution[actual] = i;
				actual++;
			}
		}
		
		/* The most voted attacks are the more likely ones */
		int choice = 0;
		boolean chosen = false;
		while (!chosen) {
			choice = voteDistribution[new Random().nextInt(voteDistribution.length)];
			if (pokemon.getHabilidades()[choice] != null) {
				chosen = true;
			}
		}
		
		return choice;
	}
	
	/**
	 * Gives a random vote to a movement.
	 */
	private int randomVote() {
		int l = pokemon.getHabilidades().length;
		return new Random().nextInt(l);
	}
	
}
