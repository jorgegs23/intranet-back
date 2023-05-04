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
@Table(name="COMPETICIONES")
public class CompeticionEntity {
	
	@Id
	@Column(name="COMPETICION", nullable= false, updatable = false)
	private String competicion;
	
	@Column(name="DESCRIPCION")
	private String descripcion;

}
