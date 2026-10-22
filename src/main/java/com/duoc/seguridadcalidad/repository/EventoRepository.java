package com.duoc.seguridadcalidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.seguridadcalidad.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
