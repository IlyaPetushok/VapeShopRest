package vapeshop.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.vapeshop.config.SpringApplicationConfig;
import project.vapeshop.dto.user.PrivilegesDTO;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class PrivilegeUnitTest {
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilter(jwtFilter).dispatchOptions(true).build();
        MvcResult mvcResult = mockMvc.perform(post("/authorization")
                .content(asJsonString(new UserDTOForAuthorization("login", "password")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        this.token = "Bearer " + mvcResult.getResponse().getContentAsString();
    }


    @Test
    public void testGetByIdRole() throws Exception {
        MvcResult mvcResult1=mockMvc.perform(get("/privileges/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddRole() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/privileges").header("Authorization", token)
                        .content(asJsonString(new PrivilegesDTO("Add support")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char id=mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1=mockMvc.perform(get("/privileges/{id}", id).header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testUpdateRole() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/privileges").header("Authorization", token)
                        .content(asJsonString(new PrivilegesDTO("Add support")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char id=mvcResult.getResponse().getContentAsString().charAt(6);
        mockMvc.perform(put("/privileges").header("Authorization", token)
                        .content(asJsonString(new PrivilegesDTO(Character.digit(id,10),"Delete user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired());
        MvcResult mvcResult1=mockMvc.perform(get("/privileges/{id}", id).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(),"{\"id\":"+id+",\"name\":\"Delete user\"}");
    }


    @Test()
    public void testGetAllRole() throws Exception {
        MvcResult mvcResult=mockMvc.perform(get("/privileges").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteRole() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/privileges").header("Authorization", token)
                        .content(asJsonString(new PrivilegesDTO("Add support")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char id=mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1=mockMvc.perform(delete("/privileges/{id}",id).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(),"true");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
