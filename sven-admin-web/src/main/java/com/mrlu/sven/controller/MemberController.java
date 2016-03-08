package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.MemberParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Member;
import com.mrlu.sven.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/admin/member")
public class MemberController extends MultiActionController {

    @Autowired
    protected IMemberService memberService;

    /**
     * member列表
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(MemberParams memberParams) {
        logger.info("find member list.");
        return memberService.getResultPage(memberParams);
    }

    /**
     * 更新member
     */
    @RequestMapping(value = "update",method= RequestMethod.POST)
    @ResponseBody
    public void update(Member member) throws SvenException {
        logger.info("[update member] " + JSON.toJSONString(member));
        memberService.updateMember(member);
    }

    /**
     * 根据ID获取member
     */
    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) {
        logger.info("[get member by id] " + id);
        return memberService.getById(id);
    }

}