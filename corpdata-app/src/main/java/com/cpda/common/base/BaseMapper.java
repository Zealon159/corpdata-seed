package com.cpda.common.base;

import java.util.List;
import java.util.Map;

/**
 * MyBatis Mapper基础接口，其他Mapper接口 请继承该接口
 * 
 */
public interface BaseMapper<T>{
	
	int insert(T record);								//新增记录
	int insertList(List<T> recordList);					//批量新增
	int update(T record);								//修改记录
	int deleteById(String id);							//按id删除记录
	int deleteByIds(String ids);						//按ids删除记录
	T selectById(String id);							//按id查询实体
	List<T> selectByIds(String ids);					//按id查询集合
	int selectCount(Map<String, Object> params);		//按条件查询总数(params可空)
	List<T> selectAll(Map<String, Object> params);		//按条件查询集合(params可空)
	boolean existsWithKeyName(Object key);				//按keyname判断记录是否已存在
}
