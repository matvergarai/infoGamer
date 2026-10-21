package com.duoc.seguridadcalidad.model;

public class Marca {

	private final String nombre;
	private final String rubro;
	private final String frase;
	private final String tono;

	public Marca(String nombre, String rubro, String frase, String tono) {
		this.nombre = nombre;
		this.rubro = rubro;
		this.frase = frase;
		this.tono = tono;
	}

	public String getNombre() {
		return nombre;
	}

	public String getRubro() {
		return rubro;
	}

	public String getFrase() {
		return frase;
	}

	public String getTono() {
		return tono;
	}
}
