package com.duoc.seguridadcalidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.duoc.seguridadcalidad.service.EventoService;

@Controller
public class HomeController {

	private final EventoService eventoService;

	public HomeController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("proximos", eventoService.proximos());
		model.addAttribute("destacados", eventoService.destacados());
		model.addAttribute("marcas", eventoService.marcas());
		return "home";
	}
}
