package com.cWww.goods.dao;

import java.util.List;

public interface BaseDAO<T> {

	public boolean insert(T t);

	public boolean delete(T t);

	public boolean update(T t);

	public List<T> findAll();

	public T findById(T t);
	
	public List<T> findByCondition(Object...params);

}
