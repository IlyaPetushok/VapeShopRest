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
import project.vapeshop.dto.common.OrderDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.dto.user.UserDTOForCommon;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.security.JwtFilter;
import vapeshop.test.config.H2Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class, SpringApplicationConfig.class})
@WebAppConfiguration
public class OrderUnitTest {
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
    public void testGetByIdOrder() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(get("/orders/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testAddOrder() throws Exception {
        List<ItemDTOInfoForCatalog> itemList = new ArrayList<>();
        itemList.add(new ItemDTOInfoForCatalog(1));
        char id=mockMvc.perform(post("/orders").header("Authorization", token)
                        .content(asJsonString(new OrderDTOFullInfo(new Date(2023, Calendar.FEBRUARY,26), StatusOrder.Sent,150.0,new UserDTOForCommon(1),itemList)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString().charAt(6);
        MvcResult mvcResult1 = mockMvc.perform(get("/orders/{id}", id).header("Authorization", token)).andReturn();
        Assertions.assertFalse(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        List<ItemDTOInfoForCatalog> itemList = new ArrayList<>();
        itemList.add(new ItemDTOInfoForCatalog(1));
        mockMvc.perform(put("/orders").header("Authorization", token)
                        .content(asJsonString(new OrderDTOFullInfo(1,new Date(2023, Calendar.FEBRUARY,26),StatusOrder.Accepted,150.0,new UserDTOForCommon(1),itemList)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUpgradeRequired());
        MvcResult mvcResult1 = mockMvc.perform(get("/orders/{id}", "1").header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(), "{\"id\":1,\"date\":61635502800000,\"status\":\"Accepted\",\"price\":150.0,\"user\":{\"id\":1,\"surname\":\"Петушок\",\"name\":\"Илья\",\"patronymic\":\"Александрович\"},\"items\":[{\"id\":2,\"photo\":\"path\\\\photo2\",\"name\":\"Испаритель Charon\"},{\"id\":3,\"photo\":\"path\\\\photo3\",\"name\":\"IJoy Captain 226\"}]}");
    }


    @Test()
    public void testGetAllOrder() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/orders").header("Authorization", token)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test()
    public void testDeleteOrder() throws Exception {
        List<ItemDTOInfoForCatalog> itemList = new ArrayList<>();
        itemList.add(new ItemDTOInfoForCatalog(1));
        mockMvc.perform(post("/orders").header("Authorization", token)
                        .content(asJsonString(new OrderDTOFullInfo(new Date(2023, Calendar.FEBRUARY,26),StatusOrder.Arrived,150.0,new UserDTOForCommon(1),itemList)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        MvcResult mvcResult1 = mockMvc.perform(delete("/orders/{id}", "2").header("Authorization", token)).andReturn();
        Assertions.assertEquals(mvcResult1.getResponse().getContentAsString(), "true");
    }

    public static String asJsonString(final Object obj) {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(obj));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
