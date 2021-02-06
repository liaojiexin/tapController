package com.vcmy.interceptor.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcmy.entity.Message;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:31
 * @Description: 页面信息
 **/
public class GalenWebMvcWrite {
    /**
     * @Author: Galen
     * @Description: 输出信息到页面
     * @Date: 2019/3/28-9:35
     * @Param: [response, respBean]
     * @return: void
     **/
    public void writeToWeb(HttpServletResponse response, Message message) throws IOException {
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(message));
        out.flush();
        out.close();
    }
}
