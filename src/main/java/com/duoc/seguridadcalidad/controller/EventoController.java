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
	public String listar(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String videojuego, @RequestParam(required = false) String categoria,
			@RequestParam(required = false) String lugar, @RequestParam(required = false) String fecha,
			@RequestParam(required = false) String modalidad, Model model) {
		LocalDate fechaBuscada = null;
		if (fecha != null && !fecha.isBlank()) {
			try {
				fechaBuscada = LocalDate.parse(fecha);
			}
			catch (DateTimeParseException ex) {
				model.addAttribute("errorFecha", "La fecha no es válida.");
			}
		}

		String nombreLimpio = texto(nombre);
		String videojuegoLimpio = texto(videojuego);
		String categoriaLimpia = texto(categoria);
		String lugarLimpio = texto(lugar);
		String modalidadLimpia = texto(modalidad);
		model.addAttribute("eventos", eventoService.buscar(nombreLimpio, videojuegoLimpio, categoriaLimpia, lugarLimpio,
				fechaBuscada, modalidadLimpia));
		model.addAttribute("categorias", eventoService.categorias());
		model.addAttribute("videojuegos", eventoService.videojuegos());
		model.addAttribute("modalidades", eventoService.modalidades());
		model.addAttribute("nombre", nombreLimpio);
		model.addAttribute("videojuego", videojuegoLimpio);
		model.addAttribute("categoria", categoriaLimpia);
		model.addAttribute("lugar", lugarLimpio);
		model.addAttribute("fecha", fecha == null ? "" : fecha);
		model.addAttribute("modalidad", modalidadLimpia);
		model.addAttribute("filtrado", !nombreLimpio.isBlank() || !videojuegoLimpio.isBlank()
				|| !categoriaLimpia.isBlank() || !lugarLimpio.isBlank() || fechaBuscada != null
				|| !modalidadLimpia.isBlank());
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

	private static String texto(String valor) {
		return valor == null ? "" : valor;
	}
}
