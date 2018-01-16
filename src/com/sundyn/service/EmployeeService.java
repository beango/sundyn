package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.EmployeeVo;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeService extends SuperDao
{
    public Map findEmployeeById(final Integer employeeID) throws SQLException {
        final String sql = "select * from appries_Employee where id = " + employeeID;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public EmployeeVo findEmployee(final Integer employeeId) {
        final String sql = "select * from appries_Employee where id =" + employeeId;
        try {
            final List list = this.getJdbcTemplate().queryForList(sql);
            if (list != null && list.size() > 0) {
                final EmployeeVo employeeVo = (EmployeeVo) list.get(0);
                return employeeVo;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addEmployee(final EmployeeVo emp) {
        final String sql = "insert into appries_employee (name,deptid,sex,job_desc,phone,cardnum,Password,picture,ext2,remark,showDeptName,showWindowName,companyName,ext3,ext4) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final Object[] arg = { emp.getName(), emp.getDeptid(), emp.getSex(), emp.getJob_desc(), emp.getPhone(), emp.getCardnum(), emp.getPassWord(), emp.getPicture(), emp.getExt2(), emp.getRemark(), emp.getShowDeptName(), emp.getShowWindowName(), emp.getCompanyName(), emp.getExt3(), emp.getExt4() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateEmployee(final EmployeeVo emp) {
        final String sql = "update appries_employee set name =?,sex=?,phone=?,cardnum=?,job_desc=?,picture=?,ext2=?,remark=?,showDeptName=?,showWindowName=? ,companyName=?,ext3=?,ext4=? where id =?";
        final Object[] arg = { emp.getName(), emp.getSex(), emp.getPhone(), emp.getCardnum(), emp.getJob_desc(), emp.getPicture(), emp.getExt2(), emp.getRemark(), emp.getShowDeptName(), emp.getShowWindowName(), emp.getCompanyName(), emp.getExt3(), emp.getExt4(), emp.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateEmployee(final Integer id, final String name, final String sex, final String cardNum, final String picture, final String ext2, final String job_desc) {
        final String sql = "update appries_employee set name =?,sex=?,cardnum=? ,picture=? ,ext2=?,job_desc=? where id =?";
        final Object[] args = { name, sex, cardNum, picture, ext2, job_desc, id };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateRemoveEmployee(final Integer deptId, final Integer employeeId) {
        final String sql = "update appries_employee set deptid =? ,ext1=null where id =?";
        final Object[] arg = { deptId, employeeId };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateMoveEmployee(final Integer id) {
        final String sql = "update appries_employee set ext1='yes' where id =?";
        final Object[] arg = { id };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delEmployee(final Integer id) {
        final String sql = "delete from appries_employee where Id=" + id;
        final String sql2 = "delete from appries_appries where EmployeeId =" + id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            this.getJdbcTemplate().update(sql2);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List findEmployeeByDeptid(final Integer deptId, final int start, final int num) throws SQLException {
        final String sql = "select Id, Name,Sex,CardNum,Phone from appries_employee where    isnull(ext1) and  deptid in (" + deptId + ")   order by id desc limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findEmployeeByDeptId(final Integer deptId) throws SQLException {
        final String sql = "select *  from appries_employee where deptid in (" + deptId + ") order by  id ,job_desc  ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countEmployeeByDeptid(final Integer deptId) throws SQLException {
        final String sql = "select count(*) from appries_employee where    isnull(ext1) and   deptid =" + deptId;
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List findEmployeeByName(final String name, final int start, final int num) throws SQLException {
        final String sql = "select Id, Name,Sex,CardNum,Phone from appries_employee where  Name  like '%" + name + "%' order by id desc limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countEmployeeByName(final String name) throws SQLException {
        final String sql = "select count(*) from appries_employee where   Name  like '%" + name + "%'";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List findMovedEmployee(final int start, final int num) throws SQLException {
        final String sql = "select  Id, Name,Sex,CardNum,Phone from appries_Employee  where  ext1='yes' order by Id desc limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countMovedEmployee() throws SQLException {
        final String sql = "select  count(*) from appries_Employee  where  ext1='yes' ";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List findEmployeeByKeyowrd(final String keyword, final String deptIdGroup) {
        final String sql = "select appries_employee.id ,appries_employee.name,appries_employee.cardnum,concat(appries_dept.name,'') as deptName from appries_employee,appries_dept where appries_employee.name like '%" + keyword + "%'  and appries_employee.deptid in (" + deptIdGroup + ")  and   appries_employee.deptId=appries_dept.id  order by appries_employee.name ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean passwordReSet(final Integer employeeId) {
        final String sql = "update appries_employee set PassWord='49BA59ABBE56E057' where id='" + employeeId + "'";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map employeeLogin(final String cardnum, final String psw) {
        final String sql = "select * from appries_employee where CardNum='" + cardnum + "' and PassWord='" + psw + "' limit 0,1";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map employeeLogin2(final String name, final String psw) {
        final String sql = "select * from appries_employee where ext2='" + name + "' and PassWord='" + psw + "' limit 0,1";
        System.out.println("employeeLogin2-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map employeeLoginMd5(final String name, final String psw) {
        final String sql = "select * from appries_employee where ext2='" + name + "' and PassWord='password('" + psw + "')' limit 0,1";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean employeeChangePsw(final String cardnum, final String psw) {
        final String sql = "update appries_employee set PassWord='" + psw + "' where CardNum='" + cardnum + "'";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map employeeFindByCardnum(final String cardnum) {
        final String sql = "select appries_employee.*,concat(appries_dept.name ,'') as deptName from appries_employee,appries_dept where CardNum='" + cardnum + "' and appries_employee.deptid=appries_dept.id  limit 0,1";
        System.out.println("employeeFindByCardnum-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean employeeExists(final String ext2) {
        final String sql = "select count(*) from appries_employee where ext2='" + ext2 + "'  limit 0,1";
        try {
            final int num = this.getJdbcTemplate().queryForInt(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean employeeCardNumExsits(final String cardNum) {
        final String sql = "select count(*) from appries_employee where CardNum='" + cardNum + "'  limit 0,1";
        try {
            final int num = this.getJdbcTemplate().queryForInt(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public List findByCardnumOrName(final String keyword) {
        final String sql = "select Name,CardNum from appries_employee where Name like '%" + keyword + "%' or CardNum like '%" + keyword + "%' order by CardNum limit 0,5 ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findByCardnum(final String cardNum) {
        final String sql = "select id, Name,CardNum from appries_employee where  CardNum = '" + cardNum + "' limit 1";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            System.out.println("\ufffd\ufffd\ufffd\ufffd\ufffd\ucce3-findByCardnum-sql=" + sql);
            return null;
        }
    }

    public Map findEmployeeByName(final String name) {
        final String sql = "select   *  from appries_employee where Name = '" + name + "' limit 0,1  ";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findEmployeeByExt2(final String ext2) {
        final String sql = "select   *  from appries_employee where ext2 = '" + ext2 + "' limit 0,1  ";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findAllEmployee() {
        final String sql = "select   *  from appries_employee  ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List employeeExcel() {
        final String sql = "select  appries_employee.Name,appries_employee.ext2,Sex,CardNum,Phone, showWindowName,showDeptName,companyName,concat(appries_dept.name ,'') as deptName from appries_employee ,appries_dept where appries_employee.deptid=appries_dept.id order by appries_dept.id ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List employeeOnline(final String deptIds, final String employeeIds, final int start, final int num) {
        String sql = "select  concat(appries_employee.Name,'') as employeeName,  appries_employee.Sex ,appries_employee.CardNum , concat(appries_dept.name,'') as deptName   ,'\u5728\u7ebf' as isline from  appries_employee , appries_dept where  appries_dept.id=appries_employee.deptid  and appries_dept.id in(" + deptIds + ")  and appries_employee.id in(" + employeeIds + ")" + " union " + "select  concat(appries_employee.Name,'') as employeeName,  appries_employee.Sex ,appries_employee.CardNum , concat(appries_dept.name,'') as deptName  ,'\u4e0d\u5728\u7ebf' as isline from  appries_employee , appries_dept where  appries_dept.id=appries_employee.deptid  and appries_dept.id in(" + deptIds + ")  and appries_employee.id not  in(" + employeeIds + ")";
        sql = "select * from (" + sql + ") as temp limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countEmployeeOnline(final String deptIds, final String employeeIds) {
        String sql = " select  concat(appries_employee.Name,'') as employeeName,  appries_employee.Sex ,appries_employee.CardNum , concat(appries_dept.name,'') as deptName   ,'\u5728\u7ebf' as isline from  appries_employee , appries_dept where  appries_dept.id=appries_employee.deptid  and appries_dept.id in(" + deptIds + ")  and appries_employee.id in(" + employeeIds + ")" + " union " + " select  concat(appries_employee.Name,'') as employeeName,  appries_employee.Sex ,appries_employee.CardNum , concat(appries_dept.name,'') as deptName  ,'\u4e0d\u5728\u7ebf' as isline from  appries_employee , appries_dept where  appries_dept.id=appries_employee.deptid  and appries_dept.id in(" + deptIds + ")  and appries_employee.id not  in(" + employeeIds + ")";
        sql = "select count(*) from (" + sql + ") as temp";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countEmployeeOnline2(final String deptIds, final String employeeIds) {
        String sql = " select  concat(appries_employee.Name,'') as employeeName,  appries_employee.Sex ,appries_employee.CardNum , concat(appries_dept.name,'') as deptName   ,'\u5728\u7ebf' as isline from  appries_employee , appries_dept where  appries_dept.id=appries_employee.deptid  and appries_dept.id in(" + deptIds + ")  and appries_employee.id in(" + employeeIds + ")";
        sql = "select count(*) from (" + sql + ") as temp";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void onlineEmployee(final String employeeId, final String name, final String cardnum) {
        final String sql = "call onlineemployee(?,?,?)";
        try {
            this.getJdbcTemplate().update(sql, new Object[] { employeeId, name, cardnum });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onlineEmployeeDel(final String mac) {
        final String sql = "call onlineemployeeDel(?)";
        try {
            this.getJdbcTemplate().update(sql, new Object[] { mac });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean UpdateEmployeePhone(final String ids) {
        final String sql = "update appries_employee set phone=null where deptid in(" + ids + ")";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List onlineEmployees(final String mac) {
        final String sql = "select CONCAT(appries_onlineemployee.employeeId,'') as 'id',CONCAT(appries_onlineemployee.ename,'') as 'name',cardnum from appries_onlineemployee";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addEmployee(final EmployeeVo emp, final String name) {
        try {
            final KeyHolder keyHolder = (KeyHolder)new GeneratedKeyHolder();
            this.getJdbcTemplate().update((PreparedStatementCreator)new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final String sql = "insert into appries_employee (name,deptid,sex,job_desc,phone,cardnum,Password,picture,ext2,remark,showDeptName,showWindowName,companyName,ext3,ext4) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    final PreparedStatement ps = connection.prepareStatement(sql, 1);
                    ps.setString(1, emp.getName());
                    ps.setInt(2, emp.getDeptid());
                    ps.setString(3, emp.getSex());
                    ps.setString(4, emp.getJob_desc());
                    ps.setString(5, emp.getPhone());
                    ps.setString(6, emp.getCardnum());
                    ps.setString(7, emp.getPassWord());
                    ps.setString(8, emp.getPicture());
                    ps.setString(9, emp.getExt2());
                    ps.setString(10, emp.getRemark());
                    ps.setString(11, emp.getShowDeptName());
                    ps.setString(12, emp.getShowWindowName());
                    ps.setString(13, emp.getCompanyName());
                    ps.setString(14, emp.getExt3());
                    ps.setString(15, emp.getExt4());
                    return ps;
                }
            }, keyHolder);
            final Long generatedId = keyHolder.getKey().longValue();
            final String hql = "update appries_employee set cardnum=" + (10000L + generatedId) + " where id=" + generatedId;
            final int num = this.getJdbcTemplate().update(hql);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployeeByCardnum(final String cardnum, final String dt) {
        final String sql = "update appries_employee set ext3='" + dt + "' where cardnum ='" + cardnum + "'";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            if (num > 0) {
                return true;
            }
            System.err.println("\u5458\u5de5\u5361\u53f7" + cardnum + "\u6ca1\u6709\u627e\u5230\uff01");
            return false;
        }
        catch (Exception e) {
            System.err.println("\u5458\u5de5\u5361\u53f7" + cardnum + "\u7684\u5458\u5de5\u66f4\u65b0\u5931\u8d25\uff01");
            e.printStackTrace();
            return false;
        }
    }
}
