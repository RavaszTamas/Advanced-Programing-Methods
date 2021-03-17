package ro.ubb.catalog.core.model.specifications;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LabProblemSpecification extends MySpecification<LabProblem> {

  public LabProblemSpecification(SearchCriteria criteria) {
    super(criteria);
  }
}
