package com.proyecto.intranet.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PartidoDto {

	private Integer id;

	private TemporadaDto temporada;

	private CompeticionDto competicion;
	
	private CategoriaDto categoria;

	private Integer jornada;

	private EquipoDto equipoLocal;

	private EquipoDto equipoVisitante;

	private LocalDateTime fecha;

	private String municipio;

	private String pabellon;

	private String direccion;
}
