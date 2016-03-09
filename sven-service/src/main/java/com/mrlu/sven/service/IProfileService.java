package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.ProfileParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Profile;

import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface IProfileService {

    /**
     * 获取分页数据
     * @param profileParams
     * @return
     */
    ResultPage<Profile> getResultPage(ProfileParams profileParams);

    /**
     * 保存profile
     * @param profile
     * @return
     */
    Profile saveProfile(Profile profile)throws SvenException;

    /**
     * 删除profile
     * @param id
     * @return
     */
    Profile deleteById(Long id)throws SvenException;
}