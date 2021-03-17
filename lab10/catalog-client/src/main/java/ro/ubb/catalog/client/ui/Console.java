//package ro.ubb.catalog.client.ui;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//import ro.ubb.catalog.core.model.Sort;
//import ro.ubb.catalog.core.model.exceptions.ClassReflectionException;
//import ro.ubb.catalog.core.model.exceptions.RepositoryException;
//import ro.ubb.catalog.core.model.exceptions.ValidatorException;
//import ro.ubb.catalog.web.dto.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//
//@Component
//public class Console {
//  private HashMap<String, Runnable> dictionaryOfCommands;
//
//  private final String StudentURL = "http://localhost:8080/api/students";
//  private final String LabProblemURL = "http://localhost:8080/api/lab_problems";
//  private final String AssignmentURL = "http://localhost:8080/api/assignments";
//
//  @Autowired private RestTemplate restTemplate;
//
//  private void initDictionaryOfCommands() {
//    dictionaryOfCommands = new HashMap<>();
//    dictionaryOfCommands.put("add student", this::addStudent);
//    dictionaryOfCommands.put("get student", this::getStudentById);
//    dictionaryOfCommands.put("get lab problem", this::getLabProblemById);
//    dictionaryOfCommands.put("get assignment", this::getAssignmentById);
//    dictionaryOfCommands.put("print students", this::printStudents);
//    dictionaryOfCommands.put("print students sorted", this::printStudentsSorted);
//    dictionaryOfCommands.put("print assignments sorted", this::printAssignmentsSorted);
//    dictionaryOfCommands.put("print lab problems sorted", this::printLabProblemsSorted);
//    dictionaryOfCommands.put("add lab problem", this::addLabProblem);
//    dictionaryOfCommands.put("print lab problems", this::printLabProblems);
//    dictionaryOfCommands.put("update lab problem", this::updateLabProblem);
//    dictionaryOfCommands.put("delete lab problem", this::deleteLabProblem);
//    dictionaryOfCommands.put("filter lab problems", this::filterLabProblemsByProblemNumber);
//    dictionaryOfCommands.put("update student", this::updateStudent);
//    dictionaryOfCommands.put("delete student", this::deleteStudent);
//    dictionaryOfCommands.put("filter students", this::filterStudentsByGroup);
//    dictionaryOfCommands.put("add assignment", this::addAssignment);
//    dictionaryOfCommands.put("print assignments", this::printAssignments);
//    dictionaryOfCommands.put("delete assignment", this::deleteAssignment);
//    dictionaryOfCommands.put("update assignment", this::updateAssignment);
//    dictionaryOfCommands.put("max mean student", this::greatestMeanOfStudent);
//    dictionaryOfCommands.put("lab problem most", this::labProblemMostAssigned);
//    dictionaryOfCommands.put("avg grade", this::averageGrade);
//    dictionaryOfCommands.put("student problems", this::studentProblems);
//    dictionaryOfCommands.put("exit", () -> System.exit(0));
//  }
//  /** ro.ubb.UI method for printing the console menu */
//  private void printMenu() {
//    String menu = "";
//    menu += "Menu options:" + System.lineSeparator();
//    menu += "- add student" + System.lineSeparator();
//    menu += "- get student" + System.lineSeparator();
//    menu += "- print students" + System.lineSeparator();
//    menu += "- print students sorted" + System.lineSeparator();
//    menu += "- update student" + System.lineSeparator();
//    menu += "- delete student" + System.lineSeparator();
//    menu += "- filter students [by group]" + System.lineSeparator();
//    menu += "- add lab problem" + System.lineSeparator();
//    menu += "- get lab problem" + System.lineSeparator();
//    menu += "- print lab problems" + System.lineSeparator();
//    menu += "- print lab problems sorted" + System.lineSeparator();
//    menu += "- update lab problem" + System.lineSeparator();
//    menu += "- delete lab problem" + System.lineSeparator();
//    menu += "- filter lab problems [by number]" + System.lineSeparator();
//    menu += "- add assignment" + System.lineSeparator();
//    menu += "- get assignment" + System.lineSeparator();
//    menu += "- print assignments" + System.lineSeparator();
//    menu += "- print assignments sorted" + System.lineSeparator();
//    menu += "- update assignment" + System.lineSeparator();
//    menu += "- delete assignment" + System.lineSeparator();
//    menu += "- max mean student" + System.lineSeparator();
//    menu += "- lab problem most" + System.lineSeparator();
//    menu += "- avg grade" + System.lineSeparator();
//    //    menu += "- student problems" + System.lineSeparator();
//    menu += "- exit" + System.lineSeparator();
//    System.out.println(menu);
//  }
//
//  /** Take specific user input and print server's answer to the call getAssignmentById call. */
//  private void getAssignmentById() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  AssignmentDto dto =
//                      restTemplate.getForObject(AssignmentURL + "/{id}", AssignmentDto.class, id);
//                  if (dto.getId() == null) {
//                    return "Assignment with that id not in the database";
//                  }
//                  return dto.toString();
//                } catch (IllegalArgumentException ex) {
//                  return ex.getMessage();
//                } catch (NullPointerException e) {
//                  return "Assignment with that id not in the database";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//
//  private void getLabProblemById() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  LabProblemDto dto =
//                      restTemplate.getForObject(LabProblemURL + "/{id}", LabProblemDto.class, id);
//                  if (dto.getId() == null) {
//                    return "Lab problem with that id not in the database";
//                  }
//                  return dto.toString();
//                } catch (IllegalArgumentException ex) {
//                  return ex.getMessage();
//                } catch (NullPointerException e) {
//                  return "Lab problem with that id not in the database";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//
//  private void getStudentById() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  StudentDto student =
//                      restTemplate.getForObject(StudentURL + "/{id}", StudentDto.class, id);
//                  if (student.getId() == null) {
//                    return "No student with that id!";
//                  }
//                  return student.toString();
//                } catch (IllegalArgumentException e) {
//                  return e.getMessage();
//                } catch (NullPointerException e) {
//                  return "No student with that id!";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//
//  private void printLabProblemsSorted() {
//    System.out.println("Sort by criteria: <order {ASC/ DESC} column-name>. 'done' when done");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Sort sort = null;
//    try {
//      while (true) {
//        System.out.println("order: ");
//        String order = input.readLine().strip();
//        if (order.equals("done")) break;
//        Sort.Direction sortingDirection;
//        if (order.equals("ASC")) {
//          sortingDirection = Sort.Direction.ASC;
//        } else if (order.equals("DESC")) {
//          sortingDirection = Sort.Direction.DESC;
//        } else {
//          System.out.println("wrong input!");
//          return;
//        }
//        System.out.println("column-name:");
//        String columnName = input.readLine().strip();
//        if (sort == null) {
//          sort = new Sort(sortingDirection, columnName);
//          sort.setClassName("LabProblem");
//        } else {
//          sort = sort.and(new Sort(sortingDirection, columnName));
//        }
//      }
//      Sort finalSort = sort;
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  List<SortColumnDto> sortColumnDtoSet = new LinkedList<>();
//                  finalSort
//                      .getSortingChain()
//                      .forEach(
//                          elem ->
//                              sortColumnDtoSet.add(
//                                  SortColumnDto.builder()
//                                      .column(elem.getValue())
//                                      .direction(elem.getKey().name())
//                                      .build()));
//
//                  return restTemplate
//                      .postForObject(
//                          AssignmentURL + "/sorted",
//                          SortDto.builder().sortColumnDtoList(sortColumnDtoSet).build(),
//                          LabProblemsDto.class)
//                      .getLabProblems().stream()
//                      .map(LabProblemDto::toString)
//                      .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//                } catch (ClassReflectionException | NullPointerException ex) {
//                  return "Invalid sorting criterias";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//    } catch (IOException e) {
//      System.out.println("Invalid input!");
//    } catch (ClassReflectionException e) {
//      System.err.println(e.getMessage());
//    }
//  }
//
//  private void printAssignmentsSorted() {
//    System.out.println("Sort by criteria: <order {ASC/ DESC} column-name>. 'done' when done");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Sort sort = null;
//    try {
//      while (true) {
//        System.out.println("order: ");
//        String order = input.readLine().strip();
//        if (order.equals("done")) break;
//        Sort.Direction sortingDirection;
//        if (order.equals("ASC")) {
//          sortingDirection = Sort.Direction.ASC;
//        } else if (order.equals("DESC")) {
//          sortingDirection = Sort.Direction.DESC;
//        } else {
//          System.out.println("wrong input!");
//          return;
//        }
//        System.out.println("column-name:");
//        String columnName = input.readLine().strip();
//        if (sort == null) {
//          sort = new Sort(sortingDirection, columnName);
//          sort.setClassName("Assignment");
//        } else {
//          sort = sort.and(new Sort(sortingDirection, columnName));
//        }
//      }
//      Sort finalSort = sort;
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  List<SortColumnDto> sortColumnDtoSet = new LinkedList<>();
//                  finalSort
//                      .getSortingChain()
//                      .forEach(
//                          elem ->
//                              sortColumnDtoSet.add(
//                                  SortColumnDto.builder()
//                                      .column(elem.getValue())
//                                      .direction(elem.getKey().name())
//                                      .build()));
//
//                  return restTemplate
//                      .postForObject(
//                          AssignmentURL + "/sorted",
//                          SortDto.builder().sortColumnDtoList(sortColumnDtoSet).build(),
//                          AssignmentsDto.class)
//                      .getAssignments().stream()
//                      .map(AssignmentDto::toString)
//                      .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//                } catch (ClassReflectionException | NullPointerException ex) {
//                  return "Invalid sorting criterias";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//    } catch (IOException e) {
//      System.out.println("Invalid input!");
//    } catch (ClassReflectionException e) {
//      System.err.println(e.getMessage());
//    }
//  }
//
//  private void printStudentsSorted() {
//    System.out.println("Sort by criteria: <order {ASC/ DESC} column-name>. 'done' when done");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Sort sort = null;
//    try {
//      while (true) {
//        System.out.println("order: ");
//        String order = input.readLine().strip();
//        if (order.equals("done")) break;
//        Sort.Direction sortingDirection;
//        if (order.equals("ASC")) {
//          sortingDirection = Sort.Direction.ASC;
//        } else if (order.equals("DESC")) {
//          sortingDirection = Sort.Direction.DESC;
//        } else {
//          System.out.println("wrong input!");
//          return;
//        }
//        System.out.println("column-name:");
//        String columnName = input.readLine().strip();
//        if (sort == null) {
//          sort = new Sort(sortingDirection, columnName);
//          sort.setClassName("Student");
//        } else {
//          sort = sort.and(new Sort(sortingDirection, columnName));
//        }
//      }
//
//      Sort finalSort = sort;
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  List<SortColumnDto> sortColumnDtoSet = new LinkedList<>();
//                  finalSort
//                      .getSortingChain()
//                      .forEach(
//                          elem ->
//                              sortColumnDtoSet.add(
//                                  SortColumnDto.builder()
//                                      .column(elem.getValue())
//                                      .direction(elem.getKey().name())
//                                      .build()));
//
//                  return restTemplate
//                      .postForObject(
//                          StudentURL + "/sorted",
//                          SortDto.builder().sortColumnDtoList(sortColumnDtoSet).build(),
//                          StudentsDto.class)
//                      .getStudents().stream()
//                      .map(StudentDto::toString)
//                      .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//                } catch (ClassReflectionException | NullPointerException ex) {
//                  return "Invalid sorting criterias";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException e) {
//      System.out.println("Invalid input!");
//    } catch (ClassReflectionException e) {
//      System.err.println(e.getMessage());
//    }
//  }
//
//  private void studentProblems() {
//
//    //        CompletableFuture.supplyAsync(
//    //                () -> {
//    //                    //              Student emptyStudent = new Student();
//    //                    try {
//    //                        return assignmentService.studentAssignedProblems().entrySet().stream()
//    //                                .map(
//    //                                        entry ->
//    //                                                entry.getKey()
//    //                                                        + System.lineSeparator()
//    //                                                        + entry.getValue().stream()
//    //                                                        .map(LabProblem::toString)
//    //                                                        .reduce((s1, s2) -> s1 +
//    // System.lineSeparator() + s2)
//    //                                                        .orElse(""))
//    //                                .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//    //                    } catch (NullPointerException e) {
//    //                        return e.getMessage();
//    //                    }
//    //                })
//    //                .thenAcceptAsync(System.out::println);
//  }
//  /*
//  private void greatestMeanOfGroup() {
//    Optional<AbstractMap.SimpleEntry<Integer, Double>> greatestMean = assignmentService.groupWithGreatestMean();
//    if (greatestMean.isPresent()) {
//      System.out.println(
//          "The greatest mean is of group id = "
//              + greatestMean.get().getKey()
//              + ": "
//              + greatestMean.get().getValue());
//    } else {
//      System.err.println("no students or assignments");
//    }
//  }
//  */
//  private void averageGrade() {
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return "The mean of all assignments is "
//                    + restTemplate
//                        .getForObject(AssignmentURL + "/averageGrade", Double.class)
//                        .toString();
//              } catch (NullPointerException e) {
//                return e.getMessage() + " assignments";
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  private void labProblemMostAssigned() {
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                MostAssignedDto result =
//                    restTemplate.getForObject(
//                        AssignmentURL + "/mostAssigned", MostAssignedDto.class);
//                return "lab problem most assigned id: "
//                    + result.getId()
//                    + " - "
//                    + result.getCount()
//                    + "times";
//              } catch (NullPointerException e) {
//                return e.getMessage() + "\nno lab problems assigned";
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  private void greatestMeanOfStudent() {
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                GreatestMeanDto result =
//                    restTemplate.getForObject(
//                        AssignmentURL + "/greatestMean", GreatestMeanDto.class);
//                return "The greatest mean is of student id = "
//                    + result.getId()
//                    + ": "
//                    + result.getMean();
//              } catch (NullPointerException e) {
//                return e.getMessage() + "\nno students or assignments";
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  /** ro.ubb.Main loop of the user interface */
//  public void run() {
//    initDictionaryOfCommands();
//    while (true) {
//      printMenu();
//      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//      try {
//        String inputString = input.readLine();
//        dictionaryOfCommands.get(inputString).run();
//      } catch (ValidatorException ex) {
//        ex.printStackTrace();
//        System.out.println(ex.getMessage());
//      } catch (IOException ex) {
//        System.out.println("Error with input!");
//      } catch (NullPointerException ex) {
//        System.out.println("Not a vaild comand");
//      }
//    }
//  }
//
//  private void printAssignments() {
//
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return restTemplate.getForObject(AssignmentURL, AssignmentsDto.class)
//                    .getAssignments().stream()
//                    .map(AssignmentDto::toString)
//                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//              } catch (NullPointerException e) {
//                return e.getMessage().substring(e.getMessage().indexOf(" ") + 1);
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  private void addAssignment() {
//    System.out.println("Read assignment {id, studentId, labProblemId. grade}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter studentId: ");
//      long studentId = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter labProblemId: ");
//      long labProblemId = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter grade: ");
//      int grade = Integer.parseInt(input.readLine().strip());
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  AssignmentDto assignmentDto =
//                      AssignmentDto.builder()
//                          .grade(grade)
//                          .studentID(studentId)
//                          .labProblemID(labProblemId)
//                          .build();
//                  assignmentDto.setId(id);
//                  AssignmentDto saveResult =
//                      restTemplate.postForObject(AssignmentURL, assignmentDto, AssignmentDto.class);
//                  if (saveResult.getId() == null) return "Assignment added";
//                  return "Assignment not added";
//                } catch (ValidatorException | RepositoryException | NullPointerException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (ValidatorException e) {
//      System.err.println(e.getMessage());
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    } catch (RepositoryException ex) {
//      System.out.println("Invalid assignment, wrong student or lab problem ID");
//    }
//  }
//
//  /** ro.ubb.UI method for adding a student */
//  private void addStudent() {
//    System.out.println("Read student {id,serialNumber, name, group}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter serial number: ");
//      String serialNumber = input.readLine().strip();
//      System.out.println("Enter name: ");
//      String name = input.readLine().strip();
//      System.out.println("Enter group: ");
//      Integer group = Integer.parseInt(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  StudentDto studentDto =
//                      StudentDto.builder()
//                          .serialNumber(serialNumber)
//                          .groupNumber(group)
//                          .name(name)
//                          .build();
//                  studentDto.setId(id);
//                  StudentDto saveResult =
//                      restTemplate.postForObject(StudentURL, studentDto, StudentDto.class);
//                  if (saveResult.getId() == null) return "Student added";
//                  return "Student not added";
//                } catch (ValidatorException | NullPointerException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (ValidatorException ex) {
//      System.out.println(ex.getMessage());
//    } catch (IOException | NumberFormatException e) {
//      System.out.println("invalid input");
//    }
//  }
//  /** ro.ubb.UI method for printing all students */
//  private void printStudents() {
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return restTemplate.getForObject(StudentURL, StudentsDto.class).getStudents()
//                    .stream()
//                    .map(StudentDto::toString)
//                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//              } catch (NullPointerException e) {
//                return "Getting students error";
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//  /** ro.ubb.UI method for adding a lab problem */
//  private void addLabProblem() {
//    System.out.println("Read lab problem {id, problem number, description}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter problem number: ");
//      int problemNumber = Integer.parseInt(input.readLine().strip());
//      System.out.println("Enter description: ");
//      String description = input.readLine().strip();
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  LabProblemDto labProblemDto =
//                      LabProblemDto.builder()
//                          .description(description)
//                          .problemNumber(problemNumber)
//                          .build();
//                  labProblemDto.setId(id);
//                  LabProblemDto saveResult =
//                      restTemplate.postForObject(LabProblemURL, labProblemDto, LabProblemDto.class);
//                  if (saveResult.getId() == null) return "Lab problem added";
//                  return "Lab problem not added";
//                } catch (ValidatorException | NullPointerException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (ValidatorException e) {
//      System.err.println(e.getMessage());
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//  /** ro.ubb.UI method for printing all lab problems */
//  private void printLabProblems() {
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return restTemplate.getForObject(LabProblemURL, LabProblemsDto.class)
//                    .getLabProblems().stream()
//                    .map(LabProblemDto::toString)
//                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//              } catch (NullPointerException e) {
//                return e.getMessage();
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//  /** ro.ubb.UI method update a lab problem */
//  private void updateLabProblem() {
//    System.out.println("Read lab problem {id, problem number, description}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter problem number: ");
//      int problemNumber = Integer.parseInt(input.readLine().strip());
//      System.out.println("Enter description: ");
//      String description = input.readLine().strip();
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  LabProblemDto labProblemDto =
//                      LabProblemDto.builder()
//                          .problemNumber(problemNumber)
//                          .description(description)
//                          .build();
//                  labProblemDto.setId(id);
//                  restTemplate.put(LabProblemURL, labProblemDto);
//                  return "Update method completed";
//                } catch (ValidatorException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (ValidatorException e) {
//      System.err.println(e.getMessage());
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//  /** ro.ubb.UI method deletes a lab problem */
//  private void deleteLabProblem() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    Long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  restTemplate.delete(LabProblemURL + "/{id}", id);
//                  return "Delete method completed";
//                } catch (RestClientException ex) {
//                  return "Delete failed";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//  /** ro.ubb.UI method filters lab problems by problem number */
//  private void filterLabProblemsByProblemNumber() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    int problemNumber;
//    try {
//      System.out.println("Enter problem number: ");
//      problemNumber = Integer.parseInt(input.readLine().strip());
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//      return;
//    }
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return restTemplate
//                    .getForObject(
//                        LabProblemURL + "/filter/{problemNumber}",
//                        LabProblemsDto.class,
//                        problemNumber)
//                    .getLabProblems().stream()
//                    .map(LabProblemDto::toString)
//                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//              } catch (IllegalArgumentException ex) {
//                return ex.getMessage();
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  /** ro.ubb.UI method update a student */
//  private void updateStudent() {
//    System.out.println("Update student {id,serialNumber, name, group}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      Long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter serial number: ");
//      String serialNumber = input.readLine().strip();
//      System.out.println("Enter name: ");
//      String name = input.readLine().strip();
//      System.out.println("Enter group: ");
//      Integer group = Integer.parseInt(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  StudentDto studentDto =
//                      StudentDto.builder()
//                          .serialNumber(serialNumber)
//                          .name(name)
//                          .groupNumber(group)
//                          .build();
//                  studentDto.setId(id);
//                  restTemplate.put(StudentURL, studentDto);
//                  return "Update method finished";
//                } catch (ValidatorException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//    } catch (ValidatorException ex) {
//      // ex.printStackTrace();
//      System.out.println(ex.getMessage());
//    } catch (IOException | NumberFormatException e) {
//      //      e.printStackTrace();
//      System.err.println("invalid input");
//    }
//  }
//  /** ro.ubb.UI method deletes a student */
//  private void deleteStudent() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  restTemplate.delete(StudentURL + "/{id}", id);
//                  return "Delete method finished";
//                } catch (RestClientException ex) {
//                  return "Delete failed";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    }
//  }
//  /** ro.ubb.UI method filters students by group number */
//  private void filterStudentsByGroup() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    int groupNumber;
//    try {
//      System.out.println("Enter group number: ");
//      groupNumber = Integer.parseInt(input.readLine().strip());
//
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//      return;
//    }
//    CompletableFuture.supplyAsync(
//            () -> {
//              try {
//                return restTemplate
//                    .getForObject(
//                        StudentURL + "/filter/{groupNumber}", StudentsDto.class, groupNumber)
//                    .getStudents().stream()
//                    .map(StudentDto::toString)
//                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
//              } catch (IllegalArgumentException ex) {
//                return ex.getMessage();
//              }
//            })
//        .thenAcceptAsync(System.out::println);
//  }
//
//  private void updateAssignment() {
//    System.out.println("Update assignment {id, studentId, labProblemId, grade}");
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    try {
//      System.out.println("Enter id: ");
//      long id = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter studentId: ");
//      long studentId = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter labProblemId: ");
//      long labProblemId = Long.parseLong(input.readLine().strip());
//      System.out.println("Enter grade: ");
//      int grade = Integer.parseInt(input.readLine().strip());
//
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  AssignmentDto assignmentDto =
//                      AssignmentDto.builder()
//                          .studentID(studentId)
//                          .labProblemID(labProblemId)
//                          .grade(grade)
//                          .build();
//                  assignmentDto.setId(id);
//                  restTemplate.put(AssignmentURL, assignmentDto);
//                  return "Update method finished";
//                } catch (ValidatorException ex) {
//                  return ex.getMessage();
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//    } catch (ValidatorException e) {
//      System.err.println(e.getMessage());
//    } catch (IOException | NumberFormatException ex) {
//      System.out.println("Invalid input!");
//    } catch (RepositoryException ex) {
//      System.out.println("Invalid assignment, wrong student or lab problem ID");
//    }
//  }
//
//  private void deleteAssignment() {
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//    long id;
//    try {
//      System.out.println("Enter id: ");
//      id = Long.parseLong(input.readLine().strip());
//      CompletableFuture.supplyAsync(
//              () -> {
//                try {
//                  restTemplate.delete(AssignmentURL + "/{id}", id);
//                  return "Delete method finished";
//                } catch (RestClientException ex) {
//                  return "Delete failed";
//                }
//              })
//          .thenAcceptAsync(System.out::println);
//    } catch (IOException | NumberFormatException e) {
//      System.err.println("bad input");
//    }
//  }
//}
