package com.atu.controller.sys;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.commons.shiro.ShiroUser;
import com.atu.controller.base.BaseController;
import com.atu.entity.sys.Resource;
import com.atu.service.sys.ResourceService;
import com.atu.util.StringUtils;

@RequestMapping("resource")
@Controller
public class ResourceController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	 /**
     * 资源管理页
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "jsp/sys/resource/resourceList";
    }

	 /**
     * 菜单树
     *
     * @return
     */
    @RequestMapping(value = "/tree",method=RequestMethod.POST)
    @ResponseBody
    public Object tree(HttpServletRequest request) {
        ShiroUser shiroUser=getShiroUser();
        return resourceService.selectTree(shiroUser,0);
    }

   
    /**
     * 资源管理列表
     *
     * @return
     */
    @RequestMapping(value = "/treeGrid",method=RequestMethod.POST)
    @ResponseBody
    public Object treeGrid(Resource resource) {
        return resourceService.selectAll(resource);
    }

    /**
     * 添加资源页
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage() {
        return "jsp/sys/resource/resourceAdd";
    }

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(Resource resource) {
        resource.setCreateTime(new Date());
        resourceService.insert(resource);
        return renderSuccess("添加成功！");
    }

    /**
     * 查询所有的菜单
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public Object allMenu() {
        return resourceService.selectAllMenu();
    }

    /**
     * 查询所有的资源tree
     */
    @RequestMapping("/allTrees")
    @ResponseBody
    public Object allTree(String roleId) {
        return resourceService.selectAllTree(roleId);
    }

    /**
     * 编辑资源页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        Resource resource = resourceService.selectById(id);
        if(resource!=null){
            if(StringUtils.isNotNull(resource.getPid())){
                Resource o=resourceService.selectById(resource.getPid());
                if(o!=null){
                    resource.setpName(o.getName());
                }
            }
        }
        model.addAttribute("resource", resource);
        return "jsp/sys/resource/resourceEdit";
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(@Valid Resource resource) {
        resourceService.updateById(resource);
        return renderSuccess("编辑成功！");
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        resourceService.deleteById(id);
        return renderSuccess("删除成功！");
    }

    /**
     * 通过名称查询
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNameLike")
    public Object getNameLike(String name){
        return resourceService.getByNameLike(name);
    }

    /**
     * 选择页面
     * @param flag=true|false 多选 |单选
     * @return
     */
    @RequestMapping("/resourceSelect")
    public String organizationSelect(String resIds,Boolean flag,Model model) {
        model.addAttribute("resIds", resIds);
        model.addAttribute("flag", flag);
        model.addAttribute("resources",resourceService.getByIds(resIds));
        return "jsp/sys/resource/resourceSelect";
    }
    /**
     * 树
     * @return
     */

    @RequestMapping(value = "/treeSelect",method=RequestMethod.POST)
    @ResponseBody
    public Object treeSelect(String resIds) {
        return resourceService.tree(resIds);
    }

}
