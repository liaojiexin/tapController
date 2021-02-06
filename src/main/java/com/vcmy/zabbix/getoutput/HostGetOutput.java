package com.vcmy.zabbix.getoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.getselector.SelectInterfaces;
import com.vcmy.zabbix.getselector.SelectorBase;
import com.vcmy.zabbix.host.HostGetRequest;

import java.util.ArrayList;

/**
 * @ClassName HostGetOutput
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/8 14:16
 * @Version 1.0
 **/
public class HostGetOutput extends OutputBase<HostGetOutput.HOST_OUTPUT>{

    public static enum  HOST_OUTPUT{
        @SerializedName("hostid")
        HOSTID("hostid"),
        @SerializedName("proxy_hostid")
        PROXY_HOSTID("proxy_hostid"),
        @SerializedName("host")
        HOST("host"),
        @SerializedName("status")
        STATUS("status"),
        @SerializedName("disable_until")
        DISABLE_UNTIL("disable_until"),
        @SerializedName("error")
        ERROR("error"),
        @SerializedName("available")
        AVAILABLE("available"),
        @SerializedName("errors_from")
        ERRORS_FROM("errors_from"),
        @SerializedName("lastaccess")
        LASTACCESS("lastaccess"),
        @SerializedName("ipmi_authtype")
        IPMI_AUTHTYPE("ipmi_authtype"),
        @SerializedName("ipmi_privilege")
        IPMI_PRIVILEGE("ipmi_privilege"),
        @SerializedName("ipmi_username")
        IPMI_USERNAME("ipmi_username"),
        @SerializedName("ipmi_password")
        IPMI_PASSWORD("ipmi_password"),
        @SerializedName("ipmi_disable_until")
        IPMI_DISABLE_UNTIL("ipmi_disable_until"),
        @SerializedName("ipmi_available")
        IPMI_AVAILABLE("ipmi_available"),
        @SerializedName("snmp_disable_until")
        SNMP_DISABLE_UNTIL("snmp_disable_until"),
        @SerializedName("snmp_available")
        SNMP_AVAILABLE("snmp_available"),
        @SerializedName("maintenanceid")
        MAINTENANCEID("maintenanceid"),
        @SerializedName("maintenance_status")
        MAINTENANCE_STATUS("maintenance_status"),
        @SerializedName("maintenance_type")
        MAINTENANCE_TYPE("maintenance_type"),
        @SerializedName("maintenance_from")
        MAINTENANCE_FROM("maintenance_from"),
        @SerializedName("ipmi_errors_from")
        IPMI_ERRORS_FROM("ipmi_errors_from"),
        @SerializedName("snmp_errors_from")
        SNMP_ERRORS_FROM("snmp_errors_from"),
        @SerializedName("ipmi_error")
        IPMI_ERROR("ipmi_error"),
        @SerializedName("snmp_error")
        SNMP_ERROR("snmp_error"),
        @SerializedName("jmx_disable_until")
        JMX_DISABLE_UNTIL("jmx_disable_until"),
        @SerializedName("jmx_available")
        JMX_AVAILABLE("jmx_available"),
        @SerializedName("jmx_errors_from")
        JMX_ERRORS_FROM("jmx_errors_from"),
        @SerializedName("jmx_error")
        JMX_ERROR("jmx_error"),
        @SerializedName("name")
        NAME("name"),
        @SerializedName("flags")
        FLAGS("flags"),
        @SerializedName("templateid")
        TEMPLATEID("templateid"),
        @SerializedName("description")
        DESCRIPTION("description"),
        @SerializedName("tls_connect")
        TLS_CONNECT("tls_connect"),
        @SerializedName("tls_accept")
        TLS_ACCEPT("tls_accept"),
        @SerializedName("tls_issuer")
        TLS_ISSUER("tls_issuer"),
        @SerializedName("tls_subject")
        TLS_SUBJECT("tls_subject"),
        @SerializedName("tls_psk_identity")
        TLS_PSK_IDENTITY("tls_psk_identity"),
        @SerializedName("tls_psk")
        TLS_PSK("tls_psk");

        private String value;
        private HOST_OUTPUT(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        SelectInterfaces sn2 = new SelectInterfaces();
        ArrayList<SelectInterfaces.SELECT_INTERFACE> si = new ArrayList<>();
        si.add(SelectInterfaces.SELECT_INTERFACE.HOSTID);
        si.add(SelectInterfaces.SELECT_INTERFACE.PORT);
        sn2.setSelector(si);
        HostGetOutput output = new HostGetOutput();
        ArrayList<HOST_OUTPUT> hostout = new ArrayList<>();
        hostout.add(HOST_OUTPUT.HOSTID);
        hostout.add(HOST_OUTPUT.HOST);
        output.setOutputFields(hostout);
        HostGetRequest request = new HostGetRequest();
        HostGetRequest.Params params = request.getParams();
        params.setSelectInterfaces(sn2);
        params.setOutput(output);
        Gson g1 = new GsonBuilder()
                .registerTypeAdapter(SelectInterfaces.class,new SelectorBase())
                .registerTypeAdapter(HostGetOutput.class,new OutputBase())
                .create();
        System.out.println(g1.toJson(params));
    }
}
