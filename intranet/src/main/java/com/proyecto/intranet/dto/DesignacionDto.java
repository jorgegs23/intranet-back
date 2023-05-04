package com.proyecto.intranet.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DesignacionDto {

	private Integer id;
	
	private LocalDate fecha;

	private PartidoDto partido; 
	
	private UsuarioDto arbitro1;
	
	private UsuarioDto arbitro2;
	
	private UsuarioDto arbitro3;
	
	private UsuarioDto oficial1;
	
	private UsuarioDto oficial2;
	
	private UsuarioDto oficial3;
	
	private UsuarioDto oficial4;
}
