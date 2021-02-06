package com.vcmy.service.strategy.impl;

import com.vcmy.dao.OutportGroupDao;
import com.vcmy.dao.PortDao;
import com.vcmy.entity.OutPortGroup;
import com.vcmy.service.strategy.OutPortGroupService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: OutPortGroupImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2021/1/3 19:44
 */
@Service
public class OutPortGroupServiceImpl implements OutPortGroupService {

    @Autowired
    private OutportGroupDao outportGroupDao;

}
