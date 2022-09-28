package com.zss.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zss.takeout.common.CustomException;
import com.zss.takeout.entity.Category;
import com.zss.takeout.entity.Dish;
import com.zss.takeout.entity.Employee;
import com.zss.takeout.entity.Setmeal;
import com.zss.takeout.mapper.CategoryMapper;
import com.zss.takeout.mapper.EmployeeMapper;
import com.zss.takeout.service.CategoryService;
import com.zss.takeout.service.DishService;
import com.zss.takeout.service.EmployeeService;
import com.zss.takeout.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //添加查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询分类是否关联菜品，关联，抛出异常
        if(count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除！");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //查询分类是否关联套餐，关联，抛出异常
        if(count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除！");
        }
        super.removeById(id);
    }
}
