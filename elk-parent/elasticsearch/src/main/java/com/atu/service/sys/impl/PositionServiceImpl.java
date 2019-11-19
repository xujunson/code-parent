package com.atu.service.sys.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.Position;
import com.atu.entity.sys.User;
import com.atu.service.sys.PositionService;
import com.atu.util.StringUtils;
import com.atu.util.result.PageInfo;
import com.atu.util.result.Tree;

/**
 * 职位管理
 */
@Service("positionService")
public class PositionServiceImpl implements PositionService {
    @Autowired
   private BaseDao<Position,String>positionDao;
    @Autowired
    private BaseDao<User,String>userDao;
    @Override
    public Serializable save(Position position) {
    	position.setId(UUID.randomUUID().toString());
        return positionDao.save(position);
    }
    
    @Override
    public void delete(String id) {
        Position position=get(id);
        if (position!=null){
            positionDao.delete(position);
        }
        //更新用户职位
        String hql="update User set positionId='' and positionName='' where positionId=?";
        userDao.execute(hql, id);
    }

    @Override
    public void update(Position position) {
        positionDao.update(position);
    }

    @Override
    public Position get(String id) {
        return positionDao.get(Position.class,id);
    }
   

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        String hql="from Position where 1=1 ";
        Map<String,Object>condition=pageInfo.getCondition();
        List<Object> params=new ArrayList<Object>();
        if(StringUtils.isNotNull(condition.get("name"))) {
        	hql+=" and name like '%"+condition.get("name")+"%'";
        }
        if(!pageInfo.getSort().equals("")) {
            hql+=" order by "+pageInfo.getSort()+" "+pageInfo.getOrder();
        }
        List<Position> Positions=positionDao.findPage(hql, pageInfo.getNowpage(), pageInfo.getPagesize(),params);
        pageInfo.setRows(Positions);
        String countHql="select count(*)  "+hql;
        int total=Integer.parseInt(positionDao.getCountByList(countHql,params).toString());
        pageInfo.setTotal(total);
    }



	@Override
	public List<Tree> selectTree() {
		List<Position> PositionList = selectTreeGrid(null);
		List<Tree> trees = new ArrayList<Tree>();
		if (PositionList != null) {
			for (Position position : PositionList) {
				Tree tree = new Tree();
				tree.setId(position.getId());
				tree.setText(position.getName());
				tree.setIconCls(position.getIconCls());
				tree.setPid(position.getPid());
				trees.add(tree);
			}
		}
		return trees;
	}


	@Override
	public List<Position> selectTreeGrid(Position position) {
		String hql = "from Position where 1=1";
		if(position!=null) {
			if(StringUtils.isNotNull(position.getName())) {
				hql+=" and name like '%"+position.getName()+"%'";
			}
		}
		hql+=" order by seq asc";
		return positionDao.find(hql);
	}

    @Override
    public List<Tree> tree(String positionIds) {
        List<Tree>trees=new ArrayList<Tree>();
        List<Position> positions = selectTreeGrid(null);
        for (int i = 0; i < positions.size(); i++) {
            Tree tree=new Tree();
            tree.setId(positions.get(i).getId());
            tree.setPid(StringUtils.isNotNull(positions.get(i).getPid())?positions.get(i).getPid():"0");
            tree.setText(positions.get(i).getName());
            tree.setIconCls("fi-folder");
            if(!positionIds.equals("")) {
                if(positionIds.contains(positions.get(i).getId())) {
                    tree.setChecked(true);
                }
            }
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<Position> getByNameLike(String name) {
        String hql="from Position where name like ?";
        List<Position>positions=positionDao.find(hql,"%"+name+"%");
        return positions;
    }

    @Override
    public List<Position> getByIds(String ids) {
        List<Position> positions=new ArrayList<Position>();
        if(StringUtils.isNotNull(ids)) {
            String hql = "from Position where id in(" + StringUtils.convertSingleStr(ids)+ ")";
            positions = positionDao.find(hql);
        }
        return positions;
    }
}
