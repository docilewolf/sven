package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.CategoryParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Category;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface ICategoryService {

    /**
     * 获取分页数据
     * @param categoryParams
     * @return
     */
    ResultPage<Category> getResultPage(CategoryParams categoryParams);

    /**
     * 保存category
     * @param category
     * @return
     */
    void saveCategory(Category category)throws SvenException;

    /**
     * 根据ID获取category
     * @param id
     * @return
     */
    Category getById(Long id);

    /**
     * 删除category
     * @param id
     * @return
     */
    Category deleteById(Long id)throws SvenException;

    void updateCategory(Category category)throws SvenException;
}