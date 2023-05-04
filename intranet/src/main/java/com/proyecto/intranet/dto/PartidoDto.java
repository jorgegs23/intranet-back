package com.proyecto.intranet.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PartidoDto {

	private Integer id;

	private TemporadaDto temporada;

	private CompeticionDto competicion;

	private Integer jornada;

	private EquipoDto equipo1;

	private EquipoDto equipo2;

	private LocalDateTime fecha;

	private String municipio;

	private String pabellon;

	private String direccion;
}
