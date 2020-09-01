package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.file.Matcher;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        Employee emp = new Employee();
        emp.setId("hrishi2994");
        emp.setFirstName("hrishi");
        emp.setLastName("gadkari");
        emp.setEmail("hrishi.gad@gmail.com");
        repository.save(emp);

    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    public void findById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/employees/hrishi2994")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("hrishi.gad@gmail.com")));
    }

    @Test
    public void findNoneById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/employees/jfgfgk")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void create() throws Exception{
        Employee emp = new Employee();
        emp.setEmail("john.doe@gmail.com");
        emp.setFirstName("John");
        emp.setLastName("Doe");

        mvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(emp))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void update(){

    }

    @Test
    public void delete(){
    }


}