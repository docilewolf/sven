package com.mrlu.sven.service.impl;

import com.mrlu.sven.SearchParams.ProfileParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IProfileDao;
import com.mrlu.sven.domain.Profile;
import com.mrlu.sven.service.IProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("profileService")
public class ProfileServiceImpl implements IProfileService {

    @Resource(name="profileDao")
    private IProfileDao profileDao;

    @Override
    public ResultPage<Profile> getResultPage(ProfileParams profileParams) {
        ResultPage<Profile> resultPage = null;
        HashMap<String, Object> param = profileParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = profileDao.getPageCountByParam(param);
        //查询列表
        List<Profile> list = profileDao.getPageListByParam(param);
        resultPage = new ResultPage<Profile>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public Profile saveProfile(Profile profile)throws SvenException{
        checkParam(profile);
        profile.setCreateAt(System.currentTimeMillis());
        profileDao.insert(profile);
        return profile;
    }

    private void checkParam(Profile profile)throws SvenException {
        if(StringUtils.isEmpty(profile.getName()) ||
                StringUtils.isEmpty(profile.getDescription()) ||
                StringUtils.isEmpty(profile.getImgUrl()) ||
                profile.getType() == null || (
                    profile.getEssayId() == null && profile.getPictureId() == null
                )){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "信息不全");
        }
    }

    @Override
    public Profile deleteById(Long id)throws SvenException{
        Profile profile = profileDao.selectById(id);
        if(profile == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "profile不存在");
        }
        profileDao.deleteById(id);
        return profile;
    }
}