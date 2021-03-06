package com.vcmy.zabbix.maintenance;



import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class MaintenanceDeleteResponse extends ZabbixApiResponse {

    private Result result;

    public MaintenanceDeleteResponse() {
        super();
    }

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        private List<Integer> maintenanceids;

        /**
         * Gets maintenanceids.
         *
         * @return Value of maintenanceids.
         */
        public List<Integer> getMaintenanceids() {
            return maintenanceids;
        }

        /**
         * Sets new maintenanceids.
         *
         * @param maintenanceids New value of maintenanceids.
         */
        public void setMaintenanceids(List<Integer> maintenanceids) {
            this.maintenanceids = maintenanceids;
        }
    }
}
