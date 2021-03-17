package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Created by radu. */
public interface StudentService {
  /**
   * Updates a student inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully added or the entity passed to
   *     the ro.ubb.repository otherwise
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  Optional<Student> addStudent(@Valid Student student)
      throws ValidatorException;
  /**
   * Returns all the students in the ro.ubb.repository
   *
   * @return a set of all the students
   */
  List<Student> getAllStudents();

  /** Return all Students sorted by the sort criteria.
   * @return*/
  List<Student> getAllStudentsSorted(Sort sort);

  /**
   * Deletes a student from the ro.ubb.repository
   *
   * @param id the id of the student to be deleted
   */
  void deleteStudent(Long id);

  /**
   * Updates a student inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully updated or the entity passed to
   *     the ro.ubb.repository otherwise
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  Student updateStudent(@Valid Student student)
      throws ValidatorException;

  /**
   * Filters the elements of the ro.ubb.repository by a given group number
   *
   * @return a {@code Set} - of entities filtered by the given group number
   */
  Set<Student> filterByGroup(Integer group);

  /**
   * Get Optional containing student with given id if there is one in the ro.ubb.repository below.
   *
   * @param id to find student by
   * @return Optional containing the sought Student or null otherwise
   */
  Optional<Student> getStudentById(Long id);

  Page<Student> getAllStudentsPaged(Pageable pageToGet);
}
