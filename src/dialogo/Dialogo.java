package dialogo;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * Clase que permite mostrar los dialogos en el juego. Un objeto
 * Dialogo se creara al principio del juego y permitira acceder a
 * los dialogos de todos los NPCs a traves de un identificador.
 * Los dialogos se encuentran almacenados en un fichero de propiedades.
 * 
 * La clase tambien se encarga de pintar en pantalla el dialogo,
 * de modo que aparezca paginado segun el usuario lo va leyendo.
 * 
 * La clase implementa el mecanismo de Internalizacion y
 * Localizacion, que permitiria traducir el programa sin tocar
 * el codigo fuente. Basta con crear otro fichero de propiedades
 * para otra localizacion.
 * 
 * Los ficheros de propiedades deben tener la sintaxis
 * Dialogos_es_ES.properties, donde <es> es el idioma y <ES> es
 * el pais.
 *
 */
public class Dialogo {
	
	private String idioma;
	private String pais;
	private Locale locale;
	private ResourceBundle bundle;
	
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
	 * Accede al fichero de propiedades correspondiente segun
	 * el Locale configurado al crear el Dialogo, y devuelve
	 * el dialogo identificado por la id.
	 * 
	 * @param id la clave que identifica al dialogo.
	 * @return el dialogo cuya clave es id.
	 */
	public String getDialogo(String id) {
		return bundle.getString(id);
	}
	
}