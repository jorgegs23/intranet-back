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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PartidoFiltroDto;
import com.proyecto.intranet.provider.PartidosProvider;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/partidos")
@Slf4j
public class PartidosController {

	@Autowired
	private PartidosProvider partidosProvider;
	
	@GetMapping("/byId/{id}")
	public MessageResponseDto<PartidoDto> getPartidoById(@PathVariable("id") Integer id){
		MessageResponseDto<PartidoDto> partido = partidosProvider.getPartidoById(id);
		return partido;		
	}
	
	@GetMapping("/all")
	public MessageResponseDto<List<PartidoDto>> getAllPartidos(){
		MessageResponseDto<List<PartidoDto>> partidos = partidosProvider.getAllPartidos();
		return partidos;		
	}

	@PostMapping("/filter")
	public MessageResponseDto<Paginated<PartidoDto>> filterPartidos(@RequestBody PartidoFiltroDto filtro) {
		try {
			MessageResponseDto<Paginated<PartidoDto>> result = partidosProvider.filtroPartidos(filtro);
			return result;
		} catch (Exception e) {
			log.error("Error al recuperar los partidos: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los partidos");
		}
	}
	
	@PostMapping("/add")
    public MessageResponseDto<PartidoDto> addPartidoDto(@RequestBody PartidoDto PartidoDto) {
		MessageResponseDto<PartidoDto> result = partidosProvider.addPartido(PartidoDto);
        return result;
    }

    @PutMapping("/edit")
    public MessageResponseDto<String> updatePartidoDto(@RequestBody PartidoDto PartidoDto) {
    	MessageResponseDto<String> result = partidosProvider.editPartido(PartidoDto);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public MessageResponseDto<String> deletePartidoDto(@PathVariable("id") List<Integer> ids) {
    	MessageResponseDto<String> result = partidosProvider.deletePartido(ids);
        return result;
    }
}
