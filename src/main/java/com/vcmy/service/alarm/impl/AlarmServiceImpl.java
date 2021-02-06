package com.vcmy.service.alarm.impl;

import com.vcmy.dao.AlarmDao;
import com.vcmy.entity.Alarm;
import com.vcmy.service.alarm.AlarmService;
import com.vcmy.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: AlarmServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/28 13:57
 */
@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmDao alarmDao;

    @Override
    public Integer selectPendAlarm() {
        return alarmDao.selectPendAlarm();
    }

    @Override
    public Integer selectCountAlarm() {
        return alarmDao.selectCountAlarm();
    }

    @Override
    public Float selectUrgency() {
        return alarmDao.selectUrgency();
    }

    @Override
    public Float selectpImportant() {
        return alarmDao.selectpImportant();
    }

    @Override
    public Float selectMinor() {
        return alarmDao.selectMinor();
    }

    @Override
    public Float selectPrompt() {
        return alarmDao.selectPrompt();
    }

    @Override
    public List<Alarm> alarmAll(Alarm alarm, Long startDate, Long endDate) {
        Date START = null; //开始时间
        Date END = null;   //结束时间
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        if(startDate!=null)
            START= TimeUtils.stampToDate(startDate);
        if(endDate!=null)
            END=TimeUtils.stampToDate(endDate);
        List<Alarm> list=alarmDao.alarmAll(alarm,START,END);
        return list;
    }

    @Override
    public int deleteAlarm(Integer alarmId) {
        return alarmDao.deleteByPrimaryKey(alarmId);
    }

    @Override
    public void insertAlarm(Alarm alarm) {
        alarmDao.insertAlarm(alarm);
    }
}
