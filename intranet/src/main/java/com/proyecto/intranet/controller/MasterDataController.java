package com.proyecto.intranet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.intranet.dto.CategoriaDto;
import com.proyecto.intranet.dto.CompeticionDto;
import com.proyecto.intranet.dto.PartidoDto;
import com.proyecto.intranet.dto.PerfilDto;
import com.proyecto.intranet.provider.MasterDataProvider;

@RestController
@RequestMapping("/masterData")
public class MasterDataController {
	
	@Autowired
	private MasterDataProvider masterDataProvider;
	
	@GetMapping("/allPerfiles")
	public ResponseEntity<List<PerfilDto>> getAllPerfiles(){
		List<PerfilDto> perfiles = masterDataProvider.getAllPerfiles();
		return new ResponseEntity<>(perfiles, HttpStatus.OK);		
	}
	
	@GetMapping("/allCategorias")
	public ResponseEntity<List<CategoriaDto>> getAllCategorias(){
		List<CategoriaDto> categorias = masterDataProvider.getAllCategorias();
		return new ResponseEntity<>(categorias, HttpStatus.OK);		
	}
	
	@GetMapping("/allCompeticiones")
	public ResponseEntity<List<CompeticionDto>> getAllCompeticiones(){
		List<CompeticionDto> competiciones = masterDataProvider.getAllCompeticiones();
		return new ResponseEntity<>(competiciones, HttpStatus.OK);		
	}
	
	@GetMapping("/competiciones/{categoria}")
	public ResponseEntity<List<CompeticionDto>> getCompeticionesByCategoria(@PathVariable("categoria") String categoria){
		List<CompeticionDto> competiciones = masterDataProvider.getCompeticionesByCategoria(categoria);
		return new ResponseEntity<>(competiciones, HttpStatus.OK);		
	}
	
	@GetMapping("/jornadas/{categoria}/{competicion}")
	public ResponseEntity<List<Integer>> getJornadasByCategoriaAndCompeticion(@PathVariable("categoria") String categoria,
			@PathVariable("competicion") String competicion){
		List<Integer> jornadas = masterDataProvider.getJornadasByCategoriaAndCompeticion(categoria, competicion);
		return new ResponseEntity<>(jornadas, HttpStatus.OK);		
	}
	
	@GetMapping("/partidos/{categoria}/{competicion}/{jornada}")
	public ResponseEntity<List<PartidoDto>> getPartidosByCategoriaAndCompeticionAndJornada(@PathVariable("categoria") String categoria,
			@PathVariable("competicion") String competicion, @PathVariable("jornada") Integer jornada){
		List<PartidoDto> partidos = masterDataProvider.getPartidosByCategoriaAndCompeticionAndJornada(categoria, competicion, jornada);
		return new ResponseEntity<>(partidos, HttpStatus.OK);		
	}
}
