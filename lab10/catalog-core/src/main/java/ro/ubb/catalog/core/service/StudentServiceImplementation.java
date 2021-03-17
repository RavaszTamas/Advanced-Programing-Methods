package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.repository.labproblem.LabProblemRepository;
import ro.ubb.catalog.core.repository.student.StudentRepository;
import ro.ubb.catalog.core.service.validators.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
  @Autowired private StudentRepository studentRepository;
  @Autowired private LabProblemService labProblemService;
  @Autowired private LabProblemRepository labProblemRepository;
  @Autowired private Validator<Student> validator;
  @Autowired private Validator<Assignment> assignmentValidator;
  @PersistenceContext // or even @Autowired
  private EntityManager entityManager;

  @Override
  public Optional<Student> addStudent(@Valid Student student) throws ValidatorException {
    log.trace("addStudent - method entered: student={}", student);

    validator.validate(student);
    log.trace("addStudent - student validated");

    Student result = studentRepository.save(student);

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
    // List<Student> result =
    // StreamSupport.stream(repository.findAllWithAssignmentsAndLabProblems().spliterator(),false).collect(Collectors.toList());
    List<Student> result = studentRepository.findAllWithAssignmentsAndLabProblems();
    log.trace("getAllStudents - method finished: result={}", result);
    return result;
  }

  /** Return all Students sorted by the sort criteria. */
  @Override
  public List<Student> getAllStudentsSorted(Sort sort) {
    log.trace("getAllStudentsSorted - method entered");
    List<Student> result;
    try {
      result =
          StreamSupport.stream(studentRepository.findAll(sort).spliterator(), false)
              .collect(Collectors.toList());
    } catch (PropertyReferenceException ex) {
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
    if (!studentRepository.existsById(id))
      throw new RepositoryException("Entity with id doesn't exists!");
    studentRepository.deleteById(id);
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
  public Student updateStudent(@Valid Student student) throws ValidatorException {
    log.trace("updateStudent - method entered: student={}", student);
    validator.validate(student);
    log.trace("updateStudent - student validated");
    Optional<Student> studentOptional = studentRepository.findById(student.getId());

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
    //    Iterable<Student> students =
    // repository.findAllua((root,query,cb)->cb.eql(root.get("groupNumber"),group));
    Iterable<Student> students = studentRepository.findAllWithGroupNumber(group);
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
    Optional<Student> student = studentRepository.findByIdWithAssignmentsAndLabProblems(id);
    log.debug("getStudentById - method exit: student={}", student);
    return student;
  }

  @Override
  public Page<Student> getAllStudentsPaged(Pageable pageToGet) {
    log.trace("getAllStudentsPaged - method entered");
    Page<Student> result = studentRepository.findAll(pageToGet);
    log.trace("getAllStudentsPaged - method finished: result={}", result);
    return result;
  }

  @Override
  public Set<Student> filterByName(String name) {
    if (name.equals("")) {
      throw new IllegalArgumentException("group negative!");
    }
    log.trace("filterByGroup - method entered: name={}", name);
    //    Iterable<Student> students =
    // repository.findAllua((root,query,cb)->cb.eql(root.get("groupNumber"),group));
    Iterable<Student> students = studentRepository.findAllWithName(name);
    Set<Student> filteredStudents = new HashSet<>();
    students.forEach(filteredStudents::add);
    //    filteredStudents.removeIf(entity -> !entity.getGroupNumber().equals(group));
    log.debug("filterByGroup - method finished: result={}", filteredStudents);
    return filteredStudents;
  }

  @Override
  @Transactional
  public Optional<Assignment> addAssignment(Long studentId, Long labProblemId, Integer grade)
      throws ValidatorException {

    Assignment assignment =
        Assignment.builder()
            .grade(grade)
            .labProblem(entityManager.getReference(LabProblem.class, labProblemId))
            .student(entityManager.getReference(Student.class, studentId))
            .build();

    log.trace("addAssignment - method entered: assignment={}", assignment);

    String errorMessage = "";
    Student student = this.getStudentById(assignment.getStudent().getId()).orElse(null);
    if (student == null) errorMessage += "Student id is not in the database ";
    LabProblem labProblem =
        labProblemService.getLabProblemById(assignment.getLabProblem().getId()).orElse(null);
    if (labProblem == null) errorMessage += "Lab problem id is not in the database";
    if (errorMessage.length() > 0) throw new RepositoryException(errorMessage);
    assignmentValidator.validate(assignment);
    log.trace("addAssignment - assignment validated assignment={}", assignment);

    List<LabProblem> labProblems = labProblemRepository.findAllWithAssignmentsAndLabStudents();
    List<Assignment> assignments =
        labProblems.stream()
            .map(LabProblem::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    Optional<Assignment> filtered =
        assignments.stream()
            .filter(
                elem ->
                    elem.getLabProblem().getId().equals(assignment.getLabProblem().getId())
                        && elem.getStudent().getId().equals(assignment.getStudent().getId()))
            .findFirst();

    filtered.ifPresent(
        s -> {
          throw new RepositoryException("Assignment already has been made");
        });

    Optional<Student> result =
        studentRepository.findByIdWithAssignmentsAndLabProblems(assignment.getStudent().getId());

    result.ifPresent(student1 -> student1.getAssignments().add(assignment));

    return Optional.empty();
  }

  @Override
  public List<Assignment> getAllAssignments() {
    log.trace("getAllAssignments - method entered");

    // List<Assignment> result =
    // StreamSupport.stream(repository.findAllWithStudentAndLabProblem().spliterator(),false).collect(Collectors.toList());

    List<Assignment> result =
        this.studentRepository.findAllWithAssignmentsAndLabProblems().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    log.trace("getAllAssignments - method finished: result={}", result);
    return result;
  }

  /** Return all Assignments sorted by the sort criteria. */
  @Override
  public List<Assignment> getAllAssignmentsSorted(Sort sort) {
    log.trace("getAllAssignmentsSorted - method entered");
    // Iterable<Assignment> assignments = repository.findAll(sort);
    Iterable<Assignment> assignments = new HashSet<>();
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

    Optional<Assignment> assignment =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .filter(elem -> elem.getId().equals(id))
            .findFirst();
    log.debug("getAssignmentById - method exit: assignment={}", assignment);
    return assignment;
  }

  /** Deletes an assignment from the ro.ubb.repository */
  @Override
  @Transactional
  public void deleteAssignment(Long id) {
    if (id == null || id < 0) {
      throw new IllegalArgumentException("Invalid studentID!");
    }
    log.trace("deleteAssignment entered id={}", id);
    Optional<Assignment> assignmentOptional =
        studentRepository.findAllWithAssignmentsAndLabProblems().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .filter(elem -> elem.getId().equals(id))
            .findFirst();
    assignmentOptional.ifPresent(
        elem -> {
          Optional<LabProblem> labProblem =
              labProblemRepository.findByIdWithAssignmentsAndStudents(elem.getLabProblem().getId());
          labProblem.ifPresent(lp -> lp.getAssignments().remove(elem));
        });
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
  public Assignment updateAssignment(Long id, Long studentId, Long labProblemId, Integer grade)
      throws ValidatorException {
    Assignment assignment =
        Assignment.builder()
            .grade(grade)
            .labProblem(entityManager.getReference(LabProblem.class, labProblemId))
            .student(entityManager.getReference(Student.class, studentId))
            .build();
    assignment.setId(id);
    log.trace("updateAssignment - method entered: assignment{}", assignment);

    String errorMessage = "";
    Student student = this.getStudentById(assignment.getStudent().getId()).orElse(null);
    if (student == null) errorMessage += "Student id is not in the database ";
    LabProblem labProblem =
        labProblemService.getLabProblemById(assignment.getLabProblem().getId()).orElse(null);
    if (labProblem == null) errorMessage += "Lab problem id is not in the database";
    if (errorMessage.length() > 0) throw new RepositoryException(errorMessage);
    assignmentValidator.validate(assignment);

    studentRepository
        .findByIdWithAssignmentsAndLabProblems(assignment.getStudent().getId())
        .ifPresent(
            myStudent ->
                myStudent.getAssignments().stream()
                    .filter(
                        as -> as.getLabProblem().getId().equals(assignment.getLabProblem().getId()))
                    .forEach(as -> as.setGrade(assignment.getGrade())));

    log.trace("updateAssignment - method finished result={}", assignment);
    return assignment;
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
    Iterable<Assignment> assignmentIterable =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    Set<Assignment> assignments =
        StreamSupport.stream(assignmentIterable.spliterator(), false).collect(Collectors.toSet());

    AbstractMap.SimpleEntry<Long, Double> result =
        this.getAllStudents().stream()
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
    Iterable<Assignment> assignmentIterable =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
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
    Iterable<Assignment> assignmentIterable =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
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
        this.getAllStudents().stream()
            .collect(Collectors.toMap(student -> student, this::getAllLabProblemsForAStudent));
    log.trace("studentAssignedProblems - method finished: result={}", result);
    return result;
  }

  @Override
  public List<Assignment> getAssignmentForStudent(Long id) {
    log.trace("getAssignmentForStudent - method entered id={}", id);
    //    List<Assignment> result =
    // StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
    //    result.removeIf(assignment -> !assignment.getStudent().getId().equals(id));
    Optional<Student> student = studentRepository.findByIdWithAssignmentsAndLabProblems(id);
    List<Assignment> result;
    if (student.isPresent()) {
      result = student.get().getAssignments().stream().collect(Collectors.toList());
    } else {
      result = new LinkedList<>();
    }
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
    labProblems.removeIf(
        elem -> assignments.stream().anyMatch(l -> l.getLabProblem().getId().equals(elem.getId())));
    log.trace("getLabProblemsAvailableForStudent - method finished: result={}", labProblems);

    return labProblems;
  }

  @Override
  public List<LabProblem> getAllLabProblemsForAStudent(Student student) {
    if (student == null) throw new IllegalArgumentException("Student must not be null");
    return student.getLabProblems().stream().collect(Collectors.toList());
    //      return repository.findAll().stream()
    //        .filter(assignment -> assignment.getStudent().getId().equals(student.getId()))
    //        .map(assignment ->
    // labProblemService.getLabProblemById(assignment.getLabProblem().getId()))
    //        .collect(Collectors.toList());
  }

  @Override
  public List<LabProblem> getAllLabProblemsForAStudent(Long id) {
    if (id == null) throw new IllegalArgumentException("Id must not be null");
    List<LabProblem> result = new ArrayList<>();
    this.getStudentById(id).ifPresent(elem -> result.addAll(elem.getLabProblems()));
    return result;
    //      return repository.findAll().stream()
    //        .filter(assignment -> assignment.getStudent().getId().equals(student.getId()))
    //        .map(assignment ->
    // labProblemService.getLabProblemById(assignment.getLabProblem().getId()))
    //        .collect(Collectors.toList());
  }

  public List<Assignment> filterAssignmentsByGrade(Integer grade) {
    if (grade == null) {
      throw new IllegalArgumentException("grade is needed!");
    }
    log.trace("filterAssignmentsByGrade - method entered: specification={}", grade);

    //    Iterable<Assignment> assignments =
    // repository.findAll((root,query,cb)->cb.equal(root.get("grade"),grade));
    Iterable<Assignment> assignments =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .filter(elem -> elem.getGrade().equals(grade))
            .collect(Collectors.toSet());
    List<Assignment> filteredAssignments = new LinkedList<>();
    assignments.forEach(filteredAssignments::add);
    //    filteredAssignments.removeIf(entity -> !entity.getGrade().equals(grade));

    log.debug("filterAssignmentsByGrade - method finished: result={}", filteredAssignments);
    return filteredAssignments;
  }

  @Override
  public Page<Assignment> getAllAssignmentsPaged(Pageable pageToGet) {
    List<Assignment> assignments =
        studentRepository.findAllWithAssignments().stream()
            .map(Student::getAssignments)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    return new PageImpl<>(assignments, pageToGet, assignments.size());
  }
}
