package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.EssayParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Essay;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface IEssayService {

    /**
     * 获取分页数据
     * @param essayParams
     * @return
     */
    ResultPage<Essay> getResultPage(EssayParams essayParams);

    /**
     * 保存essay
     * @param essay
     * @return
     */
    Essay saveEssay(Essay essay);

    /**
     * 根据ID获取essay
     * @param id
     * @return
     */
    Essay getById(Long id);

    void updateEssay(Essay essay)throws SvenException;

    /**
     * 删除essay
     * @param id
     * @return
     */
    Essay deleteById(Long id)throws SvenException;
}