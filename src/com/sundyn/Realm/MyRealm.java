package com.sundyn.Realm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.entity.AppriesPowerfunc;
import com.sundyn.service.IAppriesManagerpowerService;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.service.IAppriesPowerfuncService;
import com.sundyn.service.ManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;


/**
 * 自定义的指定Shiro验证用户登录的类
 *
 * @author
 * @create
 * @see
 */

public class MyRealm extends AuthorizingRealm {
    @Resource
    private ManagerService managerService;
    @Resource
    private IAppriesMenuService appriesMenuService;
    @Resource
    private IAppriesManagerpowerService managerpowerService;
    @Resource
    private IAppriesPowerfuncService appriesPowerFuncService;
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
        System.out.println("进入角色授权");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Map manager = (Map)session.getAttribute("manager");
        System.out.println("当前用户" + manager.get("name"));

        String currentUsername = (String) super.getAvailablePrincipal(principals);
        List<String> roleList = new ArrayList<String>();
        //从数据库中获取当前登录用户的详细信息
        //DsCommonUserService userService = (DsCommonUserService) dswork.spring.BeanFactory.getBean("dsCommonUserService");

        //DsCommonUserRoleService userRoleService = (DsCommonUserRoleService) dswork.spring.BeanFactory.getBean("dsCommonUserRoleService");
        //DsCommonFuncService funService = (DsCommonFuncService) dswork.spring.BeanFactory.getBean("dsCommonFuncService");

        //DsCommonUser user = userService.getByAccount(currentUsername);
        //permissionList = userService.getUserPermissions(user.getId());
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleList);


        List<AppriesManagerpower> managerPowers = managerpowerService.selectListEx(new EntityWrapper<AppriesManagerpower>()
                .where("managerId={0}",manager.get("id").toString()));
        ArrayList cookieMapPowerArr = new ArrayList();
        if (null!=managerPowers && managerPowers.size()>0){
            for (AppriesManagerpower powr : managerPowers){
                cookieMapPowerArr.add(powr.getPowerName());
            }
        }
        Set<String> permissions = new HashSet<>();
        List<AppriesPowerfunc> managerAllFuncs = appriesPowerFuncService.selectListEx(new EntityWrapper<AppriesPowerfunc>().in("powerName", cookieMapPowerArr.toArray()));
        for (AppriesPowerfunc f : managerAllFuncs){
            permissions.add(f.getFuncCode());
        }
        permissions.add("home");
        permissions.add("managerChangePsw");
        simpleAuthorInfo.addStringPermissions(permissions);
        return simpleAuthorInfo;
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) throws AuthenticationException {
    }

    /**
     * 验证当前登录的Subject
     *
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        System.out.println("进入用户登录");
        String username = (String) authcToken.getPrincipal();
        Map user = managerService.findByName(username);
        if (user == null) {
            throw new UnknownAccountException("当前账户不存在");
        }
        return new SimpleAuthenticationInfo(user.get("name"), user.get("password"), ByteSource.Util.bytes("TestSalt"), super.getName());
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

    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}