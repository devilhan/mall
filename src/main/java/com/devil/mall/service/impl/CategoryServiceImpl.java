package com.devil.mall.service.impl;

import com.devil.mall.consts.MallConst;
import com.devil.mall.dao.CategoryMapper;
import com.devil.mall.pojo.Category;
import com.devil.mall.service.ICategoryService;
import com.devil.mall.vo.CategoryVo;
import com.devil.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hanyanjiao
 * @date 2020/5/22
 */

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> findAll() {
        List<Category> categories = categoryMapper.findAll();
      /*for (Category category:categories){
            if(category.getParentId() .equals(MallConst.ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                categoryVos.add(categoryVo);
            }
        }*/

        //lambda + stream
        List<CategoryVo> categoryVos = categories.stream().filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categoryVos,categories);
        return ResponseVo.success(categoryVos);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.findAll();

        findSubCategoryId(id,resultSet,categories);

    }

    public void findSubCategoryId(Integer id,Set<Integer> resultSet,List<Category> categories){
        for(Category category:categories){
            //获取子目录
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());

                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }

    /**
     * 查询子目录
     * @param categoryVos
     * @param categories  数据源
     */
    private void findSubCategory(List<CategoryVo> categoryVos,List<Category> categories){

        for(CategoryVo categoryVo:categoryVos){
            List<CategoryVo> subCategoryList = new ArrayList();
            for (Category category:categories){
                //如果查到内容，添加到子目录中，继续往下查
                if(categoryVo.getId().equals(category.getParentId())){
                    CategoryVo subCategoryVo1 = category2CategoryVo(category);
                    subCategoryList.add(subCategoryVo1);
                }
                subCategoryList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());

                categoryVo.setSubCategories(subCategoryList);
                findSubCategory(subCategoryList,categories);
            }
        }
    }

    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
