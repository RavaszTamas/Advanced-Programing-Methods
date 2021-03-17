package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.StudentsDto;

/** Created by radu. */
public class ClientApp {
  public static final String URL = "http://localhost:8080/api/students";

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext("ro.ubb.catalog.client.config");
//    Console console = context.getBean(Console.class);
//    console.run();
  }

  private static void printAllStudents(RestTemplate restTemplate) {
    StudentsDto allStudents = restTemplate.getForObject(URL, StudentsDto.class);
    System.out.println(allStudents);
  }
}
