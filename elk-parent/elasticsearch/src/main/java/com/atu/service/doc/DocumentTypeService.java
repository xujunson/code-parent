package com.atu.service.doc;

import com.atu.entity.doc.DocumentType;

import java.util.List;

/**
 * @description 文件类型
 */
public interface DocumentTypeService {
    /**
     * @description 获取父类pid空的文档类型
     * @return
     */
    public List<DocumentType> getParents();

    /**
     * @description 通过pid查询子类文档类型集合
     * @param pid
     * @return
     */
    public List<DocumentType> getChilds(String pid);
}
