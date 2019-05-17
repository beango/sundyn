package com.sundyn.util;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

/**
 *HttpSessionBindingListener接口有两方需要实现的方法:
 *public synchronized void valueBound(HttpSessionBindingEvent httpsessionbindingevent)
 *public synchronized void valueUnbound(HttpSessionBindingEvent httpsessionbindingevent)
 *Session创建的时候Servlet容器将会调用valueBound方法;Session删除的时候则调用valueUnbound方法.
 */
public class onLineUser implements HttpSessionBindingListener
{
    public onLineUser()
    {
    }

    //保存在线用户的向量
    private Vector users=new Vector();

    //得到用户总数
    public int getCount()
    {
        users.trimToSize();
        return users.capacity();
    }

    //判断是否存在指定的用户
    public boolean existUser(String userName)
    {
        users.trimToSize();
        boolean existUser=false;
        for (int i=0;i<users.size();i++)
        {
            if (userName.equals((String)users.get(i)))
            {
                existUser=true;
                break;
            }
        }
        return existUser;
    }

    //删除指定的用户
    public boolean deleteUser(String userName)
    {
        users.trimToSize();
        if(existUser(userName))
        {
            int currUserIndex=-1;
            for(int i=0;i<users.size();i++)
            {
                if(userName.equals((String)users.get(i)))
                {
                    currUserIndex=i;
                    break;
                }
            }
            if (currUserIndex!=-1)
            {
                users.remove(currUserIndex);
                users.trimToSize();
                return true;
            }
        }
        return false;
    }

    //得到当前在线用户的列表
    public Vector getOnLineUser()
    {
        return users;
    }

    public void valueBound(HttpSessionBindingEvent e)
    {
        users.trimToSize();
        System.out.println("");
        if(!existUser(e.getName()))
        {
            users.add(e.getName());
            System.out.print(e.getName()+"\t  登入到系统\t"+(new Date()));
            System.out.println("     在线用户数为："+getCount());
        }else
            System.out.println(e.getName()+"已经存在");
    }

    public void valueUnbound(HttpSessionBindingEvent e)
    {
        users.trimToSize();
        String userName=e.getName();
        deleteUser(userName);
        System.out.print(userName+"\t  退出系统\t"+(new Date()));
        System.out.println("     在线用户数为："+getCount());
    }
}
