package com.duoc.seguridadcalidad.model;

public class ServicioItem {

	private final String nombre;
	private final boolean disponible;

	public ServicioItem(String nombre, boolean disponible) {
		this.nombre = nombre;
		this.disponible = disponible;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isDisponible() {
		return disponible;
	}
}
