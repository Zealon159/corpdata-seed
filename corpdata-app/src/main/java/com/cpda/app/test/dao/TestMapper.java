package com.cpda.app.test.dao;

import com.cpda.app.test.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import com.cpda.common.base.BaseMapper;

/**
 *  持久层接口
 * @author zealon
 * @email zealon@126.com
 * @date 2018-12-13 15:39:31
 * 
 */
@Mapper
public interface TestMapper extends BaseMapper<Test>{

}
