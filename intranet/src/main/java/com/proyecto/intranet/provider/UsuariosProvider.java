package com.proyecto.intranet.provider;

import java.util.List;

import com.proyecto.intranet.dto.UsuarioDto;
import com.proyecto.intranet.utils.MessageResponseDto;

public interface UsuariosProvider {

	MessageResponseDto<UsuarioDto> getUsuarioById(Integer id);

	MessageResponseDto<List<UsuarioDto>> getAllUsuarios();

	UsuarioDto loginUsuario(String login, String pass);

	MessageResponseDto<UsuarioDto> addUsuario(UsuarioDto usuarioDto);

	MessageResponseDto<String> editUsuario(UsuarioDto usuarioDto);

	MessageResponseDto<String> deleteUsuario(Integer id);

}
