package com.corpdata.system.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corpdata.system.org.dao.OrgPermissionMapper;
import com.corpdata.system.org.dao.OrgRolePermissionMapper;
import com.corpdata.system.org.entity.OrgPermission;
import com.corpdata.system.org.entity.OrgRolePermission;

/**
 * 权限服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Service
public class OrgPermissionService {
	@Autowired
	private OrgPermissionMapper orgPermissionMapper;
	
	@Autowired
	private OrgRolePermissionMapper orgRolePermissionMapper;
	
	public OrgPermission selectByPrimaryKey(String id){
		return orgPermissionMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取权限树JSON
	 * @return
	 */
	public String selectAllTreeJsonData(){
		List<OrgPermission> list = orgPermissionMapper.selectAll();
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		iterGet(list, "root", buffer);
		buffer.append("]");
		// 将,\n]替换成\n]
		String tmp = buffer.toString();
		tmp = tmp.replaceAll(",\n]", "\n]");
		return tmp;
	}
	
	
	static int count = 0;
	/**
	 * 递归生成json格式的数据{id:1,text:'',children:[]}
	 * @param list
	 * @param pid
	 * @param buffer
	 */
	public static void iterGet(List<OrgPermission> list, String pid, StringBuffer buffer) {
		for (OrgPermission node : list) {
			// 查找所有父节点为pid的所有对象，然后拼接为json格式的数据
			if (node.getParentid().equals(pid)){
				count++;
				buffer.append("{\"id\":\"" + node.getId()
						+ "\",\"resourceUrl\":\"" + node.getResourceurl()
						+ "\",\"folderId\":\"" + node.getId()
						+ "\",\"name\":\"" + node.getPermissionname()+ 
						"\",\"children\":[");
				// 递归
				iterGet(list, node.getId(), buffer);
				buffer.append("]},\n");
				count--;
			}
		}
	}
	
	/**
	 * @author cy
	 * @return
	 * @description 获取角色权限映射集合
	 */
	public List<OrgRolePermission> getOrgRolePermissionMapList(String key,String value){
		Map<String,Object> params = new HashMap<String,Object>();
		if(value!=null&&value.length()>0){
			params.put(key, value);
		}
		List<OrgRolePermission> list = orgRolePermissionMapper.getOrgRolePermissionMapList(params);
		return list;
	}


	/**
	 * @author cy
	 * @param roleId
	 * @return
	 * @description 通过角色id查询权限信息
	 */
	public String selectPermissionIdByRoleId(String roleId) {
		String permissionId = "";
		List<OrgRolePermission> list = getOrgRolePermissionMapList("roleId", roleId);
		
		for (OrgRolePermission orgRolePermission : list) {
			if(permissionId==null||permissionId.length()<0){
				permissionId = orgRolePermission.getPermission().getId();
			}else{
				permissionId += "," + orgRolePermission.getPermission().getId();
			}
		}
		return permissionId;
	}

}
