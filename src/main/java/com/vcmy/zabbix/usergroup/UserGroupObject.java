/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Suguru Yajima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vcmy.zabbix.usergroup;


import com.vcmy.zabbix.ZabbixApiParamter;

/**
 * Created by Suguru Yajima on 2014/05/13.
 */
public class UserGroupObject {

    private Integer usrgrpid;
    private String name;
    private Integer debug_mode = ZabbixApiParamter.DEBUG_MODE.DISABLE.value;
    private Integer gui_access = ZabbixApiParamter.GUI_ACCESS.SYS_DEFAULT_AUTH_METHOD.value;
    private Integer users_status = ZabbixApiParamter.USER_STATUS.ENABLE.value;

    public UserGroupObject() {
    }

    public Integer getUsrgrpid() {
        return usrgrpid;
    }

    public void setUsrgrpid(Integer usrgrpid) {
        this.usrgrpid = usrgrpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDebug_mode() {
        return debug_mode;
    }

    public void setDebug_mode(Integer debug_mode) {
        this.debug_mode = debug_mode;
    }

    public Integer getGui_access() {
        return gui_access;
    }

    public void setGui_access(Integer gui_access) {
        this.gui_access = gui_access;
    }

    public Integer getUsers_status() {
        return users_status;
    }

    public void setUsers_status(Integer users_status) {
        this.users_status = users_status;
    }

}
