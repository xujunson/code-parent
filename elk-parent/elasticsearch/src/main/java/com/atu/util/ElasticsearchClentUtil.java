package com.atu.util;

import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESBaseData;
import org.frameworkset.elasticsearch.entity.ESDatas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ES 增删改查实现
 * @link  https://gitee.com/bboss/bboss-elastic
 * </p>
 */
public class ElasticsearchClentUtil<T extends ESBaseData> {
    private String mappath;

    public ElasticsearchClentUtil(String mappath) {
        this.mappath = mappath;
    }

    /**
     * @param indexName    索引名称
     * @param indexMapping 表结构名称
     * @return
     * @description 创建索引库
     */
    public String createIndex(String indexName, String indexMapping) throws Exception {
        //加载配置文件，单实例多线程安全的
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        //判断索引表是否存在
        boolean exist = clientUtil.existIndice(indexName);
        if (exist) {
            //创建一个mapping之前先删除
            clientUtil.dropIndice(indexName);
        }
        //创建mapping
        return clientUtil.createIndiceMapping(indexName, indexMapping);
    }

    /**
     * @desciption 删除索引
     * @param indexName
     * @return
     */
    public String dropIndex(String indexName){
        //加载配置文件，单实例多线程安全的
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        return clientUtil.dropIndice(indexName);
    }

    /**
     * @param indexName 索引库名称
     * @param indexType 索引类型
     * @param id        索引id
     * @return
     * @description 删除文档索引
     */
    public String deleteDocment(String indexName, String indexType, String id) throws ElasticSearchException {
        //加载配置文件，单实例多线程安全的
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        return clientUtil.deleteDocument(indexName, indexType, id);
    }


    /**
     * @param indexName 索引库名称
     * @param indexType 索引类型
     * @param bean
     * @return
     * @description 添加文档
     */
    public String addDocument(String indexName, String indexType,T bean){
        //创建创建/修改/获取/删除文档的客户端对象，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        return clientUtil.addDocument(indexName,indexType,bean);
    }

    /**
     *
     * @param path _search为检索操作action
     * @param templateName esmapper/search.xml中定义的dsl语句
     * @param queryFiled 查询参数
     * @param keywords 查询参数值
     * @param from 分页查询的起始记录,默认为0
     * @param size 分页大小，默认为10
     * @return
     */
    public ElasticsearchResponseEntity<T> searchDocumentByKeywords(String path, String templateName, String queryFiled, String keywords,
                                                                   String from, String size, Class <T> beanClass) {
        //加载配置文件，单实例多线程安全的
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(queryFiled, keywords);
        //设置分页参数
        params.put("from",from);
        params.put("size",size);
        ElasticsearchResponseEntity<T> responseEntity = new ElasticsearchResponseEntity<T>(Integer.parseInt(from),Integer.parseInt(size));
        //执行查询，search为索引表，_search为检索操作action
        ESDatas<T> esDatas =  //ESDatas包含当前检索的记录集合，最多1000条记录，由dsl中的size属性指定
                clientUtil.searchList(path,//search为索引表，_search为检索操作action
                        templateName,//esmapper/search.xml中定义的dsl语句
                        params,//变量参数
                        beanClass);//返回的文档封装对象类型

        //获取结果对象列表，最多返回1000条记录
        List<T> documentList = esDatas.getDatas();
        System.out.println(documentList==null);
        //获取总记录数
        long totalSize = esDatas.getTotalSize();
        responseEntity.setTotal(totalSize);
        for(int i = 0; documentList != null && i < documentList.size(); i ++) {//遍历检索结果列表
            T doc = documentList.get(i);
            //记录中匹配上检索条件的所有字段的高亮内容
            Map<String, List<Object>> highLights = doc.getHighlight();
            Iterator<Map.Entry<String, List<Object>>> entries = highLights.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<Object>> entry = entries.next();
                String fieldName = entry.getKey();
                System.out.print(fieldName + ":");
                List<Object> fieldHighLightSegments = entry.getValue();
                for (Object highLightSegment : fieldHighLightSegments) {
                    /**
                     * 在dsl中通过<mark></mark>来标识需要高亮显示的内容，然后传到web ui前端的时候，通过为mark元素添加css样式来设置高亮的颜色背景样式
                     * 例如：
                     * <style type="text/css">
                     *     .mark,mark{background-color:#f39c12;padding:.2em}
                     * </style>
                     */
                    System.out.println(highLightSegment);
                }
            }
        }
        responseEntity.setRecords(documentList);
        return responseEntity;
    }

    /**
     *
     * @param path _search为检索操作action
     * @param templateName esmapper/search.xml中定义的dsl语句
     * @param  paramsMap 包含from和size,还有其他要查询的key-value
     * @return
     */
    public ElasticsearchResponseEntity<T> searchDocumentByKeywords(String path, String templateName, Map<String,String> paramsMap,
                                                                    Class <T> beanClass) {
        //加载配置文件，单实例多线程安全的
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(mappath);
        ElasticsearchResponseEntity<T> responseEntity = new ElasticsearchResponseEntity<T>(Integer.parseInt(paramsMap.get("from")),Integer.parseInt(paramsMap.get("size")));
        //执行查询，search为索引表，_search为检索操作action
        ESDatas<T> esDatas =  //ESDatas包含当前检索的记录集合，最多1000条记录，由dsl中的size属性指定
                clientUtil.searchList(path,//search为索引表，_search为检索操作action
                        templateName,//esmapper/search.xml中定义的dsl语句
                        paramsMap,//变量参数
                        beanClass);//返回的文档封装对象类型

        //获取结果对象列表，最多返回1000条记录
        List<T> documentList = esDatas.getDatas();
        System.out.println(documentList==null);
        //获取总记录数
        long totalSize = esDatas.getTotalSize();
        responseEntity.setTotal(totalSize);
        for(int i = 0; documentList != null && i < documentList.size(); i ++) {//遍历检索结果列表
            T doc = documentList.get(i);
            //记录中匹配上检索条件的所有字段的高亮内容
            Map<String, List<Object>> highLights = doc.getHighlight();
            Iterator<Map.Entry<String, List<Object>>> entries = highLights.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<Object>> entry = entries.next();
                String fieldName = entry.getKey();
                System.out.print(fieldName + ":");
                List<Object> fieldHighLightSegments = entry.getValue();
                for (Object highLightSegment : fieldHighLightSegments) {
                    /**
                     * 在dsl中通过<mark></mark>来标识需要高亮显示的内容，然后传到web ui前端的时候，通过为mark元素添加css样式来设置高亮的颜色背景样式
                     * 例如：
                     * <style type="text/css">
                     *     .mark,mark{background-color:#f39c12;padding:.2em}
                     * </style>
                     */
                    System.out.println(highLightSegment);
                }
            }
        }
        responseEntity.setRecords(documentList);
        return responseEntity;
    }

}
