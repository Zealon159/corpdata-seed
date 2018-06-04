package com.corpdata.system.org.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corpdata.system.org.dao.OrgDeptMapper;
import com.corpdata.system.org.entity.OrgDept;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 用户部门服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Service("orgDeptService")
public class OrgDeptService extends AbstractService<OrgDept>{
	
	@Autowired
	private OrgDeptMapper orgDeptMapper;
	
	@Override
	public Result save(OrgDept record) {
		// TODO Auto-generated method stub
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		String folderid = calculateFolderid(record.getParentfolderid());
		record.setFolderid(folderid);
		return super.save(record);
	}
	
	public Result update(OrgDept record,String oldParentFolderid) {
		record.setModified(new Date());
		//上级组织发生了变动，需要重新计算层级ID
		if(!oldParentFolderid.equals(record.getParentfolderid())){
			String folderid = calculateFolderid(record.getParentfolderid());
			record.setFolderid(folderid);
		}
		return super.update(record);
	}
	
	@Override
	public Result deleteById(String id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}
	
	@Override
	public OrgDept findById(String id) {
		// TODO Auto-generated method stub
		return orgDeptMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public String findByPage(DataGridRequestDTO dgRequest) {
		// TODO Auto-generated method stub
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		Page<OrgDept> list = orgDeptMapper.selectAll(dgRequest.getParams());
		return PageConvertUtil.getGridJson(list);
	}
	
	/**
	 * 获取下拉json数据
	 * @return
	 */
	public String findByCombox(boolean hasRoot){
		String json = CorpdataUtil.getOrgDeptComboxJson(orgDeptMapper.selectAllByCombox(),hasRoot);
		return json;
	}
	
	/**
	 * 计算当前组织层级id
	 * @param parentFolderid
	 * @return
	 */
	public String calculateFolderid(String parentFolderid){
		String newFolderid = "001";
		String folderid = orgDeptMapper.selectLastFolderidByParent(parentFolderid);
		if(folderid!=null){
			int folderidInt = 0;
			if(folderid.length()>3){
				folderidInt = Integer.parseInt(folderid.substring(folderid.length()-3, folderid.length()));
			}else{
				folderidInt = Integer.parseInt(folderid);
			}
			folderidInt++;
			//补零
			if(folderidInt<10){
				newFolderid="00"+folderidInt;
			}else if(folderidInt>=10 && folderidInt<100){
				newFolderid="0"+folderidInt;
			}else if(folderidInt>=100){
				newFolderid=""+folderidInt;
			}
		}
		//父级id不是根目录，需要加上父级组织的层级编号
		if(!parentFolderid.equals("root")){
			OrgDept parentOrg = orgDeptMapper.selectByPrimaryKey(parentFolderid);
			newFolderid = parentOrg.getFolderid()+newFolderid;
		}
		return newFolderid;
	}
}
