package com.atu.controller.sys;

import com.atu.commons.shiro.PasswordHash;
import com.atu.commons.shiro.ShiroUser;
import com.atu.controller.base.BaseController;
import com.atu.entity.Attach;
import com.atu.entity.sys.User;
import com.atu.entity.sys.UserInfoExt;
import com.atu.service.AttachService;
import com.atu.service.sys.OrganizationService;
import com.atu.service.sys.UserInfoExtService;
import com.atu.service.sys.UserService;
import com.atu.util.Const;
import com.atu.util.JsonUtil;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	private static final Logger logger=LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	 @Autowired
	 private PasswordHash passwordHash;
	 @Autowired
	 private ApplicationContext applicationContext;
	 @Autowired
	 private OrganizationService organizationService;
	 @Autowired
     private AttachService attachService;
    @Autowired
    private UserInfoExtService userInfoExtService;

	 /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(String id,Model model) {
        logger.info("跳转到用户管理页面1");
        return "jsp/sys/user/userList";
    }

    /**
     * 用户管理列表
     *
     * @param user
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value="/dataGrid",method=RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(User user, Integer page, Integer rows, String sort, String order) {
    	if(user.getStatus()==null) {
    		user.setStatus(1);
    	}
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        userService.selectDataGrid(pageInfo,user);
        return pageInfo;
    }
    
   
   
    /**
     * 添加用户页
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(Model model) {
        return "jsp/sys/user/userAdd";
    }
  
    
    /**
     * 添加用户
     *
     * @param user
     * @return
     * @throws Exception 
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Object add(User user,HttpServletRequest request) throws Exception {
        List<User> list = userService.selectByLoginName(user);
        System.out.println(JsonUtil.toJson(list));
        if (list != null && !list.isEmpty()) {
            return renderError("登录名已存在!");
        }
        User u1=userService.findByPhone(user.getPhone());
        if(u1!=null) {
        	 return renderError("手机号为【"+user.getPhone()+"】的用户已存在!");
        }
        /**
         * 密码加密
         */
        String salt = StringUtils.getUUId();
        String pwd = passwordHash.toHex(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(pwd);
        user.setId(UUID.randomUUID().toString());
        userService.insert(user);
        return renderSuccess("添加成功");
    }

    /**
     * 编辑用户页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        User user = userService.selectById(id);
        System.out.println(user.getRoleIdList());
        model.addAttribute("user", user);
        return "jsp/sys/user/userEdit";
    }
    /**
     * 查看用户页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewPage")
    public String viewPage(Model model, String id) {
        User user = userService.selectById(id);
        System.out.println(user.getRoleIdList());
        model.addAttribute("user", user);
        return "jsp/sys/user/userView";
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Object edit(User user) {
        // 更新密码
    	User vo = userService.selectById(user.getId());
    	String salt = vo.getSalt();
        if (!"".equals(user.getPassword().trim())) {
            String pwd = passwordHash.toHex(user.getPassword(), salt);
            user.setPassword(pwd);
            user.setSalt(vo.getSalt());
        }else {
        	user.setPassword(vo.getPassword());
        	user.setSalt(salt);
        }
        userService.update(user);
        return renderSuccess("修改成功！");
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @RequestMapping("/editPwdPage")
    public String editPwdPage() {
        return "jsp/sys/user/userEditPwd";
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param pwd
     * @return
     */
    @RequestMapping(value="/editUserPwd",method=RequestMethod.POST)
    @ResponseBody
    public Object editUserPwd(String oldPwd, String pwd) {
        User user = userService.selectById(getUserId());
        String salt = user.getSalt();
        if (!user.getPassword().equals(passwordHash.toHex(oldPwd, salt))) {
            return renderError("老密码不正确!");
        }
        userService.updatePwdByUserId(getUserId(), passwordHash.toHex(pwd, salt));
        return renderSuccess("密码修改成功！");
    }
    /**
     * 删除用户
     *
     * @param id
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Object delete(String id,HttpServletRequest request) throws Exception {
    	ShiroUser user=getShiroUser();
        String currentUserId = user.getId();
        if (id.equals(currentUserId)) {
            return renderError("不可以删除自己！");
        }else {
        		userService.deleteUserById(id);
              return renderSuccess("删除成功！");
        }
    }

    /**
     * 注销用户
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/cancel",method=RequestMethod.POST)
    @ResponseBody
    public Object cancel(String id,HttpServletRequest request) throws Exception {
        ShiroUser user=getShiroUser();
        String currentUserId = user.getId();
        if (id.equals(currentUserId)) {
            return renderError("不可以删除自己！");
        }else {
           userService.cancel(id);
            return renderSuccess("删除成功！");
        }
    }


    /**
     * 设置批量分配用户部门
     * @param model
     * @return
     */
    @RequestMapping("tosetUserOrg")
    public String tosetUserOrg(Model model) {
    	List<User>users=userService.findByOrgAndStatus();
    	model.addAttribute("users", users);
    	return "jsp/sys/user/setUserOrg";
    }
    @RequestMapping("setUserOrg")
    @ResponseBody
    public Object setUserOrg(String orgid,String userids) {
        logger.info("用户id集合："+userids);
    	if(!userids.equals("")) {
    		String []userids_=userids.split(",");
    		for(String userid:userids_) {
    			 logger.info("用户id："+userid);
    			User user=userService.get(userid);
    			user.setOrgId(orgid);
    			userService.update(user);
    		}
    	}
    	return renderSuccess("分配成功！");
    }
    
    /**
     * 查询status=1和orgid=null的用户，即导入的用户
     * @return
     */
    @ResponseBody
    @RequestMapping("searchUserByOrgAndStatus")
    public Object searchUserByOrgAndStatus() {
    	List<User>users=userService.findByOrgAndStatus();
    	return users;
    }
    

  //解决中文请求参数传入不同浏览器编码不同的问题
  	public static String processFileName(HttpServletRequest request, String fileNames) {  
  	       String codedfilename = null;  
  	       try {  
  	           String agent = request.getHeader("USER-AGENT");  
  	           if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
  	                   && -1 != agent.indexOf("Trident")) {// ie  
  	  
  	               String name = java.net.URLEncoder.encode(fileNames, "UTF8");  
  	  
  	               codedfilename = name;  
  	           } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等  
  	  
  	  
  	               codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");  
  	           }  
  	       } catch (Exception e) {  
  	           e.printStackTrace();  
  	       }  
  	       return codedfilename;  
  	   }  
  	
  	 /**
     * 单选
     * @param model
     * @return
     */
    @RequestMapping("toSelectUser")
    public String toSelectUser(String userids,Model model){
    	model.addAttribute("userids", userids);
        return "jsp/sys/user/selectSingleUser";
    }

    /**
     * 多选
     * @param model
     * @return
     */
    @RequestMapping("toMultiSelectUser")
    public String toMultiSelectUser(String userids,Model model){
    	model.addAttribute("userids", userids);
        return "jsp/sys/user/selectMultiUser";
    }

    /**
     * 部门
     * @param flag=true|false 多选 |单选
     * @return
     */
    @RequestMapping("/userSelect")
    public String userSelect(String userids,Boolean flag,Model model) {
        model.addAttribute("userids", userids);
        model.addAttribute("flag", flag);
        model.addAttribute("users",userService.getByIds(userids));
        return "jsp/sys/user/userSelect2";
    }
    
    /**
     * 用户-部门树
     * @return
     */
    @ResponseBody
    @RequestMapping("findOrgAndUserTree")
    public Object findOrgAndUserTree(String userids){
        return organizationService.findUserAndOrgTree(null,userids);
    }
  

    /**
     * 通过用户名搜索用户
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNameLike")
    public Object findUsersByName(String name) {
        List<User>users=userService.findUsersByName(name);
        return users;
    }

    /**
     * 跳转到用户基本信息页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUserInfo")
    public String toUserInfo(String id,Model model) {
    	model.addAttribute("id", id);
    	User user=userService.selectById(id);
    	model.addAttribute("user",user);
    	return "jsp/sys/user/userinfo";
    }
    /**
     * 获取用户基本信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public Object getUserInfo(String id) {
    	return userService.selectVoById(id);
    }

    @ResponseBody
    @RequestMapping("/updatePersonalInfo")
    public Object updatePersonalInfo(User user){
        userService.updatePersonalInfo(user);
        return  renderSuccess("修改成功");
    }

    /**
     * 上传头像
     * @param request
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadPhoto")
    public Object uploadPhoto(HttpServletRequest request,
                              HttpServletResponse response)throws IllegalStateException, IOException {
        String id=request.getParameter("id");
        logger.info("id={}",id);
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            List<Attach>attachList=new ArrayList<Attach>();
            String path="";
            String attachId="";
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        String myFileName = file.getOriginalFilename();
                        if (myFileName.trim() != "") {
                            String fileName = file.getOriginalFilename();
                            String fileExt = fileName.substring(
                                    fileName.lastIndexOf(".") + 1).toLowerCase();
                            SimpleDateFormat df = new SimpleDateFormat(
                                    "yyyyMMddHHmmss");
                            String newFileName = df.format(new Date());
                            String fileNames = newFileName
                                    + new Random().nextInt(1000) + "." + fileExt;
                            df= new SimpleDateFormat(
                                    "yyyyMMdd");
                            String basePath= df.format(new Date()) +"/"+ fileNames;
                            String base= "/upload/"+basePath;
                             path= Const.BASE_UPLOAD_PATH+base;
                            System.out.println("path:"+path);
                            File localFile = new File(path);
                            if (!localFile.exists()) {// 如果文件夹不存在，自动创建
                                localFile.mkdirs();
                            }
                            file.transferTo(localFile);
                            //保存附件
                            Attach attach=new Attach();
                            attach.setId(UUID.randomUUID().toString());
                            attach.setCreateTime(new Date());
                            attach.setName(fileName);
                            attach.setType(fileExt);
                            attach.setPath(base);
                            attachService.save(attach);
                            attachList.add(attach);
                            path=attach.getPath();
                            /**************保存头像*************/
                            UserInfoExt userInfoExt=new UserInfoExt();
                            userInfoExt.setObjId(id);
                            userInfoExt.setPhoto(path);
                            userInfoExt.setAttachId(attach.getId());
                            userInfoExtService.save(userInfoExt);
                            attachId=attach.getId();
                        }
                    }
                }
            }
            return renderSuccess(attachId);
    }
}