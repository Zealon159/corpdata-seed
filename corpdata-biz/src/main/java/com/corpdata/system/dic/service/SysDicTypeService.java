package com.corpdata.system.dic.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.system.dic.dao.SysDicTypeMapper;
import com.corpdata.system.dic.entity.SysDicType;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("sysDicTypeService")
public class SysDicTypeService {
	
	@Autowired
	private SysDicTypeMapper sysDicTypeMapper;

	public Result insert(SysDicType record) {
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		if (sysDicTypeMapper.insert(record)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result update(SysDicType record) {
		record.setModified(new Date());
		if (sysDicTypeMapper.updateByPrimaryKey(record)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result delete(String id) {
		if (sysDicTypeMapper.deleteByPrimaryKey(id)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public String findByPage(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		Page<SysDicType> list = sysDicTypeMapper.selectAll();
		return PageConvertUtil.getGridJson(list);
	}
	
	/**
	 * 获取下拉json数据
	 * @return
	 */
	public String findByCombox(){
		String json = CorpdataUtil.getComboxJson(sysDicTypeMapper.selectAllByCombox());
		return json;
	}
	
	public List<SysDicType> selectAllByCondition(String key, Object value){
		Map<String, Object> params = new HashMap<String, Object>();
		if(CorpdataUtil.isNotBlank(key)){
			params.put(key, value);
		}
		return sysDicTypeMapper.selectAllByCondition(params);	
	}
	
	public Map<String, Object> getSysDicTypeNav(){
		Map<String, Object> group = new HashMap<String, Object>();
		List<SysDicType> projectList = selectAllByCondition("groupId", "project");
		List<SysDicType> customerList = selectAllByCondition("groupId", "customer");
		List<SysDicType> contractList = selectAllByCondition("groupId", "contract");
		group.put("code", 1);
		group.put("project", projectList);
		group.put("customer", customerList);
		group.put("contract", contractList);
		group.put("desc", "success");
		return group;
	}
	
}
