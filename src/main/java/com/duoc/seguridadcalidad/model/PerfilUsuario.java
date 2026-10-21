package com.duoc.seguridadcalidad.model;

import java.util.ArrayList;
import java.util.List;

public class PerfilUsuario {

	private String username;
	private String nombre;
	private String email;
	private String nombreUsuario;
	private String avatar;
	private List<String> juegos = new ArrayList<>();
	private boolean notifEmail;
	private boolean notifEventos;
	private boolean notifNovedades;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<String> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<String> juegos) {
		this.juegos = juegos;
	}

	public boolean isNotifEmail() {
		return notifEmail;
	}

	public void setNotifEmail(boolean notifEmail) {
		this.notifEmail = notifEmail;
	}

	public boolean isNotifEventos() {
		return notifEventos;
	}

	public void setNotifEventos(boolean notifEventos) {
		this.notifEventos = notifEventos;
	}

	public boolean isNotifNovedades() {
		return notifNovedades;
	}

	public void setNotifNovedades(boolean notifNovedades) {
		this.notifNovedades = notifNovedades;
	}
}
