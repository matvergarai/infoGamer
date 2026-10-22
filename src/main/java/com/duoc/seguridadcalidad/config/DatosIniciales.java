package com.duoc.seguridadcalidad.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.duoc.seguridadcalidad.model.Evento;
import com.duoc.seguridadcalidad.model.Usuario;
import com.duoc.seguridadcalidad.repository.EventoRepository;
import com.duoc.seguridadcalidad.repository.UsuarioRepository;

@Component
public class DatosIniciales implements CommandLineRunner {

	private final UsuarioRepository usuarios;
	private final EventoRepository eventos;
	private final PasswordEncoder passwordEncoder;

	public DatosIniciales(UsuarioRepository usuarios, EventoRepository eventos, PasswordEncoder passwordEncoder) {
		this.usuarios = usuarios;
		this.eventos = eventos;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (usuarios.count() == 0) {
			usuarios.save(cuenta("ana", "ana123", "USER", "Ana Rojas", "ana@nexus.cl", "AnaR", "🦊",
					List.of("Valorant", "League of Legends"), true, true, false));
			usuarios.save(cuenta("carlos", "carlos123", "USER", "Carlos Muñoz", "carlos@nexus.cl", "CarMun", "⚔️",
					List.of("Counter-Strike 2", "Rocket League"), true, false, true));
			usuarios.save(cuenta("admin", "admin123", "USER,ADMIN", "Valentina Soto", "admin@nexus.cl", "AdminNexus",
					"🏆", List.of("Valorant", "EA Sports FC"), true, true, true));
		}
		if (eventos.count() == 0) {
			eventos.saveAll(List.of(
					Evento.crear("Copa Valorant Sur", "eSports", LocalDate.of(2026, 10, 31), LocalTime.of(18, 30),
							"Santiago", "Av. Vicuña Mackenna 6100, Estadio Nacional, sector norte",
							"Cierre de la fase regional. Ocho equipos, una final al mejor de tres y transmisión en vivo desde la cancha norte.",
							List.of("Liga Sur eSports", "Nexus"), true, true, true, false,
							List.of("Francisco \"Fzk\" Araya", "Javiera \"Kite\" León", "Nico Duarte"), true, "🎯"),
					Evento.crear("LAN Party Puerto", "LAN", LocalDate.of(2026, 11, 7), LocalTime.of(16, 0),
							"Valparaíso", "Av. Errázuriz 1108, Muelle Barón",
							"Trae tu equipo o usa los puestos armados. Torneos casuales de Counter-Strike 2 y Rocket League hasta la madrugada.",
							List.of("Colectivo Puerto Play"), true, true, true, true,
							List.of("Diego \"LagZero\" Soto", "Valentina Riquelme"), true, "🖥️"),
					Evento.crear("Chile Game Show 2026", "Convención", LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
							"Santiago", "Av. San Josemaría Escrivá de Balaguer 5600, Espacio Riesco, Huechuraba",
							"La feria grande del año: estudios independientes, cosplay, arenas de torneo y un pasillo completo de marcas.",
							List.of("Asociación Gamer Chile", "Nexus"), true, true, true, false,
							List.of("Matías \"Zero\" Palma", "Camila \"Nova\" Herrera", "Estudio Pixel Sur"), true, "🎪"),
					Evento.crear("Meetup Indies del Biobío", "Meetup", LocalDate.of(2026, 11, 22), LocalTime.of(11, 0),
							"Concepción", "Barros Arana 1068, Centro Cultural",
							"Demostraciones jugables de estudios chicos y una mesa para encontrar equipo, arte o música para tu juego.",
							List.of("Indies Biobío"), false, true, true, false,
							List.of("Estudio Luma", "Antonia Pérez"), false, "👾"),
					Evento.crear("Taller de speedrunning", "Taller", LocalDate.of(2026, 11, 28), LocalTime.of(15, 0),
							"Temuco", "Av. Alemania 0422, Centro de Innovación",
							"Cómo partir un cronómetro, leer una ruta y no perder la cabeza en el primer split. Cupos limitados.",
							List.of("Araucanía Speed"), true, false, true, false,
							List.of("Pablo \"Split\" Cayuqueo"), false, "⏱️"),
					Evento.crear("Final regional de League of Legends", "eSports", LocalDate.of(2026, 12, 5),
							LocalTime.of(17, 0), "Santiago", "Av. Apoquindo 4501, teatro corporativo",
							"La final del circuito andino. Hay pantalla gigante, zona de hinchada y un análisis de draft antes de cada juego.",
							List.of("Circuito Andino"), true, true, true, true,
							List.of("Martín \"Brick\" Díaz", "mesa de comentaristas Andinos"), true, "🏆"),
					Evento.crear("Noche LAN Norte", "LAN", LocalDate.of(2026, 12, 12), LocalTime.of(19, 0),
							"Antofagasta", "Av. Angamos 450, sala recreativa 3",
							"Una noche de partidas en red local. Hay puestos con monitor y un rincón para quien llega solo y quiere squad.",
							List.of("Norte Gaming", "club universitario UA"), true, true, true, true,
							List.of("Fernanda \"Salt\" Rojas"), false, "🌙"),
					Evento.crear("Expo Retro Viña", "Convención", LocalDate.of(2026, 12, 18), LocalTime.of(12, 0),
							"Viña del Mar", "Av. San Martín 199, Casino Municipal, salón exterior",
							"Consolas, arcades y un escenario de chiptune. Se puede jugar, no solo mirar vitrinas.",
							List.of("Archivo Pixel"), true, true, false, true,
							List.of("Rosa Aguilar", "banda 8-Bit Sur"), false, "📼")));
		}
	}

	private Usuario cuenta(String username, String password, String rol, String nombre, String email,
			String nombreUsuario, String avatar, List<String> juegos, boolean notifEmail, boolean notifEventos,
			boolean notifNovedades) {
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setRol(rol);
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setAvatar(avatar);
		usuario.setJuegos(new java.util.ArrayList<>(juegos));
		usuario.setNotifEmail(notifEmail);
		usuario.setNotifEventos(notifEventos);
		usuario.setNotifNovedades(notifNovedades);
		return usuario;
	}
}
