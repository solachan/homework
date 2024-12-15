package com.xiepuxin.incident.dao;

import com.xiepuxin.incident.entity.Incident;
//import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (Incident)表数据库访问层
 *
 * @author xiepuxin
 * @since 2024-12-14 22:42:59
 */
@Repository
public interface IncidentDao  extends JpaRepository<Incident, Long> {

//    /**
//     * 通过ID查询单条数据
//     *
//     * @param id 主键
//     * @return 实例对象
//     */
//    Incident queryById(Long id);
//
//    /**
//     * 查询指定行数据
//     *
//     * @param incident 查询条件
//     * @param pageable 分页对象
//     * @return 对象列表
//     */
////    List<Incident> queryAllByLimit(Incident incident, Pageable pageable);
//    List<Incident> queryAllByLimit(Incident incident, @Param("pageable") Pageable pageable);
//
//    /**
//     * 统计总行数
//     *
//     * @param incident 查询条件
//     * @return 总行数
//     */
//    long count(Incident incident);
//
//    /**
//     * 新增数据
//     *
//     * @param incident 实例对象
//     * @return 影响行数
//     */
//    int insert(Incident incident);
//
//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities List<Incident> 实例对象列表
//     * @return 影响行数
//     */
////    int insertBatch(List<Incident> entities);
//    int insertBatch(@Param("entities") List<Incident> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities List<Incident> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
////    int insertOrUpdateBatch(List<Incident> entities);
//    int insertOrUpdateBatch(@Param("entities") List<Incident> entities);
//
//    /**
//     * 修改数据
//     *
//     * @param incident 实例对象
//     * @return 影响行数
//     */
//    int update(Incident incident);
//
//    /**
//     * 通过主键删除数据
//     *
//     * @param id 主键
//     */
//    void deleteById(Long id);

}

