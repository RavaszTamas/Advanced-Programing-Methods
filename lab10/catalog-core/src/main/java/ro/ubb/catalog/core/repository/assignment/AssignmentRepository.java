// package ro.ubb.catalog.core.repository.assignment;
//
// import org.springframework.data.jpa.repository.EntityGraph;
// import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Component;
// import ro.ubb.catalog.core.model.Assignment;
// import ro.ubb.catalog.core.repository.Repository;
//
// import java.util.List;
// import java.util.Optional;
//
// @Component("assignmentCustomRepositoryNativeSql")
// public interface AssignmentRepository
//    extends Repository<Assignment, Long>,
//        JpaSpecificationExecutor<Assignment>,
//        AssignmentCustomRepository {
//
//  List<Assignment> findByGrade(int grade);
//
//  @Query("select distinct a from Assignment a")
//  @EntityGraph(
//      value = "assignmentWithStudentAndLabProblem",
//      type = EntityGraph.EntityGraphType.LOAD)
//  List<Assignment> findAllWithStudentAndLabProblem();
//
//  @Query("select distinct a from Assignment a where a.id = :assignmentId")
//  @EntityGraph(
//      value = "assignmentWithStudentAndLabProblem",
//      type = EntityGraph.EntityGraphType.LOAD)
//  Optional<Assignment> findByIdAllWithStudentAndLabProblem(
//      @Param("assignmentId") Long assignmentId);
// }
