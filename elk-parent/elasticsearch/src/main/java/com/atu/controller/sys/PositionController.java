package com.atu.controller.sys;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.controller.base.BaseController;
import com.atu.entity.sys.Position;
import com.atu.service.sys.PositionService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
/**
 * 职位管理
 */
@Controller
@RequestMapping("/position")
public class PositionController extends BaseController{
	 @Autowired
	    private PositionService positionService;
	    /**
	     * 主页
	     * @return
	     */
	    @RequestMapping("manager")
	    public String manager() {
	        return "jsp/sys/position/positionTreeList";
	    }
	    /**
	     * 列表
	     * @param page
	     * @param rows
	     * @param sort
	     * @param order
	     * @return
	     */
	    @RequestMapping(value="/dataGrid",method=RequestMethod.POST)
	    @ResponseBody
	    public Object dataGrid(Position position, Integer page, Integer rows, String sort, String order) {
	        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
	        Map<String,Object>condition=new HashMap<String,Object>();
	        if(StringUtils.isNotNull(position.getName())) {
	        	condition.put("name", position.getName());
	        }
	        pageInfo.setCondition(condition);
	        positionService.selectDataGrid(pageInfo);
	        return pageInfo;
	    }

	    /**
	     * 添加页
	     * @return
	     */
	    @RequestMapping("/addPage")
	    public String addPage() {
	        return "jsp/sys/position/positionAdd";
	    }

	    /**
	     * 添加
	     * @param position
	     * @return
	     */
	    @RequestMapping(value="/add",method=RequestMethod.POST)
	    @ResponseBody
	    public Object add(Position position) {
	        positionService.save(position);
	        return renderSuccess("添加成功！");
	    }

	    /**
	     * 删除
	     * @param id
	     * @return
	     */
	    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
	    @ResponseBody
	    public Object delete(@PathVariable("id") String id) {
	        positionService.delete(id);
	        return renderSuccess("删除成功！");
	    }
	    /**
	     * 编辑页
	     * @param model
	     * @param id
	     * @return
	     */
	    @RequestMapping("/editPage/{id}")
		public String editPage(Model model, @PathVariable("id")String id) {
		    Position position = positionService.get(id);
		    if(position!=null){
		    	if(StringUtils.isNotNull(position.getPid())){
		    		Position p=positionService.get(position.getPid());
		    		if(p!=null){
		    			position.setpName(p.getName());
					}
				}
			}
	        model.addAttribute("position", position);
	        return "jsp/sys/position/positionEdit";
	    }

	    /**
	     * 查看页
	     * @param model
	     * @param id
	     * @return
	     */
	    @RequestMapping("/viewPage/{id}")
	    public String viewPage(Model model, @PathVariable("id") String id) {
	        Position position = positionService.get(id);
			if(position!=null){
				if(StringUtils.isNotNull(position.getPid())){
					Position p=positionService.get(position.getPid());
					if(p!=null){
						position.setpName(p.getName());
					}
				}
			}
	        model.addAttribute("position", position);
	        return "jsp/sys/position/positionView";
	    }
	    /**
	     * 编辑
	     * @param position
	     * @return
	     */
	    @RequestMapping(value = "/edit",method = RequestMethod.POST)
	    @ResponseBody
	    public Object edit(Position position) {
	        positionService.update(position);
	        return renderSuccess("编辑成功！");
	    }


	 	@RequestMapping(value = "/toTreeList")
		public String toTreeList(){
			return "jsp/sys/position/positionTreeList";
		}

	/**
	 * 选择页面
	 * @param flag=true|false 多选 |单选
	 * @return
	 */
	@RequestMapping("/positionSelect")
	public String organizationSelect(String positionIds,Boolean flag,Model model) {
		model.addAttribute("positionIds", positionIds);
		model.addAttribute("flag", flag);
		model.addAttribute("positions",positionService.getByIds(positionIds));
		return "jsp/sys/position/positionSelect";
	}
	   /**
	     * 树
	     * @return
	     */

		@RequestMapping(value = "/treeSelect",method=RequestMethod.POST)
		@ResponseBody
		public Object treeSelect(String positionIds) {
			return positionService.tree(positionIds);
		}

	    /**
	     * 树列表
	     * @return
	     */
	    @RequestMapping("/treeGrid")
	    @ResponseBody
	    public Object treeGrid(Position position) {
	        return positionService.selectTreeGrid(position);
	    }

		/**
		 * 通过名称查询
		 * @param name
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/getNameLike")
		public Object getNameLike(String name){
			return positionService.getByNameLike(name);
		}
}
