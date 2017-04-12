package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import com.sundyn.vo.*;
import java.util.*;
import net.sf.json.*;

public class VoteAction extends ActionSupport
{
    private List ls;
    private Integer voteId;
    private VoteService voteService;
    
    public List getLs() {
        return this.ls;
    }
    
    public Integer getVoteId() {
        return this.voteId;
    }
    
    public VoteService getVoteService() {
        return this.voteService;
    }
    
    public void setLs(final List ls) {
        this.ls = ls;
    }
    
    public void setVoteId(final Integer voteId) {
        this.voteId = voteId;
    }
    
    public void setVoteService(final VoteService voteService) {
        this.voteService = voteService;
    }
    
    public String vote() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("Id");
        String m7 = "";
        try {
            if (!"".equals(id) && id != null) {
                this.voteService.vote(request.getParameter("Id"));
            }
            final Map i = this.voteService.findUse();
            m7 = i.get("voteTitle").toString();
            this.ls = this.voteService.findVoteSelect(Integer.parseInt(i.get("voteId").toString()));
            if (this.ls != null) {
                for (int j = 0; j < this.ls.size(); ++j) {
                    final Map v = (Map) this.ls.get(j);
                    m7 = String.valueOf(m7) + "||" + v.get("voteSelect").toString() + ",,," + v.get("voteHit").toString();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m7 = "error";
        }
        request.setAttribute("json", (Object)m7);
        return "success";
    }
    
    public String voteAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String voteTitle = request.getParameter("voteTitle");
        final String voteSelects = request.getParameter("voteSelects");
        final String voteNum = request.getParameter("voteNum");
        final VoteVo voteVo = new VoteVo();
        voteVo.setVoteTitle(voteTitle);
        voteVo.setVoteIsUse(0);
        voteVo.setVoteHit(0);
        voteVo.setVoteParentId(0);
        voteVo.setVoteNum(Integer.valueOf(voteNum));
        voteVo.setVoteDescription("aaaa");
        this.voteService.add(voteVo);
        final int parentId = this.voteService.getVoteId();
        final String[] votes = voteSelects.split(",");
        for (int i = 0; i < votes.length; ++i) {
            final VoteVo voteVo2 = new VoteVo();
            voteVo2.setVoteHit(0);
            voteVo2.setVoteParentId(parentId);
            voteVo2.setVoteSelect(votes[i]);
            this.voteService.add(voteVo2);
        }
        this.ls = this.voteService.find();
        return "success";
    }
    
    public String voteCancle() {
        try {
            this.voteService.voteCancle();
            this.ls = this.voteService.find();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public String voteDel() {
        this.voteService.delete(this.voteId);
        this.ls = this.voteService.find();
        return "success";
    }
    
    public String voteDialog() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map m = this.voteService.findById(this.voteId);
        this.ls = this.voteService.findVoteSelect(this.voteId);
        final List ls_temp = new ArrayList();
        Integer sum = 0;
        final String[] c = { "#5ed1f7", "#6ce107", "#23b30b", "#ec47f6", "#ffe941", "#daa50f", "#feafb6", "#fd759a", "#949595", "#2a2a2a", "#62d7f9", "#1365bd" };
        if (this.ls != null) {
            for (int i = 0; i < this.ls.size(); ++i) {
                final Map v = (Map) this.ls.get(i);
                sum += Integer.valueOf(v.get("voteHit").toString());
            }
            for (int i = 0; i < this.ls.size(); ++i) {
                final Map v = (Map) this.ls.get(i);
                final double p = Integer.valueOf(v.get("voteHit").toString()) * 1.0 / sum;
                v.put("p", (int)(p * 100.0));
                v.put("c", c[i]);
                ls_temp.add(v);
            }
            this.ls = ls_temp;
        }
        request.setAttribute("m", (Object)m);
        return "success";
    }
    
    public String voteM7() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String m7 = "";
        try {
            final Map i = this.voteService.findUse();
            if (i != null) {
                this.ls = this.voteService.findVoteSelect(Integer.valueOf(i.get("voteId").toString()));
                m7 = String.valueOf(m7) + i.get("voteTitle") + "||" + i.get("voteNum");
                for (int j = 0; j < this.ls.size(); ++j) {
                    m7 = String.valueOf(m7) + "||" + ((Map) this.ls.get(j)).get("voteSelect") + ",,," + ((Map) this.ls.get(j)).get("voteId");
                }
            }
            else {
                m7 = "error";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m7 = "error";
        }
        request.setAttribute("json", (Object)m7);
        return "success";
    }
    
    public String voteM7Modi() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map m7 = new HashMap();
        try {
            final Map i = this.voteService.findUse();
            this.ls = this.voteService.findVoteSelect(Integer.valueOf(i.get("voteId").toString()));
            final List select = new ArrayList();
            m7.put("voteTitle", i.get("voteTitle"));
            for (int j = 0; j < this.ls.size(); ++j) {
                final Map m_vote = new HashMap();
                m_vote.put("voteSelect", ((Map) this.ls.get(j)).get("voteSelect"));
                m_vote.put("voteId", ((Map) this.ls.get(j)).get("voteId"));
                select.add(m_vote);
            }
            m7.put("voteSelect", select);
            m7.put("voteNum", i.get("voteNum"));
            m7.put("state", "success");
        }
        catch (Exception e) {
            e.printStackTrace();
            m7.put("state", "error");
        }
        m7.put("tt", "aa\"aa");
        final JSON json = (JSON)JSONObject.fromObject((Object)m7);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String voteQuery() {
        this.ls = this.voteService.find();
        return "success";
    }
    
    public String voteUse() {
        this.voteService.voteUse(this.voteId);
        this.ls = this.voteService.find();
        return "success";
    }
}
