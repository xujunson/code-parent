package com.atu.controller.doc;

import com.atu.commons.exception.MyException;
import com.atu.controller.base.BaseController;
import com.atu.entity.Attach;
import com.atu.entity.doc.Document;
import com.atu.entity.doc.DocumentType;
import com.atu.service.AttachService;
import com.atu.service.doc.DocumentService;
import com.atu.service.doc.DocumentTypeService;
import com.atu.util.Const;
import com.atu.util.HanlpUtil;
import com.atu.util.StringUtils;
import com.atu.util.TikaUtil;
import com.atu.util.result.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 文档管理
 */
@Controller
@RequestMapping("/document")
public class DocumentController extends BaseController {


    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private AttachService attachService;

    /**
     * @description 跳转到文档列表页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "jsp/document/documentList";
    }

    /**
     * @descripition 跳转到文档添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "jsp/document/documentAdd";
    }

    /**
     * @description 查询文档类型
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("queryDocumentType")
    public Object queryDocumentType(String pid){
        List<DocumentType> documentTypeList = new ArrayList<DocumentType>();
        if(StringUtils.isNotNull(pid)){
            documentTypeList = documentTypeService.getChilds(pid);
        }else{
            documentTypeList = documentTypeService.getParents();
        }
        return documentTypeList;
    }


    /**
     * @description 添加文档
     * @param document
     * @return
     */
    @ResponseBody
    @RequestMapping("/addDoc")
    public Object addDoc(Document document) {
        String attchId = document.getAttachId();
        Attach attach = attachService.get(attchId);
        if (attach != null) {
            File f = new File(Const.BASE_UPLOAD_PATH + attach.getPath());
            System.out.println(Const.BASE_UPLOAD_PATH + attach.getPath());
            if (!f.exists()) {
                throw new MyException("文件不存在！");
            } else {
                try {
                    document.setUrl(attach.getPath());
                    String contentbody=(String) TikaUtil.getText(Const.BASE_UPLOAD_PATH + attach.getPath()).get("content");
                    document.setContentbody(contentbody);
                    document.setAgentStarttime(new Date());
                    document.setAttachId(attchId);
                    document.setTags(HanlpUtil.phrase(contentbody).toString());
                   // documentService.save(document);
                    documentService.addDocument(document);
                } catch (Exception e) {
                   throw new MyException("提取内容错误！");
                }
            }
        }
        return renderSuccess("保存成功");
    }

    /**
     * @param fileId
     * @return
     * @description 删除一条记录
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(String fileId) {
        documentService.deleteDocument(fileId);
        return renderSuccess("删除成功！");
    }

    /**
     * @param page
     * @param rows
     * @param document
     * @return
     * @description 分页查询
     */
    @ResponseBody
    @RequestMapping("/dataGrid")
    public Object dataGrid(int page, int rows, Document document) {
        PageInfo info = new PageInfo(page, rows);
        documentService.selectDataGrid(info, document);
        return info;
    }

    /**
     * @return
     * @description 创建索引
     */
    @ResponseBody
    @RequestMapping("createIndex")
    public Object createIndex() {
        documentService.createIndex();
        return renderSuccess("创建索引成功！");
    }

    /**
     * @return
     * @description 删除索引
     */
    @ResponseBody
    @RequestMapping("dropIndex")
    public Object dropIndex() {
        documentService.dropIndex();
        return renderSuccess("删除索引成功！");
    }

    /**
     * @description ES查询
     * @param page 当前页
     * @param size 分页大小
     * @param classicId 查询类别
     * @param keywords 查询关键字
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDoc")
    public Object queryDoc(int page,int size,String classicId,String keywords) {
        return documentService.queryDoc(page,size,classicId,keywords);
    }

    /**
     * @description ES查询
     * @param page 当前页
     * @param size 分页大小
     * @param typeId 查询classicId下的typeId,两者之间是父子关系
     * @param keywords 查询关键字
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDocByTypeId")
    public Object queryDocByTypeId(int page,int size,String typeId,String keywords) {
        return documentService.queryDocByTypeId(page,size,typeId,keywords);
    }

    /**
     * @description 查看
     * @param fileId
     * @param model
     * @return
     */
    @RequestMapping("/viewPage/{fileId}")
    public String viewPage(@PathVariable("fileId") String fileId, Model model){
        Document document = documentService.get(fileId);
        model.addAttribute("document",document);
        return "jsp/document/documentView";
    }
}
