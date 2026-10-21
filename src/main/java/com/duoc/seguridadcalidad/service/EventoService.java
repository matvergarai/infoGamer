package com.duoc.seguridadcalidad.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duoc.seguridadcalidad.model.Evento;
import com.duoc.seguridadcalidad.model.Marca;

@Service
public class EventoService {

	private final List<Evento> eventos = List.of(
			new Evento(1L, "Copa Valorant Sur", "eSports", LocalDate.of(2026, 10, 31), LocalTime.of(18, 30),
					"Santiago", "Av. Vicuña Mackenna 6100, Estadio Nacional, sector norte",
					"Cierre de la fase regional. Ocho equipos, una final al mejor de tres y transmisión en vivo desde la cancha norte.",
					List.of("Liga Sur eSports", "Nexus"),
					true, true, true, false,
					List.of("Francisco \"Fzk\" Araya", "Javiera \"Kite\" León", "Nico Duarte"),
					true, "🎯"),
			new Evento(2L, "LAN Party Puerto", "LAN", LocalDate.of(2026, 11, 7), LocalTime.of(16, 0),
					"Valparaíso", "Av. Errázuriz 1108, Muelle Barón",
					"Trae tu equipo o usa los puestos armados. Torneos casuales de Counter-Strike 2 y Rocket League hasta la madrugada.",
					List.of("Colectivo Puerto Play"),
					true, true, true, true,
					List.of("Diego \"LagZero\" Soto", "Valentina Riquelme"),
					true, "🖥️"),
			new Evento(3L, "Chile Game Show 2026", "Convención", LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
					"Santiago", "Av. San Josemaría Escrivá de Balaguer 5600, Espacio Riesco, Huechuraba",
					"La feria grande del año: estudios independientes, cosplay, arenas de torneo y un pasillo completo de marcas.",
					List.of("Asociación Gamer Chile", "Nexus"),
					true, true, true, false,
					List.of("Matías \"Zero\" Palma", "Camila \"Nova\" Herrera", "Estudio Pixel Sur"),
					true, "🎪"),
			new Evento(4L, "Meetup Indies del Biobío", "Meetup", LocalDate.of(2026, 11, 22), LocalTime.of(11, 0),
					"Concepción", "Barros Arana 1068, Centro Cultural",
					"Demostraciones jugables de estudios chicos y una mesa para encontrar equipo, arte o música para tu juego.",
					List.of("Indies Biobío"),
					false, true, true, false,
					List.of("Estudio Luma", "Antonia Pérez"),
					false, "👾"),
			new Evento(5L, "Taller de speedrunning", "Taller", LocalDate.of(2026, 11, 28), LocalTime.of(15, 0),
					"Temuco", "Av. Alemania 0422, Centro de Innovación",
					"Cómo partir un cronómetro, leer una ruta y no perder la cabeza en el primer split. Cupos limitados.",
					List.of("Araucanía Speed"),
					true, false, true, false,
					List.of("Pablo \"Split\" Cayuqueo"),
					false, "⏱️"),
			new Evento(6L, "Final regional de League of Legends", "eSports", LocalDate.of(2026, 12, 5), LocalTime.of(17, 0),
					"Santiago", "Av. Apoquindo 4501, teatro corporativo",
					"La final del circuito andino. Hay pantalla gigante, zona de hinchada y un análisis de draft antes de cada juego.",
					List.of("Circuito Andino"),
					true, true, true, true,
					List.of("Martín \"Brick\" Díaz", "mesa de comentaristas Andinos"),
					true, "🏆"),
			new Evento(7L, "Noche LAN Norte", "LAN", LocalDate.of(2026, 12, 12), LocalTime.of(19, 0),
					"Antofagasta", "Av. Angamos 450, sala recreativa 3",
					"Una noche de partidas en red local. Hay puestos con monitor y un rincón para quien llega solo y quiere squad.",
					List.of("Norte Gaming", "club universitario UA"),
					true, true, true, true,
					List.of("Fernanda \"Salt\" Rojas"),
					false, "🌙"),
			new Evento(8L, "Expo Retro Viña", "Convención", LocalDate.of(2026, 12, 18), LocalTime.of(12, 0),
					"Viña del Mar", "Av. San Martín 199, Casino Municipal, salón exterior",
					"Consolas, arcades y un escenario de chiptune. Se puede jugar, no solo mirar vitrinas.",
					List.of("Archivo Pixel"),
					true, true, false, true,
					List.of("Rosa Aguilar", "banda 8-Bit Sur"),
					false, "📼"));

	private final List<Marca> marcas = List.of(
			new Marca("VoltGear", "Periféricos", "Teclados y mouse pensados para una LAN que se alarga.", "volt"),
			new Marca("PixelFuel", "Bebidas", "Energía para la final, sin prometer milagros de FPS.", "fuel"),
			new Marca("SombraByte", "Hardware", "Torres armadas para el próximo parche pesado.", "sombra"),
			new Marca("NeonForge", "Setups", "Sillas y escritorios que aguantan un alargue de tres mapas.", "neon"));

	public List<Evento> proximos() {
		LocalDate hoy = LocalDate.now();
		return eventos.stream()
				.filter(evento -> !evento.getFecha().isBefore(hoy))
				.sorted(Comparator.comparing(Evento::getFecha).thenComparing(Evento::getHora))
				.limit(4)
				.toList();
	}

	public List<Evento> destacados() {
		return eventos.stream()
				.filter(Evento::isDestacado)
				.sorted(Comparator.comparing(Evento::getFecha))
				.toList();
	}

	public List<Evento> buscar(String categoria, LocalDate fecha, String ubicacion) {
		String ciudad = ubicacion == null ? "" : ubicacion.trim().toLowerCase();
		return eventos.stream()
				.filter(evento -> categoria == null || categoria.isBlank()
						|| evento.getCategoria().equalsIgnoreCase(categoria))
				.filter(evento -> fecha == null || evento.getFecha().equals(fecha))
				.filter(evento -> ciudad.isEmpty() || evento.getCiudad().toLowerCase().contains(ciudad))
				.sorted(Comparator.comparing(Evento::getFecha).thenComparing(Evento::getHora))
				.toList();
	}

	public Optional<Evento> porId(Long id) {
		return eventos.stream().filter(evento -> evento.getId().equals(id)).findFirst();
	}

	public List<String> categorias() {
		return eventos.stream().map(Evento::getCategoria).distinct().sorted().toList();
	}

	public List<Marca> marcas() {
		return marcas;
	}
}
