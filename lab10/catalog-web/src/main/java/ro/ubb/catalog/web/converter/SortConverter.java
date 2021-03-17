package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.web.dto.SortColumnDto;
import ro.ubb.catalog.web.dto.SortDto;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SortConverter {
  public static final Logger log = LoggerFactory.getLogger(SortConverter.class);

  public Sort convertDtoToModel(SortDto dto) {
    log.trace("convertDtoToModel - method entered dto={}", dto);

    List<Sort.Order> sortOrderList =
        dto.getSortColumnDtoList().stream()
            .map(
                elem -> {
                  Sort.Order sortOrder;
                  if (elem.getDirection().equals("ASC")) {
                    sortOrder = new Sort.Order(Sort.Direction.ASC, elem.getColumn());
                  } else {
                    sortOrder = new Sort.Order(Sort.Direction.DESC, elem.getColumn());
                  }
                  return sortOrder;
                })
            .collect(Collectors.toList());

    Sort sort = Sort.by(sortOrderList);

    log.trace("convertDtoToModel - method finished: sort={}", sort);

    return sort;
  }

  public SortDto convertModelToDto(Sort sort) {
    log.trace("convertModelToDto - method entered sort={}", sort);
    throw new UnsupportedOperationException("Not possible to implement");
  }
}
