package ro.ubb.catalog.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

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
public class StudentServiceTest {

  @Autowired private StudentService studentService;

  @Test
  public void findAll() throws Exception {
    List<Student> students = studentService.getAllStudents();
    assertEquals("there should be four students", 4, students.size());
  }

  @Test
  public void updateStudent() throws Exception {
    Student student =
        Student.builder().serialNumber("snnew").groupNumber(123).name("newName").build();
    student.setId(1L);
    studentService.updateStudent(student);

    assertEquals(
        "The entity with id 1 should be updated", studentService.getStudentById(1L).get(), student);
  }

  @Test
  public void createStudent() throws Exception {
    Student student =
        Student.builder().serialNumber("snnew").groupNumber(123).name("newName").build();
    student.setId(5L);
    studentService.addStudent(student);
    assertEquals("Student is added", studentService.getStudentById(5L).get(), student);
    assertEquals("Student is added", studentService.getAllStudents().size(), 5);
  }

  @Test
  public void deleteStudent() throws Exception {
    Long deleteId = 1L;
    try {
      studentService.deleteStudent(deleteId);
    } catch (RepositoryException ex) {
      fail();
    }
    assertEquals("Delete successful", studentService.getAllStudents().size(), 3);
    assertEquals("Delete successful", studentService.getStudentById(1L), Optional.empty());
  }
}
