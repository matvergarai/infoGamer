package com.duoc.seguridadcalidad.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 80)
	private String username;

	@Column(nullable = false, length = 120)
	private String password;

	@Column(nullable = false, length = 40)
	private String rol = "USER";

	@Column(nullable = false, length = 80)
	private String nombre;

	@Column(length = 120)
	private String email;

	@Column(length = 40)
	private String nombreUsuario;

	@Column(length = 16)
	private String avatar;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "usuario_juego", joinColumns = @JoinColumn(name = "usuario_id"))
	@Column(name = "juego", length = 80)
	@OrderColumn(name = "orden")
	private List<String> juegos = new ArrayList<>();

	private boolean notifEmail;
	private boolean notifEventos;
	private boolean notifNovedades;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
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
