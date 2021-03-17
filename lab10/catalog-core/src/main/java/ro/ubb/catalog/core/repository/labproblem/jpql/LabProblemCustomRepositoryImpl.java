package ro.ubb.catalog.core.repository.labproblem.jpql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.labproblem.LabProblemCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("labProblemCustomRepositoryJpqlImpl")
public class LabProblemCustomRepositoryImpl extends CustomRepositorySupport
    implements LabProblemCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(LabProblemCustomRepositoryImpl.class);

  @Override
  public List<LabProblem> findAllWithDescription(String description) {
    log.trace("findAllWithDescription jpql entered description={}", description);
    EntityManager manager = getEntityManager();
    Query query =
        manager.createQuery(
            "SELECT DISTINCT a from LabProblem a WHERE a.description = :descriptionParameter");
    query.setParameter("descriptionParameter", description);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithDescription jpql finished result={}", result);
    return result;
  }

  @Override
  public List<LabProblem> findAllWithProblemNumber(Integer problemNumber) {
    log.trace("findAllWithProblemNumber jpql entered problemNumber={}", problemNumber);
    EntityManager manager = getEntityManager();
    Query query =
        manager.createQuery(
            "SELECT DISTINCT a from LabProblem a WHERE a.problemNumber = :problemNumberParameter");
    query.setParameter("problemNumberParameter", problemNumber);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithProblemNumber jpql finished result={}", result);
    return result;
  }
}
