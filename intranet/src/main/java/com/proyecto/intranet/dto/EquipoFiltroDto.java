package com.proyecto.intranet.dto;

import lombok.Data;

@Data
public class EquipoFiltroDto {

	private String nombre;
	
	private String categoria;

	private String municipio;
	
	private Integer temporada;
	
	private int itemsPorPagina;
	
	private int pagina;
}
