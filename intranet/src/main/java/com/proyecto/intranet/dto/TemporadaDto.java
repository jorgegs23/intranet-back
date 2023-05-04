package com.proyecto.intranet.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TemporadaDto {
	
	private Integer id;
	
	private String descripcion;

	private LocalDate fechaIncio;
	
	private LocalDate fechaFin;
	
	private Boolean activa;
}
