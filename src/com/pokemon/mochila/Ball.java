package com.pokemon.mochila;

import java.util.Random;

public abstract class Ball extends Item {

	public Ball(String nombre) {
		super(nombre);
	}
	
	public static boolean atrapar(){
		Random random=new Random();
		double atrapar=random.nextDouble();
		if(atrapar>=0.5){
			return true;
		}
		else return false;
	}
	
}
