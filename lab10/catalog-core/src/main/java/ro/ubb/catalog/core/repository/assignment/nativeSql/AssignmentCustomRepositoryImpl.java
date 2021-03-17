// package ro.ubb.catalog.core.repository.assignment.nativeSql;
//
// import org.hibernate.Criteria;
// import org.hibernate.Session;
// import org.hibernate.ejb.HibernateEntityManager;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
// import ro.ubb.catalog.core.model.Assignment;
// import ro.ubb.catalog.core.repository.CustomRepositorySupport;
// import ro.ubb.catalog.core.repository.assignment.AssignmentCustomRepository;
//
// import java.util.List;
//
// @Component("assignmentCustomRepositoryNativeSqlImpl")
// public class AssignmentCustomRepositoryImpl extends CustomRepositorySupport
//    implements AssignmentCustomRepository {
//
//  private static final Logger log = LoggerFactory.getLogger(AssignmentCustomRepositoryImpl.class);
//
//  @Override
//  @Transactional
//  public List<Assignment> findAllWithGrade(Integer grade) {
//    log.trace("findAllWithGrade native sql entered grade={}", grade);
//    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
//    Session session = hibernateEntityManager.getSession();
//    org.hibernate.Query query =
//        session
//            .createSQLQuery("SELECT DISTINCT {a.*} from assignments a WHERE a.grade=:gradeParam")
//            .addEntity("a", Assignment.class)
//            .setParameter("gradeParam", grade)
//            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//    List<Assignment> result = query.getResultList();
//
//    log.trace("findAllWithGrade native sql finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithStudentId(Integer studentId) {
//    log.trace("findAllWithStudentId native sql entered studentId={}", studentId);
//    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
//    Session session = hibernateEntityManager.getSession();
//    org.hibernate.Query query =
//        session
//            .createSQLQuery(
//                "SELECT DISTINCT {a.*} from assignments a WHERE a.student_id=:studentIdParam")
//            .addEntity("a", Assignment.class)
//            .setParameter("studentIdParam", studentId)
//            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithStudentId native sql finished result={}", result);
//    return result;
//  }
//
//  @Override
//  public List<Assignment> findAllWithLabProblemId(Integer labProblemId) {
//    log.trace("findAllWithLabProblemId entered labProblemId={}", labProblemId);
//    Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
//    Session session = hibernateEntityManager.getSession();
//    org.hibernate.Query query =
//        session
//            .createSQLQuery(
//                "SELECT DISTINCT {a.*} from assignments a WHERE a.student_id=:labProblemIdParam")
//            .addEntity("a", Assignment.class)
//            .setParameter("labProblemIdParam", labProblemId)
//            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//    List<Assignment> result = query.getResultList();
//    log.trace("findAllWithLabProblemId finished result={}", result);
//    return result;
//  }
// }
