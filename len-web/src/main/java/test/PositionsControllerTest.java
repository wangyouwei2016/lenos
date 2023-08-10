package test;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.len.LenApplication;
import com.len.entity.SysPositions;
import com.len.service.PositionsService;
import com.len.util.SortParam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenApplication.class)
@Import(PageableHandlerMethodArgumentResolver.class)
public class PositionsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private PositionsService positionsService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetAllPositions() throws Exception {
        Page<SysPositions> page = new Page<>(1, 10);
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(true);
        orderItem.setColumn("code");
        when(positionsService.getAllPositionsByPage(page, new SysPositions(), orderItem)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/positions")
                .param("page", "0")
                .param("size", "10")
                .param("name", "")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testCreatePosition() throws Exception {
        SysPositions newPosition = new SysPositions();
        newPosition.setCode("test");
        newPosition.setName("test");
        newPosition.setDescription("test");
        newPosition.setDepartmentId("1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPosition)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("test"));
    }

    @Test
    public void testUpdatePosition() throws Exception {
        SysPositions updatedPosition = new SysPositions();
        updatedPosition.setCode("test");
        updatedPosition.setName("test");
        updatedPosition.setDescription("test");
        updatedPosition.setDepartmentId("1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/positions/{id}", "a7247990-5bdf-4279-ad71-6a8ddbd6b06b")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPosition)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("test"));
    }

    @Test
    public void testDeletePosition() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/positions/delete/{id}", "1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
