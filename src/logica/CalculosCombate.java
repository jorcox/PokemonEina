package logica;

import java.util.Random;

import habilidad.Categoria;
import habilidad.Habilidad;
import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * Clase que contiene los calculos que hay que realizar en un combate
 * pokemon, para calcular la probabilidad de critico, la probabilidad
 * de fallo, los puntos de vida que quita una habilidad, etc.
 *
 */
public class CalculosCombate {
	
	/**
	 * 
	 * Calcula los puntos de vida que un pokemon le quita a otro al
	 * realizar una habilidad contra el. Esta habilidad debe ser
	 * fisica o especial, pero no de estado pues estas no quitan vida. 
	 * 
	 * @param atacante el pokemon que realiza el ataque.
	 * @param oponente el pokemon que recibe el ataque y perdera vida.
	 * @param habilidad la habilidad que el atacante lanza al oponente.
	 * @return la cantidad de puntos de vida que hay que restar al oponente.
	 */
	public static int calcularDano(Pokemon atacante, Pokemon oponente, Habilidad habilidad) {
		/*
		 * n = nivel del atacante.
		 * a = ataque (fisico o especial, segun la habilidad) del atacante.
		 * p = poder de la habilidad.
		 * d = defensa (fisica o especial, segun la habilidad) del oponente.
		 * b = bonificador del 50% si el atacante y la habilidad tienen el mismo tipo.
		 * e = efectividad del tipo de la habilidad frente al tipo del oponente.
		 * v = variacion aleatoria (entre el 85% y el 100%).
		 */
		int n = atacante.getNivel();
		int a = (habilidad.getTipo().equals(Categoria.FISICO)) ? 
				atacante.getAtaque() : atacante.getAtaqueEsp();
		int p = habilidad.getPoder();
		int d = (habilidad.getTipo().equals(Categoria.FISICO)) ?
				oponente.getDefensa() : oponente.getDefensaEsp();
		double b = (atacante.getTipo().equals(habilidad.getTipo())) ? 1.5 : 1;
		double e = MatrizEfectividad.getEfectividad(habilidad, oponente);
		int v = calcularVariacion();
		
		/* Hace el calculo con reales y lo redondea porque los hp son enteros */
		double puntos = 0.01 * b * e * v * (((0.2 * n + 1) * a * p) / (25 * d) + 2);
		return (int) Math.round(puntos);
	}
	
	/**
	 * Calcula la variacion del ataque, que es un valor aleatorio que modifica
	 * ligeramente el ataque final, entre el 85% y el 100% del ataque teorico.
	 * 
	 * @return un numero entre 85 y 100.
	 */
	private static int calcularVariacion() {
		Random random = new Random();
		return random.nextInt(16) + 85;
	}
	
}
