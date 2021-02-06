package com.vcmy.dao;

import com.vcmy.entity.Office;

import java.util.List;

public interface OfficeDao {
    int deleteByPrimaryKey(Integer officeid);

    int insert(Office record);

    int insertSelective(Office record);

    Office selectByPrimaryKey(Integer officeid);

    int updateByPrimaryKeySelective(Office record);

    int updateByPrimaryKey(Office record);

    List<Office> selectOffice(Office office);
}