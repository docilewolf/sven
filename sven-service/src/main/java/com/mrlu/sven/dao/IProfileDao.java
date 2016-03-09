package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Profile;

import java.util.HashMap;
import java.util.List;

public interface IProfileDao {

    public Long insert(Profile profile);

    public Profile selectById(Long id);

    public List<Profile> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);
}