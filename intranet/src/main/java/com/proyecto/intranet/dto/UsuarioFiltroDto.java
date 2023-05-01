package com.proyecto.intranet.dto;

import lombok.Data;

@Data
public class UsuarioFiltroDto {

	private String nombre;
	
	private String perfil;
	
	private Boolean activo;
	
	private Boolean validado;
	
	private int itemsPorPagina;
	
	private int pagina;
}
