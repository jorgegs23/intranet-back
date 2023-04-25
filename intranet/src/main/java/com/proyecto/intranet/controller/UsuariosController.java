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
import com.proyecto.intranet.utils.MessageResponseDto;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosProvider usuariosProvider;
	
	@GetMapping("/byId/{id}")
	public MessageResponseDto<UsuarioDto> getUsuariosById(@PathVariable("id") Integer id){
		MessageResponseDto<UsuarioDto> usuario = usuariosProvider.getUsuarioById(id);
		return usuario;		
	}
	
	@GetMapping("/all")
	public MessageResponseDto<List<UsuarioDto>> getAllUsuarios(){
		MessageResponseDto<List<UsuarioDto>> usuarios = usuariosProvider.getAllUsuarios();
		return usuarios;		
	}

	@PostMapping("/add")
    public MessageResponseDto<UsuarioDto> addUsuarioDto(@RequestBody UsuarioDto UsuarioDto) {
		MessageResponseDto<UsuarioDto> result = usuariosProvider.addUsuario(UsuarioDto);
        return result;
    }

    @PutMapping("/edit")
    public MessageResponseDto<String> updateUsuarioDto(@RequestBody UsuarioDto UsuarioDto) {
    	MessageResponseDto<String> result = usuariosProvider.editUsuario(UsuarioDto);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public MessageResponseDto<String> deleteUsuarioDto(@PathVariable("id") Integer id) {
    	MessageResponseDto<String> result = usuariosProvider.deleteUsuario(id);
        return result;
    }
    
    @GetMapping("/auth/login")
	public ResponseEntity<UsuarioDto> login(@RequestParam String login, @RequestParam String pass){
		UsuarioDto usuario = usuariosProvider.loginUsuario(login, pass);
		return new ResponseEntity<>(usuario, HttpStatus.OK);		
	}
}
