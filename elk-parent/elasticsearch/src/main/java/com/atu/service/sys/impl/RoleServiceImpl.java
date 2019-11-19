package com.atu.service.sys.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.Resource;
import com.atu.entity.sys.Role;
import com.atu.entity.sys.RoleResource;
import com.atu.entity.sys.UserRole;
import com.atu.service.sys.RoleService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import com.atu.util.result.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private BaseDao<Role, String> roleDao;
	@Autowired
	private BaseDao<UserRole, String> userRoleDao;
	@Autowired
	private BaseDao<RoleResource, String> roleResourceDao;
	@Autowired
	private BaseDao<Resource, String> resourceDao;

	public List<Role> selectAll() {
		String hql = "from Role";
		return roleDao.find(hql);
	}

	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		String hql = "from Role where 1=1 ";
		Map<String,Object>condition=pageInfo.getCondition();
		if(StringUtils.isNotNull(condition.get("name"))) {
			hql+=" and name like '%"+condition.get("name")+"%'";
		}
		List<Role> roles = roleDao.findPage(hql, pageInfo.getNowpage(), pageInfo.getPagesize());
		pageInfo.setRows(roles);
		String countHql="select count(*)  "+hql;
		int total=Integer.parseInt(roleDao.getCount(countHql).toString());
		pageInfo.setTotal(total);

	}

	@Override
	public Object selectTree() {
		List<Tree> trees = new ArrayList<Tree>();
		List<Role> roles = this.selectAll();
		for (Role role : roles) {
			// 过滤掉超级管理员
			if (!role.getName().equals("admin")) {
				Tree tree = new Tree();
				tree.setId(role.getId().toString());
				tree.setText(role.getName());
				trees.add(tree);
			}
		}
		return trees;
	}

	@Override
	public List<String> selectResourceIdListByRoleId(String id) {
		List<String> resourceIds = new ArrayList<String>();
		String hql = "from RoleResource where roleId=?";
		List<RoleResource> roleResources = roleResourceDao.find(hql, id);
		for (RoleResource vo : roleResources) {
			resourceIds.add(vo.getResourceId().toString());
		}
		return resourceIds;
	}

	@Override
	public void updateRoleResource(String id, String resourceIds) {
		// 先删除后添加,有点爆力
		RoleResource roleResource = new RoleResource();
		roleResource.setRoleId(id);
		String hql = "delete from RoleResource where roleId=?";
		roleResourceDao.execute(hql, id);
		String[] resourceIdArray = resourceIds.split(",");
		for (String resourceId : resourceIdArray) {
			roleResource = new RoleResource();
			roleResource.setId(UUID.randomUUID().toString());
			roleResource.setRoleId(id);
			roleResource.setResourceId(resourceId);
			roleResourceDao.save(roleResource);
		}
	}

	@Override
	public void insert(Role role) {
		role.setId(UUID.randomUUID().toString());
		roleDao.save(role);
	}


	@Override
	public void deleteById(String id) {
		// 删除用户-角色
		String hql1 = "delete from UserRole where roleId=?";
		userRoleDao.execute(hql1, id);
		// 删除角色-资源
		String hql2 = "delete from RoleResource where roleId=?";
		roleResourceDao.execute(hql2, id);
		// 删除角色
		roleDao.delete(Role.class, id);
	}

	@Override
	public Role selectById(String id) {
		return roleDao.get(Role.class, id);
	}

	@Override
	public void updateById(Role role) {
		roleDao.update(role);
	}

	/**
	 * shiro权限 url地址 role角色
	 */
	@Override
	public Map<String, Set<String>> selectResourceMapByUserId(String userId) {
		Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
		List<String> roleIdList = selectRoleIdListByUserId(userId);
		Set<String> urlSet = new HashSet<String>();
		Set<String> roles = new HashSet<String>();
		for (String roleId : roleIdList) {
			List<String> resourceList = selectResourceIdListByRoleId(roleId);
			if (resourceList != null && resourceList.size() > 0) {
				for (String resourceId : resourceList) {
					Resource res = resourceDao.get(Resource.class,resourceId);
					if (StringUtils.isNotBlank(res.getUrl())) {
						urlSet.add(res.getUrl());
					}
				}
			}
			Role role = selectById(roleId);
			if (role != null) {
				roles.add(role.getCode());
			}
		}
		resourceMap.put("urls", urlSet);
		resourceMap.put("roles", roles);
		return resourceMap;
	}

	// 查询用户角色列表
	private List<String> selectRoleIdListByUserId(String userId) {
		List<String> roleIdList = new ArrayList<String>();
		String hql = "from UserRole where userId=?";
		List<UserRole> userRoles = userRoleDao.find(hql, userId);
		if (userRoles.size() > 0) {
			for (UserRole ur : userRoles) {
				roleIdList.add(ur.getRoleId().toString());
			}
		}
		return roleIdList;
	}

	@Override
	public List<Role> findbyName(String name) {
		String hql = "from Role where name=?";
		return roleDao.find(hql, name);
	}

	@Override
	public String getUserids(String roleids) {
		String userids="";
		String hql="from UserRole where roleId in("+StringUtils.convertSingleStr(roleids)+")";
		List<UserRole>urs=userRoleDao.find(hql);
		if(urs.size()>0) {
			for(UserRole ur:urs) {
				userids+=ur.getId()+",";
			}
			userids=userids.substring(0, userids.lastIndexOf(","));
		}
		return userids;
	}

}
