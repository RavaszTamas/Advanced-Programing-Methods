package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Assignment;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.web.dto.AssignmentDto;
import ro.ubb.catalog.web.dto.AssignmentsDto;
import ro.ubb.catalog.web.dto.LabProblemDto;
import ro.ubb.catalog.web.dto.StudentDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Component
public class AssignmentConverter extends BaseConverter<Assignment, AssignmentDto> {

  public static final Logger log = LoggerFactory.getLogger(AssignmentConverter.class);

  @PersistenceContext // or even @Autowired
  private EntityManager entityManager;

  @Override
  public Assignment convertDtoToModel(AssignmentDto dto) {
    //    log.trace("convertDtoToModel - method entered dto={}", dto);
    Assignment assignment = null;
    try {
      assignment =
          Assignment.builder()
              .labProblem(entityManager.getReference(LabProblem.class, dto.getLabProblemID()))
              .student(entityManager.getReference(Student.class, dto.getStudentID()))
              .grade(dto.getGrade())
              .build();
    } catch (EntityNotFoundException ex) {

      assignment = null;
    }
    //    log.trace("convertDtoToModel - method finished: assignment={}", assignment);
    return assignment;
    //        return Assignment.builder().labProblem(
    //                LabProblem.builder().
    //                        description(dto.getLabProblem().getDescription()).
    //                        problemNumber(dto.getLabProblem().getProblemNumber()).
    //                        build()
    //        ).
    //                student(Student.builder()
    //                        .serialNumber(dto.getStudent().getSerialNumber())
    //                        .name(dto.getStudent().getName())
    //                        .groupNumber(dto.getStudent().getGroupNumber())
    //                        .build()).
    //                grade(dto.getGrade())
    //                .build();
  }

  @Override
  public AssignmentDto convertModelToDto(Assignment assignment) {
    //    log.trace("convertModelToDto - method entered assignment={}", assignment);
    AssignmentDto assignmentDto =
        AssignmentDto.builder()
            .labProblemID(assignment.getLabProblem().getId())
            .studentID(assignment.getStudent().getId())
            .grade(assignment.getGrade())
            .build();
    assignmentDto.setId(assignment.getId());
    //    log.trace("convertModelToDto - method finished: assignmentDto={}", assignmentDto);
    return assignmentDto;
  }
}
