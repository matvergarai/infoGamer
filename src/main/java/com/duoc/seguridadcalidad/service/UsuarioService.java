package com.duoc.seguridadcalidad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.duoc.seguridadcalidad.model.PerfilForm;
import com.duoc.seguridadcalidad.model.PerfilUsuario;
import com.duoc.seguridadcalidad.model.RegistroForm;

@Service
public class UsuarioService {

	private final InMemoryUserDetailsManager usuarios;
	private final PasswordEncoder passwordEncoder;
	private final Map<String, PerfilUsuario> perfiles = new ConcurrentHashMap<>();

	public UsuarioService(InMemoryUserDetailsManager usuarios, PasswordEncoder passwordEncoder) {
		this.usuarios = usuarios;
		this.passwordEncoder = passwordEncoder;
		sembrar("ana", "Ana Rojas", "ana@nexus.cl", "AnaR", "🦊",
				List.of("Valorant", "League of Legends"), true, true, false);
		sembrar("carlos", "Carlos Muñoz", "carlos@nexus.cl", "CarMun", "⚔️",
				List.of("Counter-Strike 2", "Rocket League"), true, false, true);
		sembrar("admin", "Valentina Soto", "admin@nexus.cl", "AdminNexus", "🏆",
				List.of("Valorant", "EA Sports FC"), true, true, true);
	}

	public void registrar(RegistroForm form) {
		String email = form.getEmail().trim().toLowerCase();
		if (usuarios.userExists(email)) {
			throw new IllegalStateException("Ese correo ya está registrado.");
		}
		usuarios.createUser(User.builder()
				.username(email)
				.password(passwordEncoder.encode(form.getPassword()))
				.roles("USER")
				.build());

		PerfilUsuario perfil = new PerfilUsuario();
		perfil.setUsername(email);
		perfil.setNombre(form.getNombre().trim());
		perfil.setEmail(email);
		perfil.setNombreUsuario("");
		perfil.setAvatar("🎮");
		perfil.setJuegos(new ArrayList<>());
		perfiles.put(email, perfil);
	}

	public PerfilUsuario obtener(String username) {
		return perfiles.computeIfAbsent(username, this::perfilVacio);
	}

	public void actualizar(String username, PerfilForm form, List<String> catalogo) {
		PerfilUsuario perfil = obtener(username);
		perfil.setNombreUsuario(form.getNombreUsuario().trim());
		perfil.setAvatar(form.getAvatar());

		List<String> juegos = new ArrayList<>();
		if (form.getJuegos() != null) {
			form.getJuegos().stream()
					.filter(catalogo::contains)
					.forEach(juegos::add);
		}
		if (form.getOtroJuego() != null && !form.getOtroJuego().isBlank()) {
			for (String extra : form.getOtroJuego().split(",")) {
				String limpio = extra.trim();
				if (!limpio.isEmpty() && !juegos.contains(limpio)) {
					juegos.add(limpio);
				}
			}
		}
		perfil.setJuegos(juegos);
		perfil.setNotifEmail(form.isNotifEmail());
		perfil.setNotifEventos(form.isNotifEventos());
		perfil.setNotifNovedades(form.isNotifNovedades());
	}

	public String nombreVisible(String username) {
		PerfilUsuario perfil = obtener(username);
		if (perfil.getNombreUsuario() != null && !perfil.getNombreUsuario().isBlank()) {
			return perfil.getNombreUsuario();
		}
		if (perfil.getNombre() != null && !perfil.getNombre().isBlank()) {
			return perfil.getNombre();
		}
		return username;
	}

	private void sembrar(String username, String nombre, String email, String nombreUsuario, String avatar,
			List<String> juegos, boolean notifEmail, boolean notifEventos, boolean notifNovedades) {
		PerfilUsuario perfil = new PerfilUsuario();
		perfil.setUsername(username);
		perfil.setNombre(nombre);
		perfil.setEmail(email);
		perfil.setNombreUsuario(nombreUsuario);
		perfil.setAvatar(avatar);
		perfil.setJuegos(new ArrayList<>(juegos));
		perfil.setNotifEmail(notifEmail);
		perfil.setNotifEventos(notifEventos);
		perfil.setNotifNovedades(notifNovedades);
		perfiles.put(username, perfil);
	}

	private PerfilUsuario perfilVacio(String username) {
		PerfilUsuario perfil = new PerfilUsuario();
		perfil.setUsername(username);
		perfil.setNombre(username);
		perfil.setEmail(username.contains("@") ? username : "");
		perfil.setNombreUsuario("");
		perfil.setAvatar("🎮");
		perfil.setJuegos(new ArrayList<>());
		return perfil;
	}
}
