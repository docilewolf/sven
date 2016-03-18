package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.CommentParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.controller.util.ContextUtil;
import com.mrlu.sven.domain.Account;
import com.mrlu.sven.domain.Comment;
import com.mrlu.sven.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/comment")
public class CommentController extends MultiActionController {

    @Autowired
    protected ICommentService commentService;

    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(CommentParams commentParams) {
        logger.info("[find comment list params:] " + JSON.toJSONString(commentParams));
        return commentService.getResultPage(commentParams);
    }

    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public void save(Comment comment, HttpServletRequest request) throws SvenException {
        logger.info("[save comment] " + JSON.toJSONString(comment));
        Account account = ContextUtil.validCookie(request);
        commentService.saveComment(comment, account);
    }

    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("[delete comment] " + id);
        return commentService.deleteById(id);
    }

}