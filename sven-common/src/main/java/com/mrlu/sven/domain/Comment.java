package com.mrlu.sven.domain;

import java.io.Serializable;
import java.util.*;



/**
* comment
*/
public class Comment implements Serializable{
    
    public Comment() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 
    */
    private Long essayId;
    
    /**
    * 
    */
    private Long pictureId;
    
    /**
    * 
    */
    private Long commentId;
    
    /**
    * 
    */
    private String content;
    
    /**
    * 
    */
    private Long fromAccountId;
    
    /**
    * 
    */
    private Long fromMemberId;
    
    /**
    * 
    */
    private Long createAt;
    
    /**
    * 11.文章评论 12.图片评论 20.留言 30.回复
    */
    private Integer type;
    
    /**
    * 
    */
    private Long toAccountId;
    
    /**
    * 
    */
    private Long toMemberId;
    
    /**
    * 
    */
    private Integer isdel;
    
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
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
    
    public Long getCommentId(){
        return commentId;
    }

    public void setCommentId(Long commentId){
        this.commentId = commentId;
    }
    
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
    
    public Long getFromAccountId(){
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId){
        this.fromAccountId = fromAccountId;
    }
    
    public Long getFromMemberId(){
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId){
        this.fromMemberId = fromMemberId;
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
    
    public Long getToAccountId(){
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId){
        this.toAccountId = toAccountId;
    }
    
    public Long getToMemberId(){
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId){
        this.toMemberId = toMemberId;
    }
    
    public Integer getIsdel(){
        return isdel;
    }

    public void setIsdel(Integer isdel){
        this.isdel = isdel;
    }
    
}