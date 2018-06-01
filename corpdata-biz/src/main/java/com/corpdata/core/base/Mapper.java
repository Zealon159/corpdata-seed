package com.corpdata.core.base;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.select.ExistsWithPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 * https://mapperhelper.github.io/docs/2.use/
 * 
 */
public interface Mapper<T> extends
		SelectOneMapper<T>,
		SelectMapper<T>,
		SelectCountMapper<T>,
		SelectByPrimaryKeyMapper<T>,
		ExistsWithPrimaryKeyMapper<T>,
		BaseInsertMapper<T>,
		BaseUpdateMapper<T>,
		BaseDeleteMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T> {
}
