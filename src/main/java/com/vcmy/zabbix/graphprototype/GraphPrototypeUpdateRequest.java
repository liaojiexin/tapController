package com.vcmy.zabbix.graphprototype;

import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.graph.GraphItem;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.List;


/**
 * Created by Suguru Yajima on 2014/06/02.
 */
public class GraphPrototypeUpdateRequest extends ZabbixApiRequest {
    private Params params = new Params();

    public GraphPrototypeUpdateRequest() {
        setMethod("graphprototype.update");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends GraphPrototypeObject {
        private List<GraphItem> gitems;

        public Params() {
            super();
        }

        public List<GraphItem> getGitems() {
            return gitems;
        }

        public void setGitems(List<GraphItem> gitems) {
            this.gitems = gitems;
        }

        public void addGraphItem(GraphItem gitem) {
            gitems = ZbxListUtils.add(gitems, gitem);

        }
    }
}
