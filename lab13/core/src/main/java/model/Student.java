package model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** A student having group (positive integer), name (nonempty) and serialNumber (nonempty). */
@NamedEntityGraphs({
        @NamedEntityGraph(name = "studentWithAssignmentsWithLabProblems",
                attributeNodes = @NamedAttributeNode(value = "assignments", subgraph = "assignmentWitLabProblem"),
                subgraphs = @NamedSubgraph(name = "assignmentWitLabProblem",
                        attributeNodes = @NamedAttributeNode(value = "labProblem")))
})
@Entity
@Table(name = "students")
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "student_id"))})
@AllArgsConstructor
@Data
@Builder
public class Student extends BaseEntity<Long> {

  @Column(name = "serial_number")
  @NotEmpty
  @Pattern(regexp = "^[A-Za-z0-9]+$")
  private String serialNumber;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @Column(name = "group_number")
  @Min(0)
  private Integer groupNumber;

  @OneToMany(
      mappedBy = "student",
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  private Set<Assignment> assignments = new HashSet<>();

  public Student() {
    serialNumber = "";
    name = "";
    groupNumber = -1;
    setId(-1L);
  }

  public Student(String serialNumber, String name, int groupNumber) {
    this.serialNumber = serialNumber;
    this.name = name;
    this.groupNumber = groupNumber;
  }

  public Set<LabProblem> getLabProblems() {
    return assignments.stream().map(Assignment::getLabProblem).collect(Collectors.toSet());
  }

  public void addLabProblem(LabProblem labProblem) {
    assignments.add(new Assignment(this, labProblem));
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(int groupNumber) {
    this.groupNumber = groupNumber;
  }

  @Override
  public String toString() {
    return "Student{"
        + "id="
        + getId()
        + ", serialNumber='"
        + serialNumber
        + '\''
        + ", name='"
        + name
        + '\''
        + ", group="
        + groupNumber
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return getId().equals(student.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(serialNumber, name, groupNumber);
  }
}
