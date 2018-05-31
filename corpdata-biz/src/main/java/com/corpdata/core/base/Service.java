package com.corpdata.core.base;

import org.apache.ibatis.exceptions.TooManyResultsException;

import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;

import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
	Result save(T model);//持久化
    Result save(List<T> models);//批量持久化
    Result deleteById(String id);//通过主鍵刪除
    Result deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”
    Result update(T model);//更新
    T findById(String id);//通过ID查找
    T findBy(String fieldName, Object value) throws TooManyResultsException; //通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”
    List<T> findByCondition(Condition condition);//根据条件查找
    String findByPage(DataGridRequestDTO dgRequest);//分页查询结果(返回分页JsonData)
    List<T> findAll();//获取所有
    
}
