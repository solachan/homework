package com.xiepuxin.incident;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiepuxin.incident.controller.IncidentController;
import com.xiepuxin.incident.dto.IncidentDTO;
import com.xiepuxin.incident.entity.Incident;
import com.xiepuxin.incident.exception.ResourceNotFoundException;
import com.xiepuxin.incident.model.R;
import com.xiepuxin.incident.service.IncidentService;
import com.xiepuxin.incident.service.impl.IncidentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = IncidentController.class)
@SpringBootTest(classes = IncidentApplication.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IncidentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // 测试接口用
public class IncidentApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IncidentServiceImpl incidentService;

    @Mock
    private PagedResourcesAssembler<Incident> assembler;

    @InjectMocks
    @Autowired
    private IncidentController incidentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQueryByPage() throws Exception {
        // 准备测试数据
        List<Incident> incidents = Arrays.asList(
                new Incident(1L,"type 1", "Title 1", "Description 1",0,new Date()),
                new Incident(2L,"type 2", "Title 2", "Description 2",1,new Date())
        );
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC,"id");
        Page<Incident> page = new PageImpl<>(incidents, pageable, incidents.size());

        PagedModel<EntityModel<Incident>> pagedModel = assembler.toModel(page);

        // 模拟服务方法的行为
        doReturn(page).when(incidentService).queryByPage(pageable);
//        when(incidentService.queryByPage(pageable)).thenReturn(page);

        doReturn(pagedModel).when(assembler).toModel(page);
//        when(assembler.toModel(page)).thenReturn(pagedModel);

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=0&size=10&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 执行请求并验证响应
        mockMvc.perform(get("/incident?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Title 1"))
                .andExpect(jsonPath("$.data.content[1].id").value(2L))
                .andExpect(jsonPath("$.data.content[1].title").value("Title 2"))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.number").value(0))
                .andExpect(jsonPath("$.data.size").value(10));
    }

    @Test
    public void testGetIncidents_NormalCase_Page0Size10() throws Exception {
        // 准备测试数据
        List<Incident> incidents = Arrays.asList(
                new Incident(1L, "type 1", "Title 1", "Description 1", 0, new Date()),
                new Incident(2L, "type 2", "Title 2", "Description 2", 1, new Date())
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<Incident> page = new PageImpl<>(incidents, pageable, 2);

        // 模拟服务方法的行为
        when(incidentService.queryByPage(pageable)).thenReturn(page);

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 验证 JSON 路径
        mockMvc.perform(get("/incident?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Title 1"))
                .andExpect(jsonPath("$.data.content[1].id").value(2L))
                .andExpect(jsonPath("$.data.content[1].title").value("Title 2"))
                .andExpect(jsonPath("$.data.page.totalElements").value(2))
                .andExpect(jsonPath("$.data.page.totalPages").value(1))
                .andExpect(jsonPath("$.data.page.number").value(0))
                .andExpect(jsonPath("$.data.page.size").value(10));
    }

    @Test
    public void testGetIncidents_NormalCase_Page1Size10() throws Exception {
        // 准备测试数据
        List<Incident> incidents = Arrays.asList(
                new Incident(11L, "type 11", "Title 11", "Description 11", 0, new Date()),
                new Incident(12L, "type 12", "Title 12", "Description 12", 1, new Date())
        );
        Pageable pageable = PageRequest.of(1, 10);
        Page<Incident> page = new PageImpl<>(incidents, pageable, 2);

        // 模拟服务方法的行为
        when(incidentService.queryByPage(pageable)).thenReturn(page);

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 验证 JSON 路径
        mockMvc.perform(get("/incident?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(11L))
                .andExpect(jsonPath("$.data.content[0].title").value("Title 11"))
                .andExpect(jsonPath("$.data.content[1].id").value(12L))
                .andExpect(jsonPath("$.data.content[1].title").value("Title 12"))
                .andExpect(jsonPath("$.data.page.totalElements").value(2))
                .andExpect(jsonPath("$.data.page.totalPages").value(1))
                .andExpect(jsonPath("$.data.page.number").value(1))
                .andExpect(jsonPath("$.data.page.size").value(10));
    }

    @Test
    public void testGetIncidents_BoundaryCase_Page0Size0() throws Exception {
        // 准备测试数据
        List<Incident> incidents = Collections.emptyList();
//        Pageable pageable = PageRequest.of(0, 0);
//        Page<Incident> page = new PageImpl<>(incidents, pageable, 0);

        // 模拟服务方法的行为
        when(incidentService.queryByPage(any())).thenThrow(new IllegalArgumentException("Page index must not be less than zero"));

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=0&size=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 验证 JSON 路径
        mockMvc.perform(get("/incident?page=0&size=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Page index must not be less than zero"));
    }

    @Test
    public void testGetIncidents_BoundaryCase_Page100Size10() throws Exception {
        // 准备测试数据
        List<Incident> incidents = Collections.emptyList();
        Pageable pageable = PageRequest.of(100, 10);
        Page<Incident> page = new PageImpl<>(incidents, pageable, 2);

        // 模拟服务方法的行为
        when(incidentService.queryByPage(pageable)).thenReturn(page);

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=100&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 验证 JSON 路径
        mockMvc.perform(get("/incident?page=100&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty())
                .andExpect(jsonPath("$.data.page.totalElements").value(2))
                .andExpect(jsonPath("$.data.page.totalPages").value(1))
                .andExpect(jsonPath("$.data.page.number").value(100))
                .andExpect(jsonPath("$.data.page.size").value(10));
    }

    @Test
    public void testGetIncidents_ExceptionCase_ServiceThrowsException() throws Exception {
        // 准备测试数据
        Pageable pageable = PageRequest.of(0, 10);

        // 模拟服务方法的行为
        when(incidentService.queryByPage(pageable)).thenThrow(new RuntimeException("Service Error"));

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/incident?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 验证 JSON 路径
        mockMvc.perform(get("/incident?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(R.FAILURE))
                .andExpect(jsonPath("$.msg").value("Service Error"));
    }


    @Test
    public void testCreateIncident_NormalCase() throws Exception {
        // 准备测试数据
        IncidentDTO incidentDTO = new IncidentDTO(null, "type 1", "Title 1", "Description 1", 0);
        Incident createdIncident = new Incident(1L, "type 1", "Title 1", "Description 1", 0, new Date());

        // 模拟服务方法的行为
        when(incidentService.insert(any(IncidentDTO.class))).thenReturn(createdIncident);

        // 执行请求并验证响应
        mockMvc.perform(post("/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.type").value("type 1"))
                .andExpect(jsonPath("$.data.title").value("Title 1"))
                .andExpect(jsonPath("$.data.description").value("Description 1"))
                .andExpect(jsonPath("$.data.status").value(0));
    }

    @Test
    public void testCreateIncident_ExceptionCase_MissingRequiredField() throws Exception {
        // 准备测试数据
        IncidentDTO invalidIncidentDTO = new IncidentDTO(null, null, "Title 1", "Description 1", 0);

        // 执行请求并验证响应
        mockMvc.perform(post("/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidIncidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(R.FAILURE))
                .andExpect(jsonPath("$.data.type").value("Type cannot be empty"));
    }

    @Test
    public void testCreateIncident_ExceptionCase_InvalidDateFormat() throws Exception {
        // 准备测试数据
        IncidentDTO invalidIncidentDTO = new IncidentDTO(null, "type 1", "Title 1", "Description 1", 0);
        // 假设这里状态传成非数值的字符串
        // assume status is not a number
        Map<String,Object> params = new HashMap<>();
        params.put("status","invalid");
        params.put("description","description");
        params.put("type","type");
        params.put("title","title");

        // 执行请求并验证响应
        mockMvc.perform(post("/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath( "$.code").value(R.FAILURE));
    }

    @Test
    public void testCreateIncident_ExceptionCase_OutOfRangeStatus() throws Exception {
        // 准备测试数据
        IncidentDTO invalidIncidentDTO = new IncidentDTO(null, "type 1", "Title 1", "Description 1", 99); // 假设状态值只能是 0 或 1

        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(post("/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidIncidentDTO)))
                        .andReturn();

        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 执行请求并验证响应
        mockMvc.perform(post("/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidIncidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Invalid status."));
    }

    @Test
    public void testUpdateIncident_NormalCase() throws Exception {
        // 准备测试数据
        Long id = 1L;
        IncidentDTO incidentDTO = new IncidentDTO(id, "type 1", "Updated Title", "Updated Description", 1);
        Incident updatedIncident = new Incident(id, "type 1", "Updated Title", "Updated Description", 1, new Date());

        // 模拟服务方法的行为
        when(incidentService.update(incidentDTO)).thenReturn(updatedIncident);

        // 执行请求并验证响应
        mockMvc.perform(put("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.type").value("type 1"))
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.description").value("Updated Description"))
                .andExpect(jsonPath("$.data.status").value(1));
    }

    @Test
    public void testUpdateIncident_BoundaryCase_NonExistentId() throws Exception {
        // 准备测试数据
        Long id = 999L;
        IncidentDTO incidentDTO = new IncidentDTO(id, "type 1", "Updated Title", "Updated Description", 1);

        // 模拟服务方法的行为
        when(incidentService.update(incidentDTO)).thenThrow(new ResourceNotFoundException("Incident not found with id: " + id));

        // 执行请求并验证响应
        mockMvc.perform(put("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(R.FAILURE))
                .andExpect(jsonPath("$.msg").value("Incident not found with id: " + id));
    }

    @Test
    public void testUpdateIncident_ExceptionCase_MissingRequiredField() throws Exception {
        // 准备测试数据
        Long id = 1L;
        IncidentDTO invalidIncidentDTO = new IncidentDTO(id, null, "Updated Title", "Updated Description", 1);

        // 执行请求并验证响应
        mockMvc.perform(put("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidIncidentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(R.FAILURE))
                .andExpect(jsonPath("$.data.type").value("Type cannot be empty"));
    }

    @Test
    public void testUpdateIncident_ExceptionCase_InvalidDateFormat() throws Exception {
        // 准备测试数据
        Long id = 1L;
//        IncidentDTO invalidIncidentDTO = new IncidentDTO(id, "type 1", "Updated Title", "Updated Description", 1);
        // 假设这里状态传成非数值的字符串
        // assume status is not a number
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status","invalid");
        params.put("description","description");
        params.put("type","type");
        params.put("title","title");


        // 假设这里有一个字段需要日期格式，但故意设置为无效格式
        // 这里假设有一个字段名为 "date"，但为了简化示例，我们不实际添加这个字段

        // 执行请求并验证响应
        mockMvc.perform(put("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(R.FAILURE));
    }

    @Test
    public void testUpdateIncident_ExceptionCase_OutOfRangeStatus() throws Exception {
        // 准备测试数据
        Long id = 1L;
        IncidentDTO invalidIncidentDTO = new IncidentDTO(id, "type 1", "Updated Title", "Updated Description", 99); // 假设状态值只能是 0 或 1
        Incident incident = new Incident(id, "type 1", "Updated Title", "Updated Description", 99,new Date()); // 假设状态值只能是 0 或 1

        when(incidentService.queryById(id)).thenReturn(incident);
        when(incidentService.update(invalidIncidentDTO)).thenThrow(new IllegalArgumentException("Invalid status."));

        // 执行请求并验证响应
        mockMvc.perform(put("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidIncidentDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.msg").value("Invalid status."));
    }

    @Test
    public void testDeleteIncident_NormalCase() throws Exception {
        // 准备测试数据
        Long id = 1L;
        Incident incident = new Incident(id, "type 1", "Title", "Description", 1, new Date());

        // 模拟服务方法的行为
        when(incidentService.queryById(id)).thenReturn(incident);
        doNothing().when(incidentService).deleteById(eq(id));

        // 执行请求并验证响应
        mockMvc.perform(delete("/incident/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath( "$.code").value(R.SUCCESS));
    }

    @Test
    public void testDeleteIncident_BoundaryCase_NonExistentId() throws Exception {
        // 准备测试数据
        Long id = 999L;

        // 模拟服务方法的行为
        doThrow(new ResourceNotFoundException("Incident not found with id: " + id)).when(incidentService).deleteById(eq(id));

        // 执行请求并验证响应
        mockMvc.perform(delete("/incident/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Incident not found with id: " + id));
    }


    @Test
    public void testGetIncident_NormalCase() throws Exception {
        // 准备测试数据
        Long id = 1L;
        Incident incident = new Incident(id, "type 1", "Title 1", "Description 1", 0, new Date());

        // 模拟服务方法的行为
//        when(incidentService.queryById(id)).thenReturn(incident);
        doReturn(incident).when(incidentService).queryById(id);

        MvcResult result = mockMvc.perform(get("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // 打印实际响应内容
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + responseContent);

        // 执行请求并验证响应
        mockMvc.perform(get("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.type").value("type 1"))
                .andExpect(jsonPath("$.data.title").value("Title 1"))
                .andExpect(jsonPath("$.data.description").value("Description 1"))
                .andExpect(jsonPath("$.data.status").value(0));
    }

    @Test
    public void testGetIncident_BoundaryCase_NonExistentId() throws Exception {
        // 准备测试数据
        Long id = 999L;

        // 模拟服务方法的行为
        when(incidentService.queryById(id)).thenReturn(null);

        // 执行请求并验证响应
        mockMvc.perform(get("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Incident not found with id: " + id));
    }

    @Test
    public void testGetIncident_ExceptionCase_ServiceThrowsException() throws Exception {
        // 准备测试数据
        Long id = 1L;

        // 模拟服务方法的行为
        when(incidentService.queryById(id)).thenThrow(new RuntimeException("Service Error"));

        // 执行请求并验证响应
        mockMvc.perform(get("/incident/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Service Error"));
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}