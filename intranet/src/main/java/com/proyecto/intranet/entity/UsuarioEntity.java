package com.proyecto.intranet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="USUARIOS")
public class UsuarioEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_USUARIO", nullable= false, updatable = false)
	private Integer id;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="APELLIDO1")
	private String apellido1;
	
	@Column(name="APELLIDO2")
	private String apellido2;
	
	@Column(name="DOC_IDENTIDAD")
	private String docIdentidad;
	
	@Column(name="TIPO_DOC_IDENTIDAD")
	private String tipoDocIdentidad;
	
	@ManyToOne
	@JoinColumn(name="perfil")
	private PerfilEntity perfil; 
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="PASS")
	private String pass;
	
	@Column(name="TELEFONO")
	private Integer telefono;
	
	@Column(name="DIRECCION")
	private String direccion;
	
	@Column(name="MUNICIPIO")
	private String municipio;
	
	@Column(name="ACTIVO")
	private Boolean activo;
	
	@Column(name="VALIDADO")
	private Boolean validado;

//	@Enumerated(EnumType.STRING)
//	private Rol role;
	

}
