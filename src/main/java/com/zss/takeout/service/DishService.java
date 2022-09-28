package com.zss.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zss.takeout.dto.DishDto;
import com.zss.takeout.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，插入菜品和口味，操作两张表
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
