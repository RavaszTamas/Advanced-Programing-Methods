package model.specifications;

import ro.ubb.core.model.Assignment;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssignmentSpecification extends MySpecification<Assignment> {

    public AssignmentSpecification(SearchCriteria criteria){
        super(criteria);
    }
}
