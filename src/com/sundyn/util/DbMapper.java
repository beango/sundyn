package com.sundyn.util;

import org.springframework.jdbc.core.*;
import com.sundyn.vo.*;
import java.sql.*;

public class DbMapper implements RowMapper
{
    public Object mapRow(final ResultSet arg0, final int arg1) throws SQLException {
        final Dbtype db = new Dbtype();
        db.setDb(arg0.getString(1));
        db.setName(arg0.getString(2));
        db.setParam_list(arg0.getBlob(3));
        db.setBody(arg0.getBlob(4));
        return db;
    }
}
