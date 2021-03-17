package ro.ubb.catalog.core.repository.student;

import ro.ubb.catalog.core.model.Student;

import java.util.List;

public interface StudentCustomRepository {

  List<Student> findAllWithName(String name);

  List<Student> findAllWithGroupNumber(Integer groupNumber);
}
