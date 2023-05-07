package com.proyecto.intranet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.intranet.dto.EquipoDto;
import com.proyecto.intranet.dto.EquipoFiltroDto;
import com.proyecto.intranet.provider.EquiposProvider;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/equipos")
@Slf4j
public class EquiposController {

	@Autowired
	private EquiposProvider equiposProvider;
	
	@GetMapping("/byId/{id}")
	public MessageResponseDto<EquipoDto> getEquipoById(@PathVariable("id") Integer id){
		MessageResponseDto<EquipoDto> equipo = equiposProvider.getEquipoById(id);
		return equipo;		
	}
	
	@GetMapping("/all")
	public MessageResponseDto<List<EquipoDto>> getAllEquipos(){
		MessageResponseDto<List<EquipoDto>> equipos = equiposProvider.getAllEquipos();
		return equipos;		
	}

	@PostMapping("/filter")
	public MessageResponseDto<Paginated<EquipoDto>> filterEquipos(@RequestBody EquipoFiltroDto filtro) {
		try {
			MessageResponseDto<Paginated<EquipoDto>> result = equiposProvider.filtroEquipos(filtro);
			return result;
		} catch (Exception e) {
			log.error("Error al recuperar los equipos: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los equipos");
		}
	}
	
	@PostMapping("/add")
    public MessageResponseDto<EquipoDto> addEquipoDto(@RequestBody EquipoDto EquipoDto) {
		MessageResponseDto<EquipoDto> result = equiposProvider.addEquipo(EquipoDto);
        return result;
    }

    @PutMapping("/edit")
    public MessageResponseDto<String> updateEquipoDto(@RequestBody EquipoDto EquipoDto) {
    	MessageResponseDto<String> result = equiposProvider.editEquipo(EquipoDto);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public MessageResponseDto<String> deleteEquipoDto(@PathVariable("id") List<Integer> ids) {
    	MessageResponseDto<String> result = equiposProvider.deleteEquipo(ids);
        return result;
    }
    
    @GetMapping("/byCategoriaAndTemporada")
	public MessageResponseDto<List<EquipoDto>> getByCategoriaAndTemporada(
			@RequestParam(required = true) String categoria,
			@RequestParam(required = true) Integer idTemporada){
		MessageResponseDto<List<EquipoDto>> equipos = equiposProvider.getByCategoriaAndTemporada(categoria, idTemporada);
		return equipos;		
	}
}
