package com.atu.entity.doc;

import org.frameworkset.elasticsearch.entity.ESBaseData;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "doc_document")
public class Document extends ESBaseData {
    /**
     * 设定文档标识字段
     */
    @Column(name = "document_id")
    private String documentId;

    /**
     * 设置文件id，便于下载
     */
    @Id
    @Column(name = "id")
    private String fileId;
    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档简述
     */
    private String description;

    /**
     * 文档主体内容
     */
    private String contentbody;
    /**
     * 文档标签
     */
    private String tags;

    /**
     * 文档类型id
     */
    @Column(name = "type_id")
    private String typeId;
    @Transient
    private String typeName;
    /**
     * 分类，大的分类，如法律、技术标准
     */
    private String classicId;
    /**
     * 文档地址
     */
    private String url;

    /**
     * 文档开始时间
     */
    private Date agentStarttime;

    @Column(name="attach_id")
    private String attachId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentbody() {
        return contentbody;
    }

    public void setContentbody(String contentbody) {
        this.contentbody = contentbody;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClassicId() {
        return classicId;
    }

    public void setClassicId(String classicId) {
        this.classicId = classicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAgentStarttime() {
        return agentStarttime;
    }

    public void setAgentStarttime(Date agentStarttime) {
        this.agentStarttime = agentStarttime;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId='" + documentId + '\'' +
                ", fileId='" + fileId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contentbody='" + contentbody + '\'' +
                ", tags='" + tags + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", classicId='" + classicId + '\'' +
                ", url='" + url + '\'' +
                ", agentStarttime=" + agentStarttime +
                ", attachId='" + attachId + '\'' +
                '}';
    }
}
