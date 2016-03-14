package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.SearchParams.AccountParams;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Account;
import com.mrlu.sven.service.IAccountService;
import com.mrlu.sven.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/admin/account")
public class AccountController extends MultiActionController {
    @Autowired
    protected AccountServiceImpl accountService;

    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(AccountParams accountParams) {
        logger.info("find account list.");
        return accountService.getResultPage(accountParams);
    }

    @RequestMapping(value = "register",method= RequestMethod.POST)
    @ResponseBody
    public Object register(Account account) throws SvenException {
        logger.info("new user register accountInfo: " + JSON.toJSONString(account));
        return accountService.saveAccount(account);
    }

    @RequestMapping(value = "login",method= RequestMethod.POST)
    @ResponseBody
    public Object login(Account account) throws SvenException {
        logger.info("new user login accountInfo: " + JSON.toJSONString(account));
        return accountService.login(account);
    }

    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) {
        logger.info("[get account by id]" + id);
        return accountService.getById(id);
    }

    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("[delete account] " + id);
        return accountService.deleteById(id);
    }

}