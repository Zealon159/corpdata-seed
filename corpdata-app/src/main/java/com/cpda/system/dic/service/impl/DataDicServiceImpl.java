package com.cpda.system.dic.service.impl;


import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.common.utils.CommonUtil;
import com.cpda.system.dic.dao.DataDicMapper;
import com.cpda.system.dic.entity.SysDataDic;
import com.cpda.system.dic.service.DataDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataDicServiceImpl extends AbstractBaseService<SysDataDic> implements DataDicService {
	
	//@Autowired
	//private RedisService redisService;

    @Autowired
    private DataDicMapper sysDataDicMapper;

    @Override
    public Result save(SysDataDic record) {
        Result result = super.save(record);
        if (result.getCode() == 200) {
            //删除缓存
            //redisService.delete("data_dic_json:"+record.getDicType());
        }
        return result;
    }

    @Override
    public Result update(SysDataDic record) {
        Result result = super.update(record);
        if (result.getCode() == 200) {
            //删除缓存
            //redisService.delete("data_dic_json:"+record.getDicType());
        }
        return result;
    }

    public Result delete(String id, String dicType) {
        if (sysDataDicMapper.deleteById(id) > 0) {
            //删除缓存
            //redisService.delete("data_dic_json:"+dicType);
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }
    }

    /**
     * @param id
     * @return
     * @desc: 根据id查找字典名称
     */
    public String getDataDicText(String id) {
        String text = "";
        if (CommonUtil.isNotBlank(id)) {
            SysDataDic sysDataDic = sysDataDicMapper.selectById(id);
            if (sysDataDic != null) {
                text = sysDataDic.getDicName();
            }
        }
        return text;
    }

    /**
     * @return
     * @desc: 获取下拉json数据
     */
    public String findByCombox() {
        String json = CommonUtil.getComboxJson(sysDataDicMapper.selectAllByCombox());
        return json;
    }

    /**
     * @return
     * @desc: 根据类型，获取下拉json数据
     */
    //@Cacheable(value="data_dic_json",cacheManager = "redisCacheManagerString",key="#p0")
    public String findByComboxByCode(String code) {
        String json = CommonUtil.getComboxJson(sysDataDicMapper.selectAllByComboxByCode(code));
        return json;
    }

    public List<SysDataDic> findAllCode(String typeid) {
        return sysDataDicMapper.findAllCode(typeid);
    }
}
