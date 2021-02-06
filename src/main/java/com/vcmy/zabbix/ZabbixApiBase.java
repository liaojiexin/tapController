package com.vcmy.zabbix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.config.ParamConfig;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName ZabbixApiBase
 * @Description Zabbix API调用接口
 * @Author xjq
 * @Date 2018/5/7 19:41
 * @Version 1.0
 **/
@Component
public class ZabbixApiBase {
    protected static Logger logger = LoggerFactory.getLogger(ZabbixApiBase.class);

    @Autowired
    private ParamConfig config;
    
    protected String user;
    protected String password;
    protected String apiUrl;
    

    protected static ZabbixApi zabbixApi;

    public ZabbixApiBase() {
    	
    	logger.info("ZabbixApiBase 初始化......");

    }

    protected void login(String user, String password) {
    	apiUrl = config.getApiUrl();
        logger.info("Zabbix登录：" + apiUrl);
        try {
            if(zabbixApi == null){
                zabbixApi = new ZabbixApi(apiUrl);
                zabbixApi.login(user, password);
            }
        } catch (ZabbixApiException e) {
            logger.error(e.getMessage());
        }
    }

    protected Gson getGson() {

        return new GsonBuilder().setPrettyPrinting().create();
    }

    //@PostConstruct https://blog.csdn.net/qq360694660/article/details/82877222
    @PostConstruct
    public void init() {
    	user = config.getUser();
        password = config.getPassword();
        login(user, password);
    }

    @PreDestroy
    @SneakyThrows(Exception.class)
    public void destroy() {
    	logger.info("Zabbix登出：" + apiUrl);
        zabbixApi.logout();
    }
}
