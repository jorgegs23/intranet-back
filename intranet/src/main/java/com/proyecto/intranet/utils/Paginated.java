package com.proyecto.intranet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class Paginated<T> {
	/** The content. */
	private List<T> content = new ArrayList<>();
	/** The paginacion. */
	private PaginacionDto paginacion = new PaginacionDto();

	/**
	 * * From. * * @param <T> the generic type * @param <Y> the generic type
	 * * @param results the results * @param page the page * @return the paginated
	 */
	public static <T, Y> Paginated<T> from(Page<Y> page, List<T> results) {
		Paginated<T> paginated = new Paginated<>();
		paginated.setPaginacion(PaginacionDto.fromPage(page));
		paginated.setContent(results);
		return paginated;
	}

	/**
	 * * From. * * @param <T> the generic type * @param <Y> the generic type
	 * * @param page the page * @param mapper the mapper * @return the paginated
	 */
	public static <T, Y> Paginated<T> from(Page<Y> page, Function<? super Y, ? extends T> mapper) {
		Paginated<T> paginated = new Paginated<>();
		paginated.setPaginacion(PaginacionDto.fromPage(page));
		paginated.setContent(page.getContent().stream().map(mapper).collect(Collectors.toList()));
		return paginated;
	}
}
