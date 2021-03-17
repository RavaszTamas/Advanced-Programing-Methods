package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface LabProblemService {
  /**
   * Adds a lab problem inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully added or the passed entity
   *     otherwise
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  Optional<LabProblem> addLabProblem(@Valid LabProblem labProblem) throws ValidatorException;

  /**
   * Returns all the lab problems in the ro.ubb.repository in a Set
   *
   * @return a Set which stores all the lab problems
   */
  List<LabProblem> getAllLabProblems();

  /** Return all Lab problems sorted by the sort criteria. */
  List<LabProblem> getAllLabProblemsSorted(Sort sort);

  /**
   * Get Optional containing lab problem with given id if there is one in the ro.ubb.repository
   * below.
   *
   * @param id to find lab problem by
   * @return Optional containing the sought LabProblem or null otherwise
   */
  Optional<LabProblem> getLabProblemById(Long id);

  /**
   * Deletes a lab problem from the ro.ubb.repository
   *
   * @param id the id of the lab problem to be deleted
   */
  void deleteLabProblem(Long id);

  /**
   * Updates a lab problem inside the ro.ubb.repository
   *
   * @param id id number of entity to be updated
   * @param problemNumber problem number of entity to be updated
   * @param description description of entity to be updated
   * @return an {@code Optional} containing the null if successfully updated or the entity sent to
   *     the ro.ubb.repository
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  LabProblem updateLabProblem(@Valid LabProblem labProblem) throws ValidatorException;

  /**
   * Filters the elements of the ro.ubb.repository by a given problem number
   *
   * @param specification the problem number to be filtered by
   * @return a {@code Set} - of entities filtered by the given problem number
   */
  List<LabProblem> filterLabProblemsByProblemNumber(Integer problemNumber);

  List<LabProblem> filterLabProblemsByDescription(String description);

  Page<LabProblem> getAllLabProblemsPaged(Pageable pageToGet);
}
