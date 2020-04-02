package com.atu.extra.service.impl;

import com.atu.extra.dao.ExtraAdDao;
import com.atu.extra.entity.ExtraAd;
import com.atu.extra.exception.CustomException;
import com.atu.extra.service.ISpringTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <h1>Spring Transactional 测试接口实现</h1>
 */
@Slf4j
@Service
public class SpringTransactionImpl implements ISpringTransaction {

    /**
     * ExtraAd Dao
     */
    private final ExtraAdDao extraAdDao;

    @Autowired
    public SpringTransactionImpl(ExtraAdDao extraAdDao) {
        this.extraAdDao = extraAdDao;
    }

    /**
     * <h2>捕捉异常, 导致不能回滚</h2>
     */
    @Override
    @Transactional
    public void CatchExceptionCanNotRollback() {

        try {
            extraAdDao.save(new ExtraAd("qinyi"));
            throw new RuntimeException();
        } catch (Exception ex) {
            ex.printStackTrace();
            // 手动标记回滚
            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * <h2>捕捉异常并转换异常, 导致不能回滚</h2>
     */
    @Override
    @Transactional
    public void NotRuntimeExceptionCanNotRollback() throws CustomException {

        try {
            extraAdDao.save(new ExtraAd("qinyi"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    /**
     * <h2>RuntimeException 异常可以回滚</h2>
     */
    @Override
    @Transactional
    public void RuntimeExceptionCanRollback() {

        extraAdDao.save(new ExtraAd("qinyi"));
        throw new RuntimeException();
    }

    /**
     * <h2>指定异常, 可以回滚</h2>
     */
    @Override
    @Transactional(rollbackFor = {CustomException.class})
    public void AssignExceptionCanRollback() throws CustomException {

        try {
            extraAdDao.save(new ExtraAd("qinyi"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * <h2>在 private 方法上标注 transactional, 事务无效</h2>
     */
    @Transactional
    public void oneSaveMethod() {

        extraAdDao.save(new ExtraAd("qinyi"));
    }

    /**
     * <h2>Rollback Only</h2>
     * org.springframework.transaction.UnexpectedRollbackException:
     * Transaction silently rolled back because it has been marked as rollback-only
     *
     * 该异常是Spring事务在处理过程中抛出的异常
     * 在Spring中的事务方法中调用多个事务方法时：RollbackOnlyCanRollback被注解标记，同时oneSaveMethod也是一个事务方法
     * Spring的默认传播机制会把这些事务合二为一，当整个方法中的每一个子方法都没有报错时，整个方法执行完才会进行提交。
     * 那如果某个方法出现了异常，Spring就会把异常标记为 rollback-only。
     * 那如果这个子方法没有将异常往上抛出，或者说整个方法没有将异常往上抛出。那么这个异常就不会触发事务进行回滚。
     * 事务就会在整个方法执行完之后进行提交。那由于 rollback-only的存在，所以会打印Spring事务处理的异常。
     * 如果是我们的处理过程中发生的异常，那会导致整个方法处理失败，而不是事务出现回滚。
     *
     */
    @Override
    @Transactional
    public void RollbackOnlyCanRollback() throws CustomException {

        oneSaveMethod();
        try {
            extraAdDao.save(new ExtraAd());
        } catch (Exception ex) {
            ex.printStackTrace();
            //在此处主动捕获异常进行抛出，Spring就会获取到异常并进行事务回滚
            throw ex;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * <h2>在private方法上标注transactional, 事务无效</h2>
     * Spring事务管理是通过AOP(切面)实现的，AOP底层是动态代理。
     * 也就是说当我们一个方法标注了 @Transactional 注解时，Spring会帮助我们生成一个代理对象。
     * 我们去调用这个方法时其实是代理对象调用的我们的方法。
     * 我们调用NonTransactionalCanNotRollback方法时，是通过一个代理对象，
     * 去调用this.anotherOneSaveMethod()方法时，是通过this指针调用，也就是说是调用当前对象的一个属性，
     * 它只是代理对象的一个属性，此时anotherOneSaveMethod被标记的事务就没有被发现。
     *
     * 所以说在整个执行NonTransactionalCanNotRollback方法时，是不存在事务的。
     */
    @Transactional
    public void anotherOneSaveMethod() {

        extraAdDao.save(new ExtraAd("qinyi"));
        throw new RuntimeException();
    }

    /**
     * <h2>同一个类中, 一个不标注事务的方法去调用 transactional 的方法, 事务会失效</h2>
     */
    @Override
//    @Transactional
    public void NonTransactionalCanNotRollback() {

        anotherOneSaveMethod();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
