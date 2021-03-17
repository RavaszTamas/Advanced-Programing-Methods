package repository.student;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.Repository;

import java.util.List;
import java.util.Optional;

/** Created by radu. */
public interface StudentRepository extends Repository<Student, Long>, JpaSpecificationExecutor<Student>, StudentCustomRepository {

    List<Student> findByGroupNumber(int groupNumber);

    @Query("select distinct s from Student  s")
    @EntityGraph(value="studentWithAssignmentsWithLabProblems",type= EntityGraph.EntityGraphType.LOAD)
    List<Student> findAllWithAssignmentsAndLabProblems();

    @Query("select distinct s from Student s where s.id= :studentId")
    @EntityGraph(value="studentWithAssignmentsWithLabProblems",type= EntityGraph.EntityGraphType.LOAD)
    Optional<Student> findByIdWithAssignmentsAndLabProblems(@Param("studentId") Long studentId);

}
