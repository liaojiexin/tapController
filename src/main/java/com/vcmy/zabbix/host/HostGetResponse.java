package com.vcmy.zabbix.host;


import com.vcmy.zabbix.ZabbixApiResponse;
import com.vcmy.zabbix.application.ApplicationObject;
import com.vcmy.zabbix.discoveryrule.DiscoveryRuleObject;
import com.vcmy.zabbix.graph.GraphObject;
import com.vcmy.zabbix.hostgroup.HostgroupObject;
import com.vcmy.zabbix.hostinteface.HostInterfaceObject;
import com.vcmy.zabbix.item.ItemObject;
import com.vcmy.zabbix.usermacro.Macro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suguru Yajima on 2014/05/02.
 */
public class HostGetResponse extends ZabbixApiResponse {

    private List<Result> result = new ArrayList<Result>();

    public HostGetResponse() {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result extends HostObject {

        private List<HostgroupObject> groups;
        // TODO: implement a variable
        //private List<Template> parentTemplates;
        private List<ItemObject> items;
// TODO: implement a variable
        //private List<Discoveriy> discoveries;

        private List<GraphObject> graphs;
        // TODO: implement a variable
        //private List<HttpTestObject> httpTests;
        private List<ApplicationObject> applications;

        private List<Macro> macros;
        // TODO: implement a variable
        //private List<Inventory> inventory;
        private List<HostInterfaceObject> interfaces;
        // TODO: implement a variable
        //private List<screens> screens;
        private List<DiscoveryRuleObject> discoveryRule;
        private List<HostObject> hostDiscovery;

        public List<HostObject> getHostDiscovery() {
            return hostDiscovery;
        }

        public void setHostDiscovery(List<HostObject> hostDiscovery) {
            this.hostDiscovery = hostDiscovery;
        }

        public List<DiscoveryRuleObject> getDiscoveryRule() {
            return discoveryRule;
        }

        public void setDiscoveryRule(List<DiscoveryRuleObject> discoveryRule) {
            this.discoveryRule = discoveryRule;
        }

        public List<HostInterfaceObject> getInterfaces() {
            return interfaces;
        }

        public void setInterfaces(List<HostInterfaceObject> interfaces) {
            this.interfaces = interfaces;
        }

        public List<Macro> getMacros() {
            return macros;
        }

        public void setMacros(List<Macro> macros) {
            this.macros = macros;
        }

        public List<ApplicationObject> getApplications() {
            return applications;
        }

        public void setApplications(List<ApplicationObject> applications) {
            this.applications = applications;
        }

        public List<GraphObject> getGraphs() {
            return graphs;
        }

        public void setGraphs(List<GraphObject> graphs) {
            this.graphs = graphs;
        }

        public List<ItemObject> getItems() {
            return items;
        }

        public void setItems(List<ItemObject> items) {
            this.items = items;
        }

        public List<HostgroupObject> getGroups() {
            return groups;
        }

        public void setGroups(List<HostgroupObject> groups) {
            this.groups = groups;
        }
    }
}
