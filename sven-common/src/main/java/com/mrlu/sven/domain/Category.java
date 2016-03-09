package com.mrlu.sven.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;



/**
* category
*/
@XmlRootElement
public class Category implements Serializable{
    
    public Category() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 关联编号
    */
    private Long pid = 0L;
    
    /**
    * 名称
    */
    private String name;
    
    /**
    * 
    */
    private Long createAt;
    
    /**
    * 1.文章 2.图片
    */
    private Integer type;
    
    /**
    * 
    */
    private Integer isdel = 0;
    
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public Long getPid(){
        return pid;
    }

    public void setPid(Long pid){
        this.pid = pid;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public Long getCreateAt(){
        return createAt;
    }

    public void setCreateAt(Long createAt){
        this.createAt = createAt;
    }
    
    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
    }
    
    public Integer getIsdel(){
        return isdel;
    }

    public void setIsdel(Integer isdel){
        this.isdel = isdel;
    }
    
}