package com.mrlu.sven.domain;

import java.io.Serializable;
import java.util.*;



/**
* essay
*/
public class Essay implements Serializable{
    
    public Essay() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 
    */
    private String name;
    
    /**
    * 
    */
    private String description;
    
    /**
    * 
    */
    private String content;
    
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
    private Long updateAt;
    
    /**
    * 
    */
    private Integer isdel = 0;

    private Integer isMarkdown = 0;
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
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
    
    public Long getUpdateAt(){
        return updateAt;
    }

    public void setUpdateAt(Long updateAt){
        this.updateAt = updateAt;
    }
    
    public Integer getIsdel(){
        return isdel;
    }

    public void setIsdel(Integer isdel){
        this.isdel = isdel;
    }

    public Integer getIsMarkdown() {
        return isMarkdown;
    }

    public void setIsMarkdown(Integer isMarkdown) {
        this.isMarkdown = isMarkdown;
    }
}