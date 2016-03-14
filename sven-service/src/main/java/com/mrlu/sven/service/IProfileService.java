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

    ResultPage<Profile> getResultPage(ProfileParams profileParams);

    Profile saveProfile(Profile profile)throws SvenException;

    Profile deleteById(Long id)throws SvenException;

    void updateProfile(Profile profile)throws SvenException;

    Object getByParam(Profile profile);
}