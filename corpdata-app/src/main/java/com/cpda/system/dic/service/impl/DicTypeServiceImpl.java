package com.cpda.system.dic.service.impl;

import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.utils.CommonUtil;
import com.cpda.system.dic.dao.DicTypeMapper;
import com.cpda.system.dic.entity.SysDicType;
import com.cpda.system.dic.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicTypeServiceImpl extends AbstractBaseService<SysDicType> implements DicTypeService {

    @Autowired
    private DicTypeMapper dicTypeMapper;

    /**
     * 获取下拉json数据
     *
     * @return
     */
    public String findByCombox() {
        String json = CommonUtil.getComboxJson(dicTypeMapper.selectAllByCombox());
        return json;
    }

    public List<SysDicType> selectAllByCondition(String key, Object value) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (CommonUtil.isNotBlank(key)) {
            params.put(key, value);
        }
        return dicTypeMapper.selectAllByCondition(params);
    }

    public Map<String, Object> getSysDicTypeNav() {
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
