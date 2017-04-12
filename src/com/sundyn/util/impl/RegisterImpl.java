package com.sundyn.util.impl;

import com.sundyn.util.*;
import org.apache.log4j.*;
import com.sundyn.action.*;
import org.apache.struts2.*;
import org.xvolks.jnative.*;

public class RegisterImpl implements Register
{
    private static final Logger logger;
    private boolean use;
    JNative n;
    
    static {
        logger = Logger.getLogger((Class)AppriesAction.class.getClass());
    }
    
    public RegisterImpl() {
        this.n = null;
    }
    
    @Override
    public boolean isUse() {
        return this.use;
    }
    
    public void setUse(final boolean use) {
        this.use = use;
    }
    
    @Override
    public int day() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sreg.dll";
        try {
            (this.n = new JNative(pathtemp, "GetDay")).setRetVal(Type.INT);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return Integer.parseInt(res.toString());
        }
        catch (Exception e) {
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            e.printStackTrace();
            return -2;
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
    
    @Override
    public String getSerial(final String data) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sreg.dll";
        try {
            (this.n = new JNative(pathtemp, "GetReg")).setRetVal(Type.STRING);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            return "";
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
    
    @Override
    public boolean register(final String data) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sreg.dll";
        try {
            (this.n = new JNative(pathtemp, "StartReg")).setRetVal(Type.INT);
            this.n.setParameter(0, Type.STRING, data);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return res.equals("1");
        }
        catch (Exception e) {
            e.printStackTrace();
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            return false;
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
    
    @Override
    public int windowNum() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sreg.dll";
        try {
            (this.n = new JNative(pathtemp, "GetRegwNum")).setRetVal(Type.INT);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return Integer.parseInt(res);
        }
        catch (Exception e) {
            e.printStackTrace();
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            return -2;
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
    
    @Override
    public int datingNum() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sreg.dll";
        try {
            (this.n = new JNative(pathtemp, "GetRegdNum")).setRetVal(Type.INT);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return Integer.parseInt(res);
        }
        catch (Exception e) {
            e.printStackTrace();
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            return -2;
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
    
    @Override
    public boolean regCheck() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            (this.n = new JNative(String.valueOf(path) + "sreg.dll", "RegChk")).setRetVal(Type.INT);
            this.n.invoke();
            final String res = this.n.getRetVal();
            return res.equals("1");
        }
        catch (Exception e) {
            e.printStackTrace();
            RegisterImpl.logger.debug((Object)"dll\u6b7b\u4e86");
            return false;
        }
        finally {
            JNative.unLoadAllLibraries();
            this.n = null;
        }
    }
}
