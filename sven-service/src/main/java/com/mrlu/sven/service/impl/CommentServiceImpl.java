package com.mrlu.sven.service.impl;

import com.mrlu.sven.SearchParams.CommentParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.ICommentDao;
import com.mrlu.sven.dao.IMemberDao;
import com.mrlu.sven.domain.Comment;
import com.mrlu.sven.domain.Member;
import com.mrlu.sven.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {

    @Resource(name="commentDao")
    private ICommentDao commentDao;

    @Autowired
    private IMemberDao memberDao;
    @Override
    public ResultPage<Comment> getResultPage(CommentParams commentParams) {
        ResultPage<Comment> resultPage = null;
        HashMap<String, Object> param = commentParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = commentDao.getPageCountByParam(param);
        //查询列表
        List<Comment> list = commentDao.getPageListByParam(param);
        resultPage = new ResultPage<Comment>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public void saveComment(Comment comment, Long accountId)throws SvenException {
        checkParams(comment);
        Member member = memberDao.selectByAccountId(accountId);
        comment.setFromMemberId(member.getId());
        comment.setCreateAt(System.currentTimeMillis());
        commentDao.insert(comment);
    }

    private void checkParams(Comment comment)throws SvenException{
        if(null == comment
                || comment.getType() == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "信息不全");
        }
    }

    @Override
    public Comment deleteById(Long id)throws SvenException {
        Comment comment = commentDao.selectById(id);
        if(null == comment){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "comment不存在");
        }
        commentDao.deleteById(id);
        return comment;
    }
}