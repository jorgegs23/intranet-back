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

import com.proyecto.intranet.dto.DesignacionDto;
import com.proyecto.intranet.dto.DesignacionFiltroDto;
import com.proyecto.intranet.provider.DesignacionesProvider;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.Paginated;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/designaciones")
@Slf4j
public class DesignacionesController {

	@Autowired
	private DesignacionesProvider designacionesProvider;
	
	@GetMapping("/byId/{id}")
	public MessageResponseDto<DesignacionDto> getDesignacionById(@PathVariable("id") Integer id){
		MessageResponseDto<DesignacionDto> designacion = designacionesProvider.getDesignacionById(id);
		return designacion;		
	}
	
	@GetMapping("/all")
	public MessageResponseDto<List<DesignacionDto>> getAllDesignaciones(){
		MessageResponseDto<List<DesignacionDto>> designaciones = designacionesProvider.getAllDesignaciones();
		return designaciones;		
	}

	@PostMapping("/filter")
	public MessageResponseDto<Paginated<DesignacionDto>> filterDesignaciones(@RequestBody DesignacionFiltroDto filtro) {
		try {
			MessageResponseDto<Paginated<DesignacionDto>> result = designacionesProvider.filtroDesignaciones(filtro);
			return result;
		} catch (Exception e) {
			log.error("Error al recuperar los designaciones: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los designaciones");
		}
	}
	
	@PostMapping("/add")
    public MessageResponseDto<DesignacionDto> addDesignacionDto(@RequestBody DesignacionDto DesignacionDto) {
		MessageResponseDto<DesignacionDto> result = designacionesProvider.addDesignacion(DesignacionDto);
        return result;
    }

    @PutMapping("/edit")
    public MessageResponseDto<String> updateDesignacionDto(@RequestBody DesignacionDto DesignacionDto) {
    	MessageResponseDto<String> result = designacionesProvider.editDesignacion(DesignacionDto);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public MessageResponseDto<String> deleteDesignacionDto(@PathVariable("id") List<Integer> ids) {
    	MessageResponseDto<String> result = designacionesProvider.deleteDesignacion(ids);
        return result;
    }
}
