package com.xiepuxin.incident.controller;

import com.xiepuxin.incident.dto.IncidentDTO;
import com.xiepuxin.incident.entity.Incident;
import com.xiepuxin.incident.exception.ResourceNotFoundException;
import com.xiepuxin.incident.model.R;
import com.xiepuxin.incident.service.IncidentService;
import com.xiepuxin.incident.validate.AddGroup;
import com.xiepuxin.incident.validate.EditGroup;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * (Incident)表控制层
 *
 * @author makejava
 * @since 2024-12-14 22:42:57
 */
@RestController
@RequestMapping("incident")
public class IncidentController {
    /**
     * 服务对象
     */
    @Resource
    private IncidentService incidentService;

    @Autowired
    private PagedResourcesAssembler<Incident> assembler;

    /**
     * 分页查询
     *
     * @param pageable 分页对象
     * @return 查询结果
     */
    @GetMapping
    public R<PagedModel<EntityModel<Incident>>> queryByPage(Pageable pageable) {
        Page<Incident> incidents = incidentService.queryByPage(pageable);
        PagedModel<EntityModel<Incident>> pagedModel = assembler.toModel(incidents);
        return R.ok(pagedModel);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ResponseBody
    public R<Incident> queryById(@PathVariable("id") Long id) {
        Incident incident = this.incidentService.queryById(id);
        if (incident == null) {
            return R.fail("找不到该资源");
        }
        return R.ok(incident);
    }

    /**
     * 新增数据
     *
     * @param incident 实体
     * @return 新增结果
     */
    @PostMapping
    public R<Incident> add(@Validated(AddGroup.class) @RequestBody IncidentDTO incident) {
        return R.ok(this.incidentService.insert(incident));
    }

    /**
     * 编辑数据
     *
     * @param incident 实体
     * @return 编辑结果
     */
    @PutMapping("/{id}")
    public R<Incident> edit(@PathVariable Long id,@Validated(EditGroup.class) @RequestBody IncidentDTO incident) {
        Incident existingIncident = incidentService.queryById(id);
        if(existingIncident == null){
            throw new ResourceNotFoundException("Incident not found with id: " + id);
        };
        incident.setId(id);
        return R.ok(this.incidentService.update(incident));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public R<Void> deleteById(@PathVariable("id") Long id) {
        Incident existingIncident = incidentService.queryById(id);
        if(existingIncident == null){
            throw new ResourceNotFoundException("Incident not found with id: " + id);
        };
        this.incidentService.deleteById(id);
        return R.ok();
    }
}
