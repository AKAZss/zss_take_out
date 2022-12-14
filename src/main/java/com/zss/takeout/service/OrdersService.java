package com.zss.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zss.takeout.entity.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     * 提交订单
     * @param orders
     */
    void submit(Orders orders);
}
