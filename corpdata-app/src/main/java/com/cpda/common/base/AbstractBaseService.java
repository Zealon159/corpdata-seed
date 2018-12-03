package com.cpda.common.base;

import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.common.utils.PageConvertUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
    	r.setCreated(date);
    	r.setModified(date);
    	//r.setCreater(UserUtil.getCurrentUserid());
    	//r.setModifier(UserUtil.getCurrentUserid());
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
    	//r.setModifier(UserUtil.getCurrentUserid());
        if(mapper.update(record)>0){
        	return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }
    
    public Result deleteById(Long id) {
    	if(mapper.deleteById(id)>0){
    		return ResultUtil.success();
    	}else{
    		return ResultUtil.fail();
    	}
    }

    public Result deleteByIds(Long[] ids) {
        mapper.deleteByIds(ids); //todo
        return ResultUtil.success();
    }
    
    public T findById(Long id) {
		return mapper.selectById(id);
	}
    
    public List<T> findByIds(Long ids) {
        return mapper.selectByIds(ids);
    }

    public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getRows());
		Page<T> list = (Page<T>) mapper.selectAll(dgRequest.getParams());
		return PageConvertUtil.getGridJson(list);
	}
}
