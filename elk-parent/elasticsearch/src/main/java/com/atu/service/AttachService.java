package com.atu.service;

import com.atu.entity.Attach;

/**
 * 附件
 */
public interface AttachService {
    /**
     * 插入
     * @param attach
     */
    void save(Attach attach);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 获取一条记录
     * @param id
     * @return
     */
    Attach get(String id);
}
