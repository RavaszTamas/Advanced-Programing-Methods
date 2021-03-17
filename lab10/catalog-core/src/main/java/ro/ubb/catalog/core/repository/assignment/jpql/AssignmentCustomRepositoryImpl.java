// package ro.ubb.catalog.core.repository.assignment.jpql;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import ro.ubb.catalog.core.model.Assignment;
// import ro.ubb.catalog.core.repository.CustomRepositorySupport;
// import ro.ubb.catalog.core.repository.assignment.AssignmentCustomRepository;
//
// import javax.persistence.EntityManager;
// import javax.persistence.Query;
// import java.util.List;
//
// @Component("assignmentCustomRepositoryJpqlImpl")
// public class AssignmentCustomRepositoryImpl extends CustomRepositorySupport
//    implements AssignmentCustomRepository {
//
//  private static final Logger log = LoggerFactory.getLogger(AssignmentCustomRepositoryImpl.class);
//
//  @Override
//  public List<Assignment> findAllWithGrade(Integer grade) {
//    log.trace("findAllWithGrade jpql entered grade={}", grade);
//    EntityManager manager = getEntityManager();
//    Query query =
//        manager.createQuery("SELECT DISTINCT a from Assignment a WHERE a.grade =
// :gradeParameter");
//    query.setParameter("gradeParameter", grade);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithGrade jpql finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithStudentId(Integer studentId) {
//    log.trace("findAllWithStudentId jpql entered studentId={}", studentId);
//    EntityManager manager = getEntityManager();
//    Query query =
//        manager.createQuery("SELECT DISTINCT a from Assignment a WHERE a.student.id =
// :studentId");
//    query.setParameter("studentId", studentId);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithStudentId jpql finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithLabProblemId(Integer labProblemId) {
//    log.trace("findAllWithLabProblemId jpql entered labProblemId={}", labProblemId);
//    EntityManager manager = getEntityManager();
//    Query query =
//        manager.createQuery(
//            "SELECT DISTINCT a from Assignment a WHERE a.labProblem.id = :labProblemId");
//    query.setParameter("labProblemId", labProblemId);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithLabProblemId jpql finished result={}", result);
//    return result;
//  }
// }
