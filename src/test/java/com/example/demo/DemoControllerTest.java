package com.example.demo;


import com.example.demo.controller.DemoController;
import com.example.demo.dto.UserDto;
import com.example.demo.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testCreate() throws Exception {

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

        mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(inputDto)))  // input as JSON body
                .andExpect(status().isOk())                               // 200 OK
                .andExpect(jsonPath("$.studentId").value("uuid-1234"))    // check ID
                .andExpect(jsonPath("$.firstName").value("Iesh"))         // check firstName
                .andExpect(jsonPath("$.email").value("iesh@gmail.com"))   // check email
                .andDo(print());
    }

}
