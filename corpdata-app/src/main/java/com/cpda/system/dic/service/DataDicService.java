package com.cpda.system.dic.service;

import com.cpda.common.base.BaseService;
import com.cpda.common.result.Result;
import com.cpda.system.dic.entity.SysDataDic;
import java.util.List;

public interface DataDicService extends BaseService<SysDataDic> {

    Result delete(String id, String dicType);

    /**
     * @param id
     * @return
     * @desc: 根据id查找字典名称
     */
    String getDataDicText(String id);

    /**
     * @return
     * @desc: 获取下拉json数据
     */
    String findByCombox();

    /**
     * @return
     * @desc: 根据类型，获取下拉json数据
     */
    String findByComboxByCode(String code);

    List<SysDataDic> findAllCode(String typeid);
}
