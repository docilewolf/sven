package com.mrlu.sven.domain;

import java.io.Serializable;
import java.util.*;



/**
* member
*/
public class Member implements Serializable{
    
    public Member() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 账户id
    */
    private Long accountId;
    
    /**
    * 
    */
    private String name;
    
    /**
    * 头像
    */
    private String headImg;
    
    /**
    * 
    */
    private Integer age;
    
    /**
    * 0.未知 1.男 2.女
    */
    private Integer gender;
    
    /**
    * 
    */
    private String mobile;
    
    /**
    * 
    */
    private String email;
    
    /**
    * 签名
    */
    private String signature;
    
    /**
    * 
    */
    private Long createAt;
    
    /**
    * 
    */
    private Long updateAt;
    
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public Long getAccountId(){
        return accountId;
    }

    public void setAccountId(Long accountId){
        this.accountId = accountId;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getHeadImg(){
        return headImg;
    }

    public void setHeadImg(String headImg){
        this.headImg = headImg;
    }
    
    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }
    
    public Integer getGender(){
        return gender;
    }

    public void setGender(Integer gender){
        this.gender = gender;
    }
    
    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public String getSignature(){
        return signature;
    }

    public void setSignature(String signature){
        this.signature = signature;
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
    
}