package com.mrlu.sven.domain;

import java.io.Serializable;


/**
* account
*/
public class Account implements Serializable{
    
    public Account() {
    }

    
    /**
    * 编号
    */
    private Long id;
    
    /**
    * 
    */
    private String userName;
    
    /**
    * 
    */
    private String password;
    
    /**
    * 
    */
    private String email;
    
    /**
    * 
    */
    private String mobile;
    
    /**
    * 1.管理员 2.普通用户
    */
    private Integer type;
    
    /**
    * 1.未认证 2.已认证 3.黑名单
    */
    private Integer status;
    
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
    
    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    
    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
    }
    
    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
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