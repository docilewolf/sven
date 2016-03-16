package com.mrlu.sven.service.impl;

import com.google.common.collect.Maps;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IMemberDao;
import com.mrlu.sven.domain.Member;
import com.mrlu.sven.service.util.AccountUtil;
import com.mrlu.sven.util.AccountStatusEnum;
import com.mrlu.sven.util.AccountTypeEnum;
import com.mrlu.sven.SearchParams.AccountParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.dao.IAccountDao;
import com.mrlu.sven.domain.Account;
import com.mrlu.sven.service.IAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Resource(name="accountDao")
    private IAccountDao accountDao;

    @Resource(name="memberDao")
    private IMemberDao memberDao;

    @Override
    public ResultPage<Account> getResultPage(AccountParams accountParams) {
        ResultPage<Account> resultPage = null;
        HashMap<String, Object> param = accountParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = accountDao.getPageCountByParam(param);
        //查询列表
        List<Account> list = accountDao.getPageListByParam(param);
        resultPage = new ResultPage<Account>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Account saveAccount(Account account)throws SvenException{
        checkAccountParams(account);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", account.getUserName());
        Account userAccount = accountDao.getAccountByParams(map);
        if(userAccount != null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账户名已存在");
        }

        //设置账户信息
        account.setPassword(AccountUtil.generatePassword(account.getPassword()));
        account.setType(AccountTypeEnum.USER.getValue());
        account.setCreateAt(System.currentTimeMillis());
        account.setUpdateAt(System.currentTimeMillis());
        account.setStatus(AccountStatusEnum.AUTHED.getValue());
        accountDao.insert(account);

        //每个账户对应一条个人信息
        Member member = new Member();
        BeanUtils.copyProperties(account, member);
        member.setAccountId(account.getId());
        memberDao.insert(member);
        return account;
    }

    private void checkAccountParams(Account account)throws SvenException{
        if(account == null ||
                StringUtils.isEmpty(account.getUserName())
                || StringUtils.isEmpty(account.getPassword())){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账号或密码不能为空");
        }
    }

    @Override
    public Map<String, Object> getById(Long id) throws SvenException{
        if(null == id) throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账户不存在");
        Account account = accountDao.selectById(id);
        if(null == account) throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账户不存在");
        Member member = memberDao.selectByAccountId(id);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("account", account);
        resultMap.put("member", member);
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserInfo(Account account) throws SvenException {
        if(null == account) throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账户不存在");
        return getById(account.getId());
    }

    @Override
    public Account deleteById(Long id) throws SvenException{
        Account account = accountDao.selectById(id);
        if(null == account){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "account不存在");
        }
        accountDao.deleteById(id);
        return account;
    }

    @Override
    public Account login(Account account) throws SvenException {
        checkAccountParams(account);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", account.getUserName());
        Account userAccount = accountDao.getAccountByParams(map);
        String password = AccountUtil.generatePassword(account.getPassword());
        if(userAccount == null || !userAccount.getPassword().equals(password)){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "账号或密码不正确");
        }
        return userAccount;
    }
}