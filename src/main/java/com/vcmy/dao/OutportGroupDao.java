package com.vcmy.dao;

import com.vcmy.entity.OutPortGroup;
import org.apache.ibatis.annotations.Param;

public interface OutportGroupDao {
    int deleteByPrimaryKey(Integer outportgroupid);

    int insert(OutPortGroup record);

    int insertSelective(@Param("outPortGroup") OutPortGroup outPortGroup,@Param("strategyId") Integer strategyId);

    OutPortGroup selectByPrimaryKey(OutPortGroup outportgroup);

    int updateByPrimaryKeySelective(OutPortGroup record);

    int updateByPrimaryKey(OutPortGroup record);
}