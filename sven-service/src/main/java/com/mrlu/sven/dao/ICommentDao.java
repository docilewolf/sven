package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Comment;

import java.util.HashMap;
import java.util.List;

public interface ICommentDao {

    public Long insert(Comment comment);

    public Comment selectById(Long id);

    public List<Comment> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

}