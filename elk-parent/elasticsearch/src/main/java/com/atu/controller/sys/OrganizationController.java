package com.atu.controller.sys;

import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import com.atu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.controller.base.BaseController;
import com.atu.entity.sys.Organization;
import com.atu.service.sys.OrganizationService;
@RequestMapping("organization")
@Controller
public class OrganizationController extends BaseController{
	@Autowired
	private OrganizationService organizationService;
	  /**
     * 部门管理主页
     * ${we}
     * @return
     */
    @RequestMapping(value = "/manager")
    public String manager(Model model) {
        return "jsp/sys/organization/organizationList";
    }

    /**
     * 部门资源树
     * @return
     */
    @RequestMapping(value = "/tree",method=RequestMethod.POST)
    @ResponseBody
    public Object tree() {
        return organizationService.selectTree();
    }

    /**
     * 部门列表
     * #o-2
     * @return
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public Object treeGrid(Organization org) {
        return organizationService.selectTreeGrid(org);
    }

    /**
     * 添加部门页
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage() {
        return "jsp/sys/organization/organizationAdd";
    }

    /**
     * 添加部门
     *
     * @param organization
     * @return
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public Object add(@Valid Organization organization) {
        organization.setCreateTime(new Date());
        organization.setId(UUID.randomUUID().toString());
        organizationService.insert(organization);
        return renderSuccess("添加成功！");
    }

    /**
     * 编辑部门页
     *
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        Organization organization = organizationService.selectById(id);
        if(organization!=null){
            if(StringUtils.isNotNull(organization.getPid())){
                Organization o=organizationService.selectById(organization.getPid());
                if(o!=null){
                    organization.setpName(o.getName());
                }
            }
        }
        model.addAttribute("organization", organization);
        return "jsp/sys/organization/organizationEdit";
    }

    /**
     * 编辑部门
     *
     * @param organization
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(@Valid Organization organization) {
        organizationService.updateById(organization);
        return renderSuccess("编辑成功！");
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        organizationService.deleteById(id);
        return renderSuccess("删除成功！");
    }
    
    /**
    * 部门选择页面
    * @param flag=true|false 多选 |单选
    * @return
    */
   @RequestMapping("/organizationSelect")
   public String organizationSelect(String deptids,Boolean flag,Model model) {
   	model.addAttribute("deptids", deptids);
   	model.addAttribute("flag", flag);
   	model.addAttribute("orgs",organizationService.getByIds(deptids));
   	return "jsp/sys/organization/organizationSelect";
   }
   
   @RequestMapping(value = "/treeSelect",method=RequestMethod.POST)
   @ResponseBody
   public Object treeSelect(String deptids) {
       return organizationService.tree(deptids);
   }

    /**
     * 通过部门名称查询
     * @param name
     * @return
     */
   @ResponseBody
   @RequestMapping("/getNameLike")
   public Object getNameLike(String name){
       return organizationService.getByNameLike(name);
   }
}