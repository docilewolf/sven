package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.PictureParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Picture;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface IPictureService {

    /**
     * 获取分页数据
     * @param pictureParams
     * @return
     */
    ResultPage<Picture> getResultPage(PictureParams pictureParams);

    /**
     * 保存picture
     * @param picture
     * @return
     */
    void savePicture(Picture picture);

    void updatePicture(Picture picture)throws SvenException;
    /**
     * 根据ID获取picture
     * @param id
     * @return
     */
    Picture getById(Long id);

    /**
     * 删除picture
     * @param id
     * @return
     */
    Picture deleteById(Long id)throws SvenException;

    String uploadQiniu(MultipartFile multipartFile)throws Exception;
}