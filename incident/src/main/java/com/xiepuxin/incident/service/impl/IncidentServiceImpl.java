package com.xiepuxin.incident.service.impl;

import com.xiepuxin.incident.dto.IncidentDTO;
import com.xiepuxin.incident.entity.Incident;
import com.xiepuxin.incident.dao.IncidentDao;
import com.xiepuxin.incident.exception.DuplicateFingerprintException;
import com.xiepuxin.incident.exception.ResourceNotFoundException;
import com.xiepuxin.incident.service.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.Date;


/**
 * (Incident)表服务实现类
 *
 * @author makejava
 * @since 2024-12-14 22:43:08
 */
@Service("incidentService")
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentDao incidentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @Cacheable(value = "incident", key = "#id")
    public Incident queryById(Long id) {
        return this.incidentDao.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *
     * @param pageable 分页对象
     * @return 查询结果
     */
    @Override
    @Cacheable(value = "incidents", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Incident> queryByPage(Pageable pageable) {
        return this.incidentDao.findAll(pageable);
    }

    /**
     * 新增数据
     *
     * @param incident 实例对象
     * @return 实例对象
     */
    @Override
    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident insert(IncidentDTO incident) {
        Incident entity = new Incident();
        BeanUtils.copyProperties(incident,entity);

        //相同指纹的事件看做是同一个
        // Generate fingerprint
        String fingerprint = entity.generateFingerprint();
        entity.setFingerprint(fingerprint);

        // Create an example object for checking existence
        Incident exampleIncident = new Incident();
        exampleIncident.setFingerprint(fingerprint);
        Example<Incident> example = Example.of(exampleIncident, ExampleMatcher.matching());

        // Check if fingerprint already exists
        if (incidentDao.exists(example)) {
            throw new DuplicateFingerprintException("Incident with the same fingerprint already exists.");
        }

        entity.setTime(new Date());
        return this.incidentDao.save(entity);
    }

    /**
     * 修改数据
     *
     * @param incident 实例对象
     * @return 实例对象
     */
    @Override
    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident update(IncidentDTO incident) {
        Incident existingIncident = incidentDao.findById(incident.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + incident.getId()));
        Incident entity = new Incident();
        BeanUtils.copyProperties(incident,entity);
        entity.setTime(new Date());

        //相同指纹的事件看做是同一个
        // Generate fingerprint
        String fingerprint = entity.generateFingerprint();
        entity.setFingerprint(fingerprint);

        // Create an example object for checking existence
        Incident exampleIncident = new Incident();
        exampleIncident.setFingerprint(fingerprint);
        Example<Incident> example = Example.of(exampleIncident, ExampleMatcher.matching());

        Incident existedIncident = incidentDao.findOne(example).orElse(null);
        // Check if fingerprint already exists
        if (existedIncident != null && !entity.getId().equals(existedIncident.getId())) {
            throw new DuplicateFingerprintException("Incident with the same fingerprint already exists.");
        }

        return this.incidentDao.save(entity);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     */
    @Override
    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public void deleteById(Long id) {
        this.incidentDao.deleteById(id);
    }
}
