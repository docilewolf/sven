package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mrlu.sven.SearchParams.PictureParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Picture;
import com.mrlu.sven.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import java.util.List;

/**
 * Created by xiexiyang on 15/4/12.
 */
@Controller
@RequestMapping("/admin/picture")
public class PictureController extends MultiActionController {

    @Autowired
    protected IPictureService pictureService;

    /**
     * picture列表
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(PictureParams pictureParams) {
        logger.info("find picture list.");
        return pictureService.getResultPage(pictureParams);
    }

    /**
     * 新增picture
     */
    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public void save(Picture picture) {
        logger.info("[save picture] " + JSON.toJSONString(picture));
        pictureService.savePicture(picture);
    }

    /**
     * 更新picture
     */
    @RequestMapping(value = "update",method= RequestMethod.POST)
    @ResponseBody
    public void update(Picture picture) throws SvenException {
        logger.info("[update picture] " + JSON.toJSONString(picture));
        pictureService.updatePicture(picture);
    }

    /**
     * 根据ID获取picture
     */
    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) {
        logger.info("[get picture by id] " + id);
        return pictureService.getById(id);
    }


    /**
     * 删除picture
     */
    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("[delete picture] " + id);
        return pictureService.deleteById(id);
    }

    /**
     * 单个文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadQiniu(MultipartFile file) throws Exception {
        return pictureService.uploadQiniu(file);
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadImages", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadQiniu(MultipartFile[] files) throws Exception {
        List<String> list = Lists.newArrayList();
        for (MultipartFile multipartFile: files){
            String fileUrl = pictureService.uploadQiniu(multipartFile);
            list.add(fileUrl);
        };
        return list;
    }
}