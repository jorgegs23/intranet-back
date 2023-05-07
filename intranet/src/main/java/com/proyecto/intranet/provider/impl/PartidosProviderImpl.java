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

import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PartidoFiltroDto;
import com.proyecto.intranet.entity.PartidoEntity;
import com.proyecto.intranet.provider.PartidosProvider;
import com.proyecto.intranet.repository.PartidosRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.proyecto.intranet.utils.PaginacionDto;
import com.proyecto.intranet.utils.Paginated;
import com.proyecto.intranet.utils.ProviderUtiles;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PartidosProviderImpl implements PartidosProvider{
	
	@Autowired
	private PartidosRepository partidosRepository;
	
	@Autowired
	private ProviderUtiles providerUtiles;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public MessageResponseDto<PartidoDto> getPartidoById(Integer id) {
		try {
			Optional<PartidoEntity> partido = partidosRepository.findById(id);
			if (partido.isPresent()) {
				PartidoDto dto = modelMapper.map(partido.get(), PartidoDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado el partido");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el partido: " +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar el partido");
		}
	}

	@Override
	public MessageResponseDto<List<PartidoDto>> getAllPartidos() {
		try {
			List<PartidoEntity> partidos = partidosRepository.findAll();
			List<PartidoDto> dtos = ObjectMapperUtils.mapAll(partidos, PartidoDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar el partido:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar los partidos");
		}
	}

	@Override
	public MessageResponseDto<Paginated<PartidoDto>> filtroPartidos(PartidoFiltroDto filtro) {
		try {
			int page = filtro.getPagina();
			int itemsPerPage = filtro.getItemsPorPagina();		
			
			PaginacionDto pagination = new PaginacionDto();
			Paginated<PartidoDto> partidosResponse = new Paginated<>();
			Pageable pages = providerUtiles.pagesToFind(page, itemsPerPage);
			
			Page<PartidoEntity> listEnts = partidosRepository.filterPage(filtro, pages);
			if (listEnts != null && listEnts.getContent().isEmpty()) {
				if (page != 0) {
					pages = providerUtiles.pagesToFind(page - 1 , itemsPerPage);
				}
				listEnts = partidosRepository.filterPage(filtro, pages);		
			}
			
			List<PartidoDto> partidosFound = listEnts.stream().map(p -> modelMapper.map(p, PartidoDto.class)).collect(Collectors.toList());
			
			partidosResponse.setContent(partidosFound);
			pagination.setItemPerPage(listEnts.getNumberOfElements());
			pagination.setPaginas(listEnts.getTotalPages());
			pagination.setTotal(listEnts.getTotalElements());
			
			partidosResponse.setPaginacion(pagination);
			return MessageResponseDto.success(partidosResponse);
		} catch (Exception e) {
			log.error("Error al recuperar los partidos: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los partidos");
		}
	}

	@Override
	public MessageResponseDto<PartidoDto> addPartido(PartidoDto partidoDto) {
		PartidoEntity partido = new PartidoEntity();
		try {
			partido = ObjectMapperUtils.map(partidoDto, PartidoEntity.class);
			partido = partidosRepository.save(partido);
			PartidoDto dto = modelMapper.map(partido, PartidoDto.class);
			return MessageResponseDto.success(dto);
		} catch (Exception e) {
			log.error("Error al crear el partido: " +  e.getMessage());
			return MessageResponseDto.fail("Error al crear el partido");
		}
	}

	@Override
	public MessageResponseDto<String> editPartido(PartidoDto partidoDto) {
		PartidoEntity partido = new PartidoEntity();
		try {
			partido = ObjectMapperUtils.map(partidoDto, PartidoEntity.class);
			partido = partidosRepository.save(partido);
			return MessageResponseDto.success("Partido editado correctamente");
		} catch (Exception e) {
			log.error("Error al crear el partido: " +  e.getMessage());
			return MessageResponseDto.fail("Error al editar el partido");
		}
	}

	@Override
	public MessageResponseDto<String> deletePartido(List<Integer> ids) {
		try {
			if (ids == null || ids.size() < 1) {
				return MessageResponseDto.fail("No se recibieron partidos para ser eliminados");
			} 
			List<Integer> eliminadosBien = new ArrayList<Integer>();
			List<Integer> eliminadosMal= new ArrayList<Integer>();
			for(Integer id: ids) {
				try {
					partidosRepository.deleteById(id);
					eliminadosBien.add(id);
				} catch (Exception e) {
					log.error("Error al eliminar el partido con id " + id + ":" + e.getMessage() );
					eliminadosMal.add(id);
				}
			}
				
			if (eliminadosBien.size() > 0 && eliminadosMal.size() == 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Partidos eliminados correctamente");
				} else {
					return MessageResponseDto.success("Partido eliminado correctamente");
				}
			}
			StringBuilder sb = new StringBuilder();	
			if (eliminadosMal.size() > 0 ) {
				if (eliminadosMal.size() > 1) {
					sb.append("Error al eliminar los partidos.");
				} else {
					sb.append("Error al eliminar el partido.");
				}
				if (eliminadosBien.size() > 1) sb.append(" El resto se realizaron correctamente.");
				return MessageResponseDto.fail(sb.toString());
			}

		} catch (Exception e) {
			log.error("Error al eliminar partido: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar el partido");
		}
		return MessageResponseDto.fail("No se ha eliminado ningún partido");
	}

}
