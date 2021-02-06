package com.vcmy.zabbix.trend;

import com.vcmy.zabbix.GetRequestCommonParams;
import com.vcmy.zabbix.ZabbixApiRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by Suguru Yajima on 2014/06/03.
 */
public class TrendGetRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public TrendGetRequest() {
        setMethod("trend.get");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends GetRequestCommonParams {     
        private List<Integer> itemids;
        private Long time_from;
        private Long time_till;

        public Params() {
            super();
        }

        public Date getTimeFromDate() {
            if (time_from != null && time_from != 0) {
                return new Date(time_from);
            }
            return null;
        }

        public List<Integer> getItemids() {
            return itemids;
        }

        public void setItemids(List<Integer> itemids) {
            this.itemids = itemids;
        }

        public Long getTime_from() {
            return time_from;
        }

        public void setTime_from(Long time_from) {
            this.time_from = time_from;
        }

        public Long getTime_till() {
            return time_till;
        }

        public void setTime_till(Long time_till) {
            this.time_till = time_till;
        }

		public Date getTimeTillDate() {
            if (time_till != null && time_till != 0) {
                return new Date(time_till);
            }
            return null;
        }
    }
}
