package com.corpdata.core.base;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.core.exception.ServiceException;
import com.corpdata.system.org.entity.OrgDept;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Condition;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public Result save(T record) {
        if(mapper.insertSelective(record)>0){
        	return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public Result save(List<T> records) {
        mapper.insertList(records);
        return ResultUtil.success();
    }

    public Result deleteById(String id) {
    	if(mapper.deleteByPrimaryKey(id)>0){
    		return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public Result deleteByIds(String ids) {
        mapper.deleteByIds(ids);
        return ResultUtil.success();
    }

    public Result update(T record) {
        if(mapper.updateByPrimaryKeySelective(record)>0){
        	return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.select(null);
    }

    public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		Page<T> list = (Page<T>) mapper.select(null);
		return PageConvertUtil.getGridJson(list);
	}
}
