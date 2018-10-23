package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @author oKong
 * @since 2018-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("inte_log")
public class InteLog extends Model<InteLog> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String inteurl;
    private String intedata;
    private Date reqtime;
    private Integer status;
    private String reqip;
    private String note;
    private String token;
    private String ywlsh;

    public static final String ID = "id";

    public static final String INTEURL = "inteurl";

    public static final String INTEDATA = "intedata";

    public static final String REQTIME = "reqtime";

    public static final String REQIP = "reqip";

    public static final String STATUS = "status";

    public static final String NOTE = "note";

    public static final String TOKEN = "token";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
