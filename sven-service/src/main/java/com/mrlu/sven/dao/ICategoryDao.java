package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Category;

import java.util.HashMap;
import java.util.List;

public interface ICategoryDao {

    public Long insert(Category category);

    public Category selectById(Long id);

    public List<Category> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    void update(Category category);

    List<Category> selectByPid(Long id);
}