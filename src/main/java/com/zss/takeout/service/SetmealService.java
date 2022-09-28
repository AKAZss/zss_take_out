package com.zss.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zss.takeout.dto.SetmealDto;
import com.zss.takeout.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐,同时删除套餐和菜品关联关系
     * @param ids
     */
    void deleteWithDish(List<Long> ids);
}
