package com.sundyn.vo;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum LoginResultEnum {
    账号不存在( "账号不存在", "logintype.accountnotexists"),
    数据被篡改( "数据被篡改", "logintype.datanochecked"),
    密码错误( "密码错误", "logintype.passerror");

    private String code;
    private String msg;

    LoginResultEnum(String code, String key) {
        HttpServletRequest req = ServletActionContext.getRequest();
        this.msg = LocalizedTextUtil.findDefaultText(key, (Locale)req.getAttribute("Locale"));
        this.code = code;
    }

    /**
     * 根据code获取到YNEnum,取不到返回null
     *
     * @param code
     * @return
     */
    public static LoginResultEnum codeOf(String code) {
        for (LoginResultEnum ynEnum : LoginResultEnum.values()) {
            if (ynEnum.code.equalsIgnoreCase(code)) {
                return ynEnum;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return this.msg;
    }
}
