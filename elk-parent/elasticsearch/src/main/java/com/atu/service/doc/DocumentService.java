package com.atu.service.doc;

import com.atu.entity.doc.Document;
import com.atu.util.ElasticsearchResponseEntity;
import com.atu.util.result.PageInfo;

public interface DocumentService {
    /**
     * @description 获取一条记录
     * @param fileId
     * @return
     */
    Document get(String fileId);
    /**
     * @description 插入
     * @param document
     * @return
     */
    void save(Document document);

    /**
     * @description 删除
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * @description 新增文档，数据库存一条记录，ES存一条数据
     * @param document
     */
    void addDocument(Document document);

    /**
     * @description 删除文档，数据库删除一条记录，ES删除一条数据
     * @param fileId
     */
    void deleteDocument(String fileId);

    /**
     * @description 分页
     * @param info
     * @param document
     */
    void selectDataGrid(PageInfo info, Document document);

    /**
     * @description 创建索引
     * @return
     */
    String createIndex();

    /**
     * @description 删除索引
     * @return
     */
    String dropIndex();

    /**
     * @description 通过ES查询
     * @param page 当前页
     * @param size 分页大小
     * @param classicId 分类
     * @param keywords 查询的内容
     * @return
     */
    ElasticsearchResponseEntity<Document> queryDoc(int page, int size, String classicId, String keywords);


    /**
     * @description 通过ES查询
     * @param page
     * @param size
     * @param typeId 分类
     * @param keywords
     * @return
     */
    ElasticsearchResponseEntity<Document> queryDocByTypeId(int page, int size, String typeId, String keywords);
}
