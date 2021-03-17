package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class AssignmentDto extends BaseDto {

  Long studentID;
  Long labProblemID;
  Integer grade;
}
