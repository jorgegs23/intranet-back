package com.proyecto.intranet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.intranet.dto.UsuarioDto;
import com.proyecto.intranet.provider.UsuariosProvider;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosProvider usuariosProvider;
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<UsuarioDto> getUsuariosById(@PathVariable("id") Integer id){
		UsuarioDto usuario = usuariosProvider.getUsuarioById(id);
		return new ResponseEntity<>(usuario, HttpStatus.OK);		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UsuarioDto>> getAllUsuarios(){
		List<UsuarioDto> usuarios = usuariosProvider.getAllUsuarios();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);		
	}

	@PostMapping("/add")
    public ResponseEntity<String> addUsuarioDto(@RequestBody UsuarioDto UsuarioDto) {
        String result = usuariosProvider.addUsuario(UsuarioDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUsuarioDto(@RequestBody UsuarioDto UsuarioDto) {
    	String result = usuariosProvider.editUsuario(UsuarioDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUsuarioDto(@PathVariable("id") Integer id) {
    	String result = usuariosProvider.deleteUsuario(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/auth/login")
	public ResponseEntity<UsuarioDto> login(@RequestParam String login, @RequestParam String pass){
		UsuarioDto usuario = usuariosProvider.loginUsuario(login, pass);
		return new ResponseEntity<>(usuario, HttpStatus.OK);		
	}
}
