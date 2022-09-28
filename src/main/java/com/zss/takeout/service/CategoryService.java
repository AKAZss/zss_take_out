package com.zss.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zss.takeout.entity.Category;
import com.zss.takeout.entity.Employee;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
