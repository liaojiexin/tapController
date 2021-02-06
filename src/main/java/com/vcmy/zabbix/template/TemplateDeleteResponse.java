package com.vcmy.zabbix.template;


import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class TemplateDeleteResponse extends ZabbixApiResponse {
    private Result result;

    public TemplateDeleteResponse() {
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

        private List<Integer> templateids;

        /**
         * Gets templateids.
         *
         * @return Value of templateids.
         */
        public List<Integer> getTemplateids() {
            return templateids;
        }

        /**
         * Sets new templateids.
         *
         * @param templateids New value of templateids.
         */
        public void setTemplateids(List<Integer> templateids) {
            this.templateids = templateids;
        }
    }
}
