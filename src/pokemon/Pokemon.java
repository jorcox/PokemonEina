package pokemon;

import habilidad.Habilidad;
import logica.Tipo;

public class Pokemon {
	
	private String nombre;
	private int nivel;
	private Tipo tipo;
	private int ataque;
	private int defensa;
	private int ataqueEsp;
	private int defensaEsp;
	private int velocidad;
	private int precision;
	private int evasion;
	private int indiceCritico;
	private Habilidad[] habilidades;
	private int psMax;
	private int ps;
	private int experiencia;
	private int estado;
	private int id;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getEvasion() {
		return evasion;
	}
	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}
	public int getIndiceCritico() {
		return indiceCritico;
	}
	public void setIndiceCritico(int indiceCritico) {
		this.indiceCritico = indiceCritico;
	}
	public Habilidad[] getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(Habilidad[] habilidades) {
		this.habilidades = habilidades;
	}
	public Habilidad getHabilidad(int indice) {
		return habilidades[indice - 1];
	}
	public Habilidad getHabilidad1() {
		return habilidades[0];
	}
	public Habilidad getHabilidad2() {
		return habilidades[1];
	}
	public Habilidad getHabilidad3() {
		return habilidades[2];
	}
	public Habilidad getHabilidad4() {
		return habilidades[3];
	}
	public int getPsMax() {
		return psMax;
	}
	public void setPsMax(int psMax) {
		this.psMax = psMax;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public void restarPs(int ps) {
		this.ps = this.ps - ps;
		if (this.ps < 0) {
			this.ps = 0;
		}
	}
	public boolean vivo() {
		return ps>0;
	}
	public int getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
