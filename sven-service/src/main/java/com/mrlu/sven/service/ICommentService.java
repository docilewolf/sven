package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.CommentParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Comment;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface ICommentService {

    /**
     * 获取分页数据
     * @param commentParams
     * @return
     */
    ResultPage<Comment> getResultPage(CommentParams commentParams);

    /**
     * 保存comment
     * @param comment
     * @return
     */
    void saveComment(Comment comment, Long accountId)throws SvenException;

    /**
     * 删除comment
     * @param id
     * @return
     */
    Comment deleteById(Long id)throws SvenException;
}