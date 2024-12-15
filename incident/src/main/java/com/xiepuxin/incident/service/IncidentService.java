package com.xiepuxin.incident.service;

import com.xiepuxin.incident.dto.IncidentDTO;
import com.xiepuxin.incident.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * (Incident)表服务接口
 *
 * @author xiepuxin
 * @since 2024-12-14 22:43:05
 */
public interface IncidentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Incident queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageable 分页对象
     * @return 查询结果
     */
    Page<Incident> queryByPage(Pageable pageable);

    /**
     * 新增数据
     *
     * @param incident 实例对象
     * @return 实例对象
     */
    Incident insert(IncidentDTO incident);

    /**
     * 修改数据
     *
     * @param incident 实例对象
     * @return 实例对象
     */
    Incident update(IncidentDTO incident);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     */
    void deleteById(Long id);

}
