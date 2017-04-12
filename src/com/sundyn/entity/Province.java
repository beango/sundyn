package com.sundyn.entity;

import java.io.*;
import java.util.*;

public class Province implements Serializable
{
    private int id;
    private String name;
    private List<City> citys;
    private String citysString;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public List<City> getCitys() {
        return this.citys;
    }
    
    public void setCitys(final List<City> citys) {
        this.citys = citys;
    }
    
    public String getCitysString() {
        return this.citysString;
    }
    
    public void setCitysString(final String citysString) {
        this.citysString = citysString;
    }
}
