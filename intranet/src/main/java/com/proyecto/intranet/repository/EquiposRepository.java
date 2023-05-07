package com.proyecto.intranet.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.dto.EquipoFiltroDto;
import com.proyecto.intranet.entity.EquipoEntity;

@Repository
public interface EquiposRepository extends JpaRepository<EquipoEntity, Integer> {

	@Query("SELECT e FROM EquipoEntity e WHERE "
			+ " ( :#{#filtro.nombre} IS NULL OR UPPER(e.nombre) LIKE UPPER(CONCAT('%',:#{#filtro.nombre},'%'))) AND "
			+ " (:#{#filtro.categoria} IS NULL OR :#{#filtro.categoria} = e.categoria.categoria ) AND "
			+ " (:#{#filtro.municipio} IS NULL OR UPPER(e.municipio) LIKE UPPER(CONCAT('%',:#{#filtro.municipio},'%'))) AND "
			+ " (:#{#filtro.temporada} IS NULL OR :#{#filtro.temporada} = e.temporada.id )")
	Page<EquipoEntity> filterPage(@Param("filtro") EquipoFiltroDto filtro, Pageable pageable);

	@Query("SELECT e FROM EquipoEntity e WHERE "
			+ " ( :categoria IS NULL OR :categoria = e.categoria.categoria ) AND "
			+ " ( :idTemporada IS NULL OR :idTemporada =  e.temporada.id) ")
	List<EquipoEntity> findByCategoriaAndTemporada(@Param("categoria")  String categoria, 
			@Param("idTemporada") Integer idTemporada);
}
