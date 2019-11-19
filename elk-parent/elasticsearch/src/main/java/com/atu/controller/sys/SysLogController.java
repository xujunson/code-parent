package com.atu.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.controller.base.BaseController;
import com.atu.entity.sys.SysLog;
import com.atu.service.sys.SysLogService;
import com.atu.util.result.PageInfo;
/**
 * <p>
 * 系统日志 用户 前端控制器
 * </p>
 *  日志管理
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {

  @Autowired private SysLogService sysLogService;
    
    @RequestMapping("/manager")
    public String manager() {
        return "jsp/sys/sysLog/sysLogList";
    }

    @RequestMapping(value="/dataGrid",method=RequestMethod.POST)
    @ResponseBody
    public PageInfo dataGrid(SysLog sysLog,Integer page, Integer rows,
                             @RequestParam(value = "sort", defaultValue = "create_time") String sort,
                             @RequestParam(value = "order", defaultValue = "DESC") String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        sysLogService.selectDataGrid(pageInfo,sysLog);
        return pageInfo;
    }
    
}
