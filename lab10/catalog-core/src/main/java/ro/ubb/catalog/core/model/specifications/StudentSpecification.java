package ro.ubb.catalog.core.model.specifications;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import ro.ubb.catalog.core.model.Student;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSpecification extends MySpecification<Student> {

  public StudentSpecification(SearchCriteria criteria) {
    super(criteria);
  }
}
