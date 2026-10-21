package com.duoc.seguridadcalidad.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Evento {

	private final Long id;
	private final String titulo;
	private final String categoria;
	private final LocalDate fecha;
	private final LocalTime hora;
	private final String ciudad;
	private final String direccion;
	private final String descripcion;
	private final List<String> organizadores;
	private final boolean estacionamiento;
	private final boolean locomocion;
	private final boolean comidas;
	private final boolean alojamiento;
	private final List<String> expositores;
	private final boolean destacado;
	private final String emoji;

	public Evento(Long id, String titulo, String categoria, LocalDate fecha, LocalTime hora,
			String ciudad, String direccion, String descripcion, List<String> organizadores,
			boolean estacionamiento, boolean locomocion, boolean comidas, boolean alojamiento,
			List<String> expositores, boolean destacado, String emoji) {
		this.id = id;
		this.titulo = titulo;
		this.categoria = categoria;
		this.fecha = fecha;
		this.hora = hora;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.descripcion = descripcion;
		this.organizadores = List.copyOf(organizadores);
		this.estacionamiento = estacionamiento;
		this.locomocion = locomocion;
		this.comidas = comidas;
		this.alojamiento = alojamiento;
		this.expositores = List.copyOf(expositores);
		this.destacado = destacado;
		this.emoji = emoji;
	}

	public List<ServicioItem> getServicios() {
		return List.of(
				new ServicioItem("Estacionamiento", estacionamiento),
				new ServicioItem("Locomoción colectiva", locomocion),
				new ServicioItem("Servicio de comidas", comidas),
				new ServicioItem("Alojamiento", alojamiento));
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public List<String> getOrganizadores() {
		return organizadores;
	}

	public boolean isEstacionamiento() {
		return estacionamiento;
	}

	public boolean isLocomocion() {
		return locomocion;
	}

	public boolean isComidas() {
		return comidas;
	}

	public boolean isAlojamiento() {
		return alojamiento;
	}

	public List<String> getExpositores() {
		return expositores;
	}

	public boolean isDestacado() {
		return destacado;
	}

	public String getEmoji() {
		return emoji;
	}
}
