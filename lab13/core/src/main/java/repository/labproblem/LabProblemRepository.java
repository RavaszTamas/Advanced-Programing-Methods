package repository.labproblem;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LabProblemRepository extends Repository<LabProblem, Long>, JpaSpecificationExecutor<LabProblem> {

    List<LabProblem> findByProblemNumber(int problemNumber);

    @Query("select distinct l from LabProblem l")
    @EntityGraph(value="labProblemWithAssignmentsAndStudents",type= EntityGraph.EntityGraphType.LOAD)
    List<LabProblem> findAllWithAssignmentsAndLabProblems();

    @Query("select distinct l from LabProblem l where l.id= :labProblemId")
    @EntityGraph(value="labProblemWithAssignmentsAndStudents",type= EntityGraph.EntityGraphType.LOAD)
    Optional<LabProblem> findByIdWithAssignmentsAndLabProblems(@Param("labProblemId") Long labProblemId);

}
