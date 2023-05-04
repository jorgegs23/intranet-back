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
@Table(name="CATEGORIAS")
public class CategoriaEntity {

	@Id
	@Column(name="CATEGORIA", nullable= false, updatable = false)
	private String categoria;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="MODALIDAD")
	private String modalidad;
	
	@Column(name="CUANTIA")
	private Double cuantia;
	
	@Column(name="ARBITROS")
	private Integer arbitros;
	
	@Column(name="OFICIALES")
	private Integer oficiales;
}
