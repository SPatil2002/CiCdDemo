package com.example.demo;


import com.example.demo.controller.DemoController;
import com.example.demo.dto.UserDto;
import com.example.demo.service.DemoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DemoService demoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreate() throws Exception {

        UserDto inputDto = new UserDto();
        inputDto.setFirstName("Iesh");
        inputDto.setLastName("Sharma");
        inputDto.setEmail("iesh@gmail.com");

        UserDto savedDto = new UserDto();
        savedDto.setStudentId("uuid-1234");
        savedDto.setFirstName("Iesh");
        savedDto.setLastName("Sharma");
        savedDto.setEmail("iesh@gmail.com");

        when(demoService.createUser(any(UserDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/api/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(inputDto)))  // input as JSON body
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value("uuid-1234"))    // check ID
                .andExpect(jsonPath("$.firstName").value("Iesh"))         // check firstName
                .andExpect(jsonPath("$.email").value("iesh@gmail.com"))   // check email
                .andDo(print());
    }

    @Test
    public void testUpdateUser() throws Exception {

        UserDto requestDto = new UserDto();
        requestDto.setFirstName("Updated");
        requestDto.setLastName("Patil");
        requestDto.setEmail("updated@gmail.com");

        UserDto responseDto = new UserDto();
        responseDto.setStudentId("101");
        responseDto.setFirstName("Updated");
        responseDto.setLastName("Patil");
        responseDto.setEmail("updated@gmail.com");

        when(demoService.updateUser(Mockito.any(UserDto.class), Mockito.eq("101")))
                .thenReturn(responseDto);

        mockMvc.perform(
                        put("/api/101")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value("101"))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"));
    }

    @Test
    void testGetUserById() throws Exception {

        UserDto student = new UserDto();
        student.setStudentId("101");
        student.setFirstName("Saiesh");
        student.setLastName("Patil");
        student.setEmail("saiesh@gmail.com");

        when(demoService.findUserById("101"))
                .thenReturn(student);

        mockMvc.perform(get("/api/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value("101"))
                .andExpect(jsonPath("$.firstName").value("Saiesh"))
                .andExpect(jsonPath("$.lastName").value("Patil"))
                .andExpect(jsonPath("$.email").value("saiesh@gmail.com"));
    }
}
