package com.pokemon.dialogo;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 *         Clase que permite mostrar los dialogos en el juego. Un objeto Dialogo
 *         se creara al principio del juego y permitira acceder a los dialogos
 *         de todos los NPCs a traves de un identificador. Los dialogos se
 *         encuentran almacenados en un fichero de propiedades.
 * 
 *         La clase tambien se encarga de pintar en pantalla el dialogo, de modo
 *         que aparezca paginado segun el usuario lo va leyendo.
 * 
 *         La clase implementa el mecanismo de Internalizacion y Localizacion,
 *         que permitiria traducir el programa sin tocar el codigo fuente. Basta
 *         con crear otro fichero de propiedades para otra localizacion.
 * 
 *         Los ficheros de propiedades deben tener la sintaxis
 *         Dialogos_es_ES.properties, donde <es> es el idioma y <ES> es el pais.
 *
 */
public class Dialogo {

	private String idioma;
	private String pais;
	private Locale locale;
	private ResourceBundle bundle;
	private String[] dialogoActual;
	private int indiceActual;
	private int subIndiceActual;
	private int indicePalabra;

	public Dialogo(String idioma, String pais) {
		this.idioma = idioma;
		this.pais = pais;

		locale = new Locale(idioma, pais);
		bundle = ResourceBundle.getBundle("Dialogos", locale);
	}

	public String getIdioma() {
		return idioma;
	}

	public String getPais() {
		return pais;
	}

	/**
	 * Accede al fichero de propiedades correspondiente segun el Locale
	 * configurado al crear el Dialogo, y devuelve el dialogo identificado por
	 * la id.
	 * 
	 * @param id
	 *            la clave que identifica al dialogo.
	 * @return el dialogo cuya clave es id.
	 */
	public String[] getDialogo(String id) {
		int i = contarFrases(id);
		String[] dialogo = new String[i];
		for (int j = 1; j < i; j++) {
			dialogo[j] = bundle.getString(id + "_" + j);
		}
		dialogoActual = null;
		indiceActual = 1;
		subIndiceActual = 0;
		return dialogo;
	}

	public String getSiguienteLinea(String[] dialogo) {
		String lineaActual = "";
		try {
			lineaActual = dialogo[indiceActual];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		String salida = "";
		String[] palabras = lineaActual.split(" ");
		String check = "";
		try {
			check = (salida + palabras[indicePalabra]);
		} catch (IndexOutOfBoundsException e) {
		}
		while (check.length() < 40) {
			try {
				salida += palabras[indicePalabra] + " ";
				indicePalabra++;
				check = (salida + palabras[indicePalabra]);
			} catch (IndexOutOfBoundsException e) {
				indicePalabra = 0;
				indiceActual++;
				break;
			}
		}
		/*
		 * if(lineaActual.length()<40){ salida =
		 * lineaActual.substring(0,lineaActual.length()); indiceActual++; } else
		 * { try{ salida =
		 * lineaActual.substring(0+(40*subIndiceActual),39+(40*subIndiceActual))
		 * ; subIndiceActual++; } catch(IndexOutOfBoundsException e){ salida =
		 * lineaActual.substring(39+(40*(subIndiceActual-1)),lineaActual.length(
		 * )-1); subIndiceActual = 0; indiceActual++; } }
		 */

		return salida;
	}

	private int contarFrases(String id) {
		int i = 1;
		try {
			while (true) {
				bundle.getString(id + "_" + i);
				i++;
			}
		} catch (MissingResourceException e) {
		}
		return i;
	}

}