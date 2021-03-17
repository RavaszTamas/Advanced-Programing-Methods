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
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.exceptions.ClassReflectionException;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.service.LabProblemService;
import ro.ubb.catalog.web.converter.LabProblemConverter;
import ro.ubb.catalog.web.converter.SortConverter;
import ro.ubb.catalog.web.dto.*;

import java.util.LinkedList;
import java.util.Optional;

@RestController
public class LabProblemController {
  public static final Logger log = LoggerFactory.getLogger(LabProblemController.class);

  @Autowired private LabProblemService labProblemService;

  @Autowired private LabProblemConverter labProblemConverter;
  @Autowired private SortConverter sortConverter;

  @RequestMapping(value = "/lab_problems", method = RequestMethod.GET)
  public LabProblemsDto getAllLabProblems() {
    log.trace("getAllLabProblems - method entered");
    LabProblemsDto result =
        new LabProblemsDto(
            labProblemConverter.convertModelsToDtos(labProblemService.getAllLabProblems()));
    log.trace("getAllLabProblems - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/lab_problems/paged", method = RequestMethod.GET)
  public Page<LabProblemDto> getAllLabProblemsPaged(
      @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
    log.trace(
        "getAllLabProblemsPaged - method entered pageNumber={},pageSize={}", pageNumber, pageSize);
    Pageable pageToGet = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5));
    Page<LabProblem> labProblems = labProblemService.getAllLabProblemsPaged(pageToGet);
    Page<LabProblemDto> labProblemDtos =
        labProblems.map(entity -> labProblemConverter.convertModelToDto(entity));

    log.trace("getAllLabProblemsPaged - method finished: result={}", labProblemDtos);
    return labProblemDtos;
  }

  @RequestMapping(value = "/lab_problems/sorted", method = RequestMethod.POST)
  public LabProblemsDto getAllLabProblemsSorted(@RequestBody SortDto sortDto) {
    log.trace("getAllLabProblemsSorted - method entered sortDto={}", sortDto);
    Sort sort = sortConverter.convertDtoToModel(sortDto);
    LabProblemsDto result;
    try {
      result =
          new LabProblemsDto(
              labProblemConverter.convertModelsToDtos(
                  labProblemService.getAllLabProblemsSorted(sort)));
    } catch (ClassReflectionException ex) {
      result = new LabProblemsDto();
    }
    log.trace("getAllLabProblemsSorted - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/lab_problems/{id}", method = RequestMethod.GET)
  public LabProblemDto getLabProblem(@PathVariable Long id) {
    log.trace("getLabProblem - method entered id={}", id);
    LabProblemDto result;
    try {
      result =
          labProblemConverter.convertModelToDto(
              labProblemService.getLabProblemById(id).orElse(LabProblem.builder().build()));
    } catch (IllegalArgumentException ex) {
      log.trace("getLabProblem - exception caught: ex={}", ex.getMessage());
      result = LabProblemDto.builder().build();
    }
    log.trace("getLabProblem - method finished: result={}", result);
    return result;
  }

  @RequestMapping(
      value = "/lab_problems/filter/problemNumber/{problemNumber}",
      method = RequestMethod.GET)
  @ResponseBody
  public LabProblemsDto getAllLabProblemsFilteredByProblemNumber(
      @PathVariable Integer problemNumber) {
    log.trace("getAllLabProblemsFiltered - method entered problemNumber={}", problemNumber);
    LabProblemsDto result;

    try {
      try {
        result =
            new LabProblemsDto(
                labProblemConverter.convertModelsToDtos(
                    labProblemService.filterLabProblemsByProblemNumber(problemNumber)));
      } catch (IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        result =
            new LabProblemsDto(
                labProblemConverter.convertModelsToDtos(labProblemService.getAllLabProblems()));
      }
    } catch (IllegalArgumentException ex) {
      log.trace("getAllLabProblemsFiltered - exception caught: ex={}", ex.getMessage());
      result = new LabProblemsDto(new LinkedList<>());
    }
    log.trace("getAllLabProblemsFiltered - method finished: result={}", result);
    return result;
  }

  @RequestMapping(
      value = "/lab_problems/filter/description/{description}",
      method = RequestMethod.GET)
  @ResponseBody
  public LabProblemsDto getAllLabProblemsFilteredByDescription(@PathVariable String description) {
    log.trace("getAllLabProblemsFiltered - method entered description={}", description);
    LabProblemsDto result;

    try {
      try {
        result =
            new LabProblemsDto(
                labProblemConverter.convertModelsToDtos(
                    labProblemService.filterLabProblemsByDescription(description)));
      } catch (IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        result =
            new LabProblemsDto(
                labProblemConverter.convertModelsToDtos(labProblemService.getAllLabProblems()));
      }
    } catch (IllegalArgumentException ex) {
      log.trace("getAllLabProblemsFiltered - exception caught: ex={}", ex.getMessage());
      result = new LabProblemsDto(new LinkedList<>());
    }
    log.trace("getAllLabProblemsFiltered - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/lab_problems", method = RequestMethod.POST)
  public LabProblemDto saveLabProblem(@RequestBody LabProblemDto labProblemDto) {
    log.trace("saveLabProblem - method entered labProblemDto={}", labProblemDto);
    LabProblem labProblem = labProblemConverter.convertDtoToModel(labProblemDto);
    LabProblemDto resultToReturn;
    try {
      Optional<LabProblem> result = labProblemService.addLabProblem(labProblem);
      LabProblem labProblemToConvert = result.orElseGet(() -> LabProblem.builder().build());

      resultToReturn = labProblemConverter.convertModelToDto(labProblemToConvert);
    } catch (ValidatorException ex) {
      log.trace("saveLabProblem - exception caught: ex={}", ex.getMessage());
      resultToReturn = labProblemDto;
    }

    log.trace("saveLabProblem - method finished: result={}", resultToReturn);
    return resultToReturn;
  }

  @RequestMapping(value = "/lab_problems", method = RequestMethod.PUT)
  public LabProblemDto updateLabProblem(@RequestBody LabProblemDto labProblemDto) {
    log.trace("updateLabProblem - method entered: labProblemDto={}", labProblemDto);
    LabProblemDto result;
    try {
      LabProblem labProblem = labProblemConverter.convertDtoToModel(labProblemDto);
      result =
          labProblemConverter.convertModelToDto(labProblemService.updateLabProblem(labProblem));
    } catch (ValidatorException ex) {
      log.trace("updateLabProblem - exception caught: ex={}", ex.getMessage());
      result = labProblemDto;
    }
    log.trace("updateLabProblem - method finished: result={}", result);
    return result;
  }

  @RequestMapping(value = "/lab_problems/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteLabProblem(@PathVariable Long id) {
    log.trace("deleteLabProblem - method entered: id={}", id);

    try {
      labProblemService.deleteLabProblem(id);
    } catch (IllegalArgumentException | RepositoryException ex) {
      log.trace("deleteLabProblem - exception caught: ex={}", ex.getMessage());
      log.trace("deleteLabProblem - method finished");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    log.trace("deleteLabProblem - method finished");

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
