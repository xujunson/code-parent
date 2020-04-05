package com.atu.service;

import com.alibaba.fastjson.JSON;
import com.atu.dao.WorkerDao;
import com.atu.dao.WorkerDaoDefinition;
import com.atu.entity.Worker;
import com.atu.util.MapToEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Worker Jpa 功能测试</h1>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaWorkerTest {

    @Autowired
    private WorkerDao dao;

    @Autowired
    private WorkerDaoDefinition daoDefinition;

    @Test
    public void testOpWorkerWithName() {

        log.info("{}", JSON.toJSONString(dao.findByName("Y")));
        log.info("{}", JSON.toJSONString(dao.getByName("Y")));
        log.info("{}", JSON.toJSONString(dao.readByName("Y")));
        log.info("{}", JSON.toJSONString(dao.queryByName("Y")));
        log.info("{}", JSON.toJSONString(dao.streamByName("Y")));

        log.info("{}", JSON.toJSONString(daoDefinition.findByName("Y")));
    }

    @Test
    public void testMoreConditionQuery() {

        log.info("{}", JSON.toJSONString(
                dao.findByNameAndCity("Y", "北京市")));
        log.info("{}", JSON.toJSONString(
                dao.findByNameAndSalaryGreaterThanEqual("Y", 100L)
        ));
    }

    @Test
    public void testQueryOp() {

//        log.info("{}", JSON.toJSONString(dao.getMaxIdWorker()));
//        log.info("{}", JSON.toJSONString(dao.findWorkerByFirstParam(
//                "Y", 100L
//        )));
//        log.info("{}", JSON.toJSONString(dao.findWorkerBySecondParam(
//                "Y", 100L
//        )));
        log.info("{}", JSON.toJSONString(dao.getWorkerNameAndSalaryInfo()));
    }

    @Test
    public void testNativeQueryOp() throws Exception {

        log.info("{}", JSON.toJSONString(dao.findAllNativeQuery()));

        List<Map<String, Object>> result =
                dao.getWorkerNameAndSalaryInfoByNativeQuery();
        List<Worker> workers = new ArrayList<>(result.size());
        for (Map<String, Object> worker : result) {
            workers.add(
                    MapToEntity.mapToEntity(worker, Worker.class)
            );
        }

        log.info("{}", workers.size());
        log.info("{}", workers);
    }

    @Test
    public void testModify() {

        //Worker worker = dao.getMaxIdWorker();
        //worker.setSalary(100L);
        // 打印两个sql：包含了一次查询, 目的是确定当前的 save 是插入还是更新
        //dao.save(worker);   // 更新所有字段, 性能较低

        log.info("{}", dao.updateSalaryByName(100000L, "Y"));
    }

    /**
     * 排序和分页
     */
    @Test
    public void testSortAndPageable() {

        //两个参数 第一个表示排序的方向，第二个是排序的字段
        /*Sort sort01 = new Sort(Sort.Direction.DESC, "id");
        Sort sort02 = new Sort(Sort.Direction.DESC,
                "salary", "id");
        Sort sort03 = new Sort(Sort.Direction.DESC, Arrays.asList("salary", "id"));

        List<Worker> w01 = dao.findAll(sort01);
        List<Worker> w02 = dao.findAll(sort02);

        List<Worker> w03 = dao.findAllBySalaryGreaterThanEqual(100L, sort01);
        List<Worker> w04 = dao.findAllBySalaryGreaterThanEqual(100L, sort03);

        log.info("{}", w01);
        log.info("{}", w02);
        log.info("{}", w03);
        log.info("{}", w04);*/

        // page - 从0开始的索引页
        // size - 返回的页面大小
        // sort - 排序
//        Pageable pageable = new PageRequest(
//                int page, int size, Sort sort
//        );
//        new PageRequest(int page, int size); //没排序的分页

        // 每一页有2个元素, 取第一页的两个
        Pageable pageable = PageRequest.of(
                1, 2,
                Sort.by(Sort.Order.desc("salary"))
        );

        Page<Worker> workers = dao.findAll(pageable);

        log.info("总页数: {}", workers.getTotalPages());
        log.info("记录总数: {}", workers.getTotalElements());
        log.info("当前页索引(第几页): {}", workers.getNumber());
        log.info("查询的结果信息: {}", workers.getContent());
        log.info("当前页上的元素数量: {}", workers.getNumberOfElements());
        log.info("请求当前页的 Pageable: {}", workers.getPageable());
        log.info("页大小: {}", workers.getSize());
        log.info("页的排序参数: {}", workers.getSort());
        log.info("页面上是否有内容: {}", workers.hasContent());
        log.info("是否有下一页: {}", workers.hasNext());
        log.info("是否有上一页: {}", workers.hasPrevious());
        log.info("当前页是否是最后一个: {}", workers.isLast());
        log.info("下一页的 Pageable: {}", workers.nextPageable());
        log.info("上一页的 Pageable: {}", workers.previousPageable());
    }
}
