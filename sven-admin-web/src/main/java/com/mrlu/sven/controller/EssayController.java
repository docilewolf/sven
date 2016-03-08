package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.EssayParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Essay;
import com.mrlu.sven.service.IEssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Created by xiexiyang on 15/4/12.
 */
@Controller
@RequestMapping("/admin/essay")
public class EssayController extends MultiActionController {

    @Autowired
    protected IEssayService essayService;

    /**
     * essay列表
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(EssayParams essayParams) {
        logger.info("find essay list.");
        return essayService.getResultPage(essayParams);
    }

    /**
     * 新增essay
     */
    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public Object save(Essay essay) {
        logger.info("[save essay] " + JSON.toJSONString(essay));
        return essayService.saveEssay(essay);
    }

    /**
     * 更新essay
     */
    @RequestMapping(value = "update",method= RequestMethod.POST)
    @ResponseBody
    public void update(Essay essay) throws SvenException {
        logger.info("[update essay] " + JSON.toJSONString(essay));
        essayService.updateEssay(essay);
    }

    /**
     * 根据ID获取essay
     */
    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) {
        logger.info("[get essay by id] " + id);
        return essayService.getById(id);
    }


    /**
     * 删除essay
     */
    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("[delete essay by id] " + id);
        return essayService.deleteById(id);
    }

}