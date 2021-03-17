package ro.ubb.catalog.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.service.LabProblemService;
import ro.ubb.catalog.web.converter.LabProblemConverter;
import ro.ubb.catalog.web.dto.LabProblemDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by radu.
 */
public class LabProblemControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LabProblemController labProblemController;

    @Mock
    private LabProblemService labProblemService;

    @Mock
    private LabProblemConverter labProblemConverter;

    private LabProblem labProblem1;
    private LabProblem labProblem2;
    private LabProblemDto labProblemDto1;
    private LabProblemDto labProblemDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(labProblemController)
                .build();
        initData();
    }

    @Test
    public void getLabProblems() throws Exception {
        List<LabProblem> students = Arrays.asList(labProblem1, labProblem2);
        List<LabProblemDto> studentDtos =
                new LinkedList<>(Arrays.asList(labProblemDto1, labProblemDto2));
        when(labProblemService.getAllLabProblems()).thenReturn(students);
        when(labProblemConverter.convertModelsToDtos(students)).thenReturn(studentDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/lab_problems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.labProblems", hasSize(2)))
                .andExpect(jsonPath("$.labProblems[0].description", anyOf(is("desc1"), is("desc2"))))
                .andExpect(jsonPath("$.labProblems[1].problemNumber", anyOf(is(1), is(2))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(labProblemService, times(1)).getAllLabProblems();
        verify(labProblemConverter, times(1)).convertModelsToDtos(students);
        verifyNoMoreInteractions(labProblemService, labProblemConverter);


    }

    @Test
    public void updateLabProblem() throws Exception {
        when(labProblemService.updateLabProblem(labProblem1))
        .thenReturn(labProblem1);
        when(labProblemConverter.convertModelToDto(labProblem1)).thenCallRealMethod();
        when(labProblemConverter.convertDtoToModel(any(LabProblemDto.class))).thenReturn(labProblem1);
//        Map<String, LabProblemDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                .put("/lab_problems", labProblemDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(labProblemDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.description", is("desc1")));

        verify(labProblemConverter, times(1)).convertDtoToModel(any(LabProblemDto.class));
        verify(labProblemService, times(1)).updateLabProblem(labProblem1);
        verify(labProblemConverter, times(1)).convertModelToDto(labProblem1);
        verifyNoMoreInteractions(labProblemService, labProblemConverter);
    }

    private String toJsonString(Map<String, LabProblemDto> studentDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(studentDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(LabProblemDto studentDto) {
        try {
            return new ObjectMapper().writeValueAsString(studentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createLabProblem() throws Exception {

        when(labProblemService.addLabProblem(any(LabProblem.class)))
                .thenReturn(Optional.empty());

        when(labProblemConverter.convertModelToDto(any(LabProblem.class))).thenReturn(labProblemDto1);
        when(labProblemConverter.convertDtoToModel(any(LabProblemDto.class))).thenReturn(labProblem1);

//        Map<String, LabProblemDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/lab_problems", labProblemDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(labProblemDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.description", is("desc1")));

        verify(labProblemService, times(1)).addLabProblem(labProblem1);
        verify(labProblemConverter, times(1)).convertModelToDto(LabProblem.builder().build());
        verify(labProblemConverter, times(1)).convertDtoToModel(any(LabProblemDto.class));
        verifyNoMoreInteractions(labProblemService, labProblemConverter);


    }

    @Test
    public void deleteLabProblem() throws Exception {
        //when(studentService.deleteLabProblem(student1.getId())).thenReturn(true);

//    when(studentConverter.convertModelToDto(student1)).thenReturn(studentDto1);

        //        Map<String, LabProblemDto> studentDtoMap = new HashMap<>();
        //        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.delete("/lab_problems/{id}", labProblem1.getId())
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                    .content(toJsonString(studentDto1)))
                        .andExpect(status().isOk());

        verify(labProblemService, times(1)).deleteLabProblem(labProblem1.getId());

    }

    private void initData() {
        labProblem1 = LabProblem.builder().description("desc1").problemNumber(1).build();
        labProblem1.setId(1l);
        labProblem2 = LabProblem.builder().description("desc2").problemNumber(2).build();
        labProblem2.setId(2l);

        labProblemDto1 = createLabProblemDto(labProblem1);
        labProblemDto2 = createLabProblemDto(labProblem2);

    }

    private LabProblemDto createLabProblemDto(LabProblem labProblem) {
        LabProblemDto studentDto = LabProblemDto.builder()
                .description(labProblem.getDescription())
                .problemNumber(labProblem.getProblemNumber())
                .build();
        studentDto.setId(labProblem.getId());
        return studentDto;
    }


}