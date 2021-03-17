package ro.ubb.catalog.core.repository.labproblem;

import ro.ubb.catalog.core.model.LabProblem;

import java.util.List;

public interface LabProblemCustomRepository {

  List<LabProblem> findAllWithDescription(String description);

  List<LabProblem> findAllWithProblemNumber(Integer problemNumber);
}
