package ro.ubb.catalog.core.repository.student.nativeSql;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.student.StudentCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("studentCustomRepositoryNativeSqlImpl")
public class StudentCustomRepositoryImpl extends CustomRepositorySupport
    implements StudentCustomRepository {

  private static final Logger log = LoggerFactory.getLogger(StudentCustomRepositoryImpl.class);

  @Override
  @Transactional
  public List<Student> findAllWithName(String name) {
    log.trace("findAllWithName native sql entered name={}", name);
    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
    Session session = hibernateEntityManager.getSession();
    org.hibernate.Query query =
        session
            .createSQLQuery(
                "SELECT DISTINCT {s.*},{l.*},{a.*} "
                    + " from students s"
                    + " left join assignments a on s.student_id=a.student_id "
                    + " left join lab_problems l on a.lab_problem_id=l.lab_problem_id"
                    + " WHERE s.name=:nameParam")
            .addEntity("s", Student.class)
            .addJoin("a", "s.assignments")
            .addJoin("l", "a.labProblem")
            .addEntity("s", Student.class)
            .setParameter("nameParam", name)
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    List<Student> result = query.getResultList();
    log.trace("findAllWithName native sql finished result={}", result);
    return result;
  }

  @Override
  @Transactional
  public List<Student> findAllWithGroupNumber(Integer groupNumber) {
    log.trace("findAllWithGroupNumber native sql entered groupNumber={}", groupNumber);
    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
    Session session = hibernateEntityManager.getSession();
    org.hibernate.Query query =
        session
            .createSQLQuery(
                "SELECT DISTINCT {s.*},{l.*},{a.*}"
                    + " from students s"
                    + " left join assignments a on s.student_id=a.student_id "
                    + " left join lab_problems l on a.lab_problem_id=l.lab_problem_id"
                    + " WHERE s.group_number=:groupNumberParam")
            .addEntity("s", Student.class)
            .addJoin("a", "s.assignments")
            .addJoin("l", "a.labProblem")
            .addEntity("s", Student.class)
            .setParameter("groupNumberParam", groupNumber)
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    List<Student> result = query.getResultList();
    log.trace("findAllWithGroupNumber native sql finished result={}", result);
    return result;
  }
}
