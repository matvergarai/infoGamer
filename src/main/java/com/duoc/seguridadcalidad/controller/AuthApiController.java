package com.duoc.seguridadcalidad.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.seguridadcalidad.model.LoginRequest;
import com.duoc.seguridadcalidad.model.TokenRespuesta;
import com.duoc.seguridadcalidad.service.JwtService;
import com.duoc.seguridadcalidad.service.UsuarioService;

@RestController
public class AuthApiController {

	private final AuthenticationManager authenticationManager;
	private final UsuarioService usuarioService;
	private final JwtService jwtService;

	public AuthApiController(AuthenticationManager authenticationManager, UsuarioService usuarioService,
			JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.usuarioService = usuarioService;
		this.jwtService = jwtService;
	}

	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest solicitud) {
		if (solicitud == null || enBlanco(solicitud.username()) || enBlanco(solicitud.password())) {
			return ResponseEntity.badRequest().body(Map.of("mensaje", "Usuario y contraseña son obligatorios"));
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(solicitud.username(), solicitud.password()));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(401).body(Map.of("mensaje", "Usuario o contraseña incorrectos"));
		}
		UserDetails usuario = usuarioService.loadUserByUsername(solicitud.username());
		return ResponseEntity.ok(new TokenRespuesta(jwtService.generar(usuario), "Bearer"));
	}

	private static boolean enBlanco(String valor) {
		return valor == null || valor.isBlank();
	}
}
