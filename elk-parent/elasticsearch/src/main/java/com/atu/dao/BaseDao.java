package com.atu.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDao<T, PK extends Serializable> {

	// 保存
	Serializable save(T o);

	// 批量保存
	void save(Collection<T> o);

	// 删除
	void delete(T o);

	// 根据ID删除
	void delete(Class<T> clazz, PK id);

	// 批量删除
	void deleteBatch(Collection<T> o);

	// 批量删除
	void deleteBatch2(Class<T> clazz, Collection<PK> ids);

	// 更新
	void update(T o);

	// 保存或更新
	void saveOrUpdate(T o);

	// 带参查询一条记录
	T get(String hql, Object... params);

	// get方式查询
	T get(Class<T> clazz, PK id);

	// load方式查询
	T load(Class<T> clazz, PK id);

	// 查询
	List<T> find(String hql);

	// 带参查询
	List<T> find(String hql, Object... params);
	// 带参查询
	List<T> findByList(String hql, List<Object> params);

	// 分页查询
	List<T> findPage(String hql, int page, int rows);
	
	// 带参分页查询
	List<T> findPageByObject(String hql, int page, int rows, Object... params);
	// 带参分页查询
   List<T> findPage(String hql, int page, int rows, List<Object> params);

	// 执行更新、删除、修改等语句
	Object execute(String hql, Object... params);
	//使用原生sql查询
	List<T> findBySQL(Class<T> clazz, String sql, Object... params);
	/**
	 *
	 * @Title: update
	 * @Description: 执行HQL
	 * @param: @param queryString
	 * @return: List<T>
	 * @throws
	 */
	public List<T> createQuery(final String hql) throws Exception;
	/**
	 *
	 * @Title: unique
	 * @Description: 返回唯一一条数据
	 * @param: @return
	 * @return: T
	 * @throws
	 */
	public T unique(String hql) throws Exception;
	
	//查询最大的值
	Object getMaxValue(Class<T> clazz, String propertyName);

	/**
	 * 获取记录总数
	 * @param hql
	 * @param params
	 * @return
	 */
	Long getCount(String hql, Object... params);
	Long getCountByList(String hql, List<Object> params);

	/**
	 * 查询几个字段
	 * @param hql
	 * @param params
	 * @return
	 */
	public List queryColoumns(String hql, Object... params);

}
