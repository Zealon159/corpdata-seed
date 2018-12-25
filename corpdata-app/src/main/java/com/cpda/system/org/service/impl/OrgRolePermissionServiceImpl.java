package com.cpda.system.org.service.impl;

import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.system.menu.entity.SysMenu;
import com.cpda.system.org.dao.OrgRolePermissionMapper;
import com.cpda.system.org.entity.OrgRole;
import com.cpda.system.org.entity.OrgRolePermission;
import com.cpda.system.org.service.OrgRolePermissionService;
import com.cpda.system.security.shiro.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色权限关联
 * @auther: Zealon
 * @Date: 2018-12-20 15:43
 */
@Service
public class OrgRolePermissionServiceImpl implements OrgRolePermissionService {

    Logger logger = LoggerFactory.getLogger(OrgRolePermissionServiceImpl.class);

    @Autowired
    private OrgRolePermissionMapper mapper;

    /**
     * 保存角色权限
     * @param roleId  角色ID
     * @param permissionIds  权限IDs 逗号分隔 如：[1,4,7,8,19]
     * @return
     */
    @Transactional
    @Override
    public Result saveRolePermission(long roleId, String permissionIds) {
        Result result = ResultUtil.success();
        try{
            // 先删除原由权限
            mapper.deleteByRoleId(roleId);

            // 批量添加新权限
            if (permissionIds!=null){
                // 角色实例
                OrgRole role = new OrgRole();
                role.setId(roleId);

                String[] idsArray = permissionIds.split(",");
                for (int i = 0; i < idsArray.length; i++) {
                    long permissionId = Long.parseLong(idsArray[i]);
                    if (permissionId != 0){
                        SysMenu menu = new SysMenu();
                        menu.setId(permissionId);

                        OrgRolePermission record = new OrgRolePermission(role,menu);
                        mapper.insert(record);
                    }
                }
            }

            logger.info("{} 保存角色权限,角色ID:{},权限:{}", UserUtil.getCurrentUserid(),roleId,permissionIds);
        }catch (Exception ex){
            result = ResultUtil.fail();
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public void deleteRolePermissionByRoleId(long roleId) {
        mapper.deleteByRoleId(roleId);
    }

    @Override
    public String getOrgRolePermissionMapList(long roleId) {
        //List<OrgRolePermission>
        return null;
    }
}
