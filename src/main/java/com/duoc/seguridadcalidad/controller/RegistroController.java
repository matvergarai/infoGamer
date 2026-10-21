package com.duoc.seguridadcalidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.seguridadcalidad.model.RegistroForm;
import com.duoc.seguridadcalidad.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class RegistroController {

	private final UsuarioService usuarioService;

	public RegistroController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/registro")
	public String formulario(Model model) {
		model.addAttribute("registroForm", new RegistroForm());
		return "registro";
	}

	@PostMapping("/registro")
	public String registrar(@Valid @ModelAttribute RegistroForm registroForm, BindingResult result) {
		if (result.hasErrors()) {
			return "registro";
		}
		try {
			usuarioService.registrar(registroForm);
		}
		catch (IllegalStateException ex) {
			result.rejectValue("email", "duplicado", ex.getMessage());
			return "registro";
		}
		return "redirect:/login?registro";
	}
}
