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
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.ClassReflectionException;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.SortConverter;
import ro.ubb.catalog.web.converter.StudentConverter;
import ro.ubb.catalog.web.dto.SortDto;
import ro.ubb.catalog.web.dto.StudentDto;
import ro.ubb.catalog.web.dto.StudentsDto;

import java.util.LinkedList;
import java.util.Optional;

/** Created by radu. */
@RestController
public class StudentController {
  public static final Logger log = LoggerFactory.getLogger(StudentController.class);

  @Autowired private StudentService studentService;

  @Autowired private StudentConverter studentConverter;
  @Autowired private SortConverter sortConverter;

  @RequestMapping(value = "/students", method = RequestMethod.GET)
  public StudentsDto getAllStudents() {
    log.trace("getAllStudents - method entered");
    StudentsDto result =
        new StudentsDto(studentConverter.convertModelsToDtos(studentService.getAllStudents()));
    log.trace("getAllStudents - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students/paged", method = RequestMethod.GET)
  public Page<StudentDto> getAllStudentsPaged(
      @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
    log.trace(
        "getAllStudentsPaged - method entered pageNumber={},pageSize={}", pageNumber, pageSize);
    Pageable pageToGet = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5));

    Page<Student> students = studentService.getAllStudentsPaged(pageToGet);
    Page<StudentDto> studentDtos =
        students.map(entity -> studentConverter.convertModelToDto(entity));

    log.trace("getAllStudentsPaged - method finished: result={}", studentDtos);
    return studentDtos;
  }

  @RequestMapping(value = "/students/sorted", method = RequestMethod.POST)
  public StudentsDto getAllStudentsSorted(@RequestBody SortDto sortDto) {
    log.trace("getAllStudentsSorted - method entered sortDto={}", sortDto);
    Sort sort = sortConverter.convertDtoToModel(sortDto);
    StudentsDto result;
    try {
      result =
          new StudentsDto(
              studentConverter.convertModelsToDtos(studentService.getAllStudentsSorted(sort)));

    } catch (ClassReflectionException ex) {
      result = new StudentsDto();
    }
    log.trace("getAllStudentsSorted - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students/filter/groupNumber/{groupNumber}", method = RequestMethod.GET)
  public StudentsDto getAllStudentsFiltered(@PathVariable Integer groupNumber) {
    log.trace("getAllStudentsFiltered - method entered groupNumber={}", groupNumber);
    StudentsDto result;
    try {
      result =
          new StudentsDto(
              studentConverter.convertModelsToDtos(studentService.filterByGroup(groupNumber)));
    } catch (IllegalArgumentException ex) {
      log.trace("getAllStudentsFilteredByGroup - exception caught: ex={}", ex.getMessage());
      result = new StudentsDto(new LinkedList<>());
    }
    log.trace("getAllStudentsFilteredByGroup - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students/filter/name/{name}", method = RequestMethod.GET)
  public StudentsDto getAllStudentsFiltered(@PathVariable String name) {
    log.trace("getAllStudentsFiltered - method entered name={}", name);
    StudentsDto result;
    try {
      result =
          new StudentsDto(studentConverter.convertModelsToDtos(studentService.filterByName(name)));
    } catch (IllegalArgumentException ex) {
      log.trace("getAllStudentsFilteredByGroup - exception caught: ex={}", ex.getMessage());
      result = new StudentsDto(new LinkedList<>());
    }
    log.trace("getAllStudentsFilteredByGroup - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
  public StudentDto getStudent(@PathVariable Long id) {
    log.trace("getStudent - method entered id={}", id);
    StudentDto result;
    try {
      result =
          studentConverter.convertModelToDto(
              studentService.getStudentById(id).orElse(Student.builder().build()));
    } catch (IllegalArgumentException ex) {
      log.trace("getStudent - exception caught: ex={}", ex.getMessage());
      result = StudentDto.builder().build();
    }
    log.trace("getStudent - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students", method = RequestMethod.POST)
  public StudentDto saveStudent(@RequestBody StudentDto studentDto) {
    log.trace("saveStudent - method entered studentDto={}", studentDto);
    Student studentToAdd = studentConverter.convertDtoToModel(studentDto);
    StudentDto resultToReturn;
    try {
      Optional<Student> result = studentService.addStudent(studentToAdd);
      Student studentToConvert = result.orElseGet(() -> Student.builder().build());
      resultToReturn = studentConverter.convertModelToDto(studentToConvert);

    } catch (ValidatorException ex) {
      log.trace("saveStudent - exception caught: ex={}", ex.getMessage());
      resultToReturn = studentDto;
    }
    log.trace("saveStudent - method finished: result={}", resultToReturn);
    return resultToReturn;
  }

  @RequestMapping(value = "/students", method = RequestMethod.PUT)
  public StudentDto updateStudent(@RequestBody StudentDto studentDto) {
    log.trace("updateStudent - method entered: studentDto={}", studentDto);
    StudentDto result;
    try {
      Student student = studentConverter.convertDtoToModel(studentDto);
      result = studentConverter.convertModelToDto(studentService.updateStudent(student));
    } catch (ValidatorException ex) {
      log.trace("updateStudent - exception caught: ex={}", ex.getMessage());
      result = studentDto;
    }
    log.trace("updateStudent - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
    log.trace("deleteStudent - method entered: id={}", id);
    try {
      studentService.deleteStudent(id);
    } catch (IllegalArgumentException | RepositoryException ex) {
      log.trace("deleteStudent - exception caught: ex={}", ex.getMessage());
      log.trace("deleteStudent - method finished");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    log.trace("deleteStudent - method finished");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
