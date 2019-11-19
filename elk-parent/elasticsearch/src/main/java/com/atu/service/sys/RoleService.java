package com.atu.service.sys;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.atu.entity.sys.Role;
import com.atu.util.result.PageInfo;

public interface RoleService {
	void selectDataGrid(PageInfo pageInfo);

    Object selectTree();

    List<String> selectResourceIdListByRoleId(String id);

    void updateRoleResource(String id, String resourceIds);

	void insert(Role role);

	void deleteById(String id);

	Role selectById(String id);

	void updateById(Role role);
	Map<String, Set<String>> selectResourceMapByUserId(String userId);
	List<Role>findbyName(String name);
	
	String getUserids(String roleids);

}
