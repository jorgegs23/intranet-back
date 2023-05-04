package com.proyecto.intranet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.intranet.dto.TemporadaDto;
import com.proyecto.intranet.provider.TemporadasProvider;
import com.proyecto.intranet.utils.MessageResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/temporadas")
@Slf4j
public class TemporadasController {

	@Autowired
	TemporadasProvider temporadaProvider;
	
	@GetMapping("/all")
	public MessageResponseDto<List<TemporadaDto>> getAllTemporadas(){
		MessageResponseDto<List<TemporadaDto>> temporadas = temporadaProvider.getAllTemporadas();
		return temporadas;		
	}
	
	@GetMapping("/create")
    public MessageResponseDto<String> createTemporada() {
		MessageResponseDto<String> result = temporadaProvider.crearTemporada();
        return result;
    }
	
	@GetMapping("/open/{id}")
    public MessageResponseDto<String> abrirTemporada(@PathVariable("id") Integer id) {
		MessageResponseDto<String> result = temporadaProvider.abrirTemporada(id);
        return result;
    }
	
	@GetMapping("/close/{id}")
    public MessageResponseDto<String> cerrarTemporada(@PathVariable("id") Integer id) {
		MessageResponseDto<String> result = temporadaProvider.cerrarTemporada(id);
        return result;
    }
	
	
}
