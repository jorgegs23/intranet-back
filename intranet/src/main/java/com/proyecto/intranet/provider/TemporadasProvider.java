package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.TemporadaDto;
import com.proyecto.intranet.utils.MessageResponseDto;

public interface TemporadasProvider {

	MessageResponseDto<List<TemporadaDto>> getAllTemporadas();

	MessageResponseDto<String> crearTemporada();

	MessageResponseDto<String> abrirTemporada(Integer id);

	MessageResponseDto<String> cerrarTemporada(Integer id);

}
