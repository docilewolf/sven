package com.mrlu.sven.service.impl;

import com.mrlu.sven.SearchParams.PictureParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IPictureDao;
import com.mrlu.sven.domain.Picture;
import com.mrlu.sven.service.IPictureService;
import com.mrlu.sven.service.util.QiniuUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("pictureService")
public class PictureServiceImpl implements IPictureService {

    @Resource(name="pictureDao")
    private IPictureDao pictureDao;

    @Override
    public ResultPage<Picture> getResultPage(PictureParams pictureParams) {
        ResultPage<Picture> resultPage = null;
        HashMap<String, Object> param = pictureParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = pictureDao.getPageCountByParam(param);
        //查询列表
        List<Picture> list = pictureDao.getPageListByParam(param);
        resultPage = new ResultPage<Picture>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public void savePicture(Picture picture) {
        pictureDao.insert(picture);
    }

    @Override
    public void updatePicture(Picture picture) throws SvenException {
        Picture oldPicture = pictureDao.selectById(picture.getId());
        if(oldPicture == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "picture不存在");
        }
        pictureDao.update(picture);
    }

    @Override
    public Picture getById(Long id) {
        
        Picture picture = pictureDao.selectById(id);

        return picture;
    }

    @Override
    public Picture deleteById(Long id)throws SvenException{
        Picture picture = pictureDao.selectById(id);
        if(picture == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "picture不存在");
        }
        pictureDao.deleteById(id);
        return picture;
    }

    @Override
    public String uploadQiniu(MultipartFile multipartFile) throws Exception{
        return QiniuUtil.upload(multipartFile.getBytes(), multipartFile.getOriginalFilename());
    }
}