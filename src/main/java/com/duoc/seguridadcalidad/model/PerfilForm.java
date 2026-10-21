package com.duoc.seguridadcalidad.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PerfilForm {

	@NotBlank(message = "Elige un nombre de usuario")
	@Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Usa entre 3 y 20 letras, números o guion bajo")
	private String nombreUsuario;

	@NotBlank(message = "Elige un avatar")
	private String avatar;

	private List<String> juegos = new ArrayList<>();

	@Size(max = 80, message = "El juego adicional es demasiado largo")
	private String otroJuego;

	private boolean notifEmail;
	private boolean notifEventos;
	private boolean notifNovedades;

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

	public String getOtroJuego() {
		return otroJuego;
	}

	public void setOtroJuego(String otroJuego) {
		this.otroJuego = otroJuego;
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
