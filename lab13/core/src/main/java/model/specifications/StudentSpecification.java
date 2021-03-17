package model.specifications;

import ro.ubb.catalog.core.model.Student;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSpecification extends MySpecification<Student> {

  public StudentSpecification(SearchCriteria criteria){
    super(criteria);
  }
}
