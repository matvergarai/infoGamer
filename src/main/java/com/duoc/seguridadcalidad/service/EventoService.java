package com.duoc.seguridadcalidad.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.seguridadcalidad.model.Evento;
import com.duoc.seguridadcalidad.model.Marca;
import com.duoc.seguridadcalidad.repository.EventoRepository;

@Service
public class EventoService {

	private final EventoRepository eventos;

	private final List<Marca> marcas = List.of(
			new Marca("VoltGear", "Periféricos", "Teclados y mouse pensados para una LAN que se alarga.", "volt"),
			new Marca("PixelFuel", "Bebidas", "Energía para la final, sin prometer milagros de FPS.", "fuel"),
			new Marca("SombraByte", "Hardware", "Torres armadas para el próximo parche pesado.", "sombra"),
			new Marca("NeonForge", "Setups", "Sillas y escritorios que aguantan un alargue de tres mapas.", "neon"));

	public EventoService(EventoRepository eventos) {
		this.eventos = eventos;
	}

	@Transactional(readOnly = true)
	public List<Evento> proximos() {
		LocalDate hoy = LocalDate.now();
		return eventos.findAll().stream()
				.filter(evento -> !evento.getFecha().isBefore(hoy))
				.sorted(Comparator.comparing(Evento::getFecha).thenComparing(Evento::getHora))
				.limit(4)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<Evento> destacados() {
		return eventos.findAll().stream()
				.filter(Evento::isDestacado)
				.sorted(Comparator.comparing(Evento::getFecha))
				.toList();
	}

	@Transactional(readOnly = true)
	public List<Evento> buscar(String categoria, LocalDate fecha, String ubicacion) {
		String ciudad = ubicacion == null ? "" : ubicacion.trim().toLowerCase();
		return eventos.findAll().stream()
				.filter(evento -> categoria == null || categoria.isBlank()
						|| evento.getCategoria().equalsIgnoreCase(categoria))
				.filter(evento -> fecha == null || evento.getFecha().equals(fecha))
				.filter(evento -> ciudad.isEmpty() || evento.getCiudad().toLowerCase().contains(ciudad))
				.sorted(Comparator.comparing(Evento::getFecha).thenComparing(Evento::getHora))
				.toList();
	}

	@Transactional(readOnly = true)
	public java.util.Optional<Evento> porId(Long id) {
		return eventos.findById(id);
	}

	@Transactional(readOnly = true)
	public List<String> categorias() {
		return eventos.findAll().stream().map(Evento::getCategoria).distinct().sorted().toList();
	}

	public List<Marca> marcas() {
		return marcas;
	}
}
