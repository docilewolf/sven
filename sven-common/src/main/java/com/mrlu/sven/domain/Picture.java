package com.mrlu.sven.domain;

import java.io.Serializable;
import java.util.*;



/**
* picture
*/
public class Picture implements Serializable{
    
    public Picture() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 
    */
    private Long categoryId;
    
    /**
    * 
    */
    private Long createAt;
    
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
    
    public Long getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }
    
    public Long getCreateAt(){
        return createAt;
    }

    public void setCreateAt(Long createAt){
        this.createAt = createAt;
    }
    
    public Integer getIsdel(){
        return isdel;
    }

    public void setIsdel(Integer isdel){
        this.isdel = isdel;
    }
    
}