package com.corpdata.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.Date;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public Result save(T record) {
    	Date date = new Date();
    	BaseEntity r = (BaseEntity)record;
    	r.setId(CorpdataUtil.getUUID());
    	r.setCreated(date);
    	r.setModified(date);
    	r.setCreater(UserUtil.getCurrentUserid());
    	r.setModifier(UserUtil.getCurrentUserid());
        if(mapper.insert(record)>0){
        	return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public Result save(List<T> records) {
        mapper.insertList(records);
        return ResultUtil.success();
    }

    public Result update(T record) {
    	Date date = new Date();
    	BaseEntity r = (BaseEntity)record;
    	r.setModified(date);
    	r.setModifier(UserUtil.getCurrentUserid());
        if(mapper.update(record)>0){
        	return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }
    
    public Result deleteById(String id) {
    	if(mapper.deleteById(id)>0){
    		return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public Result deleteByIds(String ids) {
        mapper.deleteByIds(ids);
        return ResultUtil.success();
    }
    
    public T findById(String id) {
		return mapper.selectById(id);
	}
    
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		Page<T> list = (Page<T>) mapper.selectAll(dgRequest.getParams());
		return PageConvertUtil.getGridJson(list);
	}
}
