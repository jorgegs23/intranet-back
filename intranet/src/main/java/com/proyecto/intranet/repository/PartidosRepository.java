package com.proyecto.intranet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.dto.PartidoFiltroDto;
import com.proyecto.intranet.entity.PartidoEntity;

@Repository
public interface PartidosRepository extends JpaRepository<PartidoEntity, Integer>{

	@Query("SELECT e FROM PartidoEntity e WHERE "
			+ " (:#{#filtro.temporada} IS NULL OR :#{#filtro.temporada} = e.temporada.id) AND "
			+ " (:#{#filtro.competicion} IS NULL OR :#{#filtro.competicion} = e.competicion.competicion) AND "
			+ " (:#{#filtro.jornada} IS NULL OR :#{#filtro.jornada} = e.jornada) AND "
			+ " (:#{#filtro.categoria} IS NULL OR :#{#filtro.categoria} = e.categoria.categoria ) AND "
			+ " (:#{#filtro.equipoLocal} IS NULL OR :#{#filtro.equipoLocal} = e.equipoLocal.id ) AND "
			+ " (:#{#filtro.equipoVisitante} IS NULL OR :#{#filtro.equipoVisitante} = e.equipoVisitante.id ) AND "
			+ " (:#{#filtro.fecha} IS NULL OR :#{#filtro.fecha} = DATE(e.fecha) ) ")
	Page<PartidoEntity> filterPage(@Param("filtro") PartidoFiltroDto filtro, Pageable pageable);

}
