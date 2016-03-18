package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Account;

import java.util.HashMap;
import java.util.List;

public interface IAccountDao {

    public Long insert(Account account);

    public Account selectById(Long id);

    public List<Account> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    Account getAccountByParams(HashMap<String, Object> map);

    List<Account> selectByIds(String[] split);
}