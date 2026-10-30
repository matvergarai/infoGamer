package com.duoc.seguridadcalidad.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.duoc.seguridadcalidad.model.Evento;
import com.duoc.seguridadcalidad.model.EventoResumen;
import com.duoc.seguridadcalidad.service.EventoService;

@RestController
public class EventoApiController {

	private final EventoService eventoService;

	public EventoApiController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@GetMapping("/api/eventos")
	public List<EventoResumen> buscar(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String videojuego, @RequestParam(required = false) String categoria,
			@RequestParam(required = false) String lugar, @RequestParam(required = false) String fecha,
			@RequestParam(required = false) String modalidad) {
		LocalDate fechaBuscada = fecha == null || fecha.isBlank() ? null : LocalDate.parse(fecha);
		return eventoService.buscar(nombre, videojuego, categoria, lugar, fechaBuscada, modalidad).stream()
				.map(EventoResumen::de)
				.toList();
	}

	@GetMapping("/api/eventos/{id}")
	public Evento detalle(@PathVariable Long id) {
		return eventoService.porId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
