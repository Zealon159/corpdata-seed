package com.cpda.system.org.service.impl;


import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgDept;
import com.cpda.system.org.service.OrgDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cpda.system.org.dao.OrgDeptMapper;

/**
 * 用户部门服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Service("orgDeptService")
public class OrgDeptServiceImpl extends AbstractBaseService<OrgDept> implements OrgDeptService {
	
	@Autowired
	private OrgDeptMapper orgDeptMapper;
	
	@Override
	public Result save(OrgDept record) {
		String folderid = calculateFolderid(record.getParentfolderid());
		record.setFolderid(folderid);
		return super.save(record);
	}
	
	public Result update(OrgDept record,String oldParentFolderid) {
		//上级组织发生了变动，需要重新计算层级ID
		if(!oldParentFolderid.equals(record.getParentfolderid())){
			String folderid = calculateFolderid(record.getParentfolderid());
			record.setFolderid(folderid);
		}
		return super.update(record);
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
			OrgDept parentOrg = orgDeptMapper.selectById(parentFolderid);
			newFolderid = parentOrg.getFolderid()+newFolderid;
		}
		return newFolderid;
	}
}
