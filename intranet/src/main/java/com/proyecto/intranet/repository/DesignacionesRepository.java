package com.proyecto.intranet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.dto.DesignacionFiltroDto;
import com.proyecto.intranet.entity.DesignacionEntity;

@Repository
public interface DesignacionesRepository  extends JpaRepository<DesignacionEntity, Integer>{

	@Query("SELECT e FROM DesignacionEntity e WHERE "
			+ " (:#{#filtro.temporada} IS NULL OR :#{#filtro.temporada} = e.partido.temporada.id) AND "
//			+ " (:#{#filtro.mes} IS NULL OR :#{#filtro.mes} = e.competicion.competicion) AND "
			+ " (:#{#filtro.fecha} IS NULL OR :#{#filtro.fecha} = DATE(e.fecha) ) AND "
			+ " (:#{#filtro.usuario} IS NULL OR	"
				+ " :#{#filtro.usuario} = e.arbitro1.id OR :#{#filtro.usuario} = e.arbitro2.id OR "
				+ " :#{#filtro.usuario} = e.arbitro3.id OR  "
				+ " :#{#filtro.usuario} = e.oficial1.id OR :#{#filtro.usuario} = e.oficial2.id OR "
				+ " :#{#filtro.usuario} = e.oficial3.id OR :#{#filtro.usuario} = e.oficial4.id "
			+ ") ")
	Page<DesignacionEntity> filterPage(@Param("filtro") DesignacionFiltroDto filtro, Pageable pageable);
	
	@Query("SELECT count(e) FROM DesignacionEntity e WHERE "
			+ " :idUsuario = e.arbitro1.id OR :idUsuario = e.arbitro2.id OR "
			+ " :idUsuario = e.arbitro3.id OR  "
			+ " :idUsuario = e.oficial1.id OR :idUsuario = e.oficial2.id OR "
			+ " :idUsuario = e.oficial3.id OR :idUsuario = e.oficial4.id ")
	Integer countDesignacionesByUsuario(@Param("idUsuario") Integer idUsuario);
}
