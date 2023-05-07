package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.CategoriaDto;
import com.proyecto.intranet.dto.CompeticionDto;
import com.proyecto.intranet.dto.PerfilDto;

public interface MasterDataProvider {

	List<PerfilDto> getAllPerfiles();

	List<CategoriaDto> getAllCategorias();

	List<CompeticionDto> getAllCompeticiones();

}
