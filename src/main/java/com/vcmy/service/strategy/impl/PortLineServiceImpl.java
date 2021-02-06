package com.vcmy.service.strategy.impl;

import com.vcmy.dao.PortDao;
import com.vcmy.dao.PortLineDao;
import com.vcmy.entity.PortLine;
import com.vcmy.service.strategy.PortLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: PortLineServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2021/1/6 10:47
 */
@Service
public class PortLineServiceImpl implements PortLineService {

    @Autowired
    private PortLineDao portLineDao;

    @Autowired
    private PortDao portDao;

    @Override
    public void updatePortLine(PortLine portLine) {
        portLineDao.updatePortLine(portLine);
    }

    @Override
    public List<PortLine> selectPortLine(PortLine portLine) {
        List<PortLine> lists=portLineDao.selectPortLine(portLine);
        for (PortLine list:lists){
            list.setInPort(portDao.selectPortById(list.getInPortId()));
            list.setOutPort(portDao.selectPortById(list.getOutPortId()));
        }
        return lists;
    }
}
