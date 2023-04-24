package com.proyecto.intranet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
	
	private Integer id;
	
	private String nombre;
	
	private String apellido1;
	
	private String apellido2;
	
	private String docIdentidad;
	
	private String tipoDocIdentidad;
	
	private PerfilDto perfil;
	
	private String email;
	
	private String login;
	
	private String pass;
	
	private Integer telefono;
	
	private String direccion;
	
	private String municipio;
	
	private Boolean activo;
	
	private Boolean validado;

}
