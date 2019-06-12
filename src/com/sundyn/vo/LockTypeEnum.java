package com.sundyn.vo;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum LockTypeEnum {
    账号锁定( "账号锁定", "auditlog.accountLocked"),
    终端锁定( "终端锁定", "auditlog.agentLocked");

    private String code;
    private String msg;

    LockTypeEnum(String code, String msg) {
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
    public static LockTypeEnum codeOf(String code) {
        for (LockTypeEnum ynEnum : LockTypeEnum.values()) {
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
