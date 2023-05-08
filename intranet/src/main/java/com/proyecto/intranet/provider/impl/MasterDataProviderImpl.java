package com.proyecto.intranet.provider.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.CategoriaDto;
import com.proyecto.intranet.dto.CompeticionDto;
import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PerfilDto;
import com.proyecto.intranet.entity.CategoriaEntity;
import com.proyecto.intranet.entity.CompeticionEntity;
import com.proyecto.intranet.entity.PartidoEntity;
import com.proyecto.intranet.entity.PerfilEntity;
import com.proyecto.intranet.provider.MasterDataProvider;
import com.proyecto.intranet.repository.CategoriaRepository;
import com.proyecto.intranet.repository.CompeticionRepository;
import com.proyecto.intranet.repository.PartidosRepository;
import com.proyecto.intranet.repository.PerfilRepository;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.sun.org.apache.xml.internal.resolver.CatalogEntry;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MasterDataProviderImpl implements MasterDataProvider{

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CompeticionRepository competicionRepository;
	
	@Autowired
	private PartidosRepository partidosRepository;
	
	@Override
	public List<PerfilDto> getAllPerfiles() {
		List<PerfilDto> dtos =  new ArrayList<PerfilDto>();
		try {
			List<PerfilEntity> perfiles = perfilRepository.findAll();
			dtos = ObjectMapperUtils.mapAll(perfiles, PerfilDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar los perfiles:" +  e.getMessage());
		}
		return dtos;
	}

	@Override
	public List<CategoriaDto> getAllCategorias() {
		List<CategoriaDto> dtos =  new ArrayList<CategoriaDto>();
		try {
			List<CategoriaEntity> categorias = categoriaRepository.findAll();
			dtos = ObjectMapperUtils.mapAll(categorias, CategoriaDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar las categorias:" +  e.getMessage());
		}
		return dtos;
	}
	
	@Override
	public List<CompeticionDto> getAllCompeticiones() {
		List<CompeticionDto> dtos =  new ArrayList<CompeticionDto>();
		try {
			List<CompeticionEntity> competiciones = competicionRepository.findAll();
			dtos = ObjectMapperUtils.mapAll(competiciones, CompeticionDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar las competiciones:" +  e.getMessage());
		}
		return dtos;
	}

	@Override
	public List<CompeticionDto> getCompeticionesByCategoria(String categoria) {
		List<CompeticionDto> dtos =  new ArrayList<CompeticionDto>();
		try {
			List<CompeticionEntity> competiciones = partidosRepository.findCompeticionesByCategoria(categoria);
			dtos = ObjectMapperUtils.mapAll(competiciones, CompeticionDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar las competiciones por categoria:" +  e.getMessage());
		}
		return dtos;
	}

	@Override
	public List<Integer> getJornadasByCategoriaAndCompeticion(String categoria, String competicion) {
		List<Integer> jornadas = new ArrayList<Integer>();
		try {
			jornadas = partidosRepository.findJornadasByCategoriaAndCompeticion(categoria, competicion);
		} catch (Exception e) {
			log.error("Error al encontrar las jornadas por categoria y comeptición:" +  e.getMessage());
		}
		return jornadas;
	}

	@Override
	public List<PartidoDto> getPartidosByCategoriaAndCompeticionAndJornada(String categoria, String competicion,
			Integer jornada) {
		List<PartidoDto> dtos =  new ArrayList<PartidoDto>();
		try {
			List<PartidoEntity> partidos = partidosRepository.findPartidosByCategoriaAndCompeticionAndJornada(categoria, competicion, jornada);
			dtos = ObjectMapperUtils.mapAll(partidos, PartidoDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar los partidos por categoria, competicion y jornada:" +  e.getMessage());
		}
		return dtos;
	}
	
}
