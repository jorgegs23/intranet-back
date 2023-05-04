package com.proyecto.intranet.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DesignacionFiltroDto {
	
	private Integer temporada;
	
	private Integer mes;
	
	private LocalDate fecha;
	
	private Integer usuario;
	
	private int itemsPorPagina;
	
	private int pagina;
} 
