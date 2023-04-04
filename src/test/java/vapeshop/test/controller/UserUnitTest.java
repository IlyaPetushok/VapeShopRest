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
import project.vapeshop.dto.user.RoleDTO;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.dto.user.UserDTOForRegistration;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class UserUnitTest {

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
    public void testGetByIdUser() throws Exception {
        MvcResult mvcResult1=mockMvc.perform(get("/users/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddUser() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/users").header("Authorization", token)
                        .content(asJsonString(new UserDTOForRegistration("Petushok","Ilya","Aleksandrovich","login22222","pass","a332222@mail",new RoleDTO(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char id=mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1=mockMvc.perform(get("/users/{id}", id)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(put("/users").header("Authorization", token)
                        .content(asJsonString(new UserDTOForRegistration(1,"Pet","Ilsya","Aleksa","login","password","a212313432532@mail",new RoleDTO(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired());
        MvcResult mvcResult1=mockMvc.perform(get("/users/{id}",1).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(),"{\"id\":1,\"surname\":\"Pet\",\"name\":\"Ilsya\",\"patronymic\":\"Aleksa\",\"login\":\"login\",\"password\":\"password\",\"mail\":\"a212313432532@mail\",\"role\":{\"id\":1,\"name\":\"ROLE_USER\"}}");
    }


    @Test()
    public void testGetAlUser() throws Exception {
        MvcResult mvcResult=mockMvc.perform(get("/users").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteUser() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/users").header("Authorization", token)
                        .content(asJsonString(new UserDTOForRegistration("Pet","Ilsya","Aleksa","log1111","pass","a11+2@mail",new RoleDTO(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char id=mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1=mockMvc.perform(delete("/users/{id}",id).header("Authorization", token)).andReturn();
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
