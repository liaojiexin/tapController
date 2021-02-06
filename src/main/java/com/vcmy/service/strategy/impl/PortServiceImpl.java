package com.vcmy.service.strategy.impl;

import com.vcmy.dao.PortDao;
import com.vcmy.entity.Port;
import com.vcmy.service.strategy.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: PortServiceImpl
 * @Description: TODO 端口
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/21 15:32
 */
@Service
public class PortServiceImpl implements PortService {

    @Autowired
    private PortDao portDao;

    @Override
    public List<Port> selectPort(Port port) {
        List<Port> list=portDao.selectPort(port);
        return list;
    }

    @Override
    public int updatePort(Port port) {
        if (portDao.updatePort(port)==1)   //数据库修改成功
            return 1;
        else
            return 0;
    }

    @Override
    public void insertSelective(Port port) {
        portDao.insertSelective(port);
    }


    @Override
    public String selectIdByName(String inport) {
        return portDao.selectIdByName(inport).toString();
    }
}
