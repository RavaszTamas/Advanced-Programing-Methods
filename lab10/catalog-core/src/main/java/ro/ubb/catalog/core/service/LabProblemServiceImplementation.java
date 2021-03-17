package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.repository.labproblem.LabProblemRepository;
import ro.ubb.catalog.core.service.validators.Validator;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LabProblemServiceImplementation implements LabProblemService {
  private static final Logger log = LoggerFactory.getLogger(LabProblemServiceImplementation.class);
  @Autowired private LabProblemRepository repository;
  @Autowired private Validator<LabProblem> labProblemValidator;

  /**
   * Adds a lab problem inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully added or the passed entity
   *     otherwise
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  @Override
  public Optional<LabProblem> addLabProblem(@Valid LabProblem labProblem)
      throws ValidatorException {
    log.trace("addLabProblem - method entered: labProblem={}", labProblem);

    labProblemValidator.validate(labProblem);
    log.trace("addLabProblem - student validated");

    LabProblem result = repository.save(labProblem);
    log.trace("addLabProblem - method finished: result={}", result);
    return Optional.empty();
  }

  /**
   * Returns all the lab problems in the ro.ubb.repository in a Set
   *
   * @return a Set which stores all the lab problems
   */
  @Override
  public List<LabProblem> getAllLabProblems() {
    log.trace("getAllLabProblems - method entered");
    // List<LabProblem> result =
    // StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    List<LabProblem> result = repository.findAllWithAssignmentsAndLabStudents();
    log.trace("getAllLabProblems - method finished: result={}", result);
    return result;
  }

  /** Return all Lab problems sorted by the sort criteria. */
  @Override
  public List<LabProblem> getAllLabProblemsSorted(Sort sort) {
    log.trace("getAllLabProblemsSorted - method entered");
    Iterable<LabProblem> problems = repository.findAll(sort);
    log.trace("getAllLabProblemsSorted - students sorted");
    List<LabProblem> result =
        StreamSupport.stream(problems.spliterator(), false).collect(Collectors.toList());
    log.trace("getAllLabProblemsSorted - method finished: result={}", result);
    return result;
  }

  /**
   * Get Optional containing lab problem with given id if there is one in the ro.ubb.repository
   * below.
   *
   * @param id to find lab problem by
   * @return Optional containing the sought LabProblem or null otherwise
   */
  @Override
  public Optional<LabProblem> getLabProblemById(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("invalid id!");
    }
    log.trace("getLabProblemById - method entered: id={}", id);

    Optional<LabProblem> labProblem = repository.findByIdWithAssignmentsAndStudents(id);
    log.debug("getLabProblemById - method exit: labProblem={}", labProblem);
    return labProblem;
  }

  /**
   * Deletes a lab problem from the ro.ubb.repository
   *
   * @param id the id of the lab problem to be deleted
   */
  @Override
  public void deleteLabProblem(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("Invalid id!");
    }
    if (!repository.existsById(id)) throw new RepositoryException("Entity with id doesn't exists!");
    log.trace("deleteLabProblem - method entered: id={}", id);
    repository.deleteById(id);
    log.trace("deleteLabProblem - method finished");
  }

  /**
   * Updates a lab problem inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully updated or the entity sent to
   *     the ro.ubb.repository
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  @Override
  @Transactional
  public LabProblem updateLabProblem(@Valid LabProblem labProblem) throws ValidatorException {
    log.trace("updateLabProblem - method entered: newLabProblem={}", labProblem);

    labProblemValidator.validate(labProblem);
    log.trace("updateLabProblem - labProblem validated");
    Optional<LabProblem> labProblemOptional = repository.findById(labProblem.getId());

    labProblemOptional.ifPresent(
        labProblemToUpdate -> {
          labProblemToUpdate.setDescription(labProblem.getDescription());
          labProblemToUpdate.setProblemNumber(labProblem.getProblemNumber());
          log.debug("updateStudent - updated: labProblem={}", labProblemToUpdate);
        });
    log.trace("updateLabProblem - method finished result={}", labProblemOptional);
    return labProblemOptional.orElse(LabProblem.builder().build());
  }

  /**
   * Filters the elements of the ro.ubb.repository by a given problem number
   *
   * @return a {@code Set} - of entities filtered by the given problem number
   */
  @Override
  public List<LabProblem> filterLabProblemsByProblemNumber(Integer problemNumber) {
    if (problemNumber == null) {
      throw new IllegalArgumentException("problem number negative!");
    }
    log.trace("filterByProblemNumber - method entered: problemNumber={}", problemNumber);

    //    Iterable<LabProblem> labProblems = repository.findAll((root,query,cb)->
    // cb.equal(root.get("problemNumber"),problemNumber));
    Iterable<LabProblem> labProblems = repository.findAllWithProblemNumber(problemNumber);
    List<LabProblem> filteredLabProblems = new LinkedList<>();
    labProblems.forEach(filteredLabProblems::add);
    //    filteredLabProblems.removeIf(entity -> !entity.getProblemNumber().equals(problemNumber));

    log.debug("filterByGroup - method finished: result={}", filteredLabProblems);
    return filteredLabProblems;
  }

  @Override
  public List<LabProblem> filterLabProblemsByDescription(String description) {
    if (description == null || description.equals("")) {
      throw new IllegalArgumentException("description mut not be empty!");
    }
    log.trace("filterLabProblemsByDescription - method entered: description={}", description);

    //    Iterable<LabProblem> labProblems = repository.findAll((root,query,cb)->
    // cb.equal(root.get("problemNumber"),problemNumber));
    Iterable<LabProblem> labProblems = repository.findAllWithDescription(description);
    List<LabProblem> filteredLabProblems = new LinkedList<>();
    labProblems.forEach(filteredLabProblems::add);
    //    filteredLabProblems.removeIf(entity -> !entity.getProblemNumber().equals(problemNumber));

    log.debug("filterLabProblemsByDescription - method finished: result={}", filteredLabProblems);
    return filteredLabProblems;
  }

  @Override
  public Page<LabProblem> getAllLabProblemsPaged(Pageable pageToGet) {
    return repository.findAll(pageToGet);
  }
}
