package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppService extends SuperDao {
    private static final Logger logger;

    static {
        logger = Logger.getLogger((Class)AppService.class.getClass());
    }

    public boolean add(final String VersionCode, final String VersionName, final String appurl, final String comments, boolean isEnable) {
        final String sql = "INSERT INTO [dbo].[appries_app]([versionCode],[versionName],[appurl],[comments],[enableFlag],[ctime]) VALUES (?,?,?,?,?,?)";
        final Object[] args = {VersionCode, VersionName, appurl, comments, isEnable, new Date()};
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map getNewestApp() {
        final String sql = "select top 1 * from [dbo].[appries_app] order by versionCode desc";
        Map m = new HashMap();
        try {
            m = this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            m = null;
        }
        return m;
    }
}
