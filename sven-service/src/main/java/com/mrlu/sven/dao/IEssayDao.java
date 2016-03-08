package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Essay;

import java.util.HashMap;
import java.util.List;

public interface IEssayDao {

    public Long insert(Essay essay);

    public Essay selectById(Long id);

    public List<Essay> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    void update(Essay essay);

    void deleteByCategoryId(Long id);
}