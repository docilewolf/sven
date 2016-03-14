package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.ProfileParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Profile;
import com.mrlu.sven.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/admin/profile")
public class ProfileController extends MultiActionController {

    @Autowired
    protected IProfileService profileService;

    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(ProfileParams profileParams) {
        logger.info("find profile list.");
        return profileService.getResultPage(profileParams);
    }

    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public void save(Profile profile) throws SvenException {
        logger.info("[save profile] " + JSON.toJSONString(profile));
        profileService.saveProfile(profile);
    }

    @RequestMapping(value = "getByParam",method= RequestMethod.GET)
    @ResponseBody
    public Object getByParam(Profile profile){
        logger.info("[getByParam profile] " + JSON.toJSONString(profile));
        return profileService.getByParam(profile);
    }

    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("delete profile id:" + id);
        return profileService.deleteById(id);
    }

    @RequestMapping(value = "update",method= RequestMethod.POST)
    @ResponseBody
    public void update(Profile profile) throws SvenException {
        logger.info("[update profile] " + JSON.toJSONString(profile));
        profileService.updateProfile(profile);
    }
}