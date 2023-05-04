package com.proyecto.intranet.dto;

import lombok.Data;

@Data
public class EquipoDto {
	
	private Integer id;

	private String nombre;
	
	private CategoriaDto categoria; 
	
	private String municipio;
	
	private String pabellon;
	
	private String direccion;
	
	private TemporadaDto temporada;

}
