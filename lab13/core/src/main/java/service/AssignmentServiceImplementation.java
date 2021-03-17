package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.repository.assignment.AssignmentRepository;
import ro.ubb.catalog.core.service.validators.Validator;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AssignmentServiceImplementation implements AssignmentService {
  private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImplementation.class);
  @Autowired private AssignmentRepository repository;
  @Autowired private LabProblemService labProblemService;
  @Autowired private StudentService studentService;
  @Autowired private Validator<Assignment> assignmentValidator;

  @Override
  public Optional<Assignment> addAssignment(@Valid Assignment assignment)
      throws ValidatorException {
    log.trace(
        "addAssignment - method entered: assignment={}",
            assignment);

    String errorMessage = "";
    Student student = studentService.getStudentById(assignment.getStudent().getId()).orElse(null);
    if (student == null) errorMessage += "Student id is not in the database ";
    LabProblem labProblem = labProblemService.getLabProblemById(assignment.getLabProblem().getId()).orElse(null);
    if (labProblem == null) errorMessage += "Lab problem id is not in the database";
    if (errorMessage.length() > 0) throw new RepositoryException(errorMessage);
    assignmentValidator.validate(assignment);
    log.trace("addAssignment - assignment validated");

    Optional<Assignment> checkForPresence = repository.findById(assignment.getId());

    checkForPresence.ifPresentOrElse(assignmentEmpty -> {}, () -> repository.save(assignment));
    log.trace("addAssignment - method finished: result={}", checkForPresence);
    return checkForPresence;
  }

  @Override
  public List<Assignment> getAllAssignments() {
    log.trace("getAllAssignments - method entered");
    List<Assignment> result = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    log.trace("getAllAssignments - method finished: result={}", result);
    return result;
  }

  /** Return all Assignments sorted by the sort criteria. */
  @Override
  public List<Assignment> getAllAssignmentsSorted(Sort sort) {
    log.trace("getAllAssignmentsSorted - method entered");
    Iterable<Assignment> assignments = repository.findAll(sort);
    log.trace("getAllAssignmentsSorted - students sorted");
    List<Assignment> result =
        StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toList());
    log.trace("getAllAssignmentsSorted - method finished: result={}", result);
    return result;
  }

  /**
   * Get Optional containing assignment with given id if there is one in the ro.ubb.repository
   * below.
   *
   * @param id to find assignment by
   * @return Optional containing the sought Assignment or null otherwise
   */
  @Override
  public Optional<Assignment> getAssignmentById(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("invalid id!");
    }
    log.trace("getAssignmentById - method entered: id={}", id);

    Optional<Assignment> assignment = repository.findById(id);
    log.debug("getAssignmentById - method exit: assignment={}", assignment);
    return assignment;
  }

  /**
   * Deletes an assignment from the ro.ubb.repository
   *
   * @param id the id of the assignment to be deleted
   */
  @Override
  public void deleteAssignment(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("Invalid id!");
    }
    if (!repository.existsById(id)) throw new RepositoryException("Entity with id doesn't exists!");
    log.trace("deleteAssignment - method entered: id={}", id);
    repository.deleteById(id);
    log.trace("deleteAssignment - method finished");
  }

  /**
   * Updates an assignment inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully updated or the entity sent to
   *     the ro.ubb.repository
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  @Override
  @Transactional
  public Assignment updateAssignment(@Valid Assignment assignment)
      throws ValidatorException {
    log.trace(
        "updateAssignment - method entered: assignment{}",
            assignment);

    String errorMessage = "";
    Student student = studentService.getStudentById(assignment.getStudent().getId()).orElse(null);
    if (student == null) errorMessage += "Student id is not in the database ";
    LabProblem labProblem = labProblemService.getLabProblemById(assignment.getLabProblem().getId()).orElse(null);
    if (labProblem == null) errorMessage += "Lab problem id is not in the database";
    if (errorMessage.length() > 0) throw new RepositoryException(errorMessage);
    assignmentValidator.validate(assignment);

    Optional<Assignment> assignmentOptional = repository.findById(assignment.getId());

    assignmentOptional.ifPresent(
        assignmentObj -> {
          assignmentObj.setGrade(assignment.getGrade());
          assignmentObj.setStudent(assignment.getStudent());
          assignmentObj.setLabProblem(assignment.getLabProblem());
          log.debug("updateAssignment - updated: assignmentObj={}", assignmentObj);
        });
    log.trace(
        "updateAssignment - method finished result={}", assignmentOptional.orElse(assignment));
    return assignmentOptional.orElse(assignment);
  }

  /**
   * Returns the student id who has the biggest mean of grades
   *
   * @return an {@code Optional} containing a null if no student is in the repository otherwise an
   *     {@code Optional} containing a {@code Pair} of Long and Double, for the ID and the grade
   *     average
   */
  @Override
  @Transactional
  public AbstractMap.SimpleEntry<Long, Double> greatestMean() {
    log.trace("greatestMean - method entered");
    Iterable<Assignment> assignmentIterable = repository.findAll();
    Set<Assignment> assignments =
        StreamSupport.stream(assignmentIterable.spliterator(), false).collect(Collectors.toSet());

    AbstractMap.SimpleEntry<Long, Double> result =
        studentService.getAllStudents().stream()
            .filter(
                student ->
                    assignments.stream()
                        .anyMatch(
                            assignment -> assignment.getStudent().getId().equals(student.getId())))
            .map(
                student ->
                    new AbstractMap.SimpleEntry<>(
                        student.getId(),
                        (double)
                                assignments.stream()
                                    .filter(
                                        assignment ->
                                            assignment
                                                .getLabProblem()
                                                .getId()
                                                .equals(student.getId()))
                                    .map(Assignment::getGrade)
                                    .reduce(0, Integer::sum)
                            / (double)
                                assignments.stream()
                                    .filter(
                                        assignment ->
                                            assignment.getStudent().getId().equals(student.getId()))
                                    .count()))
            .max((pair1, pair2) -> (int) (pair1.getValue() - pair2.getValue()))
            .orElse(null);
    log.trace("greatestMean - method finished: result={}", result);
    return result;
  }

  /**
   * Returns the id of the lab problem which was assigned the most often
   *
   * @return an {@code Optional} containing a null if no student is in the repository otherwise an
   *     {@code Optional} containing a {@code Pair} of Long and Long, for the ID and the number of
   *     assignments
   */
  @Override
  @Transactional
  public AbstractMap.SimpleEntry<Long, Long> idOfLabProblemMostAssigned() {
    log.trace("idOfLabProblemMostAssigned - method entered");
    Iterable<Assignment> assignmentIterable = repository.findAll();
    Set<Assignment> assignments =
        StreamSupport.stream(assignmentIterable.spliterator(), false).collect(Collectors.toSet());

    AbstractMap.SimpleEntry<Long, Long> result =
        labProblemService.getAllLabProblems().stream()
            .map(
                labProblem ->
                    new AbstractMap.SimpleEntry<>(
                        labProblem.getId(),
                        assignments.stream()
                            .filter(
                                assignment ->
                                    assignment.getLabProblem().getId().equals(labProblem.getId()))
                            .count()))
            .max(((pair1, pair2) -> (int) (pair1.getValue() - pair2.getValue())))
            .orElse(null);
    log.trace("idOfLabProblemMostAssigned - method finished: result={}", result);
    return result;
  }

  /**
   * Returns the average grade of all the groups
   *
   * @return an {@code Optional} containing a null if no student is in the repository otherwise a
   *     {@code Double} which represents the average grade
   */
  @Override
  @Transactional
  public Double averageGrade() {
    log.trace("averageGrade - method entered");
    Iterable<Assignment> assignmentIterable = repository.findAll();
    Set<Assignment> assignments =
        StreamSupport.stream(assignmentIterable.spliterator(), false).collect(Collectors.toSet());
    int gradesSum = assignments.stream().map(Assignment::getGrade).reduce(0, Integer::sum);

    if (assignments.size() > 0) {
      Double result = (double) gradesSum / (double) assignments.size();
      log.trace("averageGrade - method finished: result={}", result);
      return result;
    } else {
      log.trace("averageGrade - method finished: result={null value}");
      return null;
    }
  }

  /**
   * Return a mapping of every Student and a list of it's assigned LabProblems.
   *
   * @return the sought Student - List of LabProblems. If student has no assignment, map to an empty
   *     list.
   */
  @Override
  @Transactional
  public Map<Student, List<LabProblem>> studentAssignedProblems() {
    log.trace("studentAssignedProblems - method entered");
    Map<Student, List<LabProblem>> result =
        studentService.getAllStudents().stream()
            .collect(Collectors.toMap(student -> student, this::getAllLabProblemsForAStudent));
    log.trace("studentAssignedProblems - method finished: result={}", result);
    return result;
  }

  @Override
  public List<Assignment> getAssignmentForStudent(Long id) {
    log.trace("getAssignmentForStudent - method entered id={}", id);
    List<Assignment> result = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    result.removeIf(assignment -> !assignment.getStudent().getId().equals(id));
    log.trace("getAssignmentForStudent - method finished: result={}", result);
    return result;
  }

  @Override
  public List<LabProblem> getLabProblemsAvailableForStudent(Long id) {
    log.trace("getLabProblemsAvailableForStudent - method entered id={}", id);
    List<Assignment> assignments = getAssignmentForStudent(id);
    log.trace("getLabProblemsAvailableForStudent - found assignments={}", assignments);
    List<LabProblem> labProblems = labProblemService.getAllLabProblems();
    log.trace("getLabProblemsAvailableForStudent - found labProblems={}", labProblems);
    labProblems.removeIf(elem-> assignments.stream().anyMatch(l -> l.getLabProblem().getId().equals(elem.getId())));
    log.trace("getLabProblemsAvailableForStudent - method finished: result={}", labProblems);

    return labProblems;
  }

  @Override
  public List<LabProblem> getAllLabProblemsForAStudent(Student student) {
    if(student == null)throw new IllegalArgumentException("Student must not be null");
    return student.getLabProblems().stream().collect(Collectors.toList());
    //      return repository.findAll().stream()
    //        .filter(assignment -> assignment.getStudent().getId().equals(student.getId()))
    //        .map(assignment ->
    // labProblemService.getLabProblemById(assignment.getLabProblem().getId()))
    //        .collect(Collectors.toList());
  }

  @Override
  public List<LabProblem> getAllLabProblemsForAStudent(Long id) {
    if(id == null)throw new IllegalArgumentException("Id must not be null");
    List<LabProblem> result = new ArrayList<>();
    studentService.getStudentById(id).ifPresent(elem-> result.addAll(elem.getLabProblems()));
    return result;
    //      return repository.findAll().stream()
    //        .filter(assignment -> assignment.getStudent().getId().equals(student.getId()))
    //        .map(assignment ->
    // labProblemService.getLabProblemById(assignment.getLabProblem().getId()))
    //        .collect(Collectors.toList());
  }
  public List<Assignment> filterAssignmentsByGrade(Integer grade){
    if (grade == null) {
      throw new IllegalArgumentException("grade is needed!");
    }
    log.trace("filterAssignmentsByGrade - method entered: specification={}", grade);

//    Iterable<Assignment> assignments = repository.findAll((root,query,cb)->cb.equal(root.get("grade"),grade));
    Iterable<Assignment> assignments = repository.findByGrade(grade);
    List<Assignment> filteredAssignments = new LinkedList<>();
    assignments.forEach(filteredAssignments::add);
//    filteredAssignments.removeIf(entity -> !entity.getGrade().equals(grade));

    log.debug("filterAssignmentsByGrade - method finished: result={}", filteredAssignments);
    return filteredAssignments;

  }

  @Override
  public Page<Assignment> getAllAssignmentsPaged(Pageable pageToGet) {
    return repository.findAll(pageToGet);
  }

}
