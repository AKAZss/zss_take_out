package com.zss.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zss.takeout.entity.Category;
import com.zss.takeout.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
