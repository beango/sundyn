package com.sundyn.vo;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum LogTypeEnum {
    登录( "登录", "auditlog.logTypeEnumLogin"),
    退出( "退出", "auditlog.logTypeEnumLoginout"),
    终端冻结( "终端冻结", "auditlog.logTypeEnumAgentLocked"),
    账号冻结( "账号冻结", "auditlog.logTypeEnumAccountLocked"),
    高频访问( "高频访问", "auditlog.logTypeEnumAccessPerTimes"),
    特殊访问时间段( "特殊访问时间段", "auditlog.logTypeEnumSpecialTime"),
    访问非常规业务( "访问非常规业务", "auditlog.logTypeEnumNotGeneral"),
    访问核心功能( "访问核心功能", "auditlog.logTypeEnumCoreService"),
    账号长期未使用( "账号长期未使用", "auditlog.logTypeEnumAccountLongTimeUnused"),
    数据被篡改( "数据被篡改", "auditlog.logTypeEnumDataTampered");

    private String code;
    private String msg;

    LogTypeEnum(String code, String msg) {
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
    public static LogTypeEnum codeOf(String code) {
        for (LogTypeEnum ynEnum : LogTypeEnum.values()) {
            if (ynEnum.code.equalsIgnoreCase(code)) {
                return ynEnum;
            }
        }
        return null;
    }
    public String getCode(){
        return this.code;
    }

    @Override
    public String toString(){
        return this.msg;
    }
}
