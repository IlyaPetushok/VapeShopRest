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
import project.vapeshop.dto.product.ItemDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.product.VaporizerDTOFullInfo;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.entity.product.Category;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class VaporizerUnitTest {
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
    public void testGetByIdVaporizer() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                get("/vaporizers/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddVaporizer() throws Exception {
        char id=mockMvc.perform(post("/items").header("Authorization", token)
                        .content(asJsonString(new ItemDTOFullInfo("photo4", "HotSpot BubleGum", new Category("Испарители,Картриджы,Койлы"), new BigDecimal(Double.toString(23.0)), 15)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult = mockMvc.perform(post("/vaporizers").header("Authorization", token)
                        .content(asJsonString(new VaporizerDTOFullInfo(0.6,"испаритель",new ItemDTOInfoForCatalog(Character.digit(id,10)))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char idVape = mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1 = mockMvc.perform(get("/vaporizers/{id}", idVape).header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateVape() throws Exception {
        mockMvc.perform(put("/vaporizers").header("Authorization", token)
                        .content(asJsonString(new VaporizerDTOFullInfo(1,1.8,"испаритель",new ItemDTOInfoForCatalog(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired());
        MvcResult mvcResult1 = mockMvc.perform(get("/vaporizers/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(), "{\"id\":1,\"resistance\":1.8,\"type\":\"испаритель\",\"itemForVaporizer\":{\"id\":1,\"photo\":\"path\\\\photo1\",\"name\":\"Мишки 3в1\"}}");
    }


    @Test()
    public void testGetAllVaporizer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/vaporizers").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteVaporizer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/vaporizers").header("Authorization", token)
                        .content(asJsonString(new VaporizerDTOFullInfo(0.6,"испаритель",new ItemDTOInfoForCatalog(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char idVaporizer = mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1 = mockMvc.perform(delete("/vaporizers/{id}",idVaporizer).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(), "true");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
