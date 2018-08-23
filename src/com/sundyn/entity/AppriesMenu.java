package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("appries_menu")
public class AppriesMenu extends Model<AppriesMenu> {
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Size(min=1,max=30,message="菜单名不能为空")
    @TableField("menuName")
    private String menuName;
    private String nav;
    @TableField("parentId")
    private Integer parentId;
    @TableField(exist = false)
    private String parentName;

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public Integer getMenuorder() {
        return menuorder;
    }

    public void setMenuorder(Integer menuorder) {
        this.menuorder = menuorder;
    }

    public static String getID() {
        return ID;
    }

    @TableField("iconCls")
    private String iconCls;
    private Integer isshow;
    private Integer menuorder;


    public static final String ID = "id";

    public static final String MENUNAME = "menuName";

    public static final String NAV = "nav";

    public static final String PARENTID = "parentId";

    public static final String ICONCLS = "iconCls";

    public static final String ISSHOW = "isshow";

    public static final String MENUORDER = "menuorder";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
