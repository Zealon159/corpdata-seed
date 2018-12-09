package com.cpda.system.security.shiro;

import com.cpda.system.org.entity.OrgUser;
import com.cpda.system.org.service.impl.OrgUserServiceImpl;
import com.cpda.system.security.shiro.util.ShiroUserPwdUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Lazy
	@Autowired
	private OrgUserServiceImpl userService;
	
	public ShiroRealm() {
        super();
    }
	
	public ShiroRealm(CacheManager cacheManager) {
        super(cacheManager);
    }
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userid = (String) upToken.getPrincipal(); // 得到用户名
		String password = new String((char[]) upToken.getCredentials()); // 得到密码
		String encrityPwd = ShiroUserPwdUtil.generateEncryptPwd(userid, password);
		Object credentials = encrityPwd;

		logger.debug("读取数据库中的" + userid + "-" + "信息");

		OrgUser user = userService.getUserInfoByUserid(userid);
		if(user==null){
			throw new UnknownAccountException("用户不存在！");
		}
		//为了实现微信登录暂做修改 如果微信用户与数据库中的用户密码一致，允许登录：user.getUserPwd().equals(password)
		if (!(user.getUserPwd().equals(encrityPwd)||user.getUserPwd().equals(password))) {
			throw new IncorrectCredentialsException("用户密码错误！");
		}
		
		if(user.getEnabledState()==false){
			throw new LockedAccountException("用户已被禁用！");
		}
		
		/* 
		 * 登录成功 查询权限对应的范围数据，这里查询和下面的权限查询显得有点重复，为了能获取到权限对应的范围只能这样了..
		 * 以后有好的方法在改进
		 */
		//user.setOrgPermission(orgPermissionService.findPermissionRangeByUserid(user.getUserid()));
		
		
		ByteSource credentialsSalt = ByteSource.Util.bytes(userid);
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, credentials, credentialsSalt, getName());
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		OrgUser user = (OrgUser) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 角色
		authorizationInfo.setRoles(userService.getRolesByUser(user.getUserid()));
		// 权限
		Set<String> permissions = userService.getPermissionsByUser(user.getUserid());
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}
	
	//设置当前用户缓存名称
	@Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		OrgUser shiroUser = (OrgUser) super.getAvailablePrincipal(principals);
        return shiroUser.getUserid();
    }
	
	/**
	 * Clear Shiro Cache.
	 */
	public void clearCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
	
	
	public static void main(String[] args) {
		ByteSource credentialsSalt = ByteSource.Util.bytes("zhang");
		Object pwd = "123";
		Object result = new SimpleHash("MD5", pwd, credentialsSalt, 3);
		System.out.println(result);
	}
}
