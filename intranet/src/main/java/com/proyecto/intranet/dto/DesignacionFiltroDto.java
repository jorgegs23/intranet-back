package com.proyecto.intranet.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DesignacionFiltroDto {
	
	private Integer temporada;
	
	private LocalDate mes;
	
	private LocalDate fecha;
	
	private Integer usuario;
	
	private List<Integer> idsDesignaciones;
	
	private int itemsPorPagina;
	
	private int pagina;
} 
