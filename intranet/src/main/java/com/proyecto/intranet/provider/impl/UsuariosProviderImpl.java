package com.proyecto.intranet.provider.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.UsuarioDto;
import com.proyecto.intranet.entity.UsuarioEntity;
import com.proyecto.intranet.provider.UsuariosProvider;
import com.proyecto.intranet.repository.UsuariosRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UsuariosProviderImpl implements UsuariosProvider{
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public MessageResponseDto<UsuarioDto> getUsuarioById(Integer id) {
		try {
			Optional<UsuarioEntity> usuario = usuariosRepository.findById(id);
			if (usuario.isPresent()) {
				UsuarioDto dto = modelMapper.map(usuario.get(), UsuarioDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado el usuario");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el usuario: " +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar el usuario");
		}
	}

	@Override
	public MessageResponseDto<List<UsuarioDto>> getAllUsuarios() {
		try {
			List<UsuarioEntity> usuarios = usuariosRepository.findAll();
			List<UsuarioDto> dtos = ObjectMapperUtils.mapAll(usuarios, UsuarioDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar el usuario:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar los usuarios");
		}
	}
	
	@Override
	public MessageResponseDto<UsuarioDto> addUsuario(UsuarioDto usuarioDto) {
		UsuarioEntity usuario = new UsuarioEntity();
		try {
			usuario = ObjectMapperUtils.map(usuarioDto, UsuarioEntity.class);
			usuario = usuariosRepository.save(usuario);
			UsuarioDto dto = modelMapper.map(usuario, UsuarioDto.class);
			return MessageResponseDto.success(dto);
		} catch (Exception e) {
			log.error("Error al crear el usuario: " +  e.getMessage());
			return MessageResponseDto.fail("Error al crear el usuario");
		}
	}
	
	@Override
	public MessageResponseDto<String> editUsuario(UsuarioDto usuarioDto) {
		UsuarioEntity usuario = new UsuarioEntity();
		try {
			usuario = ObjectMapperUtils.map(usuarioDto, UsuarioEntity.class);
			usuario = usuariosRepository.save(usuario);
			return MessageResponseDto.success("Usuario editado correctamente");
		} catch (Exception e) {
			log.error("Error al crear el usuario: " +  e.getMessage());
			return MessageResponseDto.fail("Error al editar el usuario");
		}
	}
	
	@Override
	public MessageResponseDto<String> deleteUsuario(Integer id) {
		try {
			usuariosRepository.deleteById(id);
			return MessageResponseDto.success("Usuario eliminado correctamente");
		} catch (Exception e) {
			log.error("Error al eliminar usuario: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar el usuario");
		}
	}
	
	
	@Override
	public UsuarioDto loginUsuario(String login, String pass) {
		try {
			Optional<UsuarioEntity> usuario = usuariosRepository.findByLoginAndPass(login, pass);
			if (usuario.isPresent()) {
				UsuarioDto dto = modelMapper.map(usuario.get(), UsuarioDto.class);
				return dto;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el usuario en login: " +  e.getMessage());
		}
		return null;
	}
	

	
	

}
