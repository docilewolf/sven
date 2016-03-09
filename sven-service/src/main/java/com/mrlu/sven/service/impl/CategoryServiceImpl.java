package com.mrlu.sven.service.impl;

import com.google.common.collect.Lists;
import com.mrlu.sven.SearchParams.CategoryParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.ICategoryDao;
import com.mrlu.sven.dao.IEssayDao;
import com.mrlu.sven.dao.IPictureDao;
import com.mrlu.sven.domain.Category;
import com.mrlu.sven.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Resource(name="categoryDao")
    private ICategoryDao categoryDao;

    @Resource(name="essayDao")
    private IEssayDao essayDao;

    @Autowired
    private IPictureDao pictureDao;
    @Override
    public ResultPage<Category> getResultPage(CategoryParams categoryParams) {
        ResultPage<Category> resultPage = null;
        HashMap<String, Object> param = categoryParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = categoryDao.getPageCountByParam(param);
        categoryDao.getPageCountByParam(param);
        categoryDao.getPageListByParam(param);
        //查询列表
        List<Category> list = categoryDao.getPageListByParam(param);
        resultPage = new ResultPage<Category>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public Category saveCategory(Category category)throws SvenException{
        checkParams(category);
        category.setCreateAt(System.currentTimeMillis());
        categoryDao.insert(category);
        return category;
    }

    private void checkParams(Category category)throws SvenException{
        if(category == null ||
                category.getType() == null
                || StringUtils.isEmpty(category.getName())){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "信息不全");
        }
        if(!Long.valueOf(0L).equals(category.getPid())){
            Category parentCategory = categoryDao.selectById(category.getPid());
            if(parentCategory == null){
                throw new SvenException(HttpStatus.BAD_REQUEST.value(), "父category不存在");
            }
        }
    }

    @Override
    public Category getById(Long id) {
        Category category = categoryDao.selectById(id);
        return category;
    }

    @Override
    public Category deleteById(Long id)throws SvenException{
        Category category = categoryDao.selectById(id);
        if(category == null){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "category不存在");
        }

        //删除此节点
        categoryDao.deleteById(id);

        List<Category> categoryList = categoryDao.selectByPid(id);
        for(Category childCateGory: categoryList){
            //递归删除此节点下的所有子节点
            deleteById(childCateGory.getId());
        }

        //清空节点下的所有essay的category id
        essayDao.deleteByCategoryId(id);

        //清空节点下的所有picture的category id
        pictureDao.deleteByCategoryId(id);
        return category;
    }

    @Override
    public void updateCategory(Category category) throws SvenException {
        Category oldCategory = categoryDao.selectById(category.getId());
        if(null == oldCategory){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "category不存在");
        }
        checkParams(category);
        categoryDao.update(category);
    }
}