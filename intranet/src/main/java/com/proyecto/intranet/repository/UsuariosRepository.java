package com.proyecto.intranet.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.intranet.dto.UsuarioFiltroDto;
import com.proyecto.intranet.entity.UsuarioEntity;

public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Integer>{
	
	Optional<UsuarioEntity> findByLogin(String login);
	
	Optional<UsuarioEntity> findByLoginAndPass(String login, String pass);

	@Query("SELECT u FROM UsuarioEntity u WHERE "
			+ " ( :#{#filtro.nombre} IS NULL OR UPPER(u.nombre) LIKE UPPER(CONCAT('%',:#{#filtro.nombre},'%')) "
			+ " OR UPPER(u.apellido1) LIKE UPPER(CONCAT('%',:#{#filtro.nombre},'%'))"
			+ " OR UPPER(u.apellido2) LIKE UPPER(CONCAT('%',:#{#filtro.nombre},'%'))"
			+ "	) AND "
			+ " (:#{#filtro.perfil} IS NULL OR :#{#filtro.perfil} = u.perfil.perfil ) AND "
			+ " (:#{#filtro.activo} IS NULL OR :#{#filtro.activo} = u.activo ) AND "
			+ " (:#{#filtro.validado} IS NULL OR :#{#filtro.validado} = u.validado )")
	Page<UsuarioEntity> filterPage(@Param("filtro") UsuarioFiltroDto filtro, Pageable pageable);

}
