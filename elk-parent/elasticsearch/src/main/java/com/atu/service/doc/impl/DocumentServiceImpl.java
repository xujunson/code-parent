package com.atu.service.doc.impl;

import com.alibaba.fastjson.JSONObject;
import com.atu.commons.exception.MyException;
import com.atu.dao.BaseDao;
import com.atu.entity.doc.Document;
import com.atu.entity.doc.DocumentType;
import com.atu.service.doc.DocumentService;
import com.atu.util.ElasticsearchClentUtil;
import com.atu.util.ElasticsearchResponseEntity;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description 索引操作业务层
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService{
    //ES索引配置路径
    private static final String MAPPATH="esmapper/search.xml";
    //索引表
    private static final String INDEX_NAME="document";
    //索引表类型
    private static final String INDEX_TYPE="document";

    private static final String INDEX_SEARCH="/document/document/_search";
    @Autowired
    private BaseDao<Document,String> documentDao;

    @Autowired
    private BaseDao<DocumentType,String> documentTypeDao;
    /**
     * @description 获取一条记录
     * @param fileId
     * @return
     */
    @Override
    public Document get(String fileId) {
       Document doc = documentDao.get(Document.class,fileId);
        if (StringUtils.isNotNull(doc.getTypeId())) {
            DocumentType documentType = documentTypeDao.get(DocumentType.class,doc.getTypeId());
            if(documentType != null) {
                doc.setTypeName(documentType.getTypeName());
            }
        }
        return doc;
    }

    /**
     * @description 创建索引
     * @return
     */
    @Override
    public String createIndex() {
        ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<Document>(MAPPATH);
        try {
            String result = elasticsearchClentUtil.createIndex(INDEX_NAME, INDEX_NAME);
            return result;
        }catch (Exception e){
            throw new MyException("ES服务器异常！");
        }
    }

    /**
     * @description 删除索引
     * @return
     */
    @Override
    public String dropIndex() {
        ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<Document>(MAPPATH);
        try {
            String result = elasticsearchClentUtil.dropIndex(INDEX_NAME);
            return result;
        }catch (Exception e){
            throw new MyException("ES服务器异常！");
        }
    }

    /**
     * @description 插入
     * @param document 主键值需要自己设置，如UUID等
     * @return
     */
    @Override
    public void save(Document document) {
        documentDao.save(document);
    }

    /**
     * @description 删除
     * @param id
     * @return
     */
    @Override
    public void delete(String id) {
         documentDao.delete(Document.class,id);
    }

    /**
     * @description 新增文档，数据库存一条记录，ES存一条数据
     * @param document
     */
    @Override
    public void addDocument(Document document) {
        String documentId ="";
        String id = UUID.randomUUID().toString();
        document.setFileId(id);
        ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<Document>(MAPPATH);
        try {
            String result = elasticsearchClentUtil.addDocument(INDEX_NAME, INDEX_NAME, document);
            //插入ES成功，从result中获取到documentId
            JSONObject jsonObject = JSONObject.parseObject(result);
            documentId = jsonObject.getString("_id");
        }catch (Exception e){
           throw new MyException("ES服务器异常！");
        }

        try {
            document.setDocumentId(documentId);
            document.setFileId(id);
            document.setAgentStarttime(new Date());
            save(document);
        }catch (Exception e){
            //删除ES数据
            elasticsearchClentUtil.deleteDocment(INDEX_NAME,INDEX_NAME,documentId);
            throw new MyException("保存数据出错了！");
        }
    }

    /**
     * @description 删除文档，数据库删除一条记录，ES删除一条数据
     * @param fileId
     */
    @Override
    public void deleteDocument(String fileId) {
        Document document = documentDao.get(Document.class,fileId);
        String documentId = document.getDocumentId();
        try {
            ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<Document>(MAPPATH);
            String result = elasticsearchClentUtil.deleteDocment(INDEX_NAME, INDEX_NAME, documentId);
        }catch (Exception e){
            throw new MyException("ES服务器出错了！");
        }
        delete(fileId);
    }

    /**
     * @description 分页
     * @param info
     * @param document
     */
    @Override
    public void selectDataGrid(PageInfo info, Document document) {
       String hql="from Document where 1=1";
       List<Object> params = new ArrayList<Object>();
        System.out.println("title="+document.getTitle());
       if (StringUtils.isNotNull(document.getTitle())) {
           hql+=" and title like ?";
           params.add("%"+document.getTitle()+"%");
       }
       List<Document> documentList = documentDao.findPage(hql,info.getNowpage(),info.getPagesize(),params);
       if (documentList.size() > 0) {
           for (Document doc : documentList) {
                if (StringUtils.isNotNull(doc.getTypeId())) {
                    DocumentType documentType = documentTypeDao.get(DocumentType.class,doc.getTypeId());
                    if(documentType != null) {
                        doc.setTypeName(documentType.getTypeName());
                    }
                }

           }
       }
       info.setRows(documentList);
       String counthql="select count(*) "+hql;
       Long total = documentDao.getCountByList(counthql,params);
       info.setTotal(Integer.parseInt(""+total));
    }


    /**
     * 通过ES查询
     * @param page 当前页
     * @param size 分页大小
     * @param classicId 分类
     * @param keywords 查询的内容
     * @return
     */
    @Override
    public ElasticsearchResponseEntity<Document> queryDoc(int page, int size, String classicId, String keywords) {
        try {
            String from = "0";
            if (page < 1) {
                from = "0";
            } else {
                from = "" + (page - 1) * size;
            }
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("from", from);
            paramsMap.put("size", "" + size);
            paramsMap.put("classicId", classicId);
            paramsMap.put("keywords", keywords);
            ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<>(MAPPATH);
            ElasticsearchResponseEntity<Document> documentElasticsearchResponseEntity = null;
            if(keywords != null && !keywords.trim().equals("")) {
               documentElasticsearchResponseEntity = elasticsearchClentUtil.searchDocumentByKeywords(INDEX_SEARCH, "searchPagineDatas",
                        paramsMap, Document.class);
            }else {
                documentElasticsearchResponseEntity = elasticsearchClentUtil.searchDocumentByKeywords(INDEX_SEARCH, "searchPagineDatas2",
                        paramsMap, Document.class);
            }
            return documentElasticsearchResponseEntity;
        }catch (Exception e){
            throw new MyException("服务器异常");
        }
    }

    /**
     * @description 通过ES查询
     * @param page
     * @param size
     * @param typeId 分类
     * @param keywords
     * @return
     */
    @Override
    public ElasticsearchResponseEntity<Document> queryDocByTypeId(int page, int size, String typeId, String keywords) {
        try {
            String from = "0";
            if (page < 1) {
                from = "0";
            } else {
                from = "" + (page - 1) * size;
            }
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("from", from);
            paramsMap.put("size", "" + size);
            paramsMap.put("typeId", typeId);
            paramsMap.put("keywords", keywords);
            ElasticsearchClentUtil<Document> elasticsearchClentUtil = new ElasticsearchClentUtil<>(MAPPATH);
            ElasticsearchResponseEntity<Document> documentElasticsearchResponseEntity = null;
            if(keywords != null && !keywords.trim().equals("")) {
                documentElasticsearchResponseEntity = elasticsearchClentUtil.searchDocumentByKeywords(INDEX_SEARCH, "searchPagineDatas3",
                        paramsMap, Document.class);
            }else {
                documentElasticsearchResponseEntity = elasticsearchClentUtil.searchDocumentByKeywords(INDEX_SEARCH, "searchPagineDatas4",
                        paramsMap, Document.class);
            }
            return documentElasticsearchResponseEntity;
        }catch (Exception e){
            throw new MyException("服务器异常");
        }
    }
}
