package com.vcmy.service.strategy;

import com.vcmy.entity.PortLine;

import java.util.List;

public interface PortLineService {

    void updatePortLine(PortLine portLine);

    List<PortLine> selectPortLine(PortLine portLine);
}
