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
	public UsuarioDto getUsuarioById(Integer id) {
		try {
			Optional<UsuarioEntity> usuario = usuariosRepository.findById(id);
			if (usuario.isPresent()) {
				UsuarioDto dto = modelMapper.map(usuario.get(), UsuarioDto.class);
				return dto;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el usuario: " +  e.getMessage());
		}
		return null;
	}

	@Override
	public List<UsuarioDto> getAllUsuarios() {
		try {
			List<UsuarioEntity> usuarios = usuariosRepository.findAll();
			List<UsuarioDto> dtos = ObjectMapperUtils.mapAll(usuarios, UsuarioDto.class);
			return dtos;	
		} catch (Exception e) {
			log.error("Error al encontrar el usuario:" +  e.getMessage());
		}
		return null;
	}
	
	@Override
	public String addUsuario(UsuarioDto usuarioDto) {
		UsuarioEntity usuario = new UsuarioEntity();
		try {
			usuario = ObjectMapperUtils.map(usuarioDto, UsuarioEntity.class);
			usuario = usuariosRepository.save(usuario);
			return "Usuario creado correctamente";
		} catch (Exception e) {
			log.error("Error al crear el usuario: " +  e.getMessage());
			return "Error al crear el usuario";
		}
	}
	
	@Override
	public String editUsuario(UsuarioDto usuarioDto) {
		UsuarioEntity usuario = new UsuarioEntity();
		try {
			usuario = ObjectMapperUtils.map(usuarioDto, UsuarioEntity.class);
			usuario = usuariosRepository.save(usuario);
			return "Usuario editado correctamente";
		} catch (Exception e) {
			log.error("Error al crear el usuario: " +  e.getMessage());
			return "Error al editar el usuario";
		}
	}
	
	@Override
	public String deleteUsuario(Integer id) {
		try {
			usuariosRepository.deleteById(id);
			return "Usuario eliminado correctamente";
		} catch (Exception e) {
			log.error("Error al eliminar usuario: " +  e.getMessage());
			return "Error al eliminar el usuario";
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
