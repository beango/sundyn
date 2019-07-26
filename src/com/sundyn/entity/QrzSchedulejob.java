package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangding
 * @since 2019-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("Qrz_Schedulejob")
public class QrzSchedulejob extends Model<QrzSchedulejob> {
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    private static final long serialVersionUID = 1L;

    private Long jobid;
    private String beanname;
    private String methodname;
    private String params;
    private String cron;
    private Integer status;
    private String remark;
    private Date ctime;
    private Integer cuser;


    public static final String JOBID = "jobid";

    public static final String BEANNAME = "beanname";

    public static final String METHODNAME = "methodname";

    public static final String PARAMS = "params";

    public static final String CRON = "cron";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String CTIME = "ctime";

    public static final String CUSER = "cuser";

    @Override
    protected Serializable pkVal() {
        return this.jobid;
    }

}
