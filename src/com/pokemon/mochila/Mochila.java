package com.pokemon.mochila;

import java.util.ArrayList;

public class Mochila {
	
	private ArrayList<Objeto> objetos;
	private ArrayList<Ball> balls;
	private ArrayList<MO> mos;
	
	public Mochila() {
		objetos = new ArrayList<>();
		balls = new ArrayList<>();
		mos = new ArrayList<>();
	}
	
	public void add(Objeto obj) {
		objetos.add(obj);
	}
	
	public void add(Ball ball) {
		balls.add(ball);
	}
	
	public void add(MO mo) {
		mos.add(mo);
	}
	
}
