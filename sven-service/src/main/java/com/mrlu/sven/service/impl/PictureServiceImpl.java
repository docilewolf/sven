package com.mrlu.sven.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mrlu.sven.SearchParams.PictureParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IPictureDao;
import com.mrlu.sven.dao.IPictureUrlDao;
import com.mrlu.sven.domain.Picture;
import com.mrlu.sven.domain.PictureUrl;
import com.mrlu.sven.service.IPictureService;
import com.mrlu.sven.service.util.QiniuUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("pictureService")
public class PictureServiceImpl implements IPictureService {

    @Resource(name="pictureDao")
    private IPictureDao pictureDao;

    @Resource(name = "pictureUrlDao")
    private IPictureUrlDao pictureUrlDao;

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
    public Picture savePicture(PictureParams picture) {
        picture.setCreateAt(System.currentTimeMillis());
        //先存储picture，得到id
        pictureDao.insert(picture);

        //再存储url，一对多关系
        savePictureUrl(picture);

        return picture;
    }

    @Override
    public void updatePicture(PictureParams picture) throws SvenException {
        Picture oldPicture = pictureDao.selectById(picture.getId());
        if(oldPicture == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "picture不存在");
        }
        pictureUrlDao.deleteByPictureId(picture.getId());
        savePictureUrl(picture);
    }

    private void savePictureUrl(PictureParams pictureParams){
        List<PictureUrl> pictureUrllist = Lists.newArrayList();
        for(String url: pictureParams.getPictureUrlList()){
            PictureUrl pictureUrl = new PictureUrl();
            pictureUrl.setCreateAt(System.currentTimeMillis());
            pictureUrl.setPictureId(pictureParams.getId());
            pictureUrl.setUrl(url);
            pictureUrllist.add(pictureUrl);
        }
        if(!CollectionUtils.isEmpty(pictureUrllist)){
            pictureUrlDao.batchInsert(pictureUrllist);
        }
    }

    @Override
    public Map<String, Object> getById(Long id) {

        List<PictureUrl> pictureUrlList = pictureUrlDao.selectByPictureId(id);
        Picture picture = pictureDao.selectById(id);
        Map<String, Object> map = Maps.newHashMap();
        map.put("pictureUrlList", pictureUrlList);
        map.put("picture", picture);
        return map;
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