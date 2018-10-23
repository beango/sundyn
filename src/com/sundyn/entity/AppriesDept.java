package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangding
 * @since 2018-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("appries_dept")
public class AppriesDept extends Model<AppriesDept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("fatherId")
    private Integer fatherId;
    private String name;
    private Integer child;
    private Integer lenvel;
    private String remark;
    private Integer devUndownload;
    private byte[] devSavedphoto;
    private Date lastcheck;
    private Integer clientType;
    private String productType;
    @TableField("deptType")
    private Integer deptType;
    private String deptCameraUrl;
    @TableField("dept_businessId")
    private Integer deptBusinessid;
    @TableField("dept_playListId")
    private Integer deptPlaylistid;
    private String ext1;
    private String ext2;
    private String ext3;
    @TableField("useVideo")
    private String useVideo;
    private String ext4;
    private String ext5;
    private String notice;
    @TableField("noticeNow")
    private String noticeNow;
    @TableField("noticeTime")
    private Date noticeTime;
    private Integer cityid;
    private Integer provinceid;
    private String ipadd;


    public static final String ID = "id";

    public static final String FATHERID = "fatherId";

    public static final String NAME = "name";

    public static final String CHILD = "child";

    public static final String LENVEL = "lenvel";

    public static final String REMARK = "remark";

    public static final String DEV_UNDOWNLOAD = "dev_undownload";

    public static final String DEV_SAVEDPHOTO = "dev_savedphoto";

    public static final String LASTCHECK = "lastcheck";

    public static final String CLIENT_TYPE = "client_type";

    public static final String PRODUCT_TYPE = "product_type";

    public static final String DEPTTYPE = "deptType";

    public static final String DEPT_CAMERA_URL = "dept_camera_url";

    public static final String DEPT_BUSINESSID = "dept_businessId";

    public static final String DEPT_PLAYLISTID = "dept_playListId";

    public static final String EXT1 = "ext1";

    public static final String EXT2 = "ext2";

    public static final String EXT3 = "ext3";

    public static final String USEVIDEO = "useVideo";

    public static final String EXT4 = "ext4";

    public static final String EXT5 = "ext5";

    public static final String NOTICE = "notice";

    public static final String NOTICENOW = "noticeNow";

    public static final String NOTICETIME = "noticeTime";

    public static final String CITYID = "cityid";

    public static final String PROVINCEID = "provinceid";

    public static final String IPADD = "ipadd";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
