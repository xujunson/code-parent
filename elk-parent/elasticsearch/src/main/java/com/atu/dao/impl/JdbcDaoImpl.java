package com.atu.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atu.dao.JdbcDao;


@Repository
public class JdbcDaoImpl implements JdbcDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int saveOrUpdate(String sql, Map<String, Object> paramMap) {
		return this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int delete(String sql, Map<String, Object> paramMap) {
		return this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int getCount(String sql, Map<String, Object> paramMap) {
		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
	}
	
	@Override
	public int[] batchExecute(String sql, final List<Object[]> paramList) {
		return this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			// 返回更新的记录数
			@Override
			public int getBatchSize() {
				return paramList.size();
			}

			// 设置参数
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] objs = paramList.get(i);
				for (int j = 0, length = objs.length; j < length; j++) {
					ps.setObject(j + 1, objs[j]);
				}
			}
		});
	}


	@Override
	public List<Map<String, Object>> findAll(String sql, Map<String, Object> paramMap) {
		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 1, count = meta.getColumnCount(); i <= count; i++) {
					Object obj = rs.getObject(i);
					String columnLabel = meta.getColumnLabel(i);
					if (obj instanceof java.sql.Clob) {
						java.sql.Clob clob = rs.getClob(i);
						map.put(columnLabel, clob.getSubString((long) 1, (int) clob.length()));
					} else if (obj instanceof java.sql.Date || obj instanceof java.sql.Timestamp) {
						java.sql.Timestamp timestamp = rs.getTimestamp(i);
						map.put(columnLabel, timestamp);
					} else {
						map.put(columnLabel, obj);
					}
				}
				return map;
			}
		});
	}

}
