package ro.ubb.catalog.core.repository.student.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.Assignment_;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.Student_;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.student.StudentCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("studentCustomRepositoryCriteriaImpl")
public class StudentCustomRepositoryImpl extends CustomRepositorySupport
    implements StudentCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(StudentCustomRepositoryImpl.class);

  @Override
  public List<Student> findAllWithName(String name) {
    log.trace("findAllWithName criteria entered name={}", name);
    EntityManager entityManager = getEntityManager();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
    criteriaQuery.distinct(Boolean.TRUE);
    Root<Student> studentRoot = criteriaQuery.from(Student.class);
    ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
    criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("name"), pe));

    TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
    query.setParameter(pe, name);
    List<Student> result = query.getResultList();
    log.trace("findAllWithName criteria finished result={}", result);
    return result;
  }

  @Override
  public List<Student> findAllWithGroupNumber(Integer groupNumber) {
    log.trace("findAllWithGroupNumber criteria entered groupNumber={}", groupNumber);
    EntityManager entityManager = getEntityManager();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
    criteriaQuery.distinct(Boolean.TRUE);
    Root<Student> studentRoot = criteriaQuery.from(Student.class);
    ParameterExpression<Integer> pe = criteriaBuilder.parameter(Integer.class);
    criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("groupNumber"), pe));

    Fetch<Student, Assignment> studentAssignmentFetch =
        studentRoot.fetch(Student_.assignments, JoinType.LEFT);
    studentAssignmentFetch.fetch(Assignment_.labProblem, JoinType.LEFT);

    TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
    query.setParameter(pe, groupNumber);
    List<Student> result = query.getResultList();

    log.trace("findAllWithGroupNumber criteria finished result={}", result);
    return result;
  }
}
