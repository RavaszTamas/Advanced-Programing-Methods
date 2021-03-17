package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.ClassReflectionException;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.AssignmentConverter;
import ro.ubb.catalog.web.converter.LabProblemConverter;
import ro.ubb.catalog.web.converter.SortConverter;
import ro.ubb.catalog.web.dto.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.Optional;

@RestController
public class AssignmentController {
  public static final Logger log = LoggerFactory.getLogger(AssignmentController.class);

  @PersistenceContext // or even @Autowired
  private EntityManager entityManager;

  @Autowired private StudentService studentService;

  @Autowired private AssignmentConverter assignmentConverter;
  @Autowired private LabProblemConverter labProblemConverter;
  @Autowired private SortConverter sortConverter;

  @Transactional
  @RequestMapping(value = "/assignments", method = RequestMethod.GET)
  public AssignmentsDto getAllAssignments() {
    log.trace("getAllAssignments - method entered");
    AssignmentsDto result =
        new AssignmentsDto(
            assignmentConverter.convertModelsToDtos(studentService.getAllAssignments()));
    log.trace("getAllAssignments - method finished: result={}", result);
    return result;
  }

  @Transactional
  @RequestMapping(value = "/assignments/paged", method = RequestMethod.GET)
  public Page<AssignmentDto> getAllAssignmentsPaged(
      @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
    log.trace(
        "getAllAssignmentsPaged - method entered pageNumber={},pageSize={}", pageNumber, pageSize);
    Pageable pageToGet = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5));
    Page<Assignment> assignments = studentService.getAllAssignmentsPaged(pageToGet);
    Page<AssignmentDto> assignmentDtos =
        assignments.map(entity -> assignmentConverter.convertModelToDto(entity));

    log.trace("getAllAssignmentsPaged - method finished: result={}", assignmentDtos);
    return assignmentDtos;
  }

  @RequestMapping(value = "/assignments/filter/{grade}", method = RequestMethod.GET)
  @Transactional
  @ResponseBody
  public AssignmentsDto getAllAssignmentsFiltered(@PathVariable Integer grade) {
    log.trace("getAllAssignmentsFiltered - method entered grade={}", grade);
    AssignmentsDto result;

    try {
      try {
        result =
            new AssignmentsDto(
                assignmentConverter.convertModelsToDtos(
                    studentService.filterAssignmentsByGrade(grade)));
      } catch (IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        result =
            new AssignmentsDto(
                assignmentConverter.convertModelsToDtos(studentService.getAllAssignments()));
      }
    } catch (IllegalArgumentException ex) {
      log.trace("getAllAssignmentsFiltered - exception caught: ex={}", ex.getMessage());
      result = new AssignmentsDto(new LinkedList<>());
    }
    log.trace("getAllAssignmentsFiltered - method finished: result={}", result);
    return result;
  }

  @Transactional
  @RequestMapping(value = "/assignments/sorted", method = RequestMethod.POST)
  public AssignmentsDto getAllAssignmentsSorted(@RequestBody SortDto sortDto) {
    log.trace("getAllAssignmentsSorted - method entered sortDto={}", sortDto);
    Sort sort = sortConverter.convertDtoToModel(sortDto);

    AssignmentsDto result;
    try {
      result =
          new AssignmentsDto(
              assignmentConverter.convertModelsToDtos(
                  studentService.getAllAssignmentsSorted(sort)));
    } catch (ClassReflectionException ex) {
      result = new AssignmentsDto();
    }
    log.trace("getAllAssignmentsSorted - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/{id}", method = RequestMethod.GET)
  @Transactional
  public AssignmentDto getAssignment(@PathVariable Long id) {
    log.trace("getAssignment - method entered id={}", id);
    AssignmentDto result;
    try {
      Optional<Assignment> assignmentOptional = studentService.getAssignmentById(id);
      if (assignmentOptional.isPresent())
        result = assignmentConverter.convertModelToDto(assignmentOptional.get());
      else result = AssignmentDto.builder().build();
    } catch (IllegalArgumentException ex) {
      log.trace("getAssignment - exception caught: ex={}", ex.getMessage());
      result = AssignmentDto.builder().build();
    }
    log.trace("getAssignment - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/student/{id}", method = RequestMethod.GET)
  @Transactional
  public AssignmentsDto getAssignmentForStudent(@PathVariable Long id) {
    log.trace("getAssignmentForStudent - method entered id={}", id);
    AssignmentsDto result;
    try {
      result =
          new AssignmentsDto(
              assignmentConverter.convertModelsToDtos(
                  studentService.getAssignmentForStudent(id)));
    } catch (IllegalArgumentException ex) {
      log.trace("getAssignment - exception caught: ex={}", ex.getMessage());
      result = new AssignmentsDto();
    }
    log.trace("getAssignment - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/student/available/{id}", method = RequestMethod.GET)
  @Transactional
  public LabProblemsDto getLabProblemsAvailableForStudent(@PathVariable Long id) {
    log.trace("getLabProblemsAvailableForStudent - method entered id={}", id);
    LabProblemsDto result;
    try {
      result =
          new LabProblemsDto(
              labProblemConverter.convertModelsToDtos(
                  studentService.getLabProblemsAvailableForStudent(id)));
    } catch (IllegalArgumentException ex) {
      log.trace("getLabProblemsAvailableForStudent - exception caught: ex={}", ex.getMessage());
      result = new LabProblemsDto();
    }
    log.trace("getLabProblemsAvailableForStudent - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/student/problems/{id}", method = RequestMethod.GET)
  @Transactional
  public LabProblemsDto getLabProblemsForStudent(@PathVariable Long id) {
    log.trace("getLabProblemsForStudent - method entered id={}", id);
    LabProblemsDto result;
    try {
      result =
          new LabProblemsDto(
              labProblemConverter.convertModelsToDtos(
                  studentService.getAllLabProblemsForAStudent(id)));
    } catch (IllegalArgumentException ex) {
      log.trace("getLabProblemsAvailableForStudent - exception caught: ex={}", ex.getMessage());
      result = new LabProblemsDto();
    }
    log.trace("getLabProblemsForStudent - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments", method = RequestMethod.POST)
  public AssignmentDto saveAssignment(@RequestBody AssignmentDto assignmentDto) {
    log.trace("saveAssignment - method entered assignmentDto={}", assignmentDto);
    AssignmentDto resultToReturn;

    try {

      // Assignment assignment = assignmentConverter.convertDtoToModel(assignmentDto);

      Optional<Assignment> result =
          studentService.addAssignment(
              assignmentDto.getStudentID(),
              assignmentDto.getLabProblemID(),
              assignmentDto.getGrade());
      Assignment labProblemToConvert =
          result.orElseGet(
              () ->
                  Assignment.builder()
                      .student(Student.builder().build())
                      .labProblem(LabProblem.builder().build())
                      .build());

      resultToReturn = assignmentConverter.convertModelToDto(labProblemToConvert);
    } catch (ValidatorException | EntityNotFoundException | RepositoryException ex) {
      log.trace("saveAssignment - exception caught: ex={}", ex.getMessage());
      resultToReturn = assignmentDto;
    }
    log.trace("saveAssignment - method finished: result={}", resultToReturn);
    return resultToReturn;
  }

  @RequestMapping(value = "/assignments", method = RequestMethod.PUT)
  public AssignmentDto updateAssignment(@RequestBody AssignmentDto assignmentDto) {
    log.trace("updateAssignment - method entered: labProblemDto={}", assignmentDto);
    AssignmentDto result;
    try {
      Assignment assignment = assignmentConverter.convertDtoToModel(assignmentDto);
      result =
          assignmentConverter.convertModelToDto(
              studentService.updateAssignment(
                  assignmentDto.getId(),
                  assignmentDto.getStudentID(),
                  assignmentDto.getLabProblemID(),
                  assignmentDto.getGrade()));
    } catch (ValidatorException | RepositoryException ex) {
      log.trace("updateAssignment - exception caught: ex={}", ex.getMessage());
      result = assignmentDto;
    }
    log.trace("updateAssignment - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
    log.trace("deleteAssignment - method entered: id={}", id);
    try {
      studentService.deleteAssignment(id);
    } catch (IllegalArgumentException | RepositoryException ex) {
      log.trace("deleteAssignment - exception caught: ex={}", ex.getMessage());
      log.trace("deleteAssignment - method finished");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    log.trace("deleteAssignment - method finished");

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/assignments/averageGrade", method = RequestMethod.GET)
  public Double averageGrade() {
    log.trace("averageGrade - method entered");
    Double result = studentService.averageGrade();
    log.trace("averageGrade - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/mostAssigned", method = RequestMethod.GET)
  public MostAssignedDto mostAssigned() {
    log.trace("mostAssigned - method entered");
    var resultPair = studentService.idOfLabProblemMostAssigned();
    MostAssignedDto result =
        MostAssignedDto.builder().id(resultPair.getKey()).count(resultPair.getValue()).build();
    log.trace("mostAssigned - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/assignments/greatestMean", method = RequestMethod.GET)
  public GreatestMeanDto greatestMean() {
    log.trace("greatestMean - method entered");
    var resultPair = studentService.greatestMean();
    GreatestMeanDto result =
        GreatestMeanDto.builder().id(resultPair.getKey()).mean(resultPair.getValue()).build();
    log.trace("greatestMean - method finished: result={}", result);
    return result;
  }
}
