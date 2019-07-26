package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangding
 * @since 2018-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("queue_detail")
public class QueueDetail extends Model<QueueDetail> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String ywlsh;
    private String counterlist;
    private Integer hallid;
    private String hallno;
    private String bizid;
    private String bizname;
    private Integer deptid;
    private String deptname;
    private String queuenum;
    private String mobile;
    private String cardtype;
    private String cardid;
    private String cardname;
    private Date tickettime;
    private Integer queuetype;
    private String yyno;
    private Integer ishj;
    private Date hjtime;
    private String hjcounter;
    private String hjcountername;
    private String staffno;
    private String staffname;
    private Date starttime;
    private Date endtime;

    public String getQueuetypename() {
        if(this.queuetype==null)
            return null;
        QueueTypeEnum e = QueueTypeEnum.codeOf(this.queuetype);
        if (e!=null)
            return e.msg;
        return null;
    }
    @TableField(exist = false)
    private String queuetypename;
    /**
     * 等待时长：叫号－开始办理
     */
    private Integer waittime;
    @TableField(exist = false)
    private String waittimename;
    /**
     * 办理时长：办结－叫号
     */
    private Integer servicetime;
    @TableField(exist = false)
    private String servicetimename;
    private Integer appriseresult;
    private String appriseresultname;
    private Date apprisetime;
    private Integer isagent;
    private Integer status;
    private Date ctime;

    public String getStatusname() {
        if(this.status==null)
            return null;
        StatusNameEnum e = StatusNameEnum.codeOf(this.status);
        if (e!=null){
            HttpServletRequest req = ServletActionContext.getRequest();
            String str = LocalizedTextUtil.findDefaultText(e.getMsg(), (Locale)req.getAttribute("Locale"));
            return str;
        }

        return null;
    }
    public static String getStatusname(int status) {
        StatusNameEnum e = StatusNameEnum.codeOf(status);
        if (e!=null){
            HttpServletRequest req = ServletActionContext.getRequest();
            String str = LocalizedTextUtil.findDefaultText(e.getMsg(), (Locale)req.getAttribute("Locale"));
            return str;
        }
        return null;
    }
    @TableField(exist = false)
    private String statusname;


    public static final String ID = "id";

    public static final String YWLSH = "ywlsh";

    public static final String COUNTERLIST = "counterlist";

    public static final String HALLID = "hallid";

    public static final String HALLNO = "hallno";

    public static final String BIZID = "bizid";

    public static final String BIZNAME = "bizname";

    public static final String DEPTID = "deptid";

    public static final String DEPTNAME = "deptname";

    public static final String QUEUENUM = "queuenum";

    public static final String MOBILE = "mobile";

    public static final String CARDTYPE = "cardtype";

    public static final String CARDID = "cardid";

    public static final String CARDNAME = "cardname";

    public static final String TICKETTIME = "tickettime";

    public static final String QUEUETYPE = "queuetype";

    public static final String YYNO = "yyno";

    public static final String ISHJ = "ishj";

    public static final String HJTIME = "hjtime";

    public static final String HJCOUNTER = "hjcounter";

    public static final String STAFFNO = "staffno";

    public static final String STAFFNAME = "staffname";

    public static final String STARTTIME = "starttime";

    public static final String ENDTIME = "endtime";

    public static final String WAITTIME = "waittime";

    public static final String SERVICETIME = "servicetime";

    public static final String APPRISERESULT = "appriseresult";

    public static final String APPRISERESULTNAME = "appriseresultname";

    public static final String APPRISETIME = "apprisetime";

    public static final String ISAGENT = "isagent";

    public static final String STATUS = "status";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public enum QueueTypeEnum {
        Y(0, "queuedetail.queuetype.scene"),
        N(1, "queuedetail.queuetype.reservation"),
        S(2, "queuedetail.queuetype.online");

        private int code;

        private String msg;

        QueueTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        /**
         * 根据code获取到YNEnum,取不到返回null
         *
         * @param code
         * @return
         */
        public static QueueTypeEnum codeOf(int code) {
            for (QueueTypeEnum ynEnum : QueueTypeEnum.values()) {
                if (ynEnum.code == code) {
                    return ynEnum;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }
    public enum StatusNameEnum {

        Y(0, "queuedetail.status.nocall"),
        N(1, "queuedetail.status.noprocess"),
        S(2, "queuedetail.status.processed"),
        D(-1, "queuedetail.status.canceled");
        private int code;

        private String msg;

        StatusNameEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        /**
         * 根据code获取到YNEnum,取不到返回null
         *
         * @param code
         * @return
         */
        public static StatusNameEnum codeOf(int code) {
            for (StatusNameEnum ynEnum : StatusNameEnum.values()) {
                if (ynEnum.code == code) {
                    return ynEnum;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }
}
