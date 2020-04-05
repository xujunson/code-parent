package com.atu.dao;

import com.atu.entity.Worker;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-04-05 13:52
 * @description: WorkerDao接口定义：日常开发中最常使用 JpaRepository
 */
public interface WorkerDao extends JpaRepository<Worker, Long> {
    /**
     * 只要方法名称符合JPA的规范就不需要写sql语句
     * 对于简单条件查询：查询方法以find | read | get | query | stream 开头，它们都是同义词
     * delete |remove
     * <p>
     * 根据名称查询
     *
     * @param name
     * @return
     */
    Worker findByName(String name);

    Worker getByName(String name);

    Worker readByName(String name);

    Worker queryByName(String name);

    Worker streamByName(String name);

    /**
     * <h2>多条件查询使用 And 进行连接</h2>
     * 条件的属性名称与个数要与参数的位置和个数一一对应
     *
     * @param name
     * @param city
     * @return
     */
    Worker findByNameAndCity(String name, String city);

    /**
     * JPA 规范中定义了 MySQL 几乎所有的查询关键字
     * GreaterThanEqual—— >= 的意思
     *
     * @param name
     * @param salary
     * @return
     */
    Worker findByNameAndSalaryGreaterThanEqual(String name, Long salary);

    // 如果 JPA 规范定义的查询关键字不能满足我们的需求, 可以使用 @Query 自定义查询 SQL

    /**
     * @Query ——自定义查询sql
     * 查找最大 id 的 Worker 对象
     */
    @Query("SELECT w FROM Worker w WHERE id = (SELECT MAX(id) FROM Worker)")
    Worker getMaxIdWorker();


    // 如果查询有参数, 参数有两种不同的传递方式

    /**
     * <h2>第一种查询参数的传递方式</h2>
     */
    @Query("SELECT w FROM Worker w WHERE name = ?1 AND salary >= ?2")
    List<Worker> findWorkerByFirstParam(String name, Long salary);

    /**
     * 推荐使用
     * <h2>第二种查询参数的传递方式</h2>
     */
    @Query("SELECT w FROM Worker w WHERE name = :name AND salary >= :salary")
    List<Worker> findWorkerBySecondParam(@Param("name") String name,
                                         @Param("salary") Long salary);

    /**
     * <h2>只查询实体表的部分字段</h2>
     */
    @Query("SELECT new Worker (w.name, w.salary) FROM Worker w")
    List<Worker> getWorkerNameAndSalaryInfo();

    // 原生查询：写真正的sql语句，不需要jpa做其他方面的映射，不去做sql语言方面的转化工作

    /**
     * <h2>通过原生查询获取 worker 表中的记录</h2>
     */
    @Query(value = "SELECT * FROM worker", nativeQuery = true)
    List<Worker> findAllNativeQuery();

    /**
     * 实际开发一般不使用
     * <h2>通过原生查询拿到部分字段结果</h2>
     */
    @Query(value = "SELECT w.name, w.salary FROM worker w", nativeQuery = true)
    List<Map<String, Object>> getWorkerNameAndSalaryInfoByNativeQuery();


    /**
     * 通过 @Modifying 自定义个性化的更新操作，涉及到部分字段修改
     * 1、自定义JPQL可以完成 update和delete，但是不支持insert，可以直接使用jpa里定义的save方法
     * 2、不论更新还是删除，返回类型都是int，表示更新语句所影响的行数
     * 3、必须加事务，没有事务不能执行；默认情况下spring date，每个方法上都是有事务的，但是是一个只读事务，不能够完成修改操作；
     * 在jpa的开发里  @Modifying 需要和 @Transactional 配合使用，才能够正常应用。
     * <p>
     * Modifying ：声明执行的sql是更新操作
     * Transactional ：提供事务执行
     * 由于Modifying只是声明了操作是一个修改操作，却没有修改这个方法的事务等级，因此这个方法不能进行修改。
     * 只有声明了Transactional才可以，这样覆盖了默认的Transactional配置，就可以去执行修改操作了。
     *
     * @param salary
     * @param name
     * @return
     */
    @Modifying
    @Transactional(readOnly = false)
    @Query("UPDATE Worker SET salary = :salary WHERE name = :name")
    int updateSalaryByName(@Param("salary") Long salary,
                           @Param("name") String name);

    /**
     * 排序
     * 分页可使用 Pageable
     *
     * @param salary
     * @param sort
     * @return
     */
    List<Worker> findAllBySalaryGreaterThanEqual(
            Long salary, Sort sort
    );

}
