package com.mrlu.sven.service.impl;

import com.mrlu.sven.SearchParams.EssayParams;
import com.mrlu.sven.common.ModelResult;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IEssayDao;
import com.mrlu.sven.domain.Essay;
import com.mrlu.sven.service.IEssayService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("essayService")
public class EssayServiceImpl implements IEssayService {

    @Resource(name="essayDao")
    private IEssayDao essayDao;

    @Override
    public ResultPage<Essay> getResultPage(EssayParams essayParams) {
        ResultPage<Essay> resultPage = null;
        HashMap<String, Object> param = essayParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = essayDao.getPageCountByParam(param);
        //查询列表
        List<Essay> list = essayDao.getPageListByParam(param);
        resultPage = new ResultPage<Essay>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public Essay saveEssay(Essay essay) {
        essay.setCreateAt(System.currentTimeMillis());
        essay.setUpdateAt(System.currentTimeMillis());
        essayDao.insert(essay);
        return essay;
    }

    @Override
    public void updateEssay(Essay essay)throws SvenException{
        Essay oldEssay = essayDao.selectById(essay.getId());
        if(oldEssay == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "essay不存在");
        }
        essay.setUpdateAt(System.currentTimeMillis());
        essayDao.update(essay);
    }

    @Override
    public Essay getById(Long id) {
        Essay essay = essayDao.selectById(id);
        return essay;
    }

    @Override
    public Essay deleteById(Long id)throws SvenException{
        Essay essay = essayDao.selectById(id);
        if(essay == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "essay不存在");
        }
        essayDao.deleteById(id);
        return essay;
    }
}