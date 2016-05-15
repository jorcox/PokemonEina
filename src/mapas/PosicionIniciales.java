package mapas;

import java.util.HashMap;

public class PosicionIniciales {

	private HashMap<String, Posicion> map;

	public PosicionIniciales() {
		map.put("Tranvia_n.tmx", new Posicion(200, 300));
		map.put("Terraza.tmx", new Posicion(400, 2900));
		map.put("Redes.tmx", new Posicion(1250, 2500));
		map.put("Hardware.tmx", new Posicion(550, 2400));
		map.put("Pasillo.tmx", new Posicion(700, 1850));
		map.put("Liga.tmx", new Posicion(1500, 100));
		map.put("Lab1.tmx", new Posicion(800, 2300));
		
		map.put("Terraza.tmx", new Posicion(100, 1000));
		map.put("Terraza.tmx", new Posicion(400, 2900));
		map.put("Terraza.tmx", new Posicion(400, 2900));
		map.put("Terraza.tmx", new Posicion(400, 2900));
	}
	
	public HashMap<String,Posicion> getHashMap(){
		return map;
	}

}
