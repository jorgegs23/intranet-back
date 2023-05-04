package com.proyecto.intranet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="TEMPORADA")
public class TemporadaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_TEMPORADA", nullable= false, updatable = false)
	private Integer id;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="FECHA_INICIO")
	private LocalDate fechaIncio;
	
	@Column(name="FECHA_FIN")
	private LocalDate fechaFin;
	
	@Column(name="ACTIVA")
	private Boolean activa;
}
