package ro.ubb.catalog.core.repository.labproblem;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.repository.Repository;

import java.util.List;
import java.util.Optional;

@Component("labProblemCustomRepositoryNativeSql")
public interface LabProblemRepository
    extends Repository<LabProblem, Long>,
        JpaSpecificationExecutor<LabProblem>,
        LabProblemCustomRepository {

  List<LabProblem> findByProblemNumber(int problemNumber);

  @Query("select distinct l from LabProblem l")
  @EntityGraph(
      value = "labProblemWithAssignmentsAndStudents",
      type = EntityGraph.EntityGraphType.LOAD)
  List<LabProblem> findAllWithAssignmentsAndLabStudents();

  @Query("select distinct l from LabProblem l where l.id= :labProblemId")
  @EntityGraph(
      value = "labProblemWithAssignmentsAndStudents",
      type = EntityGraph.EntityGraphType.LOAD)
  Optional<LabProblem> findByIdWithAssignmentsAndStudents(@Param("labProblemId") Long labProblemId);
}
