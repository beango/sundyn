package com.sundyn.util.impl;

import com.sundyn.util.*;
import org.apache.struts2.*;
import org.xvolks.jnative.*;

public class DesImpl implements Des
{
    @Override
    public String sDecryStr(final String str, final String key) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sdes.dll";
        JNative n = null;
        try {
            n = new JNative(pathtemp, "sEncryStrHex");
            n.setParameter(0, str);
            n.setParameter(1, key);
            n.setRetVal(Type.STRING);
            n.invoke();
            final String res = n.getRetVal();
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            JNative.unLoadAllLibraries();
            n = null;
        }
    }
    
    @Override
    public String sEncryStr(final String str, final String key) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String pathtemp = String.valueOf(path) + "sdes.dll";
        JNative n = null;
        try {
            n = new JNative(pathtemp, "sDecryStrHex");
            n.setParameter(0, str);
            n.setParameter(1, key);
            n.setRetVal(Type.STRING);
            n.invoke();
            final String res = n.getRetVal();
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            JNative.unLoadAllLibraries();
            n = null;
        }
    }
    
    @Override
    public String decode(final String str) {
        return this.sDecryStr(str, "iamthebesthehe");
    }
    
    @Override
    public String encode(final String str) {
        return this.sEncryStr(str, "iamthebesthehe");
    }
    
    public static void main(final String[] args) {
        for (int i = 0; i < 100000; ++i) {
            final String pathtemp = "D:/apache-tomcat-6.0.20/webapps/pingjia2/sdes.dll";
            JNative n = null;
            String res = "";
            try {
                n = new JNative(pathtemp, "sDecryStrHex");
                n.setParameter(0, Type.STRING, "4695625C28FADF59");
                n.setParameter(1, Type.STRING, "iamthebesthehe");
                n.setRetVal(Type.STRING);
                n.invoke();
                res = n.getRetVal();
            }
            catch (Exception e) {
                e.printStackTrace();
                res = "";
                continue;
            }
            finally {
                JNative.unLoadAllLibraries();
            }
            JNative.unLoadAllLibraries();
        }
    }
}
