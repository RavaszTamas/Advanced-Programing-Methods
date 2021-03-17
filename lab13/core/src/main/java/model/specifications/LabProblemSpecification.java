package model.specifications;

import ro.ubb.core.model.LabProblem;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LabProblemSpecification extends MySpecification<LabProblem> {

  public LabProblemSpecification(SearchCriteria criteria){
    super(criteria);
  }


}
