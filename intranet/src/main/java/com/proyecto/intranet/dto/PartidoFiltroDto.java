package com.proyecto.intranet.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PartidoFiltroDto {

	private Integer temporada;
	
	private String competicion;
	
	private Integer jornada;
	
	private String categoria;
	
	private Integer equipoLocal;
	
	private Integer equipoVisitante;
	
	private LocalDate fecha;
	
	private int itemsPorPagina;
	
	private int pagina;
}
