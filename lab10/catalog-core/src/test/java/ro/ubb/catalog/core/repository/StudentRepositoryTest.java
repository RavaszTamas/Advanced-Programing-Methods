package ro.ubb.catalog.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.repository.student.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Created by radu. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionalTestExecutionListener.class,
  DbUnitTestExecutionListener.class
})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class StudentRepositoryTest {

  @Autowired private StudentRepository studentRepository;

  @Test
  public void findAll() throws Exception {
    List<Student> students =
        StreamSupport.stream(studentRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    assertEquals("there should be four students", 4, students.size());
  }

  @Test
  public void createStudent() throws Exception {
    Student student =
        Student.builder().serialNumber("snnew").groupNumber(123).name("newName").build();
    student.setId(5L);
    studentRepository.save(student);
    assertEquals("Student is added", studentRepository.findById(5L).get(), student);
    assertEquals(
        "Student is added",
        StreamSupport.stream(studentRepository.findAll().spliterator(), false)
            .collect(Collectors.toList())
            .size(),
        5);
  }

  @Test
  public void deleteStudent() throws Exception {
    Long deleteId = 1L;
    try {
      studentRepository.deleteById(deleteId);
    } catch (RepositoryException ex) {
      fail();
    }
    assertEquals(
        "Delete successful",
        StreamSupport.stream(studentRepository.findAll().spliterator(), false)
            .collect(Collectors.toList())
            .size(),
        3);
    assertEquals("Delete successful", studentRepository.findById(1L), Optional.empty());
  }

  @Test
  public void updateStudent() throws Exception {
    Student student =
        Student.builder().serialNumber("snnew").groupNumber(123).name("newName").build();
    student.setId(1L);
    Optional<Student> toUpdate = studentRepository.findById(1L);
    toUpdate.ifPresent(
        studentUpdate -> {
          studentUpdate.setGroupNumber(student.getGroupNumber());
          studentUpdate.setSerialNumber(student.getSerialNumber());
          studentUpdate.setName(student.getName());
        });
    assertEquals("The entity with id 1 should be updated", toUpdate.get(), student);
  }
}
