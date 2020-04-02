package com.atu.extra.service;


import com.atu.extra.exception.CustomException;

/**
 * <h1>Spring Transactional 测试接口定义</h1>
 */
public interface ISpringTransaction {

    /**
     * <h2>主动捕获了异常, 导致事务不能回滚</h2>
     * */
    void CatchExceptionCanNotRollback();

    /**
     * <h2>不是 unchecked 异常, 导致事务不能回滚</h2>
     * */
    void NotRuntimeExceptionCanNotRollback() throws CustomException;

    /**
     * <h2>unchecked 异常, 事务可以回滚</h2>
     * */
    void RuntimeExceptionCanRollback();

    /**
     * <h2>指定了 rollbackFor, 事务可以回滚</h2>
     * */
    void AssignExceptionCanRollback() throws CustomException;

    /**
     * <h2>Rollback Only, 事务可以回滚</h2>
     * */
    void RollbackOnlyCanRollback() throws Exception;

    /**
     * <h2>同一个类中, 一个不标注事务的方法去调用 transactional 的方法, 事务会失效</h2>
     * */
    void NonTransactionalCanNotRollback();
}
