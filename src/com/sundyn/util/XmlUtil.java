package com.sundyn.util;

import com.sundyn.vo.button;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlUtil {
    /**
     *  xml转换成bean
     * @param xml
     * @param obj
     * @return
     */
    public static Object xmlToBean(String xml,Class obj){
        SAXBuilder builder=new SAXBuilder();
        Field[] fields = obj.getDeclaredFields();
        String  beanName=obj.getSimpleName();
        try {
            Object object = Class.forName(obj.getName()).newInstance();
            Document doc = builder.build(new StringReader(xml));
            Element books = doc.getRootElement();
            List booklist = books.getChildren(beanName);
            for (Iterator iter = booklist.iterator(); iter.hasNext(); ) {
                Element book = (Element) iter.next();
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    if (!fields[j].toString().contains("final")) {
                        fields[j].set(object,book.getChildTextTrim(fields[j].getName())==null?"":book.getChildTextTrim(fields[j].getName()));
                    }
                }
            }
            System.out.println(fields.toString());
            return object;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * xml转换成list
     * @param xml
     * @param obj
     * @return
     */
    public static List<?> xmlToList(String xml,Class obj){
        SAXBuilder builder=new SAXBuilder();
        Field[] fields = obj.getDeclaredFields();
        String beanName=obj.getSimpleName();
        try {

            List<Object> list=new ArrayList<Object>();
            Document doc = builder.build(new StringReader(xml));
            Element books = doc.getRootElement();
            List booklist = books.getChildren(beanName);
            for (Iterator iter = booklist.iterator(); iter.hasNext(); ) {
                Object object = Class.forName(obj.getName()).newInstance();
                Element book = (Element) iter.next();
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    if (!fields[j].toString().contains("final")) {
                        fields[j].set(object,book.getChildTextTrim(fields[j].getName())==null?"":book.getChildTextTrim(fields[j].getName()));
                        //System.out.println(book.getChildTextTrim(fields[j].getName())==null?"":book.getChildTextTrim(fields[j].getName()));
                    }
                }
                list.add(object);
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * bean转换成xml
     */
    public static String beanToXmls(Object obj){
        Class t=(Class)obj.getClass();
        Field[] fields = t.getDeclaredFields();
        StringBuffer buffer=new StringBuffer();
        buffer.append("<Data><Vehicle>");
        try {
            Object object = Class.forName(obj.getClass().getName()).newInstance();
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                String val = fields[j].get(obj)==null?"": fields[j].get(obj)+"";
                buffer.append("<"+fields[j].getName()+">"+val+"</"+fields[j].getName()+">\n");
            }
            buffer.append("</Data></Vehicle>");
            return buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<button> o= (List<button>) xmlToList("<root><button><text>非常满意</text><size>12</size><r>0</r><g>0</g><b>0</b><x>0</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>215</lx><ly>0</ly></button><button><text>满意</text><size>12</size><r>0</r><g>0</g><b>0</b><x>1</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>434</lx><ly>0</ly></button><button><text>基本满意</text><size>12</size><r>0</r><g>0</g><b>0</b><x>2</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>654</lx><ly>0</ly></button><button><text>不满意</text><size>12</size><r>0</r><g>0</g><b>0</b><x>3</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>147</lx><ly>195</ly></button><button><text>很不满意</text><size>12</size><r>0</r><g>0</g><b>0</b><x>4</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>350</lx><ly>195</ly></button><button><text>业务不熟</text><size>12</size><r>0</r><g>0</g><b>0</b><x>5</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>555</lx><ly>195</ly></button><button><text>态度不好</text><size>12</size><r>0</r><g>0</g><b>0</b><x>6</x><y>0</y><img>eval_0.png</img><width>150</width><height>150</height><lx>746</lx><ly>195</ly></button></root>", button.class);
        for (int i=0; i<o.size(); i++)
            System.out.println(o.get(i).getText());
    }
}
