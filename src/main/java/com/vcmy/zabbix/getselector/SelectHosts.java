package com.vcmy.zabbix.getselector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.host.HostGetRequest;

/**
 * @ClassName SelectInterfaces
 * @Description 接口选择类
 * @Author xjq
 * @Date 2018/5/8 13:50
 * @Version 1.0
 **/
public class SelectHosts extends SelectorBase<SelectHosts.SELECT_HOSTS>{

    public static enum  SELECT_HOSTS{
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
        private SELECT_HOSTS(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        SelectHosts sn1 = new SelectHosts();
        SelectHosts sn2 = new SelectHosts();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        sn1.setSelectString("extend");
        //System.out.println(gson.toJson(sn1));
        //ArrayList<SelectHosts.SELECT_ACKNOWLEDGES> si = new ArrayList<>();
        //si.add(SelectHosts.SELECT_ACKNOWLEDGES.CLOCK);
       // si.add(SelectHosts.SELECT_ACKNOWLEDGES.MESSAGE);
       // sn2.setSelector(si);
        System.out.println(gson.toJson(sn2));
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(SelectHosts.class,new SelectorBase());
        Gson g1 = gb.create();
        //System.out.println(g1.toJson(sn1));
        //System.out.println(g1.toJson(sn2));
        HostGetRequest request = new HostGetRequest();
        HostGetRequest.Params params = request.getParams();
      //  params.setSelectInterfaces(sn2);
        //params.setOutput("extend");
        System.out.println(g1.toJson(params));
    }
}
