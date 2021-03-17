package repository.assignment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.repository.Repository;

import java.util.List;

public interface AssignmentRepository extends Repository<Assignment, Long>, JpaSpecificationExecutor<Assignment> {

    List<Assignment> findByGrade(int grade);
}
