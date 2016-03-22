package habilidad;

import logica.Tipo;

public abstract class Habilidad {
	
	private String nombre;
	private String descripcion;
	private int poder;
	private int precision;
	private Tipo tipo;
	private Categoria categoria;
	
	public Habilidad(String nombre, int poder, int precision,
			Tipo tipo, Categoria categoria){
		this.nombre = nombre;
		this.poder = poder;
		this.precision = precision;
		this.tipo = tipo;
		this.categoria = categoria;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPoder() {
		return poder;
	}
	public void setPoder(int poder) {
		this.poder = poder;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
