package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class VoteService extends SuperDao
{
    public boolean add(final VoteVo voteVo) {
        final String sql = "insert into appries_vote (voteTitle,voteDescription,voteNum,voteSelect,voteHit,voteIsUse,voteParentId ) values(?,?,?,?,?,?,?)    ";
        final Object[] args = { voteVo.getVoteTitle(), voteVo.getVoteDescription(), voteVo.getVoteNum(), voteVo.getVoteSelect(), voteVo.getVoteHit(), voteVo.getVoteIsUse(), voteVo.getVoteParentId() };
        try {
            this.getJdbcTemplate().update(sql, args);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean delete(final Integer id) {
        final String sql = "delete from appries_vote where voteId=?";
        final String sql2 = "delete from appries_vote where voteParentId=?";
        final Object[] args = { id };
        try {
            this.getJdbcTemplate().update(sql, args);
            this.getJdbcTemplate().update(sql2, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public int getVoteId() {
        try {
            final String sql = "select max(voteId) from appries_vote";
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List find() {
        final String sql = "select * from appries_vote where voteParentId=? order by voteId desc limit 0,3";
        final Object[] args = { 0 };
        try {
            return this.getJdbcTemplate().queryForList(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findVoteSelect(final Integer id) {
        final String sql = "select * from appries_vote where voteParentId=?";
        final Object[] args = { id };
        try {
            return this.getJdbcTemplate().queryForList(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean vote1(final Object[] args) {
        final String sql = "update arrpies_vote set voteHit=voteHit+1 where voteId in(?)";
        try {
            this.getJdbcTemplate().update(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean vote(final String id) {
        final String sql = "update appries_vote set voteHit=voteHit+1 where voteId in(" + id + ")";
        try {
            this.getJdbcTemplate().update(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean voteUse(final Integer id) {
        final String sql = "update appries_vote set voteIsUse=0 ";
        final String sql2 = "update appries_vote set voteIsUse=1 where voteId=? ";
        final Object[] args = { id };
        try {
            this.getJdbcTemplate().update(sql);
            this.getJdbcTemplate().update(sql2, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean voteCancle() {
        final String sql = "update appries_vote set voteIsUse=0 ";
        try {
            this.getJdbcTemplate().update(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Map findById(final Integer id) {
        final String sql = "select * from  appries_vote where voteId=?";
        final Object[] args = { id };
        try {
            return this.getJdbcTemplate().queryForMap(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map findUse2() {
        final String sql = "select * from  appries_vote where voteParentId=0 order by voteIsUse desc  , voteId desc   limit 0,1 ";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map findUse() {
        final String sql = "select * from  appries_vote where voteParentId=0 and voteIsUse=1 order by voteIsUse desc  , voteId desc limit 0,1 ";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
