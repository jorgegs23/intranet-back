package com.proyecto.intranet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.intranet.entity.UsuarioEntity;

public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Integer>{
	
	Optional<UsuarioEntity> findByLogin(String login);
	
	Optional<UsuarioEntity> findByLoginAndPass(String login, String pass);

}
