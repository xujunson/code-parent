package com.atu.service.doc.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.doc.DocumentType;
import com.atu.service.doc.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("documentTypeService")
public class DocumentTypeServiceImpl implements DocumentTypeService {
    @Autowired
    private BaseDao<DocumentType,String> documentTypeDao;
    /**
     * @description 获取父类pid空的文档类型
     * @return
     */
    @Override
    public List<DocumentType> getParents() {
        String hql = "from DocumentType where pid is null";
        return documentTypeDao.find(hql);
    }

    /**
     * @description 通过pid查询子类文档类型集合
     * @param pid
     * @return
     */
    @Override
    public List<DocumentType> getChilds(String pid) {
        String hql = "from DocumentType where pid = ?";
        return documentTypeDao.find(hql,pid);
    }
}
