package com.atu.service.sys;

import com.atu.entity.sys.Position;
import com.atu.util.result.PageInfo;
import com.atu.util.result.Tree;

import java.io.Serializable;
import java.util.List;

public interface PositionService {
	Serializable save(Position position);

	void delete(String id);

	void update(Position position);

	Position get(String id);
	void selectDataGrid(PageInfo pageInfo);
	List<Tree> selectTree();
	List<Tree> tree(String positionIds);
	List<Position> selectTreeGrid(Position position);
	List<Position> getByNameLike(String name);
	List<Position>getByIds(String ids);
}
