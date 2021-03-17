package ro.ubb.catalog.core.repository.labproblem.nativesql;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
// import org.hibernate.ejb.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.labproblem.LabProblemCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("labProblemCustomRepositoryNativeSqlImpl")
public class LabProblemCustomRepositoryImpl extends CustomRepositorySupport
    implements LabProblemCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(LabProblemCustomRepositoryImpl.class);

  @Override
  @Transactional
  public List<LabProblem> findAllWithDescription(String description) {
    log.trace("findAllWithDescription native sql entered description={}", description);
    HibernateEntityManager hibernateEntityManager =
        getEntityManager().unwrap(HibernateEntityManager.class);
    Session session = hibernateEntityManager.getSession();
    org.hibernate.Query query =
        session
            .createSQLQuery(
                "SELECT DISTINCT {l.*},{a.*},{s.*} "
                    + "from lab_problems l"
                    + " left join assignments a on l.lab_problem_id=a.lab_problem_id "
                    + " left join students s on a.student_id=s.student_id"
                    + " WHERE l.description=:descriptionParam")
            .addEntity("l", LabProblem.class)
            .addJoin("a", "l.assignments")
            .addJoin("s", "a.student")
            .addEntity("l", LabProblem.class)
            .setParameter("descriptionParam", description)
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithDescription native sql finished result={}", result);
    return result;
  }

  @Override
  @Transactional
  public List<LabProblem> findAllWithProblemNumber(Integer problemNumber) {
    log.trace("findAllWithProblemNumber native sql entered problemNumber={}", problemNumber);
    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
    Session session = hibernateEntityManager.getSession();
    org.hibernate.Query query =
        session
            .createSQLQuery(
                "SELECT DISTINCT {l.*},{a.*},{s.*} "
                    + "from lab_problems l "
                    + " left join assignments a on l.lab_problem_id=a.lab_problem_id "
                    + " left join students s on a.student_id=s.student_id"
                    + " WHERE l.problem_number=:problemNumberParam")
            .addEntity("l", LabProblem.class)
            .addJoin("a", "l.assignments")
            .addJoin("s", "a.student")
            .addEntity("l", LabProblem.class)
            .setParameter("problemNumberParam", problemNumber)
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    List<LabProblem> result = query.getResultList();
    log.trace("findAllWithProblemNumber native sql finished result={}", result);
    return result;
  }
}
