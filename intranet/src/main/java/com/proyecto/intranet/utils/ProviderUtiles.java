package com.proyecto.intranet.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProviderUtiles {

//	public PaginacionDto getPagination(Page page) {
//        PaginacionDto pagination = new PaginacionDto();
//        pagination.setTotal(page.getTotalElements());
//        pagination.setPaginas(page.getTotalPages());
//        pagination.setItemPerPage(page.getNumberOfElements());
//        return pagination;
//    }
//
//
//    public <ENTITY, DTO> Paginated<DTO> getPaginated(Page<ENTITY> page, Class<DTO> dto) {
//        Paginated<DTO> paginated = new Paginated<>();
//
//        paginated.setContent(mapList(page.getContent(), dto));
//        paginated.setPaginacion(getPagination(page));
//        return paginated;
//    }
    
    public Pageable pagesToFind(int page, Integer itemsperpage) {
        return page == -1
                ? Pageable.unpaged()
                : PageRequest.of(page, itemsperpage);
    }
    
//    public <ENTITY, ID, DTO> Paginated<DTO> getAllPaginated(JpaRepository<ENTITY, ID> repository, int page, Integer itemsperpage, Class<DTO> dto) {
//        Page<ENTITY> entityPage = repository.findAll(pagesToFind(page, itemsperpage));
//        return getPaginated(entityPage, dto);
//    }
//
//    public Pageable pagesToFindSorted(int page, Integer itemsperpage, SortValueEnum sortValueEnum, List<String> sortFields) {
//        SortValue sortValues = SortValueFactory.getSortValues(sortValueEnum);
//        List<SortDto> defaultValues = sortValues.getDefaultSortValues();
//        List<String> fieldNames = sortValues.getEntityFieldNames();
//        return paginacionConSortProvider.getPageableWithSort(page, defaultValues,
//                sortFields, fieldNames);
//    }
}
