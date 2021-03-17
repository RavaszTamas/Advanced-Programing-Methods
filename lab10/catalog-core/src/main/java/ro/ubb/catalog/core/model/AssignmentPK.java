package ro.ubb.catalog.core.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AssignmentPK implements Serializable {
  private Student student;
  private LabProblem labProblem;
}
