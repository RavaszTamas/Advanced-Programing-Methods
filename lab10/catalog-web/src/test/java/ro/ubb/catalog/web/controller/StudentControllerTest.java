package ro.ubb.catalog.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.StudentConverter;
import ro.ubb.catalog.web.dto.StudentDto;


import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by radu.
 */
public class StudentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private StudentConverter studentConverter;

    private Student student1;
    private Student student2;
    private StudentDto studentDto1;
    private StudentDto studentDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .build();
        initData();
    }

    @Test
    public void getStudents() throws Exception {
        List<Student> students = Arrays.asList(student1, student2);
        List<StudentDto> studentDtos =
                new LinkedList<>(Arrays.asList(studentDto1, studentDto2));
        when(studentService.getAllStudents()).thenReturn(students);
        when(studentConverter.convertModelsToDtos(students)).thenReturn(studentDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.students", hasSize(2)))
                .andExpect(jsonPath("$.students[0].name", anyOf(is("s1"), is("s2"))))
                .andExpect(jsonPath("$.students[1].serialNumber", anyOf(is("sn1"), is("sn2"))))
                .andExpect(jsonPath("$.students[1].groupNumber", anyOf(is(1), is(2))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(studentService, times(1)).getAllStudents();
        verify(studentConverter, times(1)).convertModelsToDtos(students);
        verifyNoMoreInteractions(studentService, studentConverter);


    }

    @Test
    public void updateStudent() throws Exception {
        when(studentService.updateStudent(student1))
        .thenReturn(student1);
        when(studentConverter.convertModelToDto(student1)).thenCallRealMethod();
        when(studentConverter.convertDtoToModel(any(StudentDto.class))).thenReturn(student1);
//        Map<String, StudentDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                .put("/students", studentDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(studentDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("s1")));

        verify(studentConverter, times(1)).convertDtoToModel(any(StudentDto.class));
        verify(studentService, times(1)).updateStudent(student1);
        verify(studentConverter, times(1)).convertModelToDto(student1);
        verifyNoMoreInteractions(studentService, studentConverter);
    }

    private String toJsonString(Map<String, StudentDto> studentDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(studentDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(StudentDto studentDto) {
        try {
            return new ObjectMapper().writeValueAsString(studentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createStudent() throws Exception {

        when(studentService.addStudent(any(Student.class)))
                .thenReturn(Optional.empty());

        when(studentConverter.convertModelToDto(any(Student.class))).thenReturn(studentDto1);
        when(studentConverter.convertDtoToModel(any(StudentDto.class))).thenReturn(student1);

//        Map<String, StudentDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/students", studentDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(studentDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("s1")));

        verify(studentService, times(1)).addStudent(student1);
        verify(studentConverter, times(1)).convertModelToDto(Student.builder().build());
        verify(studentConverter, times(1)).convertDtoToModel(any(StudentDto.class));
        verifyNoMoreInteractions(studentService, studentConverter);


    }

    @Test
    public void deleteStudent() throws Exception {
        //when(studentService.deleteStudent(student1.getId())).thenReturn(true);

//    when(studentConverter.convertModelToDto(student1)).thenReturn(studentDto1);

        //        Map<String, StudentDto> studentDtoMap = new HashMap<>();
        //        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.delete("/students/{id}", student1.getId())
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                    .content(toJsonString(studentDto1)))
                        .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(student1.getId());

    }

    private void initData() {
        student1 = Student.builder().serialNumber("sn1").groupNumber(1).name("s1").build();
        student1.setId(1l);
        student2 = Student.builder().serialNumber("sn2").groupNumber(2).name("s2").build();
        student2.setId(2l);

        studentDto1 = createStudentDto(student1);
        studentDto2 = createStudentDto(student2);

    }

    private StudentDto createStudentDto(Student student) {
        StudentDto studentDto = StudentDto.builder()
                .serialNumber(student.getSerialNumber())
                .name(student.getName())
                .groupNumber(student.getGroupNumber())
                .build();
        studentDto.setId(student.getId());
        return studentDto;
    }


}