package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
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
@TableName("appries_keytype")
public class AppriesKeytype extends Model<AppriesKeytype> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    @TableField("keyNo")
    private Integer keyNo;
    @TableField("isJoy")
    private String isJoy;
    private Integer apprieserid;
    private String remark;
    private Integer ext1;
    private String ext2;
    private Integer yes;

    public String getKeyname() {
        if(this.keyNo==null)
            return null;
        KeyTypeNameEnum e = KeyTypeNameEnum.codeOf(this.keyNo);
        if (e!=null)
            return e.getName();
        return null;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    @TableField(exist = false)
    private String keyname;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String KEYNO = "keyNo";

    public static final String ISJOY = "isJoy";

    public static final String APPRIESERID = "apprieserid";

    public static final String REMARK = "remark";

    public static final String EXT1 = "ext1";

    public static final String EXT2 = "ext2";

    public static final String YES = "yes";

    @TableField(exist = false)
    public boolean FILTERDEPT = false;

    @Override
    protected Serializable pkVal() {
        return id;
    }

    public enum KeyTypeNameEnum {

        Y(7, "未评价");

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int value;
        private String name;

        KeyTypeNameEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据code获取到YNEnum,取不到返回null
         *
         * @param code
         * @return
         */
        public static KeyTypeNameEnum codeOf(int value) {
            for (KeyTypeNameEnum ynEnum : KeyTypeNameEnum.values()) {
                if (ynEnum.value == value) {
                    return ynEnum;
                }
            }
            return null;
        }
    }
}
