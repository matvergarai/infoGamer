package com.duoc.seguridadcalidad.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.seguridadcalidad.model.PerfilForm;
import com.duoc.seguridadcalidad.model.PerfilUsuario;
import com.duoc.seguridadcalidad.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class PerfilController {

	static final List<String> JUEGOS = List.of(
			"Valorant",
			"League of Legends",
			"Counter-Strike 2",
			"Fortnite",
			"Minecraft",
			"EA Sports FC",
			"Rocket League",
			"Genshin Impact",
			"Apex Legends",
			"Super Smash Bros");

	static final List<String> AVATARES = List.of("🎮", "🕹️", "👾", "🐉", "⚔️", "🏆", "🦊", "🤖");

	private final UsuarioService usuarioService;

	public PerfilController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/perfil")
	public String ver(Authentication authentication, Model model) {
		preparar(authentication.getName(), model, formularioDesde(usuarioService.obtener(authentication.getName())));
		return "perfil";
	}

	@PostMapping("/perfil")
	public String guardar(Authentication authentication, @Valid @ModelAttribute PerfilForm perfilForm,
			BindingResult result, Model model) {
		if (perfilForm.getAvatar() != null && !AVATARES.contains(perfilForm.getAvatar())) {
			result.rejectValue("avatar", "invalido", "Elige un avatar de la lista.");
		}
		if (result.hasErrors()) {
			preparar(authentication.getName(), model, perfilForm);
			return "perfil";
		}
		usuarioService.actualizar(authentication.getName(), perfilForm, JUEGOS);
		return "redirect:/perfil?ok";
	}

	private void preparar(String username, Model model, PerfilForm form) {
		PerfilUsuario cuenta = usuarioService.obtener(username);
		model.addAttribute("perfilForm", form);
		model.addAttribute("cuenta", cuenta);
		model.addAttribute("juegosDisponibles", JUEGOS);
		model.addAttribute("avatares", AVATARES);
	}

	private PerfilForm formularioDesde(PerfilUsuario perfil) {
		PerfilForm form = new PerfilForm();
		form.setNombreUsuario(perfil.getNombreUsuario() == null ? "" : perfil.getNombreUsuario());
		form.setAvatar(perfil.getAvatar() == null || perfil.getAvatar().isBlank() ? "🎮" : perfil.getAvatar());
		List<String> juegos = perfil.getJuegos() == null ? List.of() : perfil.getJuegos();
		form.setJuegos(juegos.stream().filter(JUEGOS::contains).toList());
		form.setOtroJuego(String.join(", ", juegos.stream().filter(juego -> !JUEGOS.contains(juego)).toList()));
		form.setNotifEmail(perfil.isNotifEmail());
		form.setNotifEventos(perfil.isNotifEventos());
		form.setNotifNovedades(perfil.isNotifNovedades());
		return form;
	}
}
