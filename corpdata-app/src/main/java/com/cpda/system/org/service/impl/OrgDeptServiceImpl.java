package com.cpda.system.org.service.impl;


import com.alibaba.fastjson.JSON;
import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.system.org.dao.OrgDeptMapper;
import com.cpda.system.org.entity.OrgDept;
import com.cpda.system.org.service.OrgDeptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		String parentfolderid=record.getParentfolderid();
		if(parentfolderid.equals("0")){
			parentfolderid="0";
			record.setParentfolderid(parentfolderid);
			String folderid=calculateFolderid(parentfolderid);
			record.setFolderid(folderid);
		}else{
			OrgDept parentDept=orgDeptMapper.selectById(Long.parseLong(parentfolderid));
			parentfolderid=parentDept.getFolderid();
			record.setParentfolderid(parentfolderid);
			String folderid=calculateFolderid(parentDept.getFolderid());
			record.setFolderid(folderid);
		}
		return super.save(record);
	}
	
	public Result update(OrgDept record, String oldParentFolderid) {
		//通过上级组织id，获取上级id的folderid
		String parentfolderid=record.getParentfolderid();
		if(parentfolderid.equals("0")){
			record.setParentfolderid(parentfolderid);
		}else{
			OrgDept parentDept=orgDeptMapper.selectById(Long.parseLong(parentfolderid));
			parentfolderid=parentDept.getFolderid();
		}
		record.setParentfolderid(parentfolderid);
		if(record.getFolderid().equals(parentfolderid) || parentfolderid.indexOf(record.getFolderid())>=0){
			Result result=new Result();
			result.setMsg("修改失败,不能放置在自己部门,或下级部门");
			return result; 
		}
		
		//上级组织发生了变动，需要重新计算层级ID
		if(!oldParentFolderid.equals(record.getParentfolderid())){
			//查找本组织的下级部门
			List<OrgDept> list = orgDeptMapper.selectByParentId(record.getFolderid());
			//获取本组织新的folderid
			String newFolderid=calculateFolderid(record.getParentfolderid());
			record.setFolderid(newFolderid);
			for(OrgDept dept:list){
				dept.setParentfolderid(newFolderid);
				dept.setFolderid(calculateFolderid(dept.getParentfolderid()));
				super.update(dept);
			}
		}
		return super.update(record);
	}
	
	/**
	 * 获取下拉json数据
	 * @return
	 */
	public String findByCombox(boolean hasRoot){
		//String json = CorpdataUtil.getOrgDeptComboxJson(orgDeptMapper.selectAllByCombox(),hasRoot);
		StringBuffer json = new StringBuffer("");
		if(hasRoot){//有根目录
			json.append("[{\"id\":\"0\",\"text\":\"root\",\"state\":\"open\",\"children\":");
			json.append(getDeptJson("0"));
			json.append("}]");
		}else{
			json.append("[");
			json.append(getDeptJson("0"));
			json.append("]");
		}
		return json.toString();
	}
	
	
	private String getDeptJson(String parentId) {
		StringBuffer sb = new StringBuffer("[");
		List<OrgDept> list = orgDeptMapper.selectByParentId(parentId);
		for(int i=0;i<list.size();i++){
            if(i>0){
                sb.append(",");
            }
            OrgDept dept = list.get(i);
            sb.append("{\"id\":\""+dept.getId()+"\",");
            sb.append("\"text\":\""+dept.getFoldername()+"\",");
            sb.append("\"parentFolderId\":\""+dept.getParentfolderid()+"\",");
            sb.append("\"folderid\":\""+dept.getFolderid()+"\",");
            sb.append("\"sortNumber\":\""+dept.getSortNumber()+"\"");
            sb.append("," + getMenusByParentId(dept.getFolderid()));
            sb.append("}");
        }
		sb.append("]");
		return sb.toString();
	}
	
	public String getMenusByParentId(String parentId){
        StringBuffer sb = new StringBuffer();
        sb.append("\"children\":[");
        String state="open";
        List<OrgDept> list = orgDeptMapper.selectByParentId(parentId);
        for(int i=0;i<list.size();i++){
            if(i>0){
                sb.append(",");
                state="close";
            }
            OrgDept dept = list.get(i);
            sb.append("{\"id\":\""+dept.getId()+"\",");
            sb.append("\"text\":\""+dept.getFoldername()+"\",");
            sb.append("\"parentFolderId\":\""+dept.getParentfolderid()+"\",");
            sb.append("\"folderid\":\""+dept.getFolderid()+"\",");
            sb.append("\"sortNumber\":\""+dept.getSortNumber()+"\"");
            sb.append("," + getMenusByParentId(dept.getFolderid()));
            sb.append("}");
        }
        sb.append("],\"state\":\""+state+"\"");
        return sb.toString();
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
		if(!parentFolderid.equals("0") ){
			newFolderid = parentFolderid+newFolderid;
		}
		return newFolderid;
	}

	@Override
	public String findByFromJsonTree(String id) {
        //增加返回模式，如果是数组，则直接返回List的Json字符串数组
        PageHelper.startPage(1, 1000);
        Page<OrgDept> list = (Page<OrgDept>) orgDeptMapper.selectByParentId(id);
        for(OrgDept dept :list){
        	String folderid=dept.getFolderid();
        	List<OrgDept> list2 = orgDeptMapper.selectByParentId(folderid);
        	if(list2.size()>0){
        		dept.setState("closed");        		
        	}
        }
        return JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd");
    }

	@Override
	public String findByFolderid(String folderid) {
		String id = orgDeptMapper.selectByFolderId(folderid);
		if(id==null){
			id="0";
		}
		return id;
	}

}
