package com.vcmy.zabbix.itemprototype;

import com.vcmy.zabbix.ZabbixApiResponse;
import com.vcmy.zabbix.application.ApplicationObject;
import com.vcmy.zabbix.discoveryrule.DiscoveryRuleObject;
import com.vcmy.zabbix.graph.GraphObject;
import com.vcmy.zabbix.host.HostObject;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class ItemPrototypeGetResponse extends ZabbixApiResponse {

    private List<Result> result;

    public ItemPrototypeGetResponse() {
        super();
    }

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result extends ItemPrototypeObject {

        private List<HostObject> hosts;
        private List<ApplicationObject> applications;
        private List<GraphObject> graphs;
        private DiscoveryRuleObject discoveryRule;

        /**
         * Gets graphs.
         *
         * @return Value of graphs.
         */
        public List<GraphObject> getGraphs() {
            return graphs;
        }

        /**
         * Sets new graphs.
         *
         * @param graphs New value of graphs.
         */
        public void setGraphs(List<GraphObject> graphs) {
            this.graphs = graphs;
        }


        /**
         * Gets hosts.
         *
         * @return Value of hosts.
         */
        public List<HostObject> getHosts() {
            return hosts;
        }

        /**
         * Sets new hosts.
         *
         * @param hosts New value of hosts.
         */
        public void setHosts(List<HostObject> hosts) {
            this.hosts = hosts;
        }

        /**
         * Gets applications.
         *
         * @return Value of applications.
         */
        public List<ApplicationObject> getApplications() {
            return applications;
        }

        /**
         * Sets new applications.
         *
         * @param applications New value of applications.
         */
        public void setApplications(List<ApplicationObject> applications) {
            this.applications = applications;
        }

        /**
         * Gets discoveryRule.
         *
         * @return Value of discoveryRule.
         */
        public DiscoveryRuleObject getDiscoveryRule() {
            return discoveryRule;
        }

        /**
         * Sets new discoveryRule.
         *
         * @param discoveryRule New value of discoveryRule.
         */
        public void setDiscoveryRule(DiscoveryRuleObject discoveryRule) {
            this.discoveryRule = discoveryRule;
        }
    }
}
