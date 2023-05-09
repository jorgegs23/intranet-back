package com.proyecto.intranet.provider.impl;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.dto.DesignacionDto;
import com.proyecto.intranet.dto.DesignacionFiltroDto;
import com.proyecto.intranet.entity.DesignacionEntity;
import com.proyecto.intranet.entity.TemporadaEntity;
import com.proyecto.intranet.entity.UsuarioEntity;
import com.proyecto.intranet.provider.DesignacionesProvider;
import com.proyecto.intranet.provider.JasperDocumentProvider;
import com.proyecto.intranet.repository.DesignacionesRepository;
import com.proyecto.intranet.repository.TemporadasRepository;
import com.proyecto.intranet.repository.UsuariosRepository;
import com.proyecto.intranet.utils.MessageResponseDto;
import com.proyecto.intranet.utils.ObjectMapperUtils;
import com.proyecto.intranet.utils.PaginacionDto;
import com.proyecto.intranet.utils.Paginated;
import com.proyecto.intranet.utils.ProviderUtiles;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperPrint;

@Component
@Slf4j
public class DesignacionesProviderImpl implements DesignacionesProvider{
	
	@Autowired
	private DesignacionesRepository designacionesRepository;
	
	@Autowired
	private TemporadasRepository temporadasRepository;
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private ProviderUtiles providerUtiles;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JasperDocumentProvider jasperDocumentProvider;
	
	private static final String PLANTILLA_INFORME = "informe.jasper";
	
	@Override
	public MessageResponseDto<DesignacionDto> getDesignacionById(Integer id) {
		try {
			Optional<DesignacionEntity> designacion = designacionesRepository.findById(id);
			if (designacion.isPresent()) {
				DesignacionDto dto = modelMapper.map(designacion.get(), DesignacionDto.class);
				return MessageResponseDto.success(dto);
			} else {
				return MessageResponseDto.fail("No se ha encontrado la designación");
			}
			
		} catch (Exception e) {
			log.error("Error al encontrar la designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar la designación");
		}
	}

	@Override
	public MessageResponseDto<List<DesignacionDto>> getAllDesignaciones() {
		try {
			List<DesignacionEntity> designaciones = designacionesRepository.findAll();
			List<DesignacionDto> dtos = ObjectMapperUtils.mapAll(designaciones, DesignacionDto.class);
			return MessageResponseDto.success(dtos);	
		} catch (Exception e) {
			log.error("Error al encontrar la designacion:" +  e.getMessage());
			return MessageResponseDto.fail("Error al encontrar las designaciones");
		}
	}

	@Override
	public MessageResponseDto<Paginated<DesignacionDto>> filtroDesignaciones(DesignacionFiltroDto filtro) {
		try {
			int page = filtro.getPagina();
			int itemsPerPage = filtro.getItemsPorPagina();		
			
			PaginacionDto pagination = new PaginacionDto();
			Paginated<DesignacionDto> designacionesResponse = new Paginated<>();
			Pageable pages = providerUtiles.pagesToFind(page, itemsPerPage);
			
			Page<DesignacionEntity> listEnts = designacionesRepository.filterPage(filtro, pages);
			if (listEnts != null && listEnts.getContent().isEmpty()) {
				if (page != 0) {
					pages = providerUtiles.pagesToFind(page - 1 , itemsPerPage);
				}
				listEnts = designacionesRepository.filterPage(filtro, pages);		
			}
			
			List<DesignacionDto> designacionesFound = listEnts.stream().map(p -> modelMapper.map(p, DesignacionDto.class)).collect(Collectors.toList());
			
			designacionesResponse.setContent(designacionesFound);
			pagination.setItemPerPage(listEnts.getNumberOfElements());
			pagination.setPaginas(listEnts.getTotalPages());
			pagination.setTotal(listEnts.getTotalElements());
			
			designacionesResponse.setPaginacion(pagination);
			return MessageResponseDto.success(designacionesResponse);
		} catch (Exception e) {
			log.error("Error al recuperar las designaciones: " +e.getMessage());
			return MessageResponseDto.fail("Error al recuperar las designaciones");
		}
	}

	@Override
	public MessageResponseDto<DesignacionDto> addDesignacion(DesignacionDto designacionDto) {
		DesignacionEntity designacion = new DesignacionEntity();
		try {
			designacion = ObjectMapperUtils.map(designacionDto, DesignacionEntity.class);
			designacion = designacionesRepository.save(designacion);
			DesignacionDto dto = modelMapper.map(designacion, DesignacionDto.class);
			return MessageResponseDto.success(dto);
		} catch (Exception e) {
			log.error("Error al crear la designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al crear la designación");
		}
	}

	@Override
	public MessageResponseDto<String> editDesignacion(DesignacionDto designacionDto) {
		DesignacionEntity designacion = new DesignacionEntity();
		try {
			designacion = ObjectMapperUtils.map(designacionDto, DesignacionEntity.class);
			designacion = designacionesRepository.save(designacion);
			return MessageResponseDto.success("Designacion editada correctamente");
		} catch (Exception e) {
			log.error("Error al crear el designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al editar la designación");
		}
	}

