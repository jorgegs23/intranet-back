package com.proyecto.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.entity.PerfilEntity;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, String>{

}
