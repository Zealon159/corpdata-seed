package com.corpdata.system.dic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.corpdata.common.api.redis.RedisService;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.system.dic.dao.SysDataDicMapper;
import com.corpdata.system.dic.entity.SysDataDic;
import com.corpdata.system.dic.service.SysDataDicService;

@Service("sysDataDicService")
public class SysDataDicServiceImpl extends AbstractBaseService<SysDataDic> implements SysDataDicService{
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SysDataDicMapper sysDataDicMapper;

	@Override
	public Result save(SysDataDic record) {
		Result result = super.save(record);
		if (result.getCode()==200) {
			//删除缓存
			redisService.delete("data_dic_json:"+record.getDicType());
		}
		return result;
	}
	
	@Override
	public Result update(SysDataDic record) {
		Result result = super.update(record);
		if (result.getCode()==200) {
			//删除缓存
			redisService.delete("data_dic_json:"+record.getDicType());
		}
		return result;
	}
	
	public Result delete(String id,String dicType) {
		if (sysDataDicMapper.deleteById(id)>0) {
			//删除缓存
			redisService.delete("data_dic_json:"+dicType);
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	/**
	 * @desc: 根据id查找字典名称
	 * @param id
	 * @return
	 */
	public String getDataDicText(String id){
		String text = "";
		if(CorpdataUtil.isNotBlank(id)){
			SysDataDic sysDataDic = sysDataDicMapper.selectById(id);
			if(sysDataDic!=null){
				text = sysDataDic.getDicName();
			}
		}
		return text;
	}
	
	/**
	 * @desc: 获取下拉json数据
	 * @return
	 */
	public String findByCombox(){
		String json = CorpdataUtil.getComboxJson(sysDataDicMapper.selectAllByCombox());
		return json;
	}
	
	/**
	 * @desc: 根据类型，获取下拉json数据
	 * @return
	 */
	@Cacheable(cacheNames="data_dic_json",cacheManager = "cacheManager" , key="#p0")
	public String findByComboxByCode(String code){
		System.out.println("select db ..");
		String json = CorpdataUtil.getComboxJson(sysDataDicMapper.selectAllByComboxByCode(code));
		return json;
	}
	
	public List<SysDataDic> findAllCode(String typeid){
		return sysDataDicMapper.findAllCode(typeid);
	}
}
