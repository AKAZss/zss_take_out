package com.zss.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zss.takeout.common.CustomException;
import com.zss.takeout.dto.SetmealDto;
import com.zss.takeout.entity.Setmeal;
import com.zss.takeout.entity.SetmealDish;
import com.zss.takeout.mapper.SetmealMapper;
import com.zss.takeout.service.SetmealDishService;
import com.zss.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐信息
        this.save(setmealDto);

        //保存菜品套餐信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐,同时删除套餐和菜品关联关系
     * @param ids
     */
    @Override
    @Transactional
    public void deleteWithDish(List<Long> ids) {
        //查询套餐状态，确定是否可以删除
        // select count(*) from setmeal where id in (1,2,3,4) and status = 1
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        //不可以则抛异常
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //删除套餐
        this.removeByIds(ids);
        //delete from setmeal_dish where setmeal_id in (1,2,3,4)
        //删除关联菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper1);
    }
}
