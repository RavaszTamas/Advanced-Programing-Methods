package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StudentProblemsEntryDto {
  private StudentDto studentDto;
  private LabProblemsDto labProblemsDto;
}
