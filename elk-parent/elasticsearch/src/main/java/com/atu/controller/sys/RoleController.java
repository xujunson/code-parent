package com.atu.controller.sys;

import com.atu.controller.base.BaseController;
import com.atu.entity.sys.Role;
import com.atu.service.sys.RoleService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("role")
@Controller
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("manager")
    public String manager() {
    	return "jsp/sys/role/roleList";
    }
    /**
     * 权限列表
     *
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value="/dataGrid",method=RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(Role role,Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String,Object>condition=new HashMap<String,Object>();
        if(StringUtils.isNotNull(role.getName())) {
        	condition.put("name", role.getName());
        }
        pageInfo.setCondition(condition);
        roleService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 权限树
     *
     * @return
     */
    @RequestMapping(value="/tree",method=RequestMethod.POST)
    @ResponseBody
    public Object tree() {
        return roleService.selectTree();
    }

    /**
     * 添加权限页
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage() {
        return "jsp/sys/role/roleAdd";
    }

    /**
     * 添加权限
     *
     * @param role
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Object add(Role role) {
    	List<Role>roleList=roleService.findbyName(role.getName());
    	if(roleList.size()==0) {
    	role.setId(UUID.randomUUID().toString());
        roleService.insert(role);
        return renderSuccess("添加成功！");
    	}else {
    		return renderError("用户角色名已存在！");
    	}
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
    	Role vo=roleService.selectById(id);
    	if(!vo.getName().equals("admin")) {
        roleService.deleteById(id);
        return renderSuccess("删除成功！");
    	}else {
    		return renderError("你没有删除超级管理员的权限！");
    	}
    }

    /**
     * 编辑权限页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        Role role = roleService.selectById(id);
        model.addAttribute("role", role);
        return "jsp/sys/role/roleEdit";
    }

    /**
     * 编辑权限
     *
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(Role role) {
    	Role vo=roleService.selectById(role.getId());
    	if(!vo.getName().equals("admin")) {
        roleService.updateById(role);
        return renderSuccess("编辑成功！");
    	}else {
    		return renderError("你没有编辑超级管理员的权限！");
    	}
    }

    /**
     * 授权页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(Model model, String id) {
        model.addAttribute("id", id);
        return "jsp/sys/role/roleGrant";
    }

    /**
     * 授权页面页面根据角色查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Object findResourceByRoleId(String id) {
        List<String> resources = roleService.selectResourceIdListByRoleId(id);
        return renderSuccess(resources);
    }

    /**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Object grant(String id, String resourceIds) {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }
    /**
     * 角色选择页面
     * @param roleids
     * @param flag=flase|true 多选 |单选
     * @return
     */
    @RequestMapping("/roleSelect")
    public String roleSelect(String roleids,Boolean flag,Model model) {
    	model.addAttribute("roleids", roleids);
    	model.addAttribute("flag", flag);
    	return "jsp/sys/role/roleSelect";
    }
    
}
