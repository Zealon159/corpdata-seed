package com.cpda.system.dic.service;

import com.cpda.common.base.BaseService;
import com.cpda.system.dic.entity.SysDicType;
import java.util.List;
import java.util.Map;

public interface DicTypeService extends BaseService<SysDicType> {
    /**
     * 获取下拉json数据
     *
     * @return
     */
    String findByCombox();

    List<SysDicType> selectAllByCondition(String key, Object value);

    Map<String, Object> getSysDicTypeNav();
}
