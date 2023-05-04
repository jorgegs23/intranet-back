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
@Table(name="EQUIPOS")
public class EquipoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_EQUIPO", nullable= false, updatable = false)
	private Integer id;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="CATEGORIA")
	private CategoriaEntity categoria; 
	
	@Column(name="MUNICIPIO")
	private String municipio;
	
	@Column(name="PABELLON")
	private String pabellon;
	
	@Column(name="DIRECCION")
	private String direccion;
	
	@ManyToOne
	@JoinColumn(name="TEMPORADA")
	private TemporadaEntity temporada; 
}
