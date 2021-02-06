package com.vcmy.service.strategy;

import com.vcmy.entity.Port;

import java.util.List;

public interface PortService {
    List<Port> selectPort(Port port);

    int updatePort(Port port);

    void insertSelective(Port port);

    String selectIdByName(String inport);
}
