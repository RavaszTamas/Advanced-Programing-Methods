package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.web.dto.StudentDto;

/** Created by radu. */
@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {

  public static final Logger log = LoggerFactory.getLogger(StudentConverter.class);

  @Override
  public Student convertDtoToModel(StudentDto dto) {
//    log.trace("convertDtoToModel - method entered dto={}", dto);
    Student student =
        Student.builder()
            .serialNumber(dto.getSerialNumber())
            .name(dto.getName())
            .groupNumber(dto.getGroupNumber())
            .build();
    student.setId(dto.getId());
//    log.trace("convertDtoToModel - method finished: student={}", student);
    return student;
  }

  @Override
  public StudentDto convertModelToDto(Student student) {
//    log.trace("convertModelToDto - method entered student={}", student);
    StudentDto dto =
        StudentDto.builder()
            .serialNumber(student.getSerialNumber())
            .name(student.getName())
            .groupNumber(student.getGroupNumber())
            .build();
    dto.setId(student.getId());
//    log.trace("convertModelToDto - method finished: dto={}", dto);
    return dto;
  }
}
