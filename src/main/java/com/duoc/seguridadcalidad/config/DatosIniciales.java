package com.duoc.seguridadcalidad.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public void run(String... args) {
		if (usuarios.count() == 0) {
			usuarios.save(cuenta("ana", "ana123", "USER", "Ana Rojas", "ana@nexus.cl", "AnaR", "🦊",
					List.of("Valorant", "League of Legends"), true, true, false));
			usuarios.save(cuenta("carlos", "carlos123", "USER", "Carlos Muñoz", "carlos@nexus.cl", "CarMun", "⚔️",
					List.of("Counter-Strike 2", "Rocket League"), true, false, true));
			usuarios.save(cuenta("admin", "admin123", "USER,ADMIN", "Valentina Soto", "admin@nexus.cl", "AdminNexus",
					"🏆", List.of("Valorant", "EA Sports FC"), true, true, true));
		}
		boolean sinFichaNueva = eventos.findAll().stream()
				.anyMatch(evento -> evento.getVideojuego() == null || evento.getVideojuego().isBlank());
		if (eventos.count() == 0 || sinFichaNueva) {
			eventos.deleteAll();
			eventos.flush();
			eventos.saveAll(List.of(
					evento("Copa Valorant Sur", "Valorant", "eSports", LocalDate.of(2026, 10, 31), LocalTime.of(18, 30),
							"Santiago", "Av. Vicuña Mackenna 6100, Estadio Nacional, sector norte", null, "Presencial",
							"Cierre de la fase regional. Ocho equipos, una final al mejor de tres y transmisión en vivo desde la cancha norte.",
							"Equipo de cinco jugadores inscrito en la fase regular.",
							"La inscripción del equipo cierra una semana antes. La entrada de público se retira en portería.",
							List.of("Liga Sur eSports", "Nexus"), true, true, true, false,
							List.of("Francisco \"Fzk\" Araya", "Javiera \"Kite\" León", "Nico Duarte"), true, "🎯",
							"/img/esports.svg"),
					evento("LAN Party Puerto", "Counter-Strike 2", "LAN", LocalDate.of(2026, 11, 7), LocalTime.of(16, 0),
							"Valparaíso", "Av. Errázuriz 1108, Muelle Barón", null, "Presencial",
							"Trae tu equipo o usa los puestos armados. Torneos casuales de Counter-Strike 2 y Rocket League hasta la madrugada.",
							"Llevar mouse propio si vas a jugar en un puesto compartido.",
							"Cupos por orden de llegada en la puerta. El torneo se anota en una mesa al entrar.",
							List.of("Colectivo Puerto Play"), true, true, true, true,
							List.of("Diego \"LagZero\" Soto", "Valentina Riquelme"), true, "🖥️", "/img/lan.svg"),
					evento("Chile Game Show 2026", "Varios", "Convención", LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
							"Santiago", "Av. San Josemaría Escrivá de Balaguer 5600, Espacio Riesco, Huechuraba", null,
							"Presencial",
							"La feria grande del año: estudios independientes, cosplay, arenas de torneo y un pasillo completo de marcas.",
							"Entrada general. Las arenas de torneo piden cuenta del juego correspondiente.",
							"Las entradas se compran en boletería o en el sitio de la feria hasta el día anterior.",
							List.of("Asociación Gamer Chile", "Nexus"), true, true, true, false,
							List.of("Matías \"Zero\" Palma", "Camila \"Nova\" Herrera", "Estudio Pixel Sur"), true, "🎪",
							"/img/feria.svg"),
					evento("Meetup Indies del Biobío", "Independientes", "Meetup", LocalDate.of(2026, 11, 22),
							LocalTime.of(11, 0), "Concepción", "Barros Arana 1068, Centro Cultural", null, "Presencial",
							"Demostraciones jugables de estudios chicos y una mesa para encontrar equipo, arte o música para tu juego.",
							"No hay requisito de equipo. Conviene llegar con el demo en un notebook si vas a mostrar.",
							"La asistencia es libre. Para mostrar un juego hay que avisar al organizador.",
							List.of("Indies Biobío"), false, true, true, false,
							List.of("Estudio Luma", "Antonia Pérez"), false, "👾", "/img/feria.svg"),
					evento("Taller de speedrunning", "Super Mario", "Taller", LocalDate.of(2026, 11, 28),
							LocalTime.of(15, 0), "Temuco", null, "https://nexus.cl/sala/speedrun", "Online",
							"Cómo partir un cronómetro, leer una ruta y no perder la cabeza en el primer split. Cupos limitados.",
							"Tener el juego instalado y un cronómetro a mano. La sala abre diez minutos antes.",
							"Los cupos se reservan con el enlace de inscripción. Quedan 30 lugares.",
							List.of("Araucanía Speed"), false, false, false, false,
							List.of("Pablo \"Split\" Cayuqueo"), false, "⏱️", "/img/online.svg"),
					evento("Final regional de League of Legends", "League of Legends", "eSports",
							LocalDate.of(2026, 12, 5), LocalTime.of(17, 0), "Santiago",
							"Av. Apoquindo 4501, teatro corporativo", null, "Presencial",
							"La final del circuito andino. Hay pantalla gigante, zona de hinchada y un análisis de draft antes de cada juego.",
							"El público entra con entrada. Los equipos deben llegar con las cuentas del circuito.",
							"La hinchada retira entrada en boletería. Los equipos ya vienen inscritos por el circuito.",
							List.of("Circuito Andino"), true, true, true, true,
							List.of("Martín \"Brick\" Díaz", "mesa de comentaristas Andinos"), true, "🏆",
							"/img/esports.svg"),
					evento("Noche LAN Norte", "Rocket League", "LAN", LocalDate.of(2026, 12, 12), LocalTime.of(19, 0),
							"Antofagasta", "Av. Angamos 450, sala recreativa 3", null, "Presencial",
							"Una noche de partidas en red local. Hay puestos con monitor y un rincón para quien llega solo y quiere squad.",
							"No es necesario llevar PC. Sí conviene llevar audífonos.",
							"La entrada es en puerta hasta llenar los puestos.",
							List.of("Norte Gaming", "club universitario UA"), true, true, true, true,
							List.of("Fernanda \"Salt\" Rojas"), false, "🌙", "/img/lan.svg"),
					evento("Expo Retro Viña", "Arcade", "Convención", LocalDate.of(2026, 12, 18), LocalTime.of(12, 0),
							"Viña del Mar", "Av. San Martín 199, Casino Municipal, salón exterior", null, "Presencial",
							"Consolas, arcades y un escenario de chiptune. Se puede jugar, no solo mirar vitrinas.",
							"Entrada general. Algunas máquinas piden ficha, que se compra en el salón.",
							"La entrada se paga en puerta.",
							List.of("Archivo Pixel"), true, true, false, true,
							List.of("Rosa Aguilar", "banda 8-Bit Sur"), false, "📼", "/img/feria.svg")));
		}
	}

	private static Evento evento(String titulo, String videojuego, String categoria, LocalDate fecha, LocalTime hora,
			String ciudad, String direccion, String enlace, String modalidad, String descripcion, String requisitos,
			String inscripcion, List<String> organizadores, boolean estacionamiento, boolean locomocion, boolean comidas,
			boolean alojamiento, List<String> expositores, boolean destacado, String emoji, String foto) {
		return Evento.crear(titulo, videojuego, categoria, fecha, hora, "Chile", ciudad, direccion, enlace, modalidad,
				descripcion, requisitos, inscripcion, organizadores, estacionamiento, locomocion, comidas, alojamiento,
				expositores, destacado, emoji, foto);
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
