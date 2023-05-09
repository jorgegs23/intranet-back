package com.proyecto.intranet.provider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.UsuarioDto;
import com.proyecto.intranet.dto.UsuarioFiltroDto;
import com.proyecto.intranet.entity.UsuarioEntity;
import com.proyecto.intranet.provider.UsuariosProvider;
import com.proyecto.intranet.repository.DesignacionesRepository;
import com.proyecto.intranet.repository.UsuariosRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.proyecto.intranet.utils.PaginacionDto;
import com.proyecto.intranet.utils.Paginated;
import com.proyecto.intranet.utils.ProviderUtiles;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UsuariosProviderImpl implements UsuariosProvider{
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private DesignacionesRepository designacionesRepository;
	
	@Autowired
	private ProviderUtiles providerUtiles;
	
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
	public MessageResponseDto<Paginated<UsuarioDto>> filtroUsuarios(UsuarioFiltroDto filtro) {
		try {
			int page = filtro.getPagina();
			int itemsPerPage = filtro.getItemsPorPagina();		
			
			PaginacionDto pagination = new PaginacionDto();
			Paginated<UsuarioDto> usuariosResponse = new Paginated<>();
			Pageable pages = providerUtiles.pagesToFind(page, itemsPerPage);
			
			Page<UsuarioEntity> listEnts = usuariosRepository.filterPage(filtro, pages);
			if (listEnts != null && listEnts.getContent().isEmpty()) {
				if (page != 0) {
					pages = providerUtiles.pagesToFind(page - 1 , itemsPerPage);
				}
				listEnts = usuariosRepository.filterPage(filtro, pages);		
			}
			
			List<UsuarioDto> usuariosFound = listEnts.stream().map(p -> modelMapper.map(p, UsuarioDto.class)).collect(Collectors.toList());
			
			usuariosResponse.setContent(usuariosFound);
			pagination.setItemPerPage(listEnts.getNumberOfElements());
			pagination.setPaginas(listEnts.getTotalPages());
			pagination.setTotal(listEnts.getTotalElements());
			
			usuariosResponse.setPaginacion(pagination);
			return MessageResponseDto.success(usuariosResponse);
		} catch (Exception e) {
			log.error("Error al recuperar los usuarios: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar los usuarios");
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
	public MessageResponseDto<String> deleteUsuario(List<Integer> ids) {
		try {
			if (ids == null || ids.size() < 1) {
				return MessageResponseDto.fail("No se recibieron usuarios para ser eliminados");
			} 
			List<Integer> eliminadosBien = new ArrayList<Integer>();
			List<Integer> eliminadosMal= new ArrayList<Integer>();
			List<Integer> desactivados= new ArrayList<Integer>();
			for(Integer id: ids) {
				try {
					if (designacionesRepository.countDesignacionesByUsuario(id) > 0) {	
						usuariosRepository.desactivarUsuarioById(id);
						desactivados.add(id);
					} else {
						usuariosRepository.deleteById(id);
						eliminadosBien.add(id);
					}		
				} catch (Exception e) {
					log.error("Error al eliminar el usuario con id " + id + ":" + e.getMessage() );
					eliminadosMal.add(id);
				}
			}
				
			if (eliminadosBien.size() > 0 && eliminadosMal.size() == 0  && desactivados.size() == 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Usuarios eliminados correctamente");
				} else {
					return MessageResponseDto.success("Usuario eliminado correctamente");
				}
			}
			if (eliminadosBien.size() == 0 && eliminadosMal.size() == 0  && desactivados.size() > 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Usuarios desactivados correctamente");
				} else {
					return MessageResponseDto.success("Usuario desactivado correctamente");
				}
			}
			StringBuilder sb = new StringBuilder();	
			if (eliminadosMal.size() > 0 ) {
				if (eliminadosMal.size() > 1) {
					sb.append("Error al eliminar los usuarios.");
				} else {
					sb.append("Error al eliminar el usuario.");
				}
				if (eliminadosBien.size() > 1 || desactivados.size() > 0) 
					sb.append(" El resto se eliminaron/desactivaron correctamente.");
				return MessageResponseDto.fail(sb.toString());
			}

		} catch (Exception e) {
			log.error("Error al eliminar usuario: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar el usuario");
		}
		return MessageResponseDto.fail("No se ha eliminado ningún usuario");
	}
	
	
	@Override
	public MessageResponseDto<UsuarioDto> loginUsuario(String login, String pass) {
		try {
			Optional<UsuarioEntity> usuario = usuariosRepository.findByLoginAndPass(login, pass);
			if (usuario.isPresent()) {
				if (!usuario.get().getValidado()) {
					return MessageResponseDto.fail("El usuario no esta validado");
				}
				UsuarioDto dto = modelMapper.map(usuario.get(), UsuarioDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("Usuario o contraseña incorectos");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar el usuario en login: " +  e.getMessage());
			return MessageResponseDto.fail("Error al comprobar el usuario");
		}
		
	}

	@Override
	public MessageResponseDto<List<UsuarioDto>> getAllDesignables(boolean activos) {
		try {
			Boolean allActivos = activos ? Boolean.TRUE : null;
			List<UsuarioEntity> usuarios = usuariosRepository.findAllDesignables(allActivos);
			List<UsuarioDto> dtos = ObjectMapperUtils.mapAll(usuarios, UsuarioDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar el usuario:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar los usuarios");
		}
	}

}
