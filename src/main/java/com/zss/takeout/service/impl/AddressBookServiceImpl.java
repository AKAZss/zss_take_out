package com.zss.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zss.takeout.entity.AddressBook;
import com.zss.takeout.mapper.AddressBookMapper;
import com.zss.takeout.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
