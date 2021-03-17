package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.web.dto.LabProblemDto;

@Component
public class LabProblemConverter extends BaseConverter<LabProblem, LabProblemDto> {
  public static final Logger log = LoggerFactory.getLogger(LabProblemConverter.class);

  @Override
  public LabProblem convertDtoToModel(LabProblemDto dto) {
    //    log.trace("convertDtoToModel - method entered dto={}", dto);
    LabProblem labProblem =
        LabProblem.builder()
            .description(dto.getDescription())
            .problemNumber(dto.getProblemNumber())
            .build();
    labProblem.setId(dto.getId());
    //    log.trace("convertDtoToModel - method finished: labProblem={}", labProblem);
    return labProblem;
  }

  @Override
  public LabProblemDto convertModelToDto(LabProblem labProblem) {
    //    log.trace("convertModelToDto - method entered labProblem={}", labProblem);
    LabProblemDto labProblemDto =
        LabProblemDto.builder()
            .problemNumber(labProblem.getProblemNumber())
            .description(labProblem.getDescription())
            .build();
    labProblemDto.setId(labProblem.getId());
    //    log.trace("convertModelToDto - method finished: dto={}", labProblemDto);
    return labProblemDto;
  }
}
