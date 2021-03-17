// package ro.ubb.catalog.core.service;
//
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.jpa.domain.Specification;
// import ro.ubb.catalog.core.model.Assignment;
// import ro.ubb.catalog.core.model.LabProblem;
// import ro.ubb.catalog.core.model.Student;
// import ro.ubb.catalog.core.model.exceptions.ValidatorException;
//
// import javax.validation.Valid;
// import java.util.*;
//
// public interface AssignmentService {
//
//  Optional<Assignment> addAssignment(Long studentId, Long labProblemId, Integer grade)
//      throws ValidatorException;
//
//  List<Assignment> getAllAssignments();
//
//  /** Return all Assignments sorted by the sort criteria. */
//  List<Assignment> getAllAssignmentsSorted(Sort sort);
//
//  /**
//   * Get Optional containing assignment with given id if there is one in the ro.ubb.repository
//   * below.
//   *
//   * @param id to find assignment by
//   * @return Optional containing the sought Assignment or null otherwise
//   */
//  Optional<Assignment> getAssignmentById(Long id);
//
//  /**
//   * Deletes an assignment from the ro.ubb.repository
//   *
//   * @param id the id of the assignment to be deleted
//   */
//  void deleteAssignment(Long id);
//
//  Assignment updateAssignment(Long id, Long studentId, Long labProblemId, Integer grade)
//      throws ValidatorException;
//
//  /**
//   * Returns the student id who has the biggest mean of grades
//   *
//   * @return an {@code Optional} containing a null if no student is in the repository otherwise an
//   *     {@code Optional} containing a {@code Pair} of Long and Double, for the ID and the grade
//   *     average
//   */
//  AbstractMap.SimpleEntry<Long, Double> greatestMean();
//
//  /**
//   * Returns the id of the lab problem which was assigned the most often
//   *
//   * @return an {@code Optional} containing a null if no student is in the repository otherwise an
//   *     {@code Optional} containing a {@code Pair} of Long and Long, for the ID and the number of
//   *     assignments
//   */
//  AbstractMap.SimpleEntry<Long, Long> idOfLabProblemMostAssigned();
//
//  /**
//   * Returns the average grade of all the groups
//   *
//   * @return an {@code Optional} containing a null if no student is in the repository otherwise a
//   *     {@code Double} which represents the average grade
//   */
//  Double averageGrade();
//
//  /**
//   * Return a mapping of every Student and a list of it's assigned LabProblems.
//   *
//   * @return the sought Student - List of LabProblems. If student has no assignment, map to an
// empty
//   *     list.
//   */
//  Map<Student, List<LabProblem>> studentAssignedProblems();
//
//  List<Assignment> getAssignmentForStudent(Long id);
//
//  List<LabProblem> getLabProblemsAvailableForStudent(Long id);
//
//  List<LabProblem> getAllLabProblemsForAStudent(Student student);
//
//  List<LabProblem> getAllLabProblemsForAStudent(Long id);
//
//  List<Assignment> filterAssignmentsByGrade(Integer grade);
//
//  Page<Assignment> getAllAssignmentsPaged(Pageable pageToGet);
// }
