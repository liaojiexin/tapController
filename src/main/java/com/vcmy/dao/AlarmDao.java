package com.vcmy.dao;

import com.vcmy.entity.Alarm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AlarmDao {
    int deleteByPrimaryKey(Integer alarmId);

    int insert(Alarm record);

    int insertSelective(Alarm record);

    Alarm selectByPrimaryKey(Integer alarmId);

    int updateByPrimaryKeySelective(Alarm record);

    int updateByPrimaryKey(Alarm record);

    Integer selectPendAlarm();

    Integer selectCountAlarm();

    Float selectUrgency();

    Float selectpImportant();

    Float selectMinor();

    Float selectPrompt();

    List<Alarm> alarmAll(@Param("alarm") Alarm alarm,@Param("START") Date START,@Param("END") Date END);

    void insertAlarm(Alarm alarm);
}