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
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.exceptions.RepositoryException;
import ro.ubb.catalog.core.repository.labproblem.LabProblemRepository;

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
@DatabaseSetup("/META-INF/dbtest/db-data-lab-problems.xml")
public class LabProblemRepositoryTest {

  @Autowired private LabProblemRepository labProblemRepository;

  @Test
  public void findAll() throws Exception {
    List<LabProblem> students =
        StreamSupport.stream(labProblemRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    assertEquals("there should be four students", 4, students.size());
  }

  @Test
  public void updateLabProblem() throws Exception {
    LabProblem labProblem = LabProblem.builder().description("descnew").problemNumber(123).build();
    labProblem.setId(1L);
    Optional<LabProblem> toUpdate = labProblemRepository.findById(1L);
    toUpdate.ifPresent(
        studentUpdate -> {
          studentUpdate.setDescription(labProblem.getDescription());
          studentUpdate.setProblemNumber(labProblem.getProblemNumber());
        });
    assertEquals("The entity with id 1 should be updated", toUpdate.get(), labProblem);
  }

  @Test
  public void createLabProblem() throws Exception {
    LabProblem labProblem = LabProblem.builder().description("descnew").problemNumber(123).build();
    labProblem.setId(5L);
    labProblemRepository.save(labProblem);
    assertEquals("Student is added", labProblemRepository.findById(5L).get(), labProblem);
    assertEquals(
        "Student is added",
        StreamSupport.stream(labProblemRepository.findAll().spliterator(), false)
            .collect(Collectors.toList())
            .size(),
        5);
  }

  @Test
  public void deleteLabProblem() throws Exception {
    Long deleteId = 1L;
    try {
      labProblemRepository.deleteById(deleteId);
    } catch (RepositoryException ex) {
      fail();
    }
    assertEquals(
        "Delete successful",
        StreamSupport.stream(labProblemRepository.findAll().spliterator(), false)
            .collect(Collectors.toList())
            .size(),
        3);
    assertEquals("Delete successful", labProblemRepository.findById(1L), Optional.empty());
  }
}
