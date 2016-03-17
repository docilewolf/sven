package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.AccountParams;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.controller.util.ContextUtil;
import com.mrlu.sven.domain.Account;
import com.mrlu.sven.service.IAccountService;
import com.mrlu.sven.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/account")
public class AccountController extends MultiActionController {
    @Autowired
    protected IAccountService accountService;

    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(AccountParams accountParams) {
        logger.info("find account list.");
        return accountService.getResultPage(accountParams);
    }

    @RequestMapping(value = "register",method= RequestMethod.POST)
    @ResponseBody
    public Object register(Account account, HttpServletResponse response) throws SvenException {
        logger.info("new user register accountInfo: " + JSON.toJSONString(account));
        accountService.saveAccount(account);
        return ContextUtil.setCookie(account, response);
    }

    @RequestMapping(value = "login",method= RequestMethod.POST)
    @ResponseBody
    public Object login(Account account, HttpServletResponse response) throws SvenException {
        logger.info("new user login accountInfo: " + JSON.toJSONString(account));
        return ContextUtil.setCookie(accountService.login(account), response);
    }

    @RequestMapping(value = "logout",method= RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpServletRequest request, HttpServletResponse response){
        return ContextUtil.rmCookie(request, response);
    }

    @RequestMapping(value = "getUserInfo",method= RequestMethod.GET)
    @ResponseBody
    public Object getUserInfo(HttpServletRequest request) throws SvenException {
      return accountService.getUserInfo(ContextUtil.validCookie(request));
    }

    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) throws SvenException{
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