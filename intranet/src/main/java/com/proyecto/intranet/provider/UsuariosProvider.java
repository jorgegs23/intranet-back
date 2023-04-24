package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.UsuarioDto;

public interface UsuariosProvider {

	UsuarioDto getUsuarioById(Integer id);

	List<UsuarioDto> getAllUsuarios();

	UsuarioDto loginUsuario(String login, String pass);

	String addUsuario(UsuarioDto usuarioDto);

	String editUsuario(UsuarioDto usuarioDto);

	String deleteUsuario(Integer id);

}
