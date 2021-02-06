package com.vcmy.service.alarm;

import com.vcmy.entity.Alarm;

import java.util.List;

public interface AlarmService {
    Integer selectPendAlarm();

    Integer selectCountAlarm();

    Float selectUrgency();

    Float selectpImportant();

    Float selectMinor();

    Float selectPrompt();

    List<Alarm> alarmAll(Alarm alarm, Long startDate, Long endDate);

    int deleteAlarm(Integer alarmId);

    void insertAlarm(Alarm alarm);
}
