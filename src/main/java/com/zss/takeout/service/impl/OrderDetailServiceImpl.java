package com.zss.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zss.takeout.entity.OrderDetail;
import com.zss.takeout.mapper.OrderDetailMapper;
import com.zss.takeout.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
