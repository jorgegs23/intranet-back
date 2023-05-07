package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PartidoFiltroDto;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

public interface PartidosProvider {

	MessageResponseDto<PartidoDto> getPartidoById(Integer id);

	MessageResponseDto<List<PartidoDto>> getAllPartidos();

	MessageResponseDto<Paginated<PartidoDto>> filtroPartidos(PartidoFiltroDto filtro);

	MessageResponseDto<PartidoDto> addPartido(PartidoDto partidoDto);

	MessageResponseDto<String> editPartido(PartidoDto partidoDto);

	MessageResponseDto<String> deletePartido(List<Integer> ids);
}
