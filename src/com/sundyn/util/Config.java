package com.sundyn.util;

import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.*;
import java.io.*;

public class Config
{
    SAXBuilder sb;
    Document doc;
    XMLOutputter XMLOut;
    Format format;
    String version;
    
    public Config(final String path) {
        this.sb = new SAXBuilder();
        this.format = Format.getPrettyFormat();
        this.version = "";
        try {
            this.doc = this.sb.build(path);
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void versionIncrease() {
        final Element root = this.doc.getRootElement();
        this.version = root.getChild("version").getText();
        this.version = new StringBuilder().append(Integer.parseInt(this.version) + 1).toString();
        root.getChild("version").setText(this.version);
    }
    
    public void saveAs(final String path) {
        this.format.setIndent("    ");
        this.format.setEncoding("gb2312");
        this.XMLOut = new XMLOutputter(this.format);
        try {
            this.XMLOut.output(this.doc, (OutputStream)new FileOutputStream(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        this.XMLOut = null;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(final String version) {
        this.version = version;
    }
}
