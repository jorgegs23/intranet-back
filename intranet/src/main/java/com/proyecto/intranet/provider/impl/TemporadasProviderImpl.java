package com.proyecto.intranet.provider.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.TemporadaDto;
import com.proyecto.intranet.entity.TemporadaEntity;
import com.proyecto.intranet.provider.TemporadasProvider;
import com.proyecto.intranet.repository.TemporadasRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TemporadasProviderImpl implements TemporadasProvider{
	
	@Autowired
	private TemporadasRepository temporadasRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MessageResponseDto<List<TemporadaDto>> getAllTemporadas() {
		try {
			List<TemporadaEntity> temporadas = temporadasRepository.findAll(Sort.by("fechaIncio").descending());
			List<TemporadaDto> dtos = ObjectMapperUtils.mapAll(temporadas, TemporadaDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar las temporada:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar las temporadas");
		}
	}
	
	@Override
	public MessageResponseDto<String> crearTemporada() {
		try {
			LocalDate date = LocalDate.now(ZoneId.of("GMT"));
			Optional<TemporadaEntity> temporadaActiva = temporadasRepository.findTemporadaActiva();
			if (temporadaActiva.isPresent()) {
				temporadaActiva.get().setActiva(false);
				temporadaActiva.get().setFechaFin(date.minusDays(1));
				temporadasRepository.save(temporadaActiva.get());
			}
			TemporadaEntity ent = new TemporadaEntity();
			String descripcion = String.valueOf(date.getYear()) + " - " + String.valueOf(date.getYear()+1);
			ent.setDescripcion(descripcion);
			ent.setFechaIncio(date);
			ent.setActiva(Boolean.TRUE);
			temporadasRepository.save(ent);
			return MessageResponseDto.success("Temporada creada correctamente");	
		} catch (Exception e) {
			log.error("Error al crear la temporada:" +  e.getMessage());
			return MessageResponseDto.fail("Error al crear la temporada");
		}
	}
	
	@Override
	public MessageResponseDto<String> abrirTemporada(Integer id) {
		try {
			Optional<TemporadaEntity> opt = temporadasRepository.findById(id);
			TemporadaEntity temporada;
			if (opt.isPresent()) {
				temporada = opt.get();
			} else {
				return MessageResponseDto.fail("Error al recuperar la temporada con id: " + id);
			}
			temporada.setFechaFin(null);
			temporada.setActiva(Boolean.TRUE);
			temporadasRepository.save(temporada);
			return MessageResponseDto.success("Temporada abierta correctamente");	
		} catch (Exception e) {
			log.error("Error al abrir la temporada:" +  e.getMessage());
			return MessageResponseDto.fail("Error al abrir la temporada");
		}
	}
	
	@Override
	public MessageResponseDto<String> cerrarTemporada(Integer id) {
		try {
			Optional<TemporadaEntity> opt = temporadasRepository.findById(id);
			TemporadaEntity temporada;
			if (opt.isPresent()) {
				temporada = opt.get();
			} else {
				return MessageResponseDto.fail("Error al recuperar la temporada con id: " + id);
			}
			LocalDate date = LocalDate.now(ZoneId.of("GMT"));
			temporada.setFechaFin(date);
			temporada.setActiva(Boolean.FALSE);
			temporadasRepository.save(temporada);
			return MessageResponseDto.success("Temporada cerrada correctamente");	
		} catch (Exception e) {
			log.error("Error al cerrar la temporada:" +  e.getMessage());
			return MessageResponseDto.fail("Error al cerrar la temporada");
		}
	}

	@Override
	public MessageResponseDto<TemporadaDto> getTemporadaActiva() {
		try {
			Optional<TemporadaEntity> temporada = temporadasRepository.findTemporadaActiva();
			if (temporada.isPresent()) {
				TemporadaDto dto = modelMapper.map(temporada.get(), TemporadaDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado una temporada activa");
			}	
		} catch (Exception e) {
			log.error("Error al cerrar la temporada:" +  e.getMessage());
			return MessageResponseDto.fail("Error al la temporada activa");
		}
	}
}
