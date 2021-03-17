// package ro.ubb.catalog.core.repository.assignment.criteria;
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
// import javax.persistence.TypedQuery;
// import javax.persistence.criteria.CriteriaBuilder;
// import javax.persistence.criteria.CriteriaQuery;
// import javax.persistence.criteria.ParameterExpression;
// import javax.persistence.criteria.Root;
// import java.util.List;
//
// @Component("assignmentCustomRepositoryCriteriaImpl")
// public class AssignmentCustomRepositoryImplementationCriteria extends CustomRepositorySupport
//    implements AssignmentCustomRepository {
//
//  private static final Logger log =
//      LoggerFactory.getLogger(AssignmentCustomRepositoryImplementationCriteria.class);
//
//  @Override
//  public List<Assignment> findAllWithGrade(Integer grade) {
//    log.trace("findAllWithGrade criteria entered grade={}", grade);
//    EntityManager entityManager = getEntityManager();
//    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//    CriteriaQuery<Assignment> criteriaQuery = criteriaBuilder.createQuery(Assignment.class);
//    criteriaQuery.distinct(Boolean.TRUE);
//    Root<Assignment> assignmentRoot = criteriaQuery.from(Assignment.class);
//    ParameterExpression<Integer> parameter = criteriaBuilder.parameter(Integer.class);
//    criteriaQuery
//        .select(assignmentRoot)
//        .where(criteriaBuilder.equal(assignmentRoot.get("grade"), parameter));
//    TypedQuery<Assignment> assignmentTypedQuery = entityManager.createQuery(criteriaQuery);
//    assignmentTypedQuery.setParameter(parameter, grade);
//    List<Assignment> result = assignmentTypedQuery.getResultList();
//    log.trace("findAllWithGrade criteria finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithStudentId(Integer studentId) {
//    log.trace("findAllWithStudentId criteria entered studentId={}", studentId);
//    EntityManager manager = getEntityManager();
//    Query query =
//        manager.createQuery("SELECT DISTINCT a from Assignment a WHERE a.student.id =
// :studentId");
//    query.setParameter("studentId", studentId);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithStudentId criteria finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithLabProblemId(Integer labProblemId) {
//    log.trace("findAllWithLabProblemId criteria entered labProblemId={}", labProblemId);
//    EntityManager manager = getEntityManager();
//    Query query =
//        manager.createQuery(
//            "SELECT DISTINCT a from Assignment a WHERE a.labProblem.id = :labProblemId");
//    query.setParameter("labProblemId", labProblemId);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithLabProblemId criteria finished result={}", result);
//    return result;
//  }
// }
