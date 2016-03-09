package com.mrlu.sven.domain;

import java.io.Serializable;
import java.util.*;



/**
* profile
*/
public class Profile implements Serializable{
    
    public Profile() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 简介名称
    */
    private String name;
    
    /**
    * 简介描述
    */
    private String description;
    
    /**
    * 图片url
    */
    private String imgUrl;
    
    /**
    * 类型 1.文章 2.图片
    */
    private Integer type;
    
    /**
    * 
    */
    private Long createAt;
    
    /**
    * 
    */
    private Integer isdel = 0;
    
    /**
    * 文章id
    */
    private Long essayId;
    
    /**
    * 图片id
    */
    private Long pictureId;

    /**
     * 分类id
     */
    private Long categoryId;
    
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
    
    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
    
    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
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
    
    public Long getEssayId(){
        return essayId;
    }

    public void setEssayId(Long essayId){
        this.essayId = essayId;
    }
    
    public Long getPictureId(){
        return pictureId;
    }

    public void setPictureId(Long pictureId){
        this.pictureId = pictureId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}