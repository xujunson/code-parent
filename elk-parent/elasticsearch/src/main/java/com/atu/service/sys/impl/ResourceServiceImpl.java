package com.atu.service.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atu.commons.shiro.ShiroUser;
import com.atu.dao.BaseDao;
import com.atu.entity.sys.Resource;
import com.atu.entity.sys.RoleResource;
import com.atu.entity.sys.UserRole;
import com.atu.service.sys.ResourceService;
import com.atu.util.ResourceComparator;
import com.atu.util.StringUtils;
import com.atu.util.result.Tree;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	private static final int RESOURCE_MENU = 0; // 菜单
	@Autowired
	private BaseDao<Resource, String> resourceDao;
	@Autowired
	private BaseDao<RoleResource, String> roleResourceDao;
	@Autowired
	private BaseDao<UserRole, String> userRoleDao;

	@Override
	public List<Resource> selectAll(Resource resource) {
		String hql = "from Resource where 1=1 ";
		if(resource!=null) {
			if(StringUtils.isNotNull(resource.getName())) {
				hql+=" and name like '%"+resource.getName()+"%'";
			}
		}
		hql+=" order by seq asc";
		return resourceDao.find(hql);
	}

	public List<Resource> selectByType(Integer type) {
		String hql = "from Resource where resourceType=?";
		return resourceDao.find(hql, type);
	}

	@Override
	public List<Tree> selectAllMenu() {
		List<Tree> trees = new ArrayList<Tree>();
		// 查询所有菜单
		List<Resource> resources = this.selectByType(RESOURCE_MENU);
		if (resources == null) {
			return trees;
		}
		Collections.sort(resources);//升序排列
		for (Resource resource : resources) {
			Tree tree = new Tree();
			tree.setId(resource.getId().toString());
			tree.setPid(StringUtils.isNotNull(resource.getPid())?resource.getPid().toString():"0");
			tree.setText(resource.getName());
			tree.setIconCls(resource.getIcon());
			tree.setAttributes(resource.getUrl());
			tree.setState(resource.getOpened());
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public List<Tree> selectAllTree(String roleId) {
		// 获取所有的资源 tree形式，展示
		List<Tree> trees = new ArrayList<Tree>();
		List<String> roleIdList = new ArrayList<String>();
		roleIdList.add(roleId);
		List<Resource> resourceList = selectResourceListByRoleIdList(roleIdList, null);
		List<String> resIds = new ArrayList<String>();
		for (Resource r : resourceList) {
			resIds.add(r.getId().toString());
		}
		List<Resource> resources = this.selectAll(null);
		if (resources == null) {
			return trees;
		}
		Collections.sort(resources);//升序排列
		for (Resource resource : resources) {
			Tree tree = new Tree();
			tree.setId(resource.getId().toString());
			tree.setPid(StringUtils.isNotNull(resource.getPid())?resource.getPid().toString():"0");
			tree.setText(resource.getName());
			tree.setIconCls(resource.getIcon());
			tree.setAttributes(resource.getUrl());
			tree.setState(resource.getOpened());
			if(StringUtils.contains(resIds,resource.getId().toString())) {
				tree.setChecked(true);
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public List<Tree> selectTree(ShiroUser shiroUser, Integer resourceType) {
		List<Tree> trees = new ArrayList<Tree>();
		List<Resource> resourceLists = new ArrayList<Resource>();
		System.out.println("角色:"+shiroUser.getRoles());
		if(StringUtils.contains(shiroUser.getRoles(),"ROLE_ADMIN")){
			String hql = "from Resource where resourceType=? order by seq asc";
			resourceLists = resourceDao.find(hql, resourceType);
		} else {
			List<String> roleIdList = selectRoleIdListByUserId(shiroUser.getId());
			if (roleIdList == null) {
				return trees;
			}
			resourceLists = selectResourceListByRoleIdList(roleIdList, resourceType);
		}
		if (resourceLists == null) {
			return trees;
		}
		Collections.sort(resourceLists);//升序排列
		for (Resource resource : resourceLists) {
			Tree tree = new Tree();
			tree.setId(resource.getId().toString());
			tree.setPid(StringUtils.isNotNull(resource.getPid())?resource.getPid().toString():"0");
			tree.setText(resource.getName());
			tree.setIconCls(resource.getIcon());
			tree.setAttributes(resource.getUrl());
			tree.setOpenMode(resource.getOpenMode());
			tree.setState(resource.getOpened());
			trees.add(tree);
		}
		return trees;
	}

	private List<String> selectRoleIdListByUserId(String id) {
		String hql = "from UserRole where userId=?";
		List<UserRole> userRoleIds = userRoleDao.find(hql, id);
		List<String> roleIds = new ArrayList<String>();
		for (UserRole userRole : userRoleIds) {
			roleIds.add(userRole.getRoleId().toString());
		}
		return roleIds;
	}

	private List<Resource> selectResourceListByRoleIdList(List<String> roleIdList, Integer resourceType) {
		List<Resource> resources = new ArrayList<Resource>();
		Set<String> resourceIdSet = new HashSet<String>();
		for (String roleId : roleIdList) {
			String hql = "from RoleResource where roleId=?";
			List<RoleResource> roleResources = roleResourceDao.find(hql, roleId);
			if (roleResources.size() > 0) {
				for (RoleResource rr : roleResources) {
					resourceIdSet.add(rr.getResourceId().toString());
				}
			}
		}
		if (resourceIdSet.size() > 0) {
			List<String> resList = new ArrayList<String>();
			for (String resourceId : resourceIdSet) {
				resList.add(resourceId);
			}
			for (String resId : resList) {
				String hql = "from Resource where id=? ";
				if (resourceType != null) {
					hql += " and resourceType=" + resourceType;
				}
				resources.addAll(resourceDao.find(hql, resId));
			}

		}

		ResourceComparator.sortList(resources, "seq", false);
		return resources;
	}

	@Override
	public void deleteById(String id) {
		resourceDao.delete(Resource.class, id);
		// 删除角色-资源
		String hql = "delete from RoleResource where resourceId=?";
		roleResourceDao.execute(hql, id);
		// 删除子资源
		hql = "delete from Resource where pid=?";
		resourceDao.execute(hql,  id);

	}

	@Override
	public void updateById(Resource resource) {
		Resource vo = selectById(resource.getId().toString());
		resource.setCreateTime(vo.getCreateTime());
		resourceDao.update(resource);
	}

	@Override
	public Resource selectById(String id) {
		return resourceDao.get(Resource.class, id);
	}

	@Override
	public void insert(Resource resource) {
		resource.setCreateTime(new Date());
		resource.setId(UUID.randomUUID().toString());
		resourceDao.save(resource);
	}

	@Override
	public List<Resource> getByNameLike(String name) {
		List<Resource>resources=new ArrayList<>();
		if(StringUtils.isNotNull(name)) {
			String hql = "from Resource where name like ?";
			resources=resourceDao.find(hql,"%"+name+"%");
		}
		return resources;
	}

	@Override
	public List<Tree> tree(String resIds) {
		List<Tree>trees=new ArrayList<Tree>();
		List<Resource> resources = new ArrayList<>();
		String hql="from Resource where 1=1 and resourceType=0 order by seq asc";
		resources=resourceDao.find(hql);
		for (int i = 0; i < resources.size(); i++) {
			Tree tree=new Tree();
			tree.setId(resources.get(i).getId());
			tree.setPid(StringUtils.isNotNull(resources.get(i).getPid())?resources.get(i).getPid():"0");
			tree.setText(resources.get(i).getName());
			tree.setIconCls(resources.get(i).getIcon());
			if(!resIds.equals("")) {
				if(StringUtils.contains(resIds,resources.get(i).getId())){
					tree.setChecked(true);
				}
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public List<Resource> getByIds(String resIds) {
		List<Resource>resources=new ArrayList<>();
		if(StringUtils.isNotNull(resIds)) {
			String hql = "from Resource where id in(" + StringUtils.convertSingleStr(resIds) + ")";
			resources=resourceDao.find(hql);
		}
		return resources;
	}
}
