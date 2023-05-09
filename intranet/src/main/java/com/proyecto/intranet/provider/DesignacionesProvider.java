package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.DesignacionDto;
import com.proyecto.intranet.dto.DesignacionFiltroDto;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

public interface DesignacionesProvider {

	MessageResponseDto<DesignacionDto> getDesignacionById(Integer id);

	MessageResponseDto<List<DesignacionDto>> getAllDesignaciones();

	MessageResponseDto<Paginated<DesignacionDto>> filtroDesignaciones(DesignacionFiltroDto filtro);

	MessageResponseDto<DesignacionDto> addDesignacion(DesignacionDto designacionDto);

	MessageResponseDto<String> editDesignacion(DesignacionDto designacionDto);

	MessageResponseDto<String> deleteDesignacion(List<Integer> ids);

	MessageResponseDto<byte[]> descargarInforme(DesignacionFiltroDto filtro);
}
