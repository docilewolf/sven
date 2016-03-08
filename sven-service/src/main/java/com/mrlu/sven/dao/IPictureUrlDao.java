package com.mrlu.sven.dao;

import com.mrlu.sven.domain.PictureUrl;

import java.util.HashMap;
import java.util.List;

public interface IPictureUrlDao {

    public Long insert(PictureUrl pictureUrl);

    public PictureUrl selectById(Long id);

    public List<PictureUrl> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    void update(PictureUrl oldPictureUrl);
}