package com.duoc.seguridadcalidad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.seguridadcalidad.model.PerfilForm;
import com.duoc.seguridadcalidad.model.RegistroForm;
import com.duoc.seguridadcalidad.model.Usuario;
import com.duoc.seguridadcalidad.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarios;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarios, PasswordEncoder passwordEncoder) {
		this.usuarios = usuarios;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Usuario usuario = usuarios.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.roles(usuario.getRol().split(","))
				.build();
	}

	@Transactional
	public void registrar(RegistroForm form) {
		String email = form.getEmail().trim().toLowerCase();
		if (usuarios.existsByUsername(email)) {
			throw new IllegalStateException("Ese correo ya está registrado.");
		}
		Usuario usuario = new Usuario();
		usuario.setUsername(email);
		usuario.setPassword(passwordEncoder.encode(form.getPassword()));
		usuario.setRol("USER");
		usuario.setNombre(form.getNombre().trim());
		usuario.setEmail(email);
		usuario.setNombreUsuario("");
		usuario.setAvatar("🎮");
		usuarios.save(usuario);
	}

	@Transactional
	public Usuario obtener(String username) {
		return usuarios.findByUsername(username).orElseGet(() -> {
			Usuario usuario = new Usuario();
			usuario.setUsername(username);
			usuario.setPassword(passwordEncoder.encode(username));
			usuario.setRol("USER");
			usuario.setNombre(username);
			usuario.setEmail(username.contains("@") ? username : "");
			usuario.setNombreUsuario("");
			usuario.setAvatar("🎮");
			return usuarios.save(usuario);
		});
	}

	@Transactional
	public void actualizar(String username, PerfilForm form, List<String> catalogo) {
		Usuario perfil = obtener(username);
		perfil.setNombreUsuario(form.getNombreUsuario().trim());
		perfil.setAvatar(form.getAvatar());

		List<String> juegos = new ArrayList<>();
		if (form.getJuegos() != null) {
			form.getJuegos().stream().filter(catalogo::contains).forEach(juegos::add);
		}
		if (form.getOtroJuego() != null && !form.getOtroJuego().isBlank()) {
			for (String extra : form.getOtroJuego().split(",")) {
				String limpio = extra.trim();
				if (!limpio.isEmpty() && !juegos.contains(limpio)) {
					juegos.add(limpio);
				}
			}
		}
		perfil.getJuegos().clear();
		perfil.getJuegos().addAll(juegos);
		perfil.setNotifEmail(form.isNotifEmail());
		perfil.setNotifEventos(form.isNotifEventos());
		perfil.setNotifNovedades(form.isNotifNovedades());
	}

	@Transactional(readOnly = true)
	public String nombreVisible(String username) {
		Usuario perfil = usuarios.findByUsername(username).orElse(null);
		if (perfil == null) {
			return username;
		}
		if (perfil.getNombreUsuario() != null && !perfil.getNombreUsuario().isBlank()) {
			return perfil.getNombreUsuario();
		}
		if (perfil.getNombre() != null && !perfil.getNombre().isBlank()) {
			return perfil.getNombre();
		}
		return username;
	}
}
