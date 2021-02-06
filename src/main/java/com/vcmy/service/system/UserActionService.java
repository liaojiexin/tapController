package com.vcmy.service.system;

import com.vcmy.entity.UserAction;
import java.util.List;

public interface UserActionService {
    List<UserAction> selectLog(UserAction userAction,Long startDate,Long endDate);

    int insertLog(UserAction userAction);
}
