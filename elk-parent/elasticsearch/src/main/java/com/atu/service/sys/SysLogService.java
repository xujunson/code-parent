package com.atu.service.sys;

import com.atu.entity.sys.SysLog;
import com.atu.util.result.PageInfo;

public interface SysLogService {
	void save(SysLog sysLog);
	void delete(String id);
	SysLog get(String id);
	void selectDataGrid(PageInfo pageInfo, SysLog sysLog);
}
