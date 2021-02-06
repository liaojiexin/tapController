package com.vcmy.dao;

import com.vcmy.entity.UserAction;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author liaojiexin
 * @Description 系统操作日志
 * @Date 2020/12/11 10:38
 * @Param
 * @return
 **/
public interface UserActionDao {
    int insert(UserAction record);

    int insertSelective(UserAction record);

    List<UserAction> selectLog(UserAction userAction);
}