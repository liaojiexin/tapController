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

package com.vcmy.zabbix.discoveryrule;

import lombok.Data;

/**
 * Created by Suguru Yajima on 2014/05/26.
 */
@Data
public class DCheck {

    //0 - (default) SSH; 1 - LDAP; 2 - SMTP; 3 - FTP; 4 - HTTP; 5 - POP; 6 - NNTP;
    //7 - IMAP; 8 - TCP; 9 - Zabbix agent; 10 - SNMPv1 agent; 11 - SNMPv2 agent;
    //12 - ICMP ping; 13 - SNMPv3 agent; 14 - HTTPS; 15 - Telnet.
    private Integer type;
    private String key_;
    private Integer ports;
    //0 - (default) do not use this check as a uniqueness criteria;
    //1 - use this check as a uniqueness criteria.
    private Integer uniq;
    private String snmp_community;
    private String snmpv3_authpassphrase;
    //0 - (default) MD5; 1 - SHA .
    private Integer snmpv3_authprotocol;
    private String snmpv3_contextname;
    private String snmpv3_privpassphrase;
    //0 - (default) DES; 1 - AES.
    private Integer snmpv3_privprotocol;
    //0 - noAuthNoPriv; 1 - authNoPriv; 2 - authPriv.
    private Integer snmpv3_securitylevel;
    private String snmpv3_securityname;

    public DCheck() {
    }

}
