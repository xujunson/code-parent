package com.atu.service.sys;

import java.util.List;

import com.atu.entity.sys.Organization;
import com.atu.entity.sys.User;
import com.atu.util.result.Tree;

public interface OrganizationService {
	//#o-1
	List<Tree> selectTree();
	List<Organization> selectTreeGrid(Organization org);
	void deleteById(String id);

	void updateById(Organization organization);

	Organization selectById(String id);

	void insert(Organization organization);

	List<Organization> findAll();

	List<Organization> getSubOrg(String pid);

	Organization getTopOrg();// 查询总机构
	// 通过OrgId查询用户

	List<User> getUserByOrgId(String orgId);

	// 部门-用户树
	List<Tree> findUserAndOrgByPId(String pid);
	List<Tree> findUserAndOrgByPId(String pid, String userids);

	List<Tree> findUserAndOrgTree(String pid);
	List<Tree> findUserAndOrgTree(String pid, String userids);

	String getRootId(String orgid);
	
	List<Organization>getOrgByRootId(String rootId);
	List<Tree> tree(String deptids);
	String getUserids(String orgids);

	/**
	 * 查询部门
	 * @param name 部门名称
	 * @return
	 */
	List<Organization> getByNameLike(String name);
	List<Organization>getByIds(String ids);

	
}
