package com.duoc.seguridadcalidad.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 140)
	private String titulo;

	@Column(length = 80)
	private String videojuego;

	@Column(nullable = false, length = 40)
	private String categoria;

	private LocalDate fecha;

	private LocalTime hora;

	@Column(length = 80)
	private String pais;

	@Column(length = 80)
	private String ciudad;

	@Column(length = 240)
	private String direccion;

	@Column(length = 240)
	private String enlace;

	@Column(length = 20)
	private String modalidad;

	@Column(length = 1500)
	private String descripcion;

	@Column(length = 500)
	private String requisitos;

	@Column(length = 500)
	private String inscripcion;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "evento_organizador", joinColumns = @JoinColumn(name = "evento_id"))
	@Column(name = "nombre", length = 120)
	@OrderColumn(name = "orden")
	private List<String> organizadores = new ArrayList<>();

	private boolean estacionamiento;
	private boolean locomocion;
	private boolean comidas;
	private boolean alojamiento;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "evento_expositor", joinColumns = @JoinColumn(name = "evento_id"))
	@Column(name = "nombre", length = 120)
	@OrderColumn(name = "orden")
	private List<String> expositores = new ArrayList<>();

	private boolean destacado;

	@Column(length = 16)
	private String emoji;

	@Column(length = 80)
	private String foto;

	public static Evento crear(String titulo, String videojuego, String categoria, LocalDate fecha, LocalTime hora,
			String pais, String ciudad, String direccion, String enlace, String modalidad, String descripcion,
			String requisitos, String inscripcion, List<String> organizadores, boolean estacionamiento,
			boolean locomocion, boolean comidas, boolean alojamiento, List<String> expositores, boolean destacado,
			String emoji, String foto) {
		Evento evento = new Evento();
		evento.titulo = titulo;
		evento.videojuego = videojuego;
		evento.categoria = categoria;
		evento.fecha = fecha;
		evento.hora = hora;
		evento.pais = pais;
		evento.ciudad = ciudad;
		evento.direccion = direccion;
		evento.enlace = enlace;
		evento.modalidad = modalidad;
		evento.descripcion = descripcion;
		evento.requisitos = requisitos;
		evento.inscripcion = inscripcion;
		evento.organizadores = new ArrayList<>(organizadores);
		evento.estacionamiento = estacionamiento;
		evento.locomocion = locomocion;
		evento.comidas = comidas;
		evento.alojamiento = alojamiento;
		evento.expositores = new ArrayList<>(expositores);
		evento.destacado = destacado;
		evento.emoji = emoji;
		evento.foto = foto;
		return evento;
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

	public String getVideojuego() {
		return videojuego;
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

	public String getPais() {
		return pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getEnlace() {
		return enlace;
	}

	public String getModalidad() {
		return modalidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public String getInscripcion() {
		return inscripcion;
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

	public String getFoto() {
		return foto;
	}
}
