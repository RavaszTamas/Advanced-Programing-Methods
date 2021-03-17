package ro.ubb.catalog.core.model.specifications;

import lombok.*;
import ro.ubb.catalog.core.model.Assignment;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssignmentSpecification extends MySpecification<Assignment> {

  public AssignmentSpecification(SearchCriteria criteria) {
    super(criteria);
  }
}
