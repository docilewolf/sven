package com.mrlu.sven.controller;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.SearchParams.CategoryParams;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Category;
import com.mrlu.sven.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/admin/category")
public class CategoryController extends MultiActionController {

    @Autowired
    protected ICategoryService categoryService;

    /**
     * category列表
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    @ResponseBody
    public Object list(CategoryParams categoryParams) {
        logger.info("find category list.");
        return categoryService.getResultPage(categoryParams);
    }

    /**
     * 新增category
     */
    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public void save(Category category) throws SvenException {
        logger.info("[save category] " + JSON.toJSONString(category));
        categoryService.saveCategory(category);
    }

    /**
     * 更新category
     */
    @RequestMapping(value = "update",method= RequestMethod.POST)
    @ResponseBody
    public void update(Category category) throws SvenException {
        logger.info("[update category] " + JSON.toJSONString(category));
        categoryService.updateCategory(category);
    }

    /**
     * 根据ID获取category
     * return: profileList
     */
    @RequestMapping(value = "getById",method= RequestMethod.GET)
    @ResponseBody
    public Object getById(Long id) {
        logger.info("[get category by id] " + id);
        return categoryService.getById(id);
    }


    /**
     * 删除category
     */
    @RequestMapping(value = "deleteById",method= RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id) throws SvenException {
        logger.info("[delete category] " + id);
        return categoryService.deleteById(id);
    }

}