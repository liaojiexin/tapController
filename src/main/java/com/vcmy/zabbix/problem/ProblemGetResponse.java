package com.vcmy.zabbix.problem;


import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suguru Yajima on 2014/05/02.
 */
public class ProblemGetResponse extends ZabbixApiResponse {

    private List<Result> result = new ArrayList<Result>();

    public ProblemGetResponse() {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result extends ProblemObject {

        private List<AcknowledgeObject> acknowledges;
        private List<TagObject> tags;
		
		public List<AcknowledgeObject> getAcknowledges() {
			return acknowledges;
		}
		public void setAcknowledges(List<AcknowledgeObject> acknowledges) {
			this.acknowledges = acknowledges;
		}
		public List<TagObject> getTags() {
			return tags;
		}
		public void setTags(List<TagObject> tags) {
			this.tags = tags;
		}
        
        
    }
}
