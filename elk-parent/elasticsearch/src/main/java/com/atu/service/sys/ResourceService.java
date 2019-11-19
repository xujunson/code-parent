package com.atu.service.sys;

import java.util.List;

import com.atu.commons.shiro.ShiroUser;
import com.atu.entity.sys.Resource;
import com.atu.util.result.Tree;

public interface ResourceService {
	 List<Resource> selectAll(Resource resource);

	    List<Tree> selectAllMenu();

	    List<Tree> selectAllTree(String roleId);

	    List<Tree> selectTree(ShiroUser shiroUser, Integer resourceType);

		void deleteById(String id);

		void updateById(Resource resource);

		Resource selectById(String id);

		void insert(Resource resource);

		List<Resource>getByNameLike(String name);
		List<Tree>tree(String resIds);
		List<Resource>getByIds(String resIds);


	
}
