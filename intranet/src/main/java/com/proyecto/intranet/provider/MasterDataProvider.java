package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.CategoriaDto;
import com.proyecto.intranet.dto.CompeticionDto;
import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PerfilDto;

public interface MasterDataProvider {

	List<PerfilDto> getAllPerfiles();

	List<CategoriaDto> getAllCategorias();

	List<CompeticionDto> getAllCompeticiones();

	List<CompeticionDto> getCompeticionesByCategoria(String categoria);

	List<Integer> getJornadasByCategoriaAndCompeticion(String categoria, String competicion);

	List<PartidoDto> getPartidosByCategoriaAndCompeticionAndJornada(String categoria, String competicion,
			Integer jornada);

}
