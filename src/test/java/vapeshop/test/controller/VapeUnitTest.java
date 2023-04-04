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
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.product.VapeDTOFullInfo;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class VapeUnitTest {
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
    public void testGetByIdVape() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(get("/vapes/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddVape() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/vapes").header("Authorization", token)
                        .content(asJsonString(new VapeDTOFullInfo(120, 22450, "Мод", new ItemDTOInfoForCatalog(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char idVape = mvcResult.getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1 = mockMvc.perform(get("/vapes/{id}", idVape).header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateVape() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/vapes").header("Authorization", token)
                        .content(asJsonString(new VapeDTOFullInfo(120, 22450, "Мод", new ItemDTOInfoForCatalog(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        char idVape = mvcResult.getResponse().getContentAsString().charAt(6);
        mockMvc.perform(put("/vapes").header("Authorization", token)
                        .content(asJsonString(new VapeDTOFullInfo(Character.digit(idVape,10),120, 22450, "Мод", new ItemDTOInfoForCatalog(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired());
        MvcResult mvcResult1 = mockMvc.perform(get("/vapes/{id}", idVape).header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(), "{\"id\":"+idVape+",\"power\":120,\"battery\":22450,\"type\":\"Мод\",\"itemForVape\":{\"id\":1,\"photo\":\"photo4\",\"name\":\"HotSpot BubleGum Update\"}}");
    }


    @Test()
    public void testGetAllVape() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/vapes").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteCategory() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(delete("/vapes/{id}", "1").header("Authorization", token)).andReturn();
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
