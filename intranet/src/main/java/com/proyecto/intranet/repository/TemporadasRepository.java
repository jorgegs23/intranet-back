package com.proyecto.intranet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.entity.TemporadaEntity;

@Repository
public interface TemporadasRepository extends JpaRepository<TemporadaEntity, Integer>{

	@Query("SELECT t FROM TemporadaEntity t WHERE t.activa = true")
	Optional<TemporadaEntity> findTemporadaActiva();
	
}
