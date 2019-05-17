package com.sundyn.service;

import com.sundyn.dao.SuperDao;

import java.util.List;

public class MenuService extends SuperDao {
	public List GetAll(String[] powername) {
        String sql = "select distinct t1.id,t1.menuName as text ,t1.nav url ,t1.parentId, t1.iconCls, t1.menuorder " +
                "from appries_menu t1 join appries_powerfunc t2 on t1.funcCode=t2.funcCode " +
                "where t1.isshow=1 ";

        if (powername!=null && powername.length>0){
            String ins = "";
            for (String power: powername) {
                ins += ",'" + power + "'";
            }
            ins = ins.substring(1);
            sql += "and t2.powername in (" + ins + ") ";
        }
        sql += "order by t1.menuorder asc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List GetMenuByName(Integer managerid) {
	    if (managerid == null)
	        return null;
        String sql = "select distinct t1.id,t1.menuName as text ,t1.nav url ,t1.parentId, t1.iconCls, t1.menuorder\n" +
                "from appries_menu t1 join appries_powerfunc t2 on t1.funcCode=t2.funcCode \n" +
                "join appries_power t3 on t2.powername=t3.name\n" +
                "join appries_managerpower t4 on t4.powerid=t3.id\n" +
                "where t1.isshow=1 and t4.managerid=" + managerid;


        sql += "order by t1.menuorder asc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
