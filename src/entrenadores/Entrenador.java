package entrenadores;

import java.util.List;

import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * Clase que representa al entrenador pokemon, tanto si se
 * trata del jugador como de NPCs. Un Entrenador tiene un nombre,
 * un genero y una lista de pokemon que utilizara en combate.
 *
 */
public abstract class Entrenador {
	
	private String nombre;
	private boolean esHombre;
	private List<Pokemon> equipo;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Pokemon> getEquipo() {
		return equipo;
	}
	
	public void setEquipo(List<Pokemon> equipo) {
		this.equipo = equipo;
	}

	public boolean isHombre() {
		return esHombre;
	}

	public void setHombre(boolean genero) {
		this.esHombre = genero;
	}
	
	/**
	 * 
	 * Un entrenador se considera vivo si alguno de sus pokemon
	 * esta vivo. En caso contrario, esta debilitado y su combate
	 * termina.
	 * 
	 * @return true, si tiene algun pokemon vivo; false, en otro caso.
	 */
	public boolean vivo() {
		boolean vivo = true;
		for (int i=0; i<equipo.size() && vivo; i++) {
			if (!equipo.get(i).vivo()) { 
				vivo = false;
			}
		}
		return vivo;
	}
	
}
