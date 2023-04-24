package com.proyecto.intranet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PERFILES")
public class PerfilEntity {
	
	@Id
	@Column(name="PERFIL", nullable= false, updatable = false)
	private String perfil;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="ACTIVO")
	private String activo;

}
