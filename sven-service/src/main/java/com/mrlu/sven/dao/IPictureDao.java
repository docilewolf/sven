package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Picture;

import java.util.HashMap;
import java.util.List;

public interface IPictureDao {

    public Long insert(Picture picture);

    public Picture selectById(Long id);

    public List<Picture> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    void update(Picture oldPicture);

    void deleteByCategoryId(Long id);
}