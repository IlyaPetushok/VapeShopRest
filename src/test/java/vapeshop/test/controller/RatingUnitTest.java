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
import project.vapeshop.dto.common.RatingDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class RatingUnitTest {
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
    public void testGetByIdRating() throws Exception {
        MvcResult mvcResult1=mockMvc.perform(get("/ratings/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddRating() throws Exception {
        char id=mockMvc.perform(post("/ratings").header("Authorization", token)
                        .content(asJsonString(new RatingDTOFullInfo("good", 3, new ItemDTOInfoForCatalog(1), new UserDTOAfterAuthorization(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1 = mockMvc.perform(get("/ratings/{id}", id).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(),"{\"id\":"+id+",\"comment\":\"good\",\"quantityStar\":3,\"idUser\":1}");

    }

    @Test
    public void testUpdateRating() throws Exception {
        char id=mockMvc.perform(post("/ratings").header("Authorization", token)
                        .content(asJsonString(new RatingDTOFullInfo("good", 3, new ItemDTOInfoForCatalog(1), new UserDTOAfterAuthorization(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult = mockMvc.perform(put("/ratings").header("Authorization", token)
                        .content(asJsonString(new RatingDTOFullInfo(Character.digit(id,10),"bad", 3, new ItemDTOInfoForCatalog(1), new UserDTOAfterAuthorization(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired()).andReturn();
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "{\"id\":3,\"comment\":\"bad\",\"quantityStar\":3,\"idUser\":1}");
    }


    @Test()
    public void testGetAllRating() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ratings").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteRating() throws Exception {
        char id=mockMvc.perform(post("/ratings").header("Authorization", token)
                        .content(asJsonString(new RatingDTOFullInfo("good", 3, new ItemDTOInfoForCatalog(1), new UserDTOAfterAuthorization(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult = mockMvc.perform(delete("/ratings/{id}", id).header("Authorization", token))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
