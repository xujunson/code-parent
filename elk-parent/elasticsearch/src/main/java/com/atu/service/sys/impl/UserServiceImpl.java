package com.atu.service.sys.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.*;
import com.atu.service.sys.PositionService;
import com.atu.service.sys.UserInfoExtService;
import com.atu.service.sys.UserService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private BaseDao<User, String> userDao;
	@Autowired
	private BaseDao<Organization, String> organizationDao;
	@Autowired
	private BaseDao<Role, String> roleDao;
	@Autowired
	private BaseDao<UserRole, String> userRoleDao;
	@Autowired
	private PositionService positionService;
	@Autowired
	private UserInfoExtService userInfoExtService;
	@Override
	public Serializable insert(User user) {
		user.setCreateTime(new Date());
		String id = UUID.randomUUID().toString();
		user.setId(id);
		//设置职位
		if(StringUtils.isNotNull(user.getPositionId())) {
			Position position = positionService.get(user.getPositionId());
			if(position!=null) {
				user.setPositionName(position.getName());
			}
		}
		//设置部门
		if(StringUtils.isNotNull(user.getOrgId())) {
			Organization organization = organizationDao.get(Organization.class, user.getOrgId());
			if(organization!=null) {
				user.setOrgName(organization.getName());
			}
		}
		String userid =(String) userDao.save(user);
		// 添加角色
		if (!StringUtils.isNull(user.getRoleIdList())) {
			String roleIds[] = user.getRoleIdList().trim().split(",");
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setId(UUID.randomUUID().toString());
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleDao.save(userRole);
			}
		}
		return userid;
	}

	
	@Override
	public User selectVoById(String id) {
		return userDao.get(User.class, id);
	}

	@Override
	public void update(User user) {
		User vo = selectVoById(user.getId().toString());
		user.setCreateTime(vo.getCreateTime());
		if(!vo.getPositionId().equals(user.getPositionId())) {
			//设置职位
			if(StringUtils.isNotNull(user.getPositionId())) {
				Position position = positionService.get(user.getPositionId());
				if(position!=null) {
					user.setPositionName(position.getName());
				}
			}else {
				user.setPositionName("");
			}
		}else {
			Position position = positionService.get(user.getPositionId());
			if(position!=null) {
				user.setPositionName(position.getName());
			}
		}
		if(!user.getOrgId().equals(vo.getOrgId())) {
			if(StringUtils.isNotNull(user.getOrgId())) {
				Organization organization = organizationDao.get(Organization.class, user.getOrgId());
				if(organization!=null) {
					user.setOrgName(organization.getName());
				}else {
					user.setOrgName("");
				}
			}
		}else {
			Organization organization = organizationDao.get(Organization.class, user.getOrgId());
			if(organization!=null) {
				user.setOrgName(organization.getName());
			}else {
				user.setOrgName("");
			}
		}
		userDao.update(user);
		// 修改角色
		if (!StringUtils.isNull(user.getRoleIdList())) {
			String hql = "from UserRole where userId=?";
			List<UserRole> urList = userRoleDao.find(hql, user.getId());
			// 删除角色关联
			if (urList.size() > 0) {
				String hql1 = "delete from UserRole where userId=?";
				userRoleDao.execute(hql1, user.getId());
			}
			// 添加角色关联
			String roleIds[] = user.getRoleIdList().trim().split(",");
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setId(UUID.randomUUID().toString());
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleDao.save(userRole);
			}
		}

	}

	// u-1
	@Override
	public void selectDataGrid(PageInfo pageInfo, User user) {
		String hql = "from User where 1=1 ";
		if (user.getName() != null && !user.getName().trim().equals("")) {
			hql += " and name like '%" + user.getName().trim() + "%'";
		}
		if (user.getPhone() != null && !user.getPhone().equals("")) {
			hql += " and phone='" + user.getPhone().trim() + "'";
		}
		if (user.getOrgId() != null && !user.getOrgId().equals("") ) {
			hql += " and orgId='" + user.getOrgId() + "'";
		}
		hql += " and status=" + user.getStatus();
		hql+=" and isShow=1";
		List<User> users = userDao.findPage(hql, pageInfo.getNowpage(), pageInfo.getPagesize());
		if (users.size() > 0) {
			for (User vo : users) {
				// 设置所属部门
				if (StringUtils.isNotNull(vo.getOrgId())) {
					Organization o = organizationDao.get(Organization.class, vo.getOrgId());
					if(o!=null) {
					  vo.setOrgName(o.getName());
					}
				}
				
				//设置职位
				/*if(StringUtils.isNotNull(vo.getPositionId())) {
					Position position = positionService.get(vo.getPositionId());
					if(position!=null) {
						user.setPositionName(position.getName());
					}
				}*/
				// 设置角色
				String userId = vo.getId();
				String hql12 = "from UserRole where userId=?";
				List<UserRole> urList = userRoleDao.find(hql12, userId);
				if (urList.size() > 0) {
					List<Role> roles = new ArrayList<Role>();
					for (UserRole ur : urList) {
						roles.add(roleDao.get(Role.class, ur.getRoleId()));
					}
					if (roles.size() > 0) {
						String result = "";
						for (Role r : roles) {
							result += r.getName() + ",";
						}
						result = result.substring(0, result.lastIndexOf(","));
						vo.setRoleNameList(result);
					}
				}

			}
		}
		pageInfo.setRows(users);
		String countHql="select count(*)  "+hql;
		int total=Integer.parseInt(userDao.getCount(countHql).toString());
		pageInfo.setTotal(total);
	}

	@Override
	public void deleteUserById(String id) {
		userDao.delete(User.class, id);
		// 删除用户-角色
		String hql = "delete from UserRole where userId=?";
		userRoleDao.execute(hql, id);
	}

	@Override
	public User getUserByUsernameAndPassword(User user) {
		String hql = "from User where username=? and password=? and status=1";
		List<User> users = userDao.find(hql, user.getUsername(), user.getPassword());
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<User> selectByLoginName(User user) {
		String hql = "from User where username=? and status=1";
		return userDao.find(hql, user.getUsername());
	}

	@Override
	public User selectById(String userId) {
		User vo = userDao.get(User.class, userId);
		convert(vo);
		return vo;
	}

	public void convert(User vo){
		// 设置所属部门
		if (StringUtils.isNotNull(vo.getOrgId())) {
			Organization o = organizationDao.get(Organization.class, vo.getOrgId());
			if(o!=null) {
				vo.setOrgName(o.getName());
			}
		}
		//设置职位
		/*if(StringUtils.isNotNull(vo.getPositionId())) {
			Position position = positionService.get(vo.getPositionId());
			if(position!=null) {
				vo.setPositionName(position.getName());
			}
		}*/
		// 设置角色
		String hql1 = "from UserRole where userId=?";
		List<UserRole> urList = userRoleDao.find(hql1, vo.getId());
		if (urList.size() > 0) {
			List<Role> roles = new ArrayList<Role>();
			for (UserRole ur : urList) {
				roles.add(roleDao.get(Role.class, ur.getRoleId()));
			}
			vo.setRoleList(roles);
			if (roles.size() > 0) {
				String roleIdList = "";
				String result = "";
				for (Role r : roles) {
					result += r.getName() + ",";
					roleIdList += r.getId() + ",";
				}
				result = result.substring(0, result.lastIndexOf(","));
				roleIdList = roleIdList.substring(0, roleIdList.lastIndexOf(","));
				vo.setRoleNameList(result);
				vo.setRoleIdList(roleIdList);
			}
		}

		//设置头像
		UserInfoExt infoExt=userInfoExtService.getByObjId(vo.getId());
		if(infoExt!=null){
			vo.setUserInfoExt(infoExt);
		}
	}
	@Override
	public List<User> selectByUsername(String username) {
		String hql = "from User where username=? and status=1";
		return userDao.find(hql, username);
	}

	@Override
	public void updatePwdByUserId(String userId, String hex) {
		String hql = "update User set password=? where id=?";
		userDao.execute(hql, hex, userId);

	}

	@Override
	public void save(User user) {
		user.setId(UUID.randomUUID().toString());
		userDao.save(user);
	}

	@Override
	public List<User> findByOrgAndStatus() {
		String hql = "from User where orgId is null and status=1 and isShow=1";
		List<User> users = userDao.find(hql);
		return users;
	}

	@Override
	public User findByPhone(String phone) {
		String hql = "from User where phone=? and status=1";
		List<User> users = userDao.find(hql, phone);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public User get(String id) {
		User user = userDao.get(User.class,id);
		return user;
	}

	/**
	 * 根据ids集合查询用户
	 * @param ids
	 * @return
	 */
	@Override
	public List<User> getUserByIds(String ids) {
		List<User>users=new ArrayList<>();
		String hql="from User where id in("+StringUtils.convertSingleStr(ids)+")";
		users=userDao.find(hql);
		return users;
	}


	public List<Role> findAllRoleByuserId(String userid) {
		List<Role> roles = new ArrayList<Role>();
		String hql = "from UserRole where userId=?";
		List<UserRole> userRoles = userRoleDao.find(hql, userid);
		if (userRoles.size() > 0) {
			for (UserRole ur : userRoles) {
				Role role = roleDao.get(Role.class, ur.getRoleId());
				roles.add(role);
			}
		}
		return roles;
	}



	@Override
	public List<User> finAll() {
		String hql="from User where 1=1 and isShow=1 and status=1";
		List<User> users = userDao.find(hql);
		if(users.size()>0) {
			for(User user:users) {
				convert(user);
			}
		}
		return users;
	}

	protected void convertUsers(List<User>users){
		if(users.size()>0) {
			for(User user:users) {
				convert(user);
			}
		}
	}
	@Override
	public List<User> findUsersByName(String name) {
		List<User>users=new ArrayList<User>();
		if(StringUtils.isNotNull(name)) {
			String hql="from User where 1=1 and name like ? and isShow=1 and status=1";
			users=userDao.find(hql, "%"+name+"%");
			convertUsers(users);
		}
		return users;
	}

	@Override
	public List<User> getByIds(String ids) {
		List<User>users=new ArrayList<User>();
		if(StringUtils.isNotNull(ids)){
			String hql="from User where id in("+StringUtils.convertSingleStr(ids)+")";
			users=userDao.find(hql);
		}
		return users;
	}

	/**
	 * 注销用户
	 * @param id
	 */
	@Override
	public void cancel(String id) {
		String hql="update User set status=0 where id=?";
		userDao.execute(hql,id);
	}

	/**
	 * 按职位查询用户
	 */
	@Override
	public List<User> findByPositionId(String positionId) {
		String hql="from User where positionId=?";
		return userDao.find(hql, positionId);
	}

	/**
	 * 通过角色ID查询用户
	 */
	@Override
	public List<User> findByRoleIds(String roleIds) {
		String hql="select u from User u where u.status=1 and u.isShow=1 and u.id in (select ur.userId from UserRole ur where ur.roleId in("+StringUtils.convertSingleStr(roleIds)+"))";
		return userDao.find(hql);
	}

	/**
	 * 修改用户信息
	 * @param user
	 */
	@Override
	public void updatePersonalInfo(User user) {
		String hql="update User set name=?,username=?,sex=?,superior=?,superiorName=?,orgId=?,phone=?,idCard=? where id=?";
		userDao.execute(hql,user.getName(),user.getUsername(),user.getSex()
		,user.getSuperior(),user.getSuperiorName(),user.getOrgId(),user.getPhone(),user.getIdCard(),user.getId());
	}
}

