package ro.ubb.catalog.core.repository.labproblem.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.labproblem.LabProblemCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("labProblemCustomRepositoryCriteriaImpl")
public class LabProblemCustomRepositoryImpl extends CustomRepositorySupport
    implements LabProblemCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(LabProblemCustomRepositoryImpl.class);

  @Override
  public List<LabProblem> findAllWithDescription(String description) {
    log.trace("findAllWithDescription criteria entered description={}", description);
    EntityManager entityManager = getEntityManager();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<LabProblem> criteriaQuery = criteriaBuilder.createQuery(LabProblem.class);
    criteriaQuery.distinct(Boolean.TRUE);
    Root<LabProblem> studentRoot = criteriaQuery.from(LabProblem.class);
    ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
    criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("description"), pe));

    TypedQuery<LabProblem> query = entityManager.createQuery(criteriaQuery);
    query.setParameter(pe, description);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithDescription criteria finished result={}", result);
    return result;
  }

  @Override
  public List<LabProblem> findAllWithProblemNumber(Integer problemNumber) {
    log.trace("findAllWithProblemNumber criteria entered problemNumber={}", problemNumber);
    EntityManager entityManager = getEntityManager();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<LabProblem> criteriaQuery = criteriaBuilder.createQuery(LabProblem.class);
    criteriaQuery.distinct(Boolean.TRUE);
    Root<LabProblem> studentRoot = criteriaQuery.from(LabProblem.class);
    ParameterExpression<Integer> pe = criteriaBuilder.parameter(Integer.class);
    criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("problemNumber"), pe));

    TypedQuery<LabProblem> query = entityManager.createQuery(criteriaQuery);
    query.setParameter(pe, problemNumber);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithGroupNumber criteria finished result={}", result);
    return result;
  }
}
