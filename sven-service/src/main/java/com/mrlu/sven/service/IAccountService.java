package com.mrlu.sven.service;

import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.SearchParams.AccountParams;
import com.mrlu.sven.domain.Account;

import java.util.List;
import java.util.Map;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface IAccountService {

    /**
     * 获取分页数据
     * @param accountParams
     * @return
     */
    ResultPage<Account> getResultPage(AccountParams accountParams);

    /**
     * 保存account
     * @param account
     * @return
     */
    Account saveAccount(Account account)throws SvenException;

    /**
     * 根据ID获取account
     * @param id
     * @return
     */
    Map<String, Object> getById(Long id)throws SvenException;

    Map<String, Object> getUserInfo(Account account)throws SvenException;
    /**
     * 删除account
     * @param id
     * @return
     */
    Account deleteById(Long id)throws SvenException;

    Account login(Account account)throws SvenException;

    List<Account> getByIds(String ids);
}