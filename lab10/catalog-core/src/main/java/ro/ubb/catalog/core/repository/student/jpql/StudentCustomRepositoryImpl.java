package ro.ubb.catalog.core.repository.student.jpql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.student.StudentCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("studentCustomRepositoryJpqlImpl")
public class StudentCustomRepositoryImpl extends CustomRepositorySupport
    implements StudentCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(StudentCustomRepositoryImpl.class);

  @Override
  public List<Student> findAllWithName(String name) {
    log.trace("findAllWithName jpql entered name={}", name);
    EntityManager manager = getEntityManager();
    Query query =
        manager.createQuery(
            "SELECT DISTINCT s from Student s "
                + "left join fetch s.assignments asign "
                + "left join fetch asign.labProblem "
                + "WHERE s.name = :nameParameter");
    query.setParameter("nameParameter", name);
    List<Student> result = query.getResultList();
    log.trace("findAllWithName jpql finished result={}", result);
    return result;
  }

  @Override
  public List<Student> findAllWithGroupNumber(Integer groupNumber) {
    log.trace("findAllWithGroupNumber jpql entered groupNumber={}", groupNumber);
    EntityManager manager = getEntityManager();
    Query query =
        manager.createQuery(
            "SELECT DISTINCT s from Student s "
                + "left join fetch s.assignments asign "
                + "left join fetch asign.labProblem "
                + "WHERE s.groupNumber = :groupParameter");
    query.setParameter("groupParameter", groupNumber);
    List<Student> result = query.getResultList();
    log.trace("findAllWithGroupNumber jpql finished result={}", result);
    return result;
  }
}
