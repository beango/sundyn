package com.sundyn.Realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义的指定Shiro验证用户登录的类
 *
 * @author
 * @create
 * @see
 */

public class MyRealm extends AuthorizingRealm {

    /**
     * 为当前登录的Subject授予角色和权限
     *
     * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
     * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * @see 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * @see 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        System.out.println("授权验证");
        String currentUsername = (String) super.getAvailablePrincipal(principals);
        List<String> roleList = new ArrayList<String>();
        List<String> permissionList = new ArrayList<String>();
        //从数据库中获取当前登录用户的详细信息
        //DsCommonUserService userService = (DsCommonUserService) dswork.spring.BeanFactory.getBean("dsCommonUserService");

        //DsCommonUserRoleService userRoleService = (DsCommonUserRoleService) dswork.spring.BeanFactory.getBean("dsCommonUserRoleService");
        //DsCommonFuncService funService = (DsCommonFuncService) dswork.spring.BeanFactory.getBean("dsCommonFuncService");

        //DsCommonUser user = userService.getByAccount(currentUsername);
        //permissionList = userService.getUserPermissions(user.getId());
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleList);
        simpleAuthorInfo.addStringPermissions(permissionList);
        for (String s : permissionList) {
            System.out.println("perc:" + s);
        }
        return simpleAuthorInfo;
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken authcToken,
                                          AuthenticationInfo info) throws AuthenticationException {
        return;
    }

    public AuthenticationInfo doGetAuthenticationInfo2(AuthenticationToken token) {
        /*System.out.println("doGetAuthorizationInfo2" + (token == null));
        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		AuthenticationInfo authc = super.doGetAuthenticationInfo(token);

		String account = (String) authc.getPrincipals().getPrimaryPrincipal();
		System.out.println("doGetAuthorizationInfo2: " + account);
		DsCommonUserService userService = (DsCommonUserService) dswork.spring.BeanFactory.getBean("dsCommonUserService");
		DsCommonUser user = userService.getByAccount(account);

		SecurityUtils.getSubject().getSession().setAttribute("user", user);

		return authc;*/
        return null;
    }

    /**
     * 验证当前登录的Subject
     *
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        System.out.println("登录验证");
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        //两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //DsCommonUserService userService = (DsCommonUserService) dswork.spring.BeanFactory.getBean("dsCommonUserService");
        //DsCommonUser user = userService.getByAccount(token.getUsername());
        /*if (null != user) {
            //使用netid作为realmName
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), user.getNetid());
            this.setSession("currentUser", user);
            System.out.println("doGetAuthorizationInfo2---not null");
            return authcInfo;
        } else {
            System.out.println("doGetAuthorizationInfo2---null");
            return null;
        }*/
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
}