package com.atu.service.sys;

import com.atu.entity.sys.User;
import com.atu.util.result.PageInfo;

import java.io.Serializable;
import java.util.List;

public interface UserService {
	Serializable insert(User user);

	User selectVoById(String id);

	void update(User user);

	void selectDataGrid(PageInfo pageInfo, User user);

	void deleteUserById(String id);

	User getUserByUsernameAndPassword(User user);

	List<User> selectByLoginName(User user);

	User selectById(String userId);

	List<User> selectByUsername(String username);

	void updatePwdByUserId(String userId, String hex);
	

	void save(User user);

	List<User> findByOrgAndStatus();

	User get(String id);

	/**
	 * 根据ids集合查询用户
	 * @param ids
	 * @return
	 */
	List<User>getUserByIds(String ids);
	User findByPhone(String phone);

	/**
	 * 注销用户
	 * @param id
	 */
	void cancel(String id);

    /**
     * 查询所有用户
     * @return
     */
	List<User> finAll();
	/**
	 * 通过姓名查询用户
	 * @param name
	 * @return
	 */
	List<User> findUsersByName(String name);
	List<User>getByIds(String ids);
	/**
	 * 按职位查询用户
	 * @param positionId
	 * @return
	 */
	List<User>findByPositionId(String positionId);
	/**
	 * 通过角色ID查询用户
	 * @param roleIds
	 * @return
	 */
	List<User>findByRoleIds(String roleIds);

	/**
	 *
	 * 修改用户信息
	 * @param user
	 */
	void updatePersonalInfo(User user);
}
