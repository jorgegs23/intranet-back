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

import com.proyecto.intranet.dto.EquipoDto;
import com.proyecto.intranet.dto.EquipoFiltroDto;
import com.proyecto.intranet.entity.EquipoEntity;
import com.proyecto.intranet.provider.EquiposProvider;
import com.proyecto.intranet.repository.EquiposRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.proyecto.intranet.utils.PaginacionDto;
import com.proyecto.intranet.utils.Paginated;
import com.proyecto.intranet.utils.ProviderUtiles;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EquiposProviderImpl implements EquiposProvider{

	@Autowired
	private EquiposRepository equiposRepository;
	
	@Autowired
	private ProviderUtiles providerUtiles;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MessageResponseDto<EquipoDto> getEquipoById(Integer id) {
		try {
			Optional<EquipoEntity> equipo = equiposRepository.findById(id);
			if (equipo.isPresent()) {
				EquipoDto dto = modelMapper.map(equipo.get(), EquipoDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado el equipo");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el equipo: " +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar el equipo");
		}
	}

	@Override
	public MessageResponseDto<List<EquipoDto>> getAllEquipos() {
		try {
			List<EquipoEntity> equipos = equiposRepository.findAll();
			List<EquipoDto> dtos = ObjectMapperUtils.mapAll(equipos, EquipoDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar el equipo:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar los equipos");
		}
	}

	@Override
	public MessageResponseDto<Paginated<EquipoDto>> filtroEquipos(EquipoFiltroDto filtro) {
		try {
			int page = filtro.getPagina();
			int itemsPerPage = filtro.getItemsPorPagina();		
			
			PaginacionDto pagination = new PaginacionDto();
			Paginated<EquipoDto> equiposResponse = new Paginated<>();
			Pageable pages = providerUtiles.pagesToFind(page, itemsPerPage);
			
			Page<EquipoEntity> listEnts = equiposRepository.filterPage(filtro, pages);
			if (listEnts != null && listEnts.getContent().isEmpty()) {
				if (page != 0) {
					pages = providerUtiles.pagesToFind(page - 1 , itemsPerPage);
				}
				listEnts = equiposRepository.filterPage(filtro, pages);		
			}
			
			List<EquipoDto> equiposFound = listEnts.stream().map(p -> modelMapper.map(p, EquipoDto.class)).collect(Collectors.toList());
			
			equiposResponse.setContent(equiposFound);
			pagination.setItemPerPage(listEnts.getNumberOfElements());
			pagination.setPaginas(listEnts.getTotalPages());
			pagination.setTotal(listEnts.getTotalElements());
			
			equiposResponse.setPaginacion(pagination);
			return MessageResponseDto.success(equiposResponse);
		} catch (Exception e) {
			log.error("Error al recuperar los equipos: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los equipos");
		}
	}

	@Override
	public MessageResponseDto<EquipoDto> addEquipo(EquipoDto equipoDto) {
		EquipoEntity equipo = new EquipoEntity();
		try {
			equipo = ObjectMapperUtils.map(equipoDto, EquipoEntity.class);
			equipo = equiposRepository.save(equipo);
			EquipoDto dto = modelMapper.map(equipo, EquipoDto.class);
			return MessageResponseDto.success(dto);
		} catch (Exception e) {
			log.error("Error al crear el equipo: " +  e.getMessage());
			return MessageResponseDto.fail("Error al crear el equipo");
		}
	}

	@Override
	public MessageResponseDto<String> editEquipo(EquipoDto equipoDto) {
		EquipoEntity equipo = new EquipoEntity();
		try {
			equipo = ObjectMapperUtils.map(equipoDto, EquipoEntity.class);
			equipo = equiposRepository.save(equipo);
			return MessageResponseDto.success("Equipo editado correctamente");
		} catch (Exception e) {
			log.error("Error al crear el equipo: " +  e.getMessage());
			return MessageResponseDto.fail("Error al editar el equipo");
		}
	}

	@Override
	public MessageResponseDto<String> deleteEquipo(List<Integer> ids) {
		try {
			if (ids == null || ids.size() < 1) {
				return MessageResponseDto.fail("No se recibieron equipos para ser eliminados");
			} 
			List<Integer> eliminadosBien = new ArrayList<Integer>();
			List<Integer> eliminadosMal= new ArrayList<Integer>();
			for(Integer id: ids) {
				try {
					equiposRepository.deleteById(id);
					eliminadosBien.add(id);
				} catch (Exception e) {
					log.error("Error al eliminar el equipo con id " + id + ":" + e.getMessage() );
					eliminadosMal.add(id);
				}
			}
				
			if (eliminadosBien.size() > 0 && eliminadosMal.size() == 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Equipos eliminados correctamente");
				} else {
					return MessageResponseDto.success("Equipo eliminado correctamente");
				}
			}
			StringBuilder sb = new StringBuilder();	
			if (eliminadosMal.size() > 0 ) {
				if (eliminadosMal.size() > 1) {
					sb.append("Error al eliminar los equipos.");
				} else {
					sb.append("Error al eliminar el equipo.");
				}
				if (eliminadosBien.size() > 1) sb.append(" El resto se realizaron correctamente.");
				return MessageResponseDto.fail(sb.toString());
			}

		} catch (Exception e) {
			log.error("Error al eliminar equipo: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar el equipo");
		}
		return MessageResponseDto.fail("No se ha eliminado ningún equipo");
	}

	@Override
	public MessageResponseDto<List<EquipoDto>> getByCategoriaAndTemporada(String categoria, Integer idTemporada) {
		try {
			List<EquipoEntity> equipos = equiposRepository.findByCategoriaAndTemporada(categoria, idTemporada);
			List<EquipoDto> dtos = ObjectMapperUtils.mapAll(equipos, EquipoDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar el equipo:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar los equipos");
		}
	}
}
