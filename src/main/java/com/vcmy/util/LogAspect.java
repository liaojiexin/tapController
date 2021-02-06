package com.vcmy.util;

import com.google.gson.Gson;
import com.vcmy.annotation.Log;
import com.vcmy.entity.User;
import com.vcmy.entity.UserAction;
import com.vcmy.service.system.UserActionService;
import com.vcmy.service.system.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LogAspect
 * @Description: TODO 日志切面 参考：https://www.cnblogs.com/wm-dv/p/11735828.html
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/19 21:40
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private UserService userService;

    /**
     58      * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     59      */
      @Pointcut("@annotation(com.vcmy.annotation.Log)")
      public void LogPoinCut() {}

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "LogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        UserAction userAction = new UserAction();
        try {
            User user=userService.selectByUserId(WebContextUtils.getId());
            userAction.setName(user.getName()); // 用户名

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            Log log = method.getAnnotation(Log.class);
            if (log != null) {
                String module = log.module();
                String action = log.action();
                userAction.setModule(module); // 操作模块
                userAction.setAction(action); // 操作类型
            }

            String result=JsonUtils.obj2json(keys);  //返回结果转化为json格式
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<>();
            map=gson.fromJson(result,map.getClass());
            if(map.get("code").equals("0")){
                userAction.setResult("成功");     //原因
                userAction.setReason("无");  //结果
            }
            else{
                userAction.setResult("失败");     //结果
                userAction.setReason(map.get("msg").toString());       //原因
            }
//            operlog.setOperRespParam(JSON.toJSONString(keys)); // 返回结果
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userAction.setTime(new Date()); //时间
            userAction.setIP(IpUtils.getIpAddr(request)); // 请求IP
            userAction.setLog("用户名为"+userAction.getName()+"(账号:"+user.getUserName()+",IP地址:"+userAction.getIP()+")的操作者在" +
                    simpleDateFormat.format(userAction.getTime())+"时对"+userAction.getModule()+"模块进行"+userAction.getAction()+"操作");   //日志
            userActionService.insertLog(userAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
