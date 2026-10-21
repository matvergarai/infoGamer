package com.duoc.seguridadcalidad.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.duoc.seguridadcalidad.service.UsuarioService;

@ControllerAdvice
public class ModeloGlobal {

	private final UsuarioService usuarioService;

	public ModeloGlobal(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@ModelAttribute("logueado")
	public boolean logueado() {
		return autenticado() != null;
	}

	@ModelAttribute("nombreVisible")
	public String nombreVisible() {
		Authentication autenticado = autenticado();
		if (autenticado == null) {
			return "";
		}
		return usuarioService.nombreVisible(autenticado.getName());
	}

	@ModelAttribute("avatarSesion")
	public String avatarSesion() {
		Authentication autenticado = autenticado();
		if (autenticado == null) {
			return "";
		}
		return usuarioService.obtener(autenticado.getName()).getAvatar();
	}

	private Authentication autenticado() {
		Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
		if (autenticacion == null || !autenticacion.isAuthenticated()
				|| autenticacion instanceof AnonymousAuthenticationToken) {
			return null;
		}
		return autenticacion;
	}
}
