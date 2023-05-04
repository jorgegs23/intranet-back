package com.proyecto.intranet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name="DESIGNACIONES")
public class DesignacionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_DESIGNACION", nullable= false, updatable = false)
	private Integer id;
	
	@Column(name="FECHA")
	private LocalDate fecha;
	
	@OneToOne
	@JoinColumn(name="ID_PARTIDO")
	private PartidoEntity partido; 
	
	@ManyToOne
	@JoinColumn(name="ID_ARBITRO1")
	private UsuarioEntity arbitro1;
	
	@ManyToOne
	@JoinColumn(name="ID_ARBITRO2")
	private UsuarioEntity arbitro2;
	
	@ManyToOne
	@JoinColumn(name="ID_ARBITRO3")
	private UsuarioEntity arbitro3;
	
	@ManyToOne
	@JoinColumn(name="ID_OFICIAL1")
	private UsuarioEntity oficial1;
	
	@ManyToOne
	@JoinColumn(name="ID_OFICIAL2")
	private UsuarioEntity oficial2;
	
	@ManyToOne
	@JoinColumn(name="ID_OFICIAL3")
	private UsuarioEntity oficial3;
	
	@ManyToOne
	@JoinColumn(name="ID_OFICIAL4")
	private UsuarioEntity oficial4;
}
