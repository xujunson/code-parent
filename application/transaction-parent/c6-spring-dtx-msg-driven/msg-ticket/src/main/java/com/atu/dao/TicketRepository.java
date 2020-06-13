package com.atu.dao;

import com.atu.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOwner(Long owner);

    Ticket findOneByTicketNum(Long num);

    /**
     * 通过sql执行
     * 实现了幂等性
     * 执行一次与多次效果相同
     *
     * 同一个实例的 persistence context是同一个,
     * 当调用lockTicket方法时，即使数据没有被写到数据库里面,
     * springdata jpa会帮助考虑这些事情，做一些优化，到底什么时候同步到数据库。
     *
     * 那么在多实例的情况下，调用完方法之后事务已经结束，但是没有立刻同步到数据库，
     * 如果出现这种情况就需要设置 clearAutomatically = true。需要进行高并发测试
     *
     *
     * @param customerId
     * @param ticketNum
     * @return
     */
    //    @Modifying(clearAutomatically = true)
    @Modifying
    @Query("UPDATE ticket SET lockUser = ?1 WHERE lockUser is NULL and ticketNum = ?2")
    int lockTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int unLockTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET owner = ?1, lockUser = null WHERE lockUser = ?1 and ticketNum = ?2")
    int moveTicket(Long customerId, Long ticketNum);

    @Modifying
    @Query("UPDATE ticket SET owner = NULL WHERE owner = ?1 and ticketNum = ?2")
    int unMoveTicket(Long customerId, Long ticketNum);

    /**
     * clearAutomatically 是否自动维护 persistence context 同步的问题
     * true-取消
     *
     * @param o
     * @return
     */
    @Override
    @Modifying(clearAutomatically = true)
    Ticket save(Ticket o);

}
