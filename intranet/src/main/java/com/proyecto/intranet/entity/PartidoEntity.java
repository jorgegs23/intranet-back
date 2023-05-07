package com.proyecto.intranet.entity;

import java.time.LocalDateTime;

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
@Table(name="PARTIDOS")
public class PartidoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_PARTIDO", nullable= false, updatable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="TEMPORADA")
	private TemporadaEntity temporada;
	
	@ManyToOne
	@JoinColumn(name="CATEGORIA")
	private CategoriaEntity categoria;
	
	@ManyToOne
	@JoinColumn(name="COMPETICION")
	private CompeticionEntity competicion;

	@Column(name="JORNADA")
	private Integer jornada;
	
	@ManyToOne
	@JoinColumn(name="EQUIPO1")
	private EquipoEntity equipoLocal;
	
	@ManyToOne
	@JoinColumn(name="EQUIPO2")
	private EquipoEntity equipoVisitante;
	
	@Column(name="FECHA")
	private LocalDateTime fecha;
	
	@Column(name="MUNICIPIO")
	private String municipio;
	
	@Column(name="PABELLON")
	private String pabellon;
	
	@Column(name="DIRECCION")
	private String direccion;
}
