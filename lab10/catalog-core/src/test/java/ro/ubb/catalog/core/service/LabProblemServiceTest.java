package ro.ubb.catalog.core.service;

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

import java.util.List;
import java.util.Optional;

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
public class LabProblemServiceTest {

  @Autowired private LabProblemService labProblemService;

  @Test
  public void findAll() throws Exception {
    List<LabProblem> labProblems = labProblemService.getAllLabProblems();
    assertEquals("there should be four students", 4, labProblems.size());
  }

  @Test
  public void updateStudent() throws Exception {
    LabProblem labProblem = LabProblem.builder().description("descnew").problemNumber(123).build();
    labProblem.setId(1L);
    labProblemService.updateLabProblem(labProblem);

    assertEquals(
        "The entity with id 1 should be updated",
        labProblemService.getLabProblemById(1L).get(),
        labProblem);
  }

  @Test
  public void createStudent() throws Exception {
    LabProblem labProblem = LabProblem.builder().description("descnew").problemNumber(123).build();
    labProblem.setId(5L);
    labProblemService.addLabProblem(labProblem);
    assertEquals("Student is added", labProblemService.getLabProblemById(5L).get(), labProblem);
    assertEquals("Student is added", labProblemService.getAllLabProblems().size(), 5);
  }

  @Test
  public void deleteStudent() throws Exception {
    Long deleteId = 1L;
    try {
      labProblemService.deleteLabProblem(deleteId);
    } catch (RepositoryException ex) {
      fail();
    }
    assertEquals("Delete successful", labProblemService.getAllLabProblems().size(), 3);
    assertEquals("Delete successful", labProblemService.getLabProblemById(1L), Optional.empty());
  }
}
