package com.cpda.common.base;

import com.cpda.common.result.Result;
import java.util.List;

/**
 * Service 层基础接口，其他Service 接口 请继承该接口
 */
public interface BaseService<T> {
	Result save(T model);//持久化
    Result save(List<T> models);//批量持久化
    Result deleteById(Long id);//通过主鍵刪除
    Result deleteByIds(Long[] ids);//批量刪除 eg：ids -> “1,2,3,4”
    Result update(T model);//更新
    T findById(Long id);//通过ID查找
    //List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”
    String findByPage(int page,int rows);//分页查询结果(返回分页JsonData)
    
}
