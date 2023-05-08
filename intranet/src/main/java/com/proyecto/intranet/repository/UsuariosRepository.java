package com.proyecto.intranet.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Query("SELECT u FROM UsuarioEntity u WHERE u.perfil.perfil IN ('ARB','OFI') AND u.validado = true "
			+ " AND (:activos IS NULL OR u.activo = :activos)")
	List<UsuarioEntity> findAllDesignables(@Param("activos") Boolean activos);

	@Transactional
	@Modifying
	@Query("UPDATE UsuarioEntity u SET u.activo = false WHERE u.id=:id")
	void desactivarUsuarioById(@Param("id") Integer id);
}
