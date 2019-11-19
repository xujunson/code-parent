package com.atu.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.Organization;
import com.atu.entity.sys.User;
import com.atu.service.sys.OrganizationService;
import com.atu.util.StringUtils;
import com.atu.util.result.Tree;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
	private static final Logger log=LoggerFactory.getLogger(OrganizationServiceImpl.class);
	@Autowired
	private BaseDao<Organization, String> organizationDao;
	@Autowired
	private BaseDao<User, String> userDao;

	// #o-1
	@Override
	public List<Tree> selectTree() {
		List<Organization> organizationList = selectTreeGrid(null);
		List<Tree> trees = new ArrayList<Tree>();
		if (organizationList != null) {
			for (Organization organization : organizationList) {
				Tree tree = new Tree();
				tree.setId(organization.getId());
				tree.setText(organization.getName());
				tree.setIconCls(organization.getIcon());
				tree.setPid(organization.getPid());
				trees.add(tree);
			}
		}
		return trees;
	}

	@Override
	public List<Organization> selectTreeGrid(Organization org) {
		String hql = "from Organization where 1=1";
		if(org!=null) {
			if(StringUtils.isNotNull(org.getName())) {
				hql+=" and name like '%"+org.getName()+"%'";
			}
		}
		hql+="  order by seq asc";
		return organizationDao.find(hql);
	}

	@Override
	public void deleteById(String id) {
		organizationDao.delete(Organization.class, id);
		// 设置user的orgId=null
		String hql = "update User set orgId='' and orgName='' where orgId=?";
		userDao.execute(hql, id);

	}

	@Override
	public void updateById(Organization organization) {
		Organization vo = selectById(organization.getId());
		organization.setCreateTime(vo.getCreateTime());
		organizationDao.update(organization);
	}

	@Override
	public Organization selectById(String id) {
		return organizationDao.get(Organization.class, id);
	}

	@Override
	public void insert(Organization organization) {
		organization.setCreateTime(new Date());
		organization.setId(UUID.randomUUID().toString());
		organizationDao.save(organization);
	}



	@Override
	public List<Organization> findAll() {
		String hql = "from Organization";
		return organizationDao.find(hql);
	}

	@Override
	public List<Organization> getSubOrg(String pid) {
		if (pid != null) {
			String hql = "from Organization where pid=?";
			return organizationDao.find(hql, pid);
		} else {
			String hql = "from Organization where pid=''";
			return organizationDao.find(hql);
		}
	}

	@Override
	public Organization getTopOrg() {
		String hql = "from Organization where pid=''";
		return organizationDao.find(hql).get(0);
	}

	@Override
	public List<User> getUserByOrgId(String orgId) {
		String hql = "from User where orgId=? and status=1";
		return userDao.find(hql, orgId);
	}

	@Override
	public List<Tree> findUserAndOrgByPId(String pid) {
		List<Tree> trees = new ArrayList<Tree>();
		List<User> users = getUserByOrgId(pid);
		if (users.size() > 0) {
			List<Tree> treeUsers = new ArrayList<Tree>();
			for (User user : users) {
				Tree tree = new Tree();
				tree.setText(user.getName());
				tree.setId("USER" + user.getId());
				tree.setIconCls("fi-torso");
				tree.setPid(pid.toString());
				tree.setType("1");
				treeUsers.add(tree);
			}
			trees.addAll(treeUsers);
		}
		List<Organization> organizations = getSubOrg(pid);
		if (organizations.size() > 0) {
			List<Tree> treeOrgs = new ArrayList<Tree>();
			for (Organization organization : organizations) {
				Tree tree = new Tree();
				tree.setText(organization.getName());
				tree.setId(organization.getId().toString());
				tree.setIconCls("fi-torsos-all");
				tree.setPid(pid == null ? "0" : pid.toString());
				tree.setType("0");
				treeOrgs.add(tree);
			}
			trees.addAll(treeOrgs);
		}
		return trees;
	}

	@Override
	public List<Tree> findUserAndOrgTree(String pid) {
		List<Tree> trees = new ArrayList<Tree>();
		List<Tree> childList = findUserAndOrgByPId(pid);
		for (Tree entity : childList) {
			trees.add(entity);
			if (entity.getType().equals("0")) {
				trees.addAll(findUserAndOrgTree(entity.getId()));
			}
		}
		return trees;
	}
	@Override
	public List<Tree> findUserAndOrgTree(String pid,String userids) {
		List<Tree> trees = new ArrayList<Tree>();
		List<Tree> childList = findUserAndOrgByPId(pid,userids);
		for (Tree entity : childList) {
			trees.add(entity);
			if (entity.getType().equals("0")) {
				trees.addAll(findUserAndOrgTree(entity.getId(),userids));
			}
		}
		return trees;
	}

	// 子节点向上递归得到根节点
	@Override
	public String getRootId(String orgid) {
		Organization o = organizationDao.get(Organization.class, orgid);
		return o.getPid().equals("") ? o.getId() : getRootId(o.getPid());
	}

	@Override
	public List<Organization> getOrgByRootId(String rootId) {
		List<Organization> orgs = new ArrayList<Organization>();
		Organization o = organizationDao.get(Organization.class, rootId);
		orgs.add(o);
		orgs.addAll(treeJson(rootId));
		return orgs;
	}

	public List<Organization> treeJson(String pid) {
		List<Organization> orgs = new ArrayList<Organization>();
		List<Organization> child = getSubOrg(pid);
		if (child.size() > 0) {
			for (Organization o : child) {
				orgs.add(o);
				orgs.addAll(treeJson(o.getId()));
			}
		}
		return orgs;
	}

	@Override
	public List<Tree> findUserAndOrgByPId(String pid, String userids) {
		List<Tree> trees = new ArrayList<Tree>();
		List<User> users = getUserByOrgId(pid);
		if (users.size() > 0) {
			List<Tree> treeUsers = new ArrayList<Tree>();
			for (User user : users) {
				Tree tree = new Tree();
				tree.setText(user.getName());
				tree.setId("USER" + user.getId());
				log.info("用户ids={}",userids);
				log.info("是否包含："+userids.contains(user.getId()));
				if(StringUtils.isNotNull(userids)) {
					if(userids.contains(user.getId())) {
						tree.setChecked(true);
					}
				}
				tree.setIconCls("fi-torso");
				tree.setPid(pid.toString());
				tree.setType("1");
				treeUsers.add(tree);
			}
			trees.addAll(treeUsers);
		}
		List<Organization> organizations = getSubOrg(pid);
		if (organizations.size() > 0) {
			List<Tree> treeOrgs = new ArrayList<Tree>();
			for (Organization organization : organizations) {
				Tree tree = new Tree();
				tree.setText(organization.getName());
				tree.setId(organization.getId().toString());
				tree.setIconCls("fi-torsos-all");
				tree.setPid(pid == null ? "0" : pid.toString());
				tree.setType("0");
				treeOrgs.add(tree);
			}
			trees.addAll(treeOrgs);
		}
		return trees;
	}

	@Override
	public List<Tree> tree(String deptids) {
		List<Tree>trees=new ArrayList<Tree>();
		List<Organization> orgs = selectTreeGrid(null);
		for (int i = 0; i < orgs.size(); i++) {
			Tree tree=new Tree();
			tree.setId(orgs.get(i).getId());
			tree.setPid(StringUtils.isNotNull(orgs.get(i).getPid())?orgs.get(i).getPid():"0");
			tree.setText(orgs.get(i).getName());
			tree.setIconCls(orgs.get(i).getIcon());
			if(!deptids.equals("")) {
				if(deptids.contains(orgs.get(i).getId())) {
					tree.setChecked(true);
				}
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public String getUserids(String orgids) {
		String userids="";
		String hql="from User where orgId in(?)";
		List<User> users = userDao.find(hql, orgids);
		if(users.size()>0) {
			for(User user:users) {
				userids+=user.getId()+",";
			}
			userids=userids.substring(0,userids.lastIndexOf(","));
		}
		return userids;
	}

	@Override
	public List<Organization> getByNameLike(String name) {
		String hql="from Organization where name like ?";
		List<Organization>orgs=organizationDao.find(hql,"%"+name+"%");
		return orgs;
	}

	@Override
	public List<Organization> getByIds(String ids) {
		List<Organization> orgs=new ArrayList<Organization>();
		if(StringUtils.isNotNull(ids)) {
			String hql = "from Organization where id in(" + StringUtils.convertSingleStr(ids) + ")";
			 orgs = organizationDao.find(hql);
		}
		return orgs;
	}
}
