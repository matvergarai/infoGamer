package com.duoc.seguridadcalidad.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.duoc.seguridadcalidad.service.EventoService;

@Controller
public class EventoController {

	private final EventoService eventoService;

	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@GetMapping("/eventos")
	public String listar(@RequestParam(required = false) String categoria,
			@RequestParam(required = false) String fecha,
			@RequestParam(required = false) String ubicacion,
			Model model) {
		LocalDate fechaBuscada = null;
		if (fecha != null && !fecha.isBlank()) {
			try {
				fechaBuscada = LocalDate.parse(fecha);
			}
			catch (DateTimeParseException ex) {
				model.addAttribute("errorFecha", "La fecha no es válida.");
			}
		}

		String categoriaLimpia = categoria == null ? "" : categoria;
		String ubicacionLimpia = ubicacion == null ? "" : ubicacion;
		model.addAttribute("eventos", eventoService.buscar(categoriaLimpia, fechaBuscada, ubicacionLimpia));
		model.addAttribute("categorias", eventoService.categorias());
		model.addAttribute("categoria", categoriaLimpia);
		model.addAttribute("fecha", fecha == null ? "" : fecha);
		model.addAttribute("ubicacion", ubicacionLimpia);
		model.addAttribute("filtrado", !categoriaLimpia.isBlank() || fechaBuscada != null || !ubicacionLimpia.isBlank());
		return "eventos";
	}

	@GetMapping("/eventos/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		return eventoService.porId(id)
				.map(evento -> {
					model.addAttribute("evento", evento);
					return "evento";
				})
				.orElse("redirect:/eventos?noEncontrado");
	}
}
