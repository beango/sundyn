package com.sundyn.vo;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum PowerTypeEnum {
    业务办理( "业务办理", "power.enum.powertype.service"),
    系统管理( "系统管理", "power.enum.powertype.systemmanage"),
    安全管理( "安全管理", "power.enum.powertype.security"),
    审计管理( "审计管理", "power.enum.powertype.audit");

    private String code;
    private String msg;

    PowerTypeEnum(String code, String msg) {
        HttpServletRequest req = ServletActionContext.getRequest();
        this.msg = LocalizedTextUtil.findDefaultText(msg, (Locale)req.getAttribute("Locale"));
        this.code = code;
    }

    /**
     * 根据code获取到YNEnum,取不到返回null
     *
     * @param code
     * @return
     */
    public static PowerTypeEnum codeOf(String code) {
        for (PowerTypeEnum ynEnum : PowerTypeEnum.values()) {
            if (ynEnum.code.equalsIgnoreCase(code)) {
                return ynEnum;
            }
        }
        return null;
    }

    public String getCode() { return this.code; }
    @Override
    public String toString(){
        return this.msg;
    }
}
