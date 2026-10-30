package com.duoc.seguridadcalidad.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResumen(Long id, String nombre, String videojuego, String categoria, String pais, String ciudad,
		LocalDate fecha, LocalTime hora, String modalidad) {

	public static EventoResumen de(Evento evento) {
		return new EventoResumen(evento.getId(), evento.getTitulo(), evento.getVideojuego(), evento.getCategoria(),
				evento.getPais(), evento.getCiudad(), evento.getFecha(), evento.getHora(), evento.getModalidad());
	}
}
