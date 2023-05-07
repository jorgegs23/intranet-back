package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.EquipoDto;
import com.proyecto.intranet.dto.EquipoFiltroDto;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

public interface EquiposProvider {

	MessageResponseDto<EquipoDto> getEquipoById(Integer id);

	MessageResponseDto<List<EquipoDto>> getAllEquipos();

	MessageResponseDto<Paginated<EquipoDto>> filtroEquipos(EquipoFiltroDto filtro);

	MessageResponseDto<EquipoDto> addEquipo(EquipoDto equipoDto);

	MessageResponseDto<String> editEquipo(EquipoDto equipoDto);

	MessageResponseDto<String> deleteEquipo(List<Integer> ids);

	MessageResponseDto<List<EquipoDto>> getByCategoriaAndTemporada(String idCategoria, Integer idTemporada);

}
