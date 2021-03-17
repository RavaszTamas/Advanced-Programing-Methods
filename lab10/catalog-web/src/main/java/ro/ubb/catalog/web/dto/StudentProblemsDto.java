package ro.ubb.catalog.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StudentProblemsDto {
  private Set<StudentProblemsEntryDto> studentProblemsEntryDtoSet;
}
