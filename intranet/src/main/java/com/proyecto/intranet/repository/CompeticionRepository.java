package com.proyecto.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.intranet.entity.CompeticionEntity;

@Repository
public interface CompeticionRepository extends JpaRepository<CompeticionEntity, String>{

}
