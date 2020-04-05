package com.atu.dao;

import com.atu.entity.Worker;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author: Tom
 * @date: 2020-04-05 13:55
 * @description: 不继承类，只需加注解，实际开发中不常用
 */
@RepositoryDefinition(domainClass = Worker.class, idClass = Long.class)
public interface WorkerDaoDefinition {
    Worker findByName(String name);
}
