package pokemon;

import logica.Tipo;

public class Pokemon {
	
	private int nivel;
	private Tipo tipo;
	private int ataque;
	private int defensa;
	private int ataqueEsp;
	private int defensaEsp;
	private int velocidad;
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getDefensa() {
		return defensa;
	}
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	public int getAtaqueEsp() {
		return ataqueEsp;
	}
	public void setAtaqueEsp(int ataqueEsp) {
		this.ataqueEsp = ataqueEsp;
	}
	public int getDefensaEsp() {
		return defensaEsp;
	}
	public void setDefensaEsp(int defensaEsp) {
		this.defensaEsp = defensaEsp;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	
}
