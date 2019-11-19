package com.atu.service.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.Attach;
import com.atu.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
/**
* 附件
*/
@Service("attachService")
public class AttachServiceImpl implements AttachService {
    @Autowired
    private BaseDao<Attach,String> attachDao;
    @Override
    public void save(Attach attach) {
        attach.setCreateTime(new Date());
        attachDao.save(attach);
    }

    @Override
    public void delete(String id){
        attachDao.delete(Attach.class,id);
    }


    /**
     * 获取一条记录
     * @param id
     * @return
     */
    @Override
    public Attach get(String id) {
        return attachDao.get(Attach.class,id);
    }
}
