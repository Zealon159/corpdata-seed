package com.corpdata.system.dic.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.api.redis.RedisService;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.system.dic.dao.SysDataDicMapper;
import com.corpdata.system.dic.entity.SysDataDic;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("sysDataDicService")
public class SysDataDicService {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SysDataDicMapper sysDataDicMapper;

	public Result insert(SysDataDic record) {
		record.setId(CorpdataUtil.getUUID());
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		if (sysDataDicMapper.insert(record)>0) {
			//删除缓存
			redisService.delete("data_dic_json:"+record.getDicType());
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result update(SysDataDic record) {
		record.setModified(new Date());
		if (sysDataDicMapper.updateByPrimaryKey(record)>0) {
			//删除缓存
			redisService.delete("data_dic_json:"+record.getDicType());
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result delete(String id,String dicType) {
		if (sysDataDicMapper.deleteByPrimaryKey(id)>0) {
			//删除缓存
			redisService.delete("data_dic_json:"+dicType);
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public String findByPage(int pageNo, int pageSize, String dictype, String searchStr) {
		PageHelper.startPage(pageNo, pageSize);
		if(dictype.equals("0")){
			dictype = null;
		}
		Page<SysDataDic> list = sysDataDicMapper.selectAllByCondition(dictype, searchStr);
		
		return PageConvertUtil.getGridJson(list);
	}
	
	public String findByPage(int pageNo, int pageSize) {
		return findByPage(pageNo, pageSize, null, null);
	}
	
	public SysDataDic selectByPrimaryKey(String id){
		return sysDataDicMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @desc: 根据id查找字典名称
	 * @param id
	 * @return
	 */
	public String getDataDicText(String id){
		String text = "";
		if(CorpdataUtil.isNotBlank(id)){
			SysDataDic sysDataDic = sysDataDicMapper.selectByPrimaryKey(id);
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
	@Cacheable(value="data_dic_json",cacheManager = "redisCacheManagerString",key="#p0")
	public String findByComboxByCode(String code){
		String json = CorpdataUtil.getComboxJson(sysDataDicMapper.selectAllByComboxByCode(code));
		return json;
	}
	
	public List<SysDataDic> findAllCode(String typeid){
		return sysDataDicMapper.findAllCode(typeid);
	}
}
