package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** A LabProblem having a number (positive integer) and a description (nonempty). */
@NamedEntityGraphs({
  @NamedEntityGraph(
      name = "labProblemWithAssignmentsAndStudents",
      attributeNodes =
          @NamedAttributeNode(value = "assignments", subgraph = "assignmentsWithStudent"),
      subgraphs =
          @NamedSubgraph(
              name = "assignmentsWithStudent",
              attributeNodes = @NamedAttributeNode(value = "labProblem")))
})
@Entity
@Table(name = "lab_problems")
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "lab_problem_id"))})
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class LabProblem extends BaseEntity<Long> {
  @Column(name = "problem_number")
  @Min(0)
  private Integer problemNumber;

  @Column(name = "description")
  @NotEmpty
  private String description;

  @OneToMany(
      mappedBy = "labProblem",
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Assignment> assignments = new HashSet<>();

  public LabProblem() {
    this.problemNumber = -1;
    this.description = "";
    setId(-1L);
  }

  public LabProblem(int problemNumber, String description) {
    this.problemNumber = problemNumber;
    this.description = description;
  }

  public Set<Student> getStudents() {
    return assignments.stream().map(Assignment::getStudent).collect(Collectors.toSet());
  }

  public void addStudent(Student student) {
    assignments.add(new Assignment(student, this));
  }

  public Integer getProblemNumber() {
    return problemNumber;
  }

  public void setProblemNumber(int problemNumber) {
    this.problemNumber = problemNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "LabProblem{"
        + "id="
        + getId()
        + ", problemNumber="
        + problemNumber
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