	@Override
	public MessageResponseDto<String> deleteDesignacion(List<Integer> ids) {
		try {
			if (ids == null || ids.size() < 1) {
				return MessageResponseDto.fail("No se recibieron designaciones para ser eliminadas");
			} 
			List<Integer> eliminadosBien = new ArrayList<Integer>();
			List<Integer> eliminadosMal= new ArrayList<Integer>();
			for(Integer id: ids) {
				try {
					designacionesRepository.deleteById(id);
					eliminadosBien.add(id);
				} catch (Exception e) {
					log.error("Error al eliminar la designacion con id " + id + ":" + e.getMessage() );
					eliminadosMal.add(id);
				}
			}
				
			if (eliminadosBien.size() > 0 && eliminadosMal.size() == 0 ) {
				if (eliminadosBien.size() > 1) {
					return MessageResponseDto.success("Designaciones eliminadas correctamente");
				} else {
					return MessageResponseDto.success("Designacion eliminado correctamente");
				}
			}
			StringBuilder sb = new StringBuilder();	
			if (eliminadosMal.size() > 0 ) {
				if (eliminadosMal.size() > 1) {
					sb.append("Error al eliminar las designaciones.");
				} else {
					sb.append("Error al eliminar la designacion.");
				}
				if (eliminadosBien.size() > 1) sb.append(" El resto se realizaron correctamente.");
				return MessageResponseDto.fail(sb.toString());
			}

		} catch (Exception e) {
			log.error("Error al eliminar designacion: " +  e.getMessage());
			return MessageResponseDto.fail("Error al eliminar la designación");
		}
		return MessageResponseDto.fail("No se ha eliminado ninguna designación");
	}

	@Override
	public MessageResponseDto<byte[]> descargarInforme(DesignacionFiltroDto filtro){
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		DecimalFormat df = new DecimalFormat("0.00");
		try {
			List<DesignacionEntity> listEnts = new ArrayList<DesignacionEntity>();
			if (filtro.getIdsDesignaciones() != null  && !filtro.getIdsDesignaciones().isEmpty()) {
				listEnts = designacionesRepository.filterListByIds(filtro.getIdsDesignaciones());
			} else {
				listEnts = designacionesRepository.filterList(filtro);
			}
			if (listEnts.isEmpty()) {
				return MessageResponseDto.fail("No se han obtenido designaciones para realizar el informe");
			}
			Optional<TemporadaEntity> temporada = temporadasRepository.findById(filtro.getTemporada());
			if (!temporada.isPresent()) {
				return MessageResponseDto.fail("Error al obtener la temporada del filtro");
			}
			Optional<UsuarioEntity> usuario = usuariosRepository.findById(filtro.getUsuario());
			if (!usuario.isPresent()) {
				return MessageResponseDto.fail("Error al obtener la información del usuario");
			}
			HashMap<String,Object> params = new HashMap<String, Object>();
			Double total = 0.0;
			String nombreUsuario = usuario.get().getNombre();
			if (usuario.get().getApellido1() != null) nombreUsuario += " " + usuario.get().getApellido1();
			if (usuario.get().getApellido2() != null) nombreUsuario += " " + usuario.get().getApellido2();
			params.put("nombre", nombreUsuario);
			params.put("telefono", usuario.get().getTelefono() != null ? usuario.get().getTelefono().toString() : "" );
			params.put("email", usuario.get().getEmail() != null ? usuario.get().getEmail() : "" );
			params.put("municipio", usuario.get().getMunicipio() != null ? usuario.get().getMunicipio() : "" );
			params.put("direccion", usuario.get().getDireccion() != null ? usuario.get().getDireccion() : "" );
			
			params.put("temporada", temporada.get().getDescripcion() != null ? temporada.get().getDescripcion() : "");
			
			ArrayList<Hashtable<String, Object>> tabla = new ArrayList<>();
			for (DesignacionEntity ent: listEnts) {
				Hashtable<String, Object> linea = new Hashtable<>();
				if (ent.getPartido() != null) {
					linea.put("fecha", ent.getPartido().getFecha() != null ? dateFormat.format(ent.getPartido().getFecha()) : "");
					linea.put("jornada", ent.getPartido().getJornada() != null ? ent.getPartido().getJornada().toString()  : "");
					linea.put("equipoLocal", ent.getPartido().getEquipoLocal() != null ? ent.getPartido().getEquipoLocal().getNombre()  : "");
					linea.put("equipoVisitante", ent.getPartido().getEquipoVisitante() != null ? ent.getPartido().getEquipoVisitante().getNombre()  : "");
					linea.put("categoria", ent.getPartido().getCategoria() != null ? ent.getPartido().getCategoria().getDescripcion()  : "");
					linea.put("importe", ent.getPartido().getCategoria() != null ? df.format(ent.getPartido().getCategoria().getCuantia())  : "");
					if(ent.getPartido().getCategoria().getCuantia() != null) 
						total += ent.getPartido().getCategoria().getCuantia();
					tabla.add(linea);
				}
			}
			params.put("total", df.format(total));
			params.put("tabla", tabla);
			
			JasperPrint jasperPrint = jasperDocumentProvider.crearJasperNoFields(params, PLANTILLA_INFORME);
			byte[] informe =  jasperDocumentProvider.getBytesJasper(jasperPrint);
			jasperDocumentProvider.saveDocToFile(informe, "C:\\compartido\\prueba.pdf"); 
			return MessageResponseDto.success(informe);
		} catch (Exception e) {
			log.error("Error al obtener generar el informe: " +  e.getMessage());
			return MessageResponseDto.fail("Error al obtener generar el informe");
		}
		
		//
	}
	

}
