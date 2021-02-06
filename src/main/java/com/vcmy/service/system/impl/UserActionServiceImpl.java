package com.vcmy.service.system.impl;

import com.vcmy.dao.UserActionDao;
import com.vcmy.entity.UserAction;
import com.vcmy.service.system.UserActionService;
import com.vcmy.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.calendar.LocalGregorianCalendar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserActionServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/15 15:27
 */
@Service
public class UserActionServiceImpl  implements UserActionService {

    @Autowired
    private UserActionDao userActionDao;

    @Override
    public List<UserAction> selectLog(UserAction userAction,Long startDate,Long endDate) {
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        if(startDate==null)
            userAction.setSTART(calendar.getTime()); //开始时间默认一个小时前的时间
        else
            userAction.setSTART(TimeUtils.stampToDate(startDate));
        if(endDate==null)
            userAction.setEND(new Date());     //结束时间默认为当前时间
        else
            userAction.setEND(TimeUtils.stampToDate(endDate));
        List<UserAction> list=userActionDao.selectLog(userAction);
        return list;
    }

    @Override
    public int insertLog(UserAction userAction) {
        return userActionDao.insertSelective(userAction);
    }
}
