package com.proyecto.intranet.provider.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.PerfilDto;
import com.proyecto.intranet.entity.PerfilEntity;
import com.proyecto.intranet.provider.MasterDataProvider;
import com.proyecto.intranet.repository.PerfilRepository;
import com.proyecto.intranet.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MasterDataProviderImpl implements MasterDataProvider{

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Override
	public List<PerfilDto> getAllPerfiles() {
		try {
			List<PerfilEntity> perfiles = perfilRepository.findAll();
			List<PerfilDto> dtos = ObjectMapperUtils.mapAll(perfiles, PerfilDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar los perfiles:" +  e.getMessage());
		}
		return null;
	}
	
}
