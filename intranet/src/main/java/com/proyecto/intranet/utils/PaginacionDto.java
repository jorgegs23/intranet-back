package com.proyecto.intranet.utils;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PaginacionDto {
	/** The paginas. */
	private int paginas;
	/** The total. */
	private Long total;
	/** The item per page. */
	private int itemPerPage;

	/**
	 * * From page. * * @param <T> the generic type * @param page the page * @return
	 * the paginacion dto
	 */
	public static <T> PaginacionDto fromPage(Page<T> page) {
		PaginacionDto paginacionDto = new PaginacionDto();
		paginacionDto.setItemPerPage(page.getNumberOfElements());
		paginacionDto.setPaginas(page.getTotalPages());
		paginacionDto.setTotal(page.getTotalElements());
		return paginacionDto;
	}
}
