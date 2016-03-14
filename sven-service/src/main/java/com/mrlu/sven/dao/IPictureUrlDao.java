package com.mrlu.sven.dao;

import com.mrlu.sven.domain.PictureUrl;

import java.util.HashMap;
import java.util.List;

public interface IPictureUrlDao {

    public void batchInsert(List<PictureUrl> pictureUrlList);

    public List<PictureUrl> selectByPictureId(Long id);

    public void deleteByPictureId(Long id);
}