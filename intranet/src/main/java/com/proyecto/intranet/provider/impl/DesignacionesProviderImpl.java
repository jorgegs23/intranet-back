package com.proyecto.intranet.provider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.DesignacionDto;
import com.proyecto.intranet.dto.DesignacionFiltroDto;
import com.proyecto.intranet.entity.DesignacionEntity;
import com.proyecto.intranet.provider.DesignacionesProvider;
import com.proyecto.intranet.repository.DesignacionesRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.proyecto.intranet.utils.PaginacionDto;
import com.proyecto.intranet.utils.Paginated;
import com.proyecto.intranet.utils.ProviderUtiles;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DesignacionesProviderImpl implements DesignacionesProvider{
	
	@Autowired
	private DesignacionesRepository designacionesRepository;
	
	@Autowired
	private ProviderUtiles providerUtiles;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public MessageResponseDto<DesignacionDto> getDesignacionById(Integer id) {
		try {
			Optional<DesignacionEntity> designacion = designacionesRepository.findById(id);
			if (designacion.isPresent()) {
				DesignacionDto dto = modelMapper.map(designacion.get(), DesignacionDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado la designación");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar la designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar la designación");
		}
	}

	@Override
	public MessageResponseDto<List<DesignacionDto>> getAllDesignaciones() {
		try {
			List<DesignacionEntity> designaciones = designacionesRepository.findAll();
			List<DesignacionDto> dtos = ObjectMapperUtils.mapAll(designaciones, DesignacionDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar la designacion:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar las designaciones");
		}
	}

	@Override
	public MessageResponseDto<Paginated<DesignacionDto>> filtroDesignaciones(DesignacionFiltroDto filtro) {
		try {
			int page = filtro.getPagina();
			int itemsPerPage = filtro.getItemsPorPagina();		
			
			PaginacionDto pagination = new PaginacionDto();
			Paginated<DesignacionDto> designacionesResponse = new Paginated<>();
			Pageable pages = providerUtiles.pagesToFind(page, itemsPerPage);
			
			Page<DesignacionEntity> listEnts = designacionesRepository.filterPage(filtro, pages);
			if (listEnts != null && listEnts.getContent().isEmpty()) {
				if (page != 0) {
					pages = providerUtiles.pagesToFind(page - 1 , itemsPerPage);
				}
				listEnts = designacionesRepository.filterPage(filtro, pages);		
			}
			
			List<DesignacionDto> designacionesFound = listEnts.stream().map(p -> modelMapper.map(p, DesignacionDto.class)).collect(Collectors.toList());
			
			designacionesResponse.setContent(designacionesFound);
			pagination.setItemPerPage(listEnts.getNumberOfElements());
			pagination.setPaginas(listEnts.getTotalPages());
			pagination.setTotal(listEnts.getTotalElements());
			
			designacionesResponse.setPaginacion(pagination);
			return MessageResponseDto.success(designacionesResponse);
		} catch (Exception e) {
			log.error("Error al recuperar las designaciones: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar las designaciones");
		}
	}

	@Override
	public MessageResponseDto<DesignacionDto> addDesignacion(DesignacionDto designacionDto) {
		DesignacionEntity designacion = new DesignacionEntity();
		try {
			designacion = ObjectMapperUtils.map(designacionDto, DesignacionEntity.class);
			designacion = designacionesRepository.save(designacion);
			DesignacionDto dto = modelMapper.map(designacion, DesignacionDto.class);
			return MessageResponseDto.success(dto);
		} catch (Exception e) {
			log.error("Error al crear la designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al crear la designación");
		}
	}

	@Override
	public MessageResponseDto<String> editDesignacion(DesignacionDto designacionDto) {
		DesignacionEntity designacion = new DesignacionEntity();
		try {
			designacion = ObjectMapperUtils.map(designacionDto, DesignacionEntity.class);
			designacion = designacionesRepository.save(designacion);
			return MessageResponseDto.success("Designacion editada correctamente");
		} catch (Exception e) {
			log.error("Error al crear el designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al editar la designación");
		}
	}

	@Override
	public MessageResponseDto<String> deleteDesignacion(List<Integer> ids) {
		try {
			if (ids == null || ids.size() < 1) {
				return MessageResponseDto.fail("No se recibieron designaciones para ser eliminadas");
			} 
			List<Integer> eliminadosBien = new ArrayList<Integer>();
			List<Integer> eliminadosMal= new ArrayList<Integer>();
			for(Integer id: ids) {
				try {
					designacionesRepository.deleteById(id);
					eliminadosBien.add(id);
				} catch (Exception e) {
					log.error("Error al eliminar la designacion con id " + id + ":" + e.getMessage() );
					eliminadosMal.add(id);
				}
			}
				
			if (eliminadosBien.size() > 0 && eliminadosMal.size() == 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Designaciones eliminadas correctamente");
				} else {
					return MessageResponseDto.success("Designacion eliminado correctamente");
				}
			}
			StringBuilder sb = new StringBuilder();	
			if (eliminadosMal.size() > 0 ) {
				if (eliminadosMal.size() > 1) {
					sb.append("Error al eliminar las designaciones.");
				} else {
					sb.append("Error al eliminar la designacion.");
				}
				if (eliminadosBien.size() > 1) sb.append(" El resto se realizaron correctamente.");
				return MessageResponseDto.fail(sb.toString());
			}

		} catch (Exception e) {
			log.error("Error al eliminar designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar la designación");
		}
		return MessageResponseDto.fail("No se ha eliminado ninguna designación");
	}
}
