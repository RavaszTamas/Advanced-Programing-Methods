package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.repository.student.StudentRepository;
import ro.ubb.catalog.core.service.validators.Validator;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ravasz Tamas The ro.ubb.labproblem.web.controller for the student entities, and for the
 *     functionalities which are performed on these entities
 */
@Service
public class StudentServiceImplementation implements StudentService {
  private static final Logger log = LoggerFactory.getLogger(StudentServiceImplementation.class);
  @Autowired private StudentRepository repository;
  @Autowired private Validator<Student> validator;

  @Override
  public Optional<Student> addStudent(@Valid Student student)
      throws ValidatorException {
    log.trace(
        "addStudent - method entered: student={}",student);

    validator.validate(student);
    log.trace("addStudent - student validated");

    Student result = repository.save(student);

    log.trace("addStudent - method finished: result={}", result);
    return Optional.empty();
  }

  /**
   * Returns all the students in the ro.ubb.repository
   *
   * @return a set of all the students
   */
  @Override
  public List<Student> getAllStudents() {
    log.trace("getAllStudents - method entered");
    List<Student> result = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    log.trace("getAllStudents - method finished: result={}", result);
    return result;
  }

  /** Return all Students sorted by the sort criteria. */
  @Override
  public List<Student> getAllStudentsSorted(Sort sort) {
    log.trace("getAllStudentsSorted - method entered");
    List<Student> result;
    try{
    result = StreamSupport.stream(repository.findAll(sort).spliterator(),false).collect(Collectors.toList());
    }
    catch (PropertyReferenceException ex){
      log.trace("getAllStudentsSorted excpetion occured={}", ex.getMessage());
      return new ArrayList<>();
    }
    log.trace("getAllStudentsSorted - students sorted");
    log.trace("getAllStudentsSorted - method finished: result={}", result);
    return result;
  }

  /**
   * Deletes a student from the ro.ubb.repository
   *
   * @param id the id of the student to be deleted
   */
  @Override
  public void deleteStudent(Long id) {
    if (id == null || id < 0) throw new IllegalArgumentException("Invalid id!");
    log.trace("deleteStudent - method entered: id={}", id);
    if (!repository.existsById(id)) throw new RepositoryException("Entity with id doesn't exists!");
    repository.deleteById(id);
    log.trace("deleteStudent - method finished");
  }

  /**
   * Updates a student inside the ro.ubb.repository
   *
   * @return an {@code Optional} containing the null if successfully updated or the entity passed to
   *     the ro.ubb.repository otherwise
   * @throws ValidatorException if the object is incorrectly defined by the user
   */
  @Override
  @Transactional
  public Student updateStudent(@Valid Student student)
      throws ValidatorException {
    log.trace(
        "updateStudent - method entered: student={}",
            student);
    validator.validate(student);
    log.trace("updateStudent - student validated");
    Optional<Student> studentOptional = repository.findById(student.getId());

    studentOptional.ifPresent(
        s -> {
          s.setName(student.getName());
          s.setGroupNumber(student.getGroupNumber());
          s.setSerialNumber(student.getSerialNumber());
          log.debug("updateStudent - updated: s={}", s);
        });
    log.trace("updateStudent - method finished result={}", studentOptional);

    return studentOptional.orElse(student);
  }

  /**
   * Filters the elements of the ro.ubb.repository by a given group number
   *
   * @return a {@code Set} - of entities filtered by the given group number
   */
  @Override
  public Set<Student> filterByGroup(Integer group) {
    if (group < 0) {
      throw new IllegalArgumentException("group negative!");
    }
    log.trace("filterByGroup - method entered: group={}", group);
//    Iterable<Student> students = repository.findAll((root,query,cb)->cb.equal(root.get("groupNumber"),group));
    Iterable<Student> students=repository.findByGroupNumber(group);
    Set<Student> filteredStudents = new HashSet<>();
    students.forEach(filteredStudents::add);
//    filteredStudents.removeIf(entity -> !entity.getGroupNumber().equals(group));
    log.debug("filterByGroup - method finished: result={}", filteredStudents);
    return filteredStudents;
  }

  /**
   * Get Optional containing student with given id if there is one in the ro.ubb.repository below.
   *
   * @param id to find student by
   * @return Optional containing the sought Student or null otherwise
   */
  @Override
  public Optional<Student> getStudentById(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("invalid id!");
    }
    log.trace("getStudentById - method entered: id={}", id);
    Optional<Student> student = repository.findById(id);
    log.debug("getStudentById - method exit: student={}", student);
    return student;
  }

  @Override
  public Page<Student> getAllStudentsPaged(Pageable pageToGet) {
    log.trace("getAllStudentsPaged - method entered");
    Page<Student> result = repository.findAll(pageToGet);
    log.trace("getAllStudentsPaged - method finished: result={}", result);
    return result;
  }
}
