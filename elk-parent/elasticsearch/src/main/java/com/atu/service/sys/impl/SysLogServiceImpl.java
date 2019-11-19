package com.atu.service.sys.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.SysLog;
import com.atu.service.sys.SysLogService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	 @Autowired
	 private BaseDao<SysLog,String>sysLogDao;
	@Override
	public void save(SysLog sysLog) {
		sysLog.setId(UUID.randomUUID().toString());
		sysLog.setCreateTime(new Date());
		sysLogDao.save(sysLog);

	}

	@Override
	public void delete(String id) {
		sysLogDao.delete(SysLog.class, id);
	}

	@Override
	public SysLog get(String id) {
		return sysLogDao.get(SysLog.class, id);
	}

	@Override
	public void selectDataGrid(PageInfo pageInfo,SysLog sysLog) {
		 String hql="from SysLog where 1=1 ";
		 List<Object>params=new ArrayList<Object>();
		 if(StringUtils.isNotNull(sysLog.getUsername())){
		 	hql+=" and username like ?";
		 	params.add("%"+sysLog.getUsername()+"%");
		 }
		 if(StringUtils.isNotNull(sysLog.getOptType())){
		 	hql+=" and optType =?";
		 	params.add(sysLog.getOptType());
		 }
		 if(StringUtils.isNotNull(sysLog.getCreateTimeStart())){
		 	hql+=" and createTime>=?";
		 	params.add(sysLog.getCreateTimeStart());
		 }
		 if(StringUtils.isNotNull(sysLog.getGetCreateTimeEnd())){
		 	hql+=" and createTime<=?";
		 	params.add(sysLog.getGetCreateTimeEnd());
		 }
		 hql+=" order by createTime desc";
	     List<SysLog> sysLogs=sysLogDao.findPage(hql, pageInfo.getNowpage(), pageInfo.getPagesize(),params);
	     pageInfo.setRows(sysLogs);
		String countHql="select count(*)  "+hql;
		int total=Integer.parseInt(sysLogDao.getCountByList(countHql,params).toString());
	     pageInfo.setTotal(total);

	}

}
