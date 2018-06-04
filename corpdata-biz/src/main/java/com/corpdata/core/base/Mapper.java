package com.corpdata.core.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.insert.InsertMapper;
import tk.mybatis.mapper.common.base.select.ExistsWithPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 * https://mapperhelper.github.io/docs/2.use/
 * 
 */
public interface Mapper<T> extends
		//SelectOneMapper<T>,
		//SelectMapper<T>,
		//SelectCountMapper<T>,
		ExistsWithPrimaryKeyMapper<T>,
		InsertMapper<T>,
		BaseUpdateMapper<T>,
		//UpdateByPrimaryKeyMapper<T>,
		BaseDeleteMapper<T>,
        //IdsMapper<T>,
        InsertListMapper<T> {
}
